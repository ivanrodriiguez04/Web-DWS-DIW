package vistaProyectoFinal.DWS_DIW.servicios;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import vistaProyectoFinal.DWS_DIW.dtos.CuentaDto;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class CuentaServicio {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String API_URL = "http://localhost:8081/api/cuentas/";

    /**
     * Obtiene todas las cuentas de un usuario desde la API.
     */
    public List<CuentaDto> obtenerCuentasPorUsuario(long idUsuario) {
        ResponseEntity<CuentaDto[]> response = restTemplate.getForEntity(API_URL + "usuario/" + idUsuario, CuentaDto[].class);
        return response.getStatusCode() == HttpStatus.OK && response.getBody() != null ? Arrays.asList(response.getBody()) : List.of();
    }

    /**
     * Crea una cuenta nueva en la API.
     */
    public boolean crearCuenta(CuentaDto cuentaDto) {
        cuentaDto.setDineroCuenta(0.0); // El dinero inicial siempre será 0
        cuentaDto.setIbanCuenta(generarIban()); // Generar IBAN automáticamente

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CuentaDto> requestEntity = new HttpEntity<>(cuentaDto, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(API_URL + "crear", requestEntity, String.class);
        
        return response.getStatusCode() == HttpStatus.CREATED;
    }

    /**
     * Elimina una cuenta en la API.
     */
    public boolean eliminarCuenta(long idCuenta) {
        ResponseEntity<Void> response = restTemplate.exchange(API_URL + "eliminar/" + idCuenta, HttpMethod.DELETE, null, Void.class);
        return response.getStatusCode() == HttpStatus.OK;
    }

    /**
     * Genera un IBAN aleatorio.
     */
    private String generarIban() {
        return "ES" + (long) (Math.random() * 1_000_000_000_000_000L) + UUID.randomUUID().toString().substring(0, 4);
    }
}
