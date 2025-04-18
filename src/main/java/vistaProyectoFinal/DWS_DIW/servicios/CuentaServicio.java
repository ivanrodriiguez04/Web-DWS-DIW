package vistaProyectoFinal.DWS_DIW.servicios;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import vistaProyectoFinal.DWS_DIW.configuracion.SesionLogger;
import vistaProyectoFinal.DWS_DIW.dtos.CuentaDto;
import vistaProyectoFinal.DWS_DIW.dtos.SucursalDto;

import java.math.BigInteger;
import java.util.*;

/**
 * Servicio que gestiona la lógica relacionada con cuentas bancarias,
 * conectándose a la API REST del backend para obtener, crear o eliminar cuentas.
 * También genera IBANs válidos conforme al formato bancario español.
 * 
 * Funciona como intermediario entre los controladores de la parte web
 * y la API que almacena la lógica de negocio y acceso a base de datos.
 * 
 * Puerto API: 8081
 * Puerto Web: 8080
 * 
 * @author irodhan
 */
@Service
public class CuentaServicio {

    private static final SesionLogger logger = new SesionLogger(CuentaServicio.class);
    private final RestTemplate restTemplate = new RestTemplate();
    private final String API_URL = "http://localhost:8081/api/cuentas/";

    /**
     * Obtiene todas las cuentas asociadas a un usuario usando su email.
     *
     * @param emailUsuario Email del usuario autenticado.
     * @return Lista de cuentas o lista vacía si ocurre un error.
     */
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

    /**
     * Crea una nueva cuenta bancaria para el usuario.
     * Este proceso genera un IBAN válido en formato español
     * usando los datos de la sucursal según la ciudad del usuario.
     * 
     * Si el IBAN ya está en uso, lo reintenta hasta 5 veces.
     *
     * @param cuentaDto Objeto con los datos base de la cuenta.
     * @return true si se crea correctamente; false si hay error o IBAN duplicado.
     */
    public boolean crearCuenta(CuentaDto cuentaDto) {
        try {
            cuentaDto.setDineroCuenta(0.0); // Inicializa cuenta sin saldo
            String email = cuentaDto.getEmailUsuario();

            // 1. Obtener datos del usuario (como ciudad) desde la API
            ResponseEntity<Object[]> userResponse = restTemplate.getForEntity(API_URL + "usuario/email/" + email, Object[].class);
            if (!userResponse.getStatusCode().is2xxSuccessful() || userResponse.getBody() == null || userResponse.getBody().length == 0) {
                logger.warn("No se pudo obtener datos del usuario con email: " + email);
                return false;
            }

            // Extraer ciudad desde la primera cuenta (se asume que todas tienen el usuario anidado)
            Map cuenta = (Map) userResponse.getBody()[0];
            Map usuario = (Map) cuenta.get("usuario");
            String ciudad = (String) usuario.get("ciudadUsuario");

            // 2. Obtener lista de sucursales desde la API
            ResponseEntity<SucursalDto[]> sucursalResponse = restTemplate.getForEntity("http://localhost:8081/api/sucursales", SucursalDto[].class);
            if (!sucursalResponse.getStatusCode().is2xxSuccessful() || sucursalResponse.getBody() == null) {
                logger.warn("No se pudieron obtener sucursales");
                return false;
            }

            // Buscar la sucursal que coincida con la ciudad del usuario
            SucursalDto sucursal = Arrays.stream(sucursalResponse.getBody())
                .filter(s -> s.getCiudadSucursal().equalsIgnoreCase(ciudad))
                .findFirst()
                .orElse(null);

            if (sucursal == null) {
                logger.warn("No se encontró sucursal para ciudad: " + ciudad);
                return false;
            }

            // 3. Generar un IBAN válido y reintentar hasta 5 veces si ya está en uso
            for (int i = 0; i < 5; i++) {
                String iban = generarIbanReal(sucursal.getCodigoBanco(), sucursal.getCodigoSucursal());
                cuentaDto.setIbanCuenta(iban);

                // Preparar solicitud a la API
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

    /**
     * Genera un IBAN español válido siguiendo el estándar de 24 dígitos:
     * ESkk BBBB SSSS DD CCCCCCCCCC
     * 
     * @param codigoBanco Código de 4 cifras del banco.
     * @param codigoSucursal Código de 4 cifras de la sucursal.
     * @return IBAN generado con dígitos de control correctos.
     */
    private String generarIbanReal(String codigoBanco, String codigoSucursal) {
        // Generar número de cuenta aleatorio (10 dígitos)
        String numeroCuenta = String.format("%010d", new Random().nextInt(1_000_000_000));
        // Calcular los dos dígitos de control nacionales (DC)
        String dc = calcularDC(codigoBanco, codigoSucursal, numeroCuenta);
        // Construir CCC (código cuenta cliente completo)
        String ccc = codigoBanco + codigoSucursal + dc + numeroCuenta;
        // Calcular los dígitos de control del IBAN (kk)
        return calcularDigitosControlIBAN("ES00" + ccc);
    }

    /**
     * Calcula los dos dígitos de control nacionales (DC) para una cuenta española.
     *
     * @param banco Código del banco (4 dígitos).
     * @param sucursal Código de la sucursal (4 dígitos).
     * @param cuenta Número de cuenta (10 dígitos).
     * @return Dos dígitos de control como String.
     */
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

    /**
     * Calcula los 2 dígitos de control del IBAN (kk) usando el algoritmo MOD 97.
     *
     * @param ibanSinDC IBAN con '00' como dígitos de control (ej. ES00 + CCC).
     * @return IBAN con dígitos de control correctos.
     */
    private String calcularDigitosControlIBAN(String ibanSinDC) {
        String pais = "ES";
        String ccc = ibanSinDC.substring(4);
        String rearranged = ccc + "142800"; // E=14, S=28, 00 como placeholder

        BigInteger num = new BigInteger(rearranged);
        int mod97 = 98 - num.mod(BigInteger.valueOf(97)).intValue();

        String digitosControl = (mod97 < 10) ? "0" + mod97 : String.valueOf(mod97);
        return pais + digitosControl + ccc;
    }

    /**
     * Elimina una cuenta bancaria llamando al endpoint de la API.
     *
     * @param idCuenta ID único de la cuenta a eliminar.
     * @return true si se eliminó correctamente; false si no se encontró o hubo error.
     */
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
}
