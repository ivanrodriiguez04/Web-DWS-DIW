package vistaProyectoFinal.DWS_DIW.servicios;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import vistaProyectoFinal.DWS_DIW.dtos.UsuarioDto;

import java.util.Arrays;
import java.util.List;

@Service
public class AdministradorServicio {

    private final String API_URL = "http://localhost:8081/api/administrador";
    private final RestTemplate restTemplate;

    public AdministradorServicio(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<UsuarioDto> obtenerUsuarios() {
        try {
            ResponseEntity<UsuarioDto[]> response = restTemplate.getForEntity(API_URL + "/usuarios", UsuarioDto[].class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return Arrays.asList(response.getBody());
            }
        } catch (RestClientException e) {
            System.err.println("Error al obtener usuarios: " + e.getMessage());
        }
        return List.of();
    }

    public boolean eliminarUsuario(Long id) {
        try {
            restTemplate.delete(API_URL + "/eliminar/" + id);
            System.out.println("Usuario eliminado: " + id);
            return true;
        } catch (HttpClientErrorException.NotFound e) {
            System.err.println("Error: Usuario no encontrado con ID " + id);
        } catch (RestClientException e) {
            System.err.println("Error en la eliminación del usuario: " + e.getMessage());
        }
        return false;
    }
}
