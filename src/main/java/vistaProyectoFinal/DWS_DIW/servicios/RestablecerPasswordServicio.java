package vistaProyectoFinal.DWS_DIW.servicios;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import vistaProyectoFinal.DWS_DIW.configuracion.SesionLogger;

@Service
public class RestablecerPasswordServicio {
    private static final SesionLogger logger = new SesionLogger(RestablecerPasswordServicio.class);
    private final String API_URL = "http://localhost:8081/api/usuarios/restablecer";

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean enviarNuevaContrasena(String email, String token, String nuevaContrasena) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String contrasenaEncriptada = passwordEncoder.encode(nuevaContrasena);
            
            logger.info("Enviando solicitud de restablecimiento de contraseña para: " + email);
            
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("email", email);
            requestBody.put("token", token);
            requestBody.put("nuevaContrasena", contrasenaEncriptada);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, requestEntity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                logger.info("Contraseña restablecida exitosamente para: " + email);
                return true;
            } else {
                logger.warn("Error al restablecer contraseña para: " + email + " - Código de respuesta: " + response.getStatusCode());
                return false;
            }
        } catch (Exception e) {
            logger.error("Excepción al restablecer contraseña para: " + email + " - " + e.getMessage());
            return false;
        }
    }
}
