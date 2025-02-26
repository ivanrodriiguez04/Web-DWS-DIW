package vistaProyectoFinal.DWS_DIW.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ConfirmacionControlador {

    private final String API_URL = "http://localhost:8081/api/registro/confirmar";

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/confirmar")
    public String confirmarCuenta(@RequestParam("token") String token, HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(token, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                API_URL + "?token=" + token, HttpMethod.GET, requestEntity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) { // ✅ Ahora validamos con 200 OK
            request.setAttribute("mensaje", "Cuenta confirmada. Ahora puedes iniciar sesión.");
            return "inicioSesion"; // Redirige correctamente a inicioSesion.jsp
        } else {
            request.setAttribute("mensaje", "Token inválido o expirado.");
            return "registro"; // Redirige a registro.jsp con mensaje de error
        }
    }

}
