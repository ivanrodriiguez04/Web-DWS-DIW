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

import vistaProyectoFinal.DWS_DIW.configuracion.SesionLogger;

/**
 * Clase de servicio encargada de gestionar la recuperación de contraseña a través de la API.
 * 
 * @author irodhan - 06/03/2025
 */
@Service
public class RecuperarPasswordServicio {
    private static final SesionLogger logger = new SesionLogger(RecuperarPasswordServicio.class);
    private final String API_URL = "http://localhost:8081/api/usuarios/guardarToken";

    @Autowired
    private EmailServicio emailServicio;

    /**
     * Envía un correo electrónico con un enlace para la recuperación de contraseña.
     * @param email Email del usuario.
     * @param urlBase URL base del sitio para generar el enlace de recuperación.
     * @return true si el correo fue enviado correctamente, false en caso contrario.
     */
    public boolean enviarCorreoRecuperacion(String email, String urlBase) {
        try {
            // Generación de un token único y su fecha de expiración
            String token = UUID.randomUUID().toString();
            LocalDateTime fechaExpiracion = LocalDateTime.now().plusMinutes(30);

            // Guardar el token en la API
            if (!guardarTokenEnAPI(email, token, fechaExpiracion)) {
                logger.warn("Error al guardar el token en la API.");
                return false;
            }

            // Construcción del mensaje de recuperación
            String enlaceRecuperacion = urlBase + "/restablecerPassword.jsp?token=" + token + "&email=" + email;
            String mensaje = "<p>Hola,</p>"
                    + "<p>Has solicitado recuperar tu contraseña.</p>"
                    + "<p>Haz clic en el siguiente enlace para restablecerla (válido por 30 minutos):</p>"
                    + "<p><a href='" + enlaceRecuperacion + "'>Restablecer contraseña</a></p>"
                    + "<p>Si no solicitaste esto, ignora este correo.</p>";

            // Envío del correo
            boolean enviado = emailServicio.enviarCorreo(email, "Recuperación de contraseña", mensaje);
            if (enviado) {
                logger.info("Correo de recuperación enviado a: " + email);
            } else {
                logger.warn("No se pudo enviar el correo de recuperación a: " + email);
            }
            return enviado;
        } catch (Exception e) {
            logger.error("Error al enviar correo de recuperación a " + email + ": " + e.getMessage());
            return false;
        }
    }

    /**
     * Guarda el token de recuperación de contraseña en la API.
     * @param email Email del usuario.
     * @param token Token generado para la recuperación.
     * @param fechaExpiracion Fecha de expiración del token.
     * @return true si el token fue guardado correctamente, false en caso contrario.
     */
    private boolean guardarTokenEnAPI(String email, String token, LocalDateTime fechaExpiracion) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("email", email);
            requestBody.put("token", token);
            requestBody.put("fechaExpiracion", fechaExpiracion.toString());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, requestEntity, String.class);

            boolean success = response.getStatusCode() == HttpStatus.OK;
            if (success) {
                logger.info("Token de recuperación guardado correctamente en la API para el email: " + email);
            } else {
                logger.warn("No se pudo guardar el token en la API. Respuesta: " + response.getStatusCode());
            }
            return success;
        } catch (Exception e) {
            logger.error("Error al guardar token en la API para " + email + ": " + e.getMessage());
            return false;
        }
    }
}