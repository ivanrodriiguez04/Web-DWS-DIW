package vistaProyectoFinal.DWS_DIW.servicios;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import vistaProyectoFinal.DWS_DIW.dtos.CuentaDto;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class CuentaServicio {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String API_URL = "http://localhost:8081/api/cuentas/";

    public List<CuentaDto> obtenerCuentasPorEmail(String emailUsuario) {
        try {
            ResponseEntity<CuentaDto[]> response = restTemplate.getForEntity(API_URL + "usuario/email/" + emailUsuario, CuentaDto[].class);
            return (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) 
                ? Arrays.asList(response.getBody()) 
                : List.of();
        } catch (Exception e) {
            System.err.println("❌ Error obteniendo cuentas: " + e.getMessage());
            return List.of();
        }
    }

    public boolean crearCuenta(CuentaDto cuentaDto) {
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

        try {
            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(API_URL + "crear", requestEntity, String.class);

            if (response.getStatusCode() == HttpStatus.CREATED) {
                return true;
            } else {
                System.err.println("⚠️ Error al crear cuenta: " + response.getBody());
                return false;
            }
        } catch (Exception e) {
            System.err.println("❌ Excepción en crearCuenta: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarCuenta(long idCuenta) {
        try {
            ResponseEntity<Void> response = restTemplate.exchange(API_URL + "eliminar/" + idCuenta, HttpMethod.DELETE, null, Void.class);
            return response.getStatusCode() == HttpStatus.OK;
        } catch (Exception e) {
            System.err.println("❌ Error eliminando cuenta: " + e.getMessage());
            return false;
        }
    }

    private String generarIban() {
        Random random = new Random();
        return "ES" + (random.nextInt(900000) + 100000) + (random.nextInt(900000000) + 100000000);
    }
}
