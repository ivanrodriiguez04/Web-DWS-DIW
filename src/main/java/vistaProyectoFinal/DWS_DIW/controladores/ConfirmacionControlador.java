package vistaProyectoFinal.DWS_DIW.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpServletRequest;
import vistaProyectoFinal.DWS_DIW.configuracion.SesionLogger;

@Controller
public class ConfirmacionControlador {

    private static final SesionLogger logger = new SesionLogger(ConfirmacionControlador.class);
    private final String API_URL = "http://localhost:8081/api/registro/confirmar";

    @Autowired
    private RestTemplate restTemplate;

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
