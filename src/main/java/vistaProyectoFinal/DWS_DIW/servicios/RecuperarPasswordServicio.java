package vistaProyectoFinal.DWS_DIW.servicios;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RecuperarPasswordServicio {
    private final String API_URL = "http://localhost:8081/api/usuarios/guardarToken";

    @Autowired
    private EmailServicio emailServicio;

    public boolean enviarCorreoRecuperacion(String email, String urlBase) {
        // ✅ Generar un token único
        String token = UUID.randomUUID().toString();
        LocalDateTime fechaExpiracion = LocalDateTime.now().plusMinutes(30); // Expira en 30 minutos

        // ✅ Guardar el token en la API
        if (!guardarTokenEnAPI(email, token, fechaExpiracion)) {
            System.out.println("❌ Error al guardar el token en la API.");
            return false;
        }

        // ✅ Crear enlace de recuperación con el token
        String enlaceRecuperacion = urlBase + "/restablecerPassword.jsp?token=" + token + "&email=" + email;

        // ✅ Contenido del correo
        String mensaje = "<p>Hola,</p>"
                + "<p>Has solicitado recuperar tu contraseña.</p>"
                + "<p>Haz clic en el siguiente enlace para restablecerla (válido por 30 minutos):</p>"
                + "<p><a href='" + enlaceRecuperacion + "'>Restablecer contraseña</a></p>"
                + "<p>Si no solicitaste esto, ignora este correo.</p>";

        return emailServicio.enviarCorreo(email, "Recuperación de contraseña", mensaje);
    }

    private boolean guardarTokenEnAPI(String email, String token, LocalDateTime fechaExpiracion) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            // ✅ Crear JSON para enviar a la API
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("email", email);
            requestBody.put("token", token);
            requestBody.put("fechaExpiracion", fechaExpiracion.toString());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

            // ✅ Enviar solicitud POST a la API
            ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, requestEntity, String.class);

            return response.getStatusCode() == HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
