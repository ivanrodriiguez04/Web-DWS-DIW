package vistaProyectoFinal.DWS_DIW.servicios;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import vistaProyectoFinal.DWS_DIW.configuracion.SesionLogger;
import vistaProyectoFinal.DWS_DIW.dtos.CuentaDto;
import vistaProyectoFinal.DWS_DIW.dtos.SucursalDto;

import java.math.BigInteger;
import java.util.*;

@Service
public class CuentaServicio {

    private static final SesionLogger logger = new SesionLogger(CuentaServicio.class);
    private final RestTemplate restTemplate = new RestTemplate();
    private final String API_URL = "http://16.170.127.156:8081/apiProyectoFinal/api/";

    public List<CuentaDto> obtenerCuentasPorEmail(String emailUsuario) {
        try {
            ResponseEntity<CuentaDto[]> response = restTemplate.getForEntity(API_URL + "cuentas/usuario/email/" + emailUsuario, CuentaDto[].class);
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
            cuentaDto.setDineroCuenta(0.0);
            String email = cuentaDto.getEmailUsuario();

            // ✅ USAR EL ENDPOINT CORRECTO DEFINIDO EN EL BACKEND
            ResponseEntity<Map> userResponse = restTemplate.getForEntity(API_URL + "cuentas/usuario/datos/email/" + email, Map.class);
            if (!userResponse.getStatusCode().is2xxSuccessful() || userResponse.getBody() == null) {
                logger.warn("No se pudo obtener el usuario con email: " + email);
                return false;
            }

            Map usuario = userResponse.getBody();
            String ciudad = (String) usuario.get("ciudadUsuario");

            ResponseEntity<SucursalDto[]> sucursalResponse = restTemplate.getForEntity(API_URL + "sucursales", SucursalDto[].class);
            if (!sucursalResponse.getStatusCode().is2xxSuccessful() || sucursalResponse.getBody() == null) {
                logger.warn("No se pudieron obtener sucursales");
                return false;
            }

            SucursalDto sucursal = Arrays.stream(sucursalResponse.getBody())
                .filter(s -> s.getCiudadSucursal().equalsIgnoreCase(ciudad))
                .findFirst()
                .orElse(null);

            if (sucursal == null) {
                logger.warn("No se encontró sucursal para ciudad: " + ciudad);
                return false;
            }

            for (int i = 0; i < 5; i++) {
                String iban = generarIbanReal(sucursal.getCodigoBanco(), sucursal.getCodigoSucursal());
                cuentaDto.setIbanCuenta(iban);

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
                ResponseEntity<String> response = restTemplate.postForEntity(API_URL + "cuentas/crear", requestEntity, String.class);

                if (response.getStatusCode() == HttpStatus.CREATED) {
                    logger.info("Cuenta creada exitosamente con IBAN: " + iban);
                    return true;
                } else {
                    logger.warn("IBAN en uso (" + iban + "), intento #" + (i + 1) + " fallido. Reintentando...");
                }
            }

            logger.error("No se pudo crear la cuenta después de varios intentos.");
            return false;

        } catch (Exception e) {
            logger.error("Error al crear cuenta: " + e.getMessage());
            return false;
        }
    }

    private String generarIbanReal(String codigoBanco, String codigoSucursal) {
        String numeroCuenta = String.format("%010d", new Random().nextInt(1_000_000_000));
        String dc = calcularDC(codigoBanco, codigoSucursal, numeroCuenta);
        String ccc = codigoBanco + codigoSucursal + dc + numeroCuenta;
        return calcularDigitosControlIBAN("ES00" + ccc);
    }

    private String calcularDC(String banco, String sucursal, String cuenta) {
        int[] pesosEntidadSucursal = {4, 8, 5, 10, 9, 7, 3, 6};
        int[] pesosCuenta = {1, 2, 4, 8, 5, 10, 9, 7, 3, 6};

        String parte1 = banco + sucursal;
        int suma1 = 0;
        for (int i = 0; i < 8; i++) {
            suma1 += Character.getNumericValue(parte1.charAt(i)) * pesosEntidadSucursal[i];
        }
        int dc1 = 11 - (suma1 % 11);
        if (dc1 == 10) dc1 = 1;
        else if (dc1 == 11) dc1 = 0;

        int suma2 = 0;
        for (int i = 0; i < 10; i++) {
            suma2 += Character.getNumericValue(cuenta.charAt(i)) * pesosCuenta[i];
        }
        int dc2 = 11 - (suma2 % 11);
        if (dc2 == 10) dc2 = 1;
        else if (dc2 == 11) dc2 = 0;

        return String.valueOf(dc1) + String.valueOf(dc2);
    }

    private String calcularDigitosControlIBAN(String ibanSinDC) {
        String pais = "ES";
        String ccc = ibanSinDC.substring(4);
        String rearranged = ccc + "142800";
        BigInteger num = new BigInteger(rearranged);
        int mod97 = 98 - num.mod(BigInteger.valueOf(97)).intValue();
        String digitosControl = (mod97 < 10) ? "0" + mod97 : String.valueOf(mod97);
        return pais + digitosControl + ccc;
    }

    public boolean eliminarCuenta(long idCuenta) {
        try {
            ResponseEntity<Void> response = restTemplate.exchange(API_URL + "cuentas/eliminar/" + idCuenta, HttpMethod.DELETE, null, Void.class);
            return response.getStatusCode() == HttpStatus.OK;
        } catch (Exception e) {
            logger.error("Error eliminando cuenta con ID " + idCuenta + ": " + e.getMessage());
            return false;
        }
    }
}
