package vistaProyectoFinal.DWS_DIW.servicios;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import vistaProyectoFinal.DWS_DIW.dtos.CuentaDto;

import java.util.Arrays;
import java.util.List;

@Service
public class CuentaServicio {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String API_URL = "http://localhost:8081/api/cuentas/";

    public List<CuentaDto> obtenerCuentasPorUsuario(long idUsuario) {
        String url = API_URL + "usuario/" + idUsuario;
        ResponseEntity<CuentaDto[]> response = restTemplate.getForEntity(url, CuentaDto[].class);
        return response.getStatusCode() == HttpStatus.OK && response.getBody() != null ? Arrays.asList(response.getBody()) : List.of();
    }

    public boolean crearCuenta(CuentaDto cuentaDto) {
        String url = API_URL + "crear";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CuentaDto> requestEntity = new HttpEntity<>(cuentaDto, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
        return response.getStatusCode() == HttpStatus.CREATED;
    }

    public boolean eliminarCuenta(long idCuenta) {
        String url = API_URL + "eliminar/" + idCuenta;
        ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);
        return response.getStatusCode() == HttpStatus.OK;
    }
}