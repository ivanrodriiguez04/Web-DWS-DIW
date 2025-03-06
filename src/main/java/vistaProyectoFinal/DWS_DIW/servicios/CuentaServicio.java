package vistaProyectoFinal.DWS_DIW.servicios;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import vistaProyectoFinal.DWS_DIW.configuracion.SesionLogger;
import vistaProyectoFinal.DWS_DIW.dtos.CuentaDto;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class CuentaServicio {

    private static final SesionLogger logger = new SesionLogger(CuentaServicio.class);
    private final RestTemplate restTemplate = new RestTemplate();
    private final String API_URL = "http://localhost:8081/api/cuentas/";

    public List<CuentaDto> obtenerCuentasPorEmail(String emailUsuario) {
        try {
            ResponseEntity<CuentaDto[]> response = restTemplate.getForEntity(API_URL + "usuario/email/" + emailUsuario, CuentaDto[].class);
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                logger.info("Cuentas obtenidas correctamente para el usuario: " + emailUsuario);
                return Arrays.asList(response.getBody());
            }
        } catch (Exception e) {
            logger.error("Error obteniendo cuentas para el usuario " + emailUsuario + ": " + e.getMessage());
        }
        return List.of();
    }

    public boolean crearCuenta(CuentaDto cuentaDto) {
        try {
            // 🔹 Se asignan aquí los valores antes de enviarlo a la API
            cuentaDto.setDineroCuenta(0.0);
            cuentaDto.setIbanCuenta(generarIban());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> requestBody = Map.of(
                "emailUsuario", cuentaDto.getEmailUsuario(),
                "nombreCuenta", cuentaDto.getNombreCuenta(),
                "tipoCuenta", cuentaDto.getTipoCuenta(),
                "ibanCuenta", cuentaDto.getIbanCuenta(),
                "dineroCuenta", cuentaDto.getDineroCuenta()
            );

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(API_URL + "crear", requestEntity, String.class);

            if (response.getStatusCode() == HttpStatus.CREATED) {
                logger.info("Cuenta creada exitosamente para el usuario: " + cuentaDto.getEmailUsuario());
                return true;
            } else {
                logger.warn("Error al crear cuenta para el usuario: " + cuentaDto.getEmailUsuario() + " - Respuesta: " + response.getBody());
                return false;
            }
        } catch (Exception e) {
            logger.error("Excepción al crear cuenta para el usuario " + cuentaDto.getEmailUsuario() + ": " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarCuenta(long idCuenta) {
        try {
            ResponseEntity<Void> response = restTemplate.exchange(API_URL + "eliminar/" + idCuenta, HttpMethod.DELETE, null, Void.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                logger.info("Cuenta eliminada correctamente. ID: " + idCuenta);
                return true;
            } else {
                logger.warn("No se pudo eliminar la cuenta. ID: " + idCuenta);
                return false;
            }
        } catch (Exception e) {
            logger.error("Error eliminando cuenta con ID " + idCuenta + ": " + e.getMessage());
            return false;
        }
    }

    private String generarIban() {
        Random random = new Random();
        String iban = "ES" + (random.nextInt(900000) + 100000) + (random.nextInt(900000000) + 100000000);
        logger.info("IBAN generado: " + iban);
        return iban;
    }
}
