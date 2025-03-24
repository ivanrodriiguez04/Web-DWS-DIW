package vistaProyectoFinal.DWS_DIW.servicios;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import vistaProyectoFinal.DWS_DIW.configuracion.SesionLogger;
import vistaProyectoFinal.DWS_DIW.dtos.UsuarioDto;

import java.util.Arrays;
import java.util.List;

/**
 * Clase de servicio que maneja la obtención y eliminación de usuarios administradores desde la API externa.
 * 
 * @author irodhan - 06/03/2025
 */
@Service
public class SuperAdministradorServicio {

    private static final SesionLogger logger = new SesionLogger(AdministradorServicio.class);
    
    private final String API_URL = "http://localhost:8081/api/administrador";
    private final RestTemplate restTemplate;

    /**
     * Constructor con inyección de RestTemplate.
     * @param restTemplate Cliente REST para realizar peticiones HTTP.
     */
    public SuperAdministradorServicio(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Obtiene la lista de usuarios administradores desde la API.
     * @return Lista de objetos UsuarioDto o lista vacía en caso de error.
     */
    public List<UsuarioDto> obtenerUsuarios() {
        try {
            ResponseEntity<UsuarioDto[]> response = restTemplate.getForEntity(API_URL + "/usuarios", UsuarioDto[].class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                logger.info("Usuarios obtenidos correctamente desde la API.");
                return Arrays.asList(response.getBody());
            }
        } catch (RestClientException e) {
            logger.error("Error al obtener usuarios: " + e.getMessage());
        }
        return List.of();
    }

    /**
     * Elimina un usuario administrador de la API.
     * @param id Identificador del usuario a eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
    public boolean eliminarUsuario(Long id) {
        try {
            restTemplate.delete(API_URL + "/eliminar/" + id);
            logger.info("Usuario eliminado con éxito. ID: " + id);
            return true;
        } catch (HttpClientErrorException.NotFound e) {
            logger.warn("Intento de eliminar usuario no encontrado. ID: " + id);
        } catch (RestClientException e) {
            logger.error("Error en la eliminación del usuario (ID " + id + "): " + e.getMessage());
        }
        return false;
    }
}
