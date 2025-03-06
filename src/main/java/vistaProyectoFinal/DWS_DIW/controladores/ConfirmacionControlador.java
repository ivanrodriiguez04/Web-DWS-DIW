package vistaProyectoFinal.DWS_DIW.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpServletRequest;
import vistaProyectoFinal.DWS_DIW.configuracion.SesionLogger;

/**
 * Controlador encargado de procesar la confirmación de cuentas mediante un token.
 * 
 * @author irodhan - 06/03/2025
 */
@Controller
public class ConfirmacionControlador {

    private static final SesionLogger logger = new SesionLogger(ConfirmacionControlador.class);
    private final String API_URL = "http://localhost:8081/api/registro/confirmar";

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Procesa la confirmación de una cuenta utilizando un token.
     * 
     * @param token   Token de confirmación proporcionado en la solicitud.
     * @param request Objeto HttpServletRequest para agregar mensajes de respuesta.
     * @return La vista correspondiente dependiendo del resultado de la confirmación.
     */
    @GetMapping("/confirmar")
    public String confirmarCuenta(@RequestParam("token") String token, HttpServletRequest request) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> requestEntity = new HttpEntity<>(token, headers);
            ResponseEntity<String> response = restTemplate.exchange(
                    API_URL + "?token=" + token, HttpMethod.GET, requestEntity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                logger.info("Cuenta confirmada correctamente.");
                request.setAttribute("mensaje", "Cuenta confirmada. Ahora puedes iniciar sesión.");
                return "inicioSesion";
            } else {
                logger.warn("Token inválido o expirado.");
                request.setAttribute("mensaje", "Token inválido o expirado.");
                return "registro";
            }
        } catch (Exception e) {
            logger.error("Error al confirmar cuenta: " + e.getMessage());
            request.setAttribute("mensaje", "Ocurrió un error.");
            return "registro";
        }
    }
}
