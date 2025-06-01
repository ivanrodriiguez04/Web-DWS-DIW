package vistaProyectoFinal.DWS_DIW.servicios;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import vistaProyectoFinal.DWS_DIW.configuracion.SesionLogger;

/**
 * Clase de servicio encargada de gestionar el registro de usuarios en la API.
 * 
 * @author irodhan - 06/03/2025
 */
@Service
public class RegistroServicio {
    private static final SesionLogger logger = new SesionLogger(RegistroServicio.class);
    private final String API_URL = "http://localhost:8081/apiProyectoFinal/api/registro/usuario";

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private EmailServicio emailServicio;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Registra un nuevo usuario en el sistema.
     * @param nombreCompleto Nombre completo del usuario.
     * @param telefono Teléfono del usuario.
     * @param email Correo electrónico del usuario.
     * @param password Contraseña del usuario.
     * @param dni Documento de identidad del usuario.
     * @param fotoDniFrontal Imagen frontal del DNI.
     * @param fotoDniTrasero Imagen trasera del DNI.
     * @param fotoUsuario Imagen del usuario.
     * @return true si el usuario fue registrado con éxito, false en caso contrario.
     */
    public boolean registrarUsuario(String nombreCompleto, String telefono, String email, String password,  String ciudad,String dni,
                                    MultipartFile fotoDniFrontal, MultipartFile fotoDniTrasero, MultipartFile fotoUsuario) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            String token = UUID.randomUUID().toString();
            String passwordEncriptada = passwordEncoder.encode(password);

            Map<String, Object> datosUsuario = new HashMap<>();
            datosUsuario.put("nombreCompletoUsuario", nombreCompleto);
            datosUsuario.put("telefonoUsuario", telefono);
            datosUsuario.put("emailUsuario", email);
            datosUsuario.put("passwordUsuario", passwordEncriptada);
            datosUsuario.put("ciudadUsuario", ciudad);
            datosUsuario.put("dniUsuario", dni);
            datosUsuario.put("token", token);

            try {
                datosUsuario.put("fotoDniFrontalUsuario", fotoDniFrontal.getBytes());
                datosUsuario.put("fotoDniTraseroUsuario", fotoDniTrasero.getBytes());
                datosUsuario.put("fotoUsuario", fotoUsuario.getBytes());
            } catch (Exception e) {
                logger.error("Error al procesar archivos de usuario: " + e.getMessage());
                return false;
            }

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(datosUsuario, headers);
            ResponseEntity<Void> response = restTemplate.postForEntity(API_URL, requestEntity, Void.class);

            if (response.getStatusCode() == HttpStatus.CREATED) {
                enviarCorreoConfirmacion(email, token);
                logger.info("Usuario registrado exitosamente: " + email);
                return true;
            } else {
                logger.warn("Error al registrar usuario: " + email + " - Código de respuesta: " + response.getStatusCode());
                return false;
            }
        } catch (Exception e) {
            logger.error("Excepción al registrar usuario: " + email + " - " + e.getMessage());
            return false;
        }
    }

    /**
     * Envía un correo de confirmación para activar la cuenta del usuario.
     * @param email Correo electrónico del usuario.
     * @param token Token de confirmación generado.
     */
    private void enviarCorreoConfirmacion(String email, String token) {
        try {
            String asunto = "Confirma tu cuenta en InnovaBank";
            String enlaceConfirmacion = "http://16.170.127.156:8080/confirmar?token=" + token;
            String mensaje = "<html><body>"
                    + "<h2>¡Bienvenido a InnovaBank!</h2>"
                    + "<p>Gracias por registrarte. Para activar tu cuenta, haz clic en el siguiente enlace:</p>"
                    + "<a href='" + enlaceConfirmacion + "' style='padding:10px; background:#28a745; color:white; text-decoration:none;'>Confirmar cuenta</a>"
                    + "<p>Si no solicitaste esta cuenta, ignora este correo.</p>"
                    + "</body></html>";

            boolean enviado = emailServicio.enviarCorreo(email, asunto, mensaje);
            if (enviado) {
                logger.info("Correo de confirmación enviado a: " + email);
            } else {
                logger.warn("No se pudo enviar el correo de confirmación a: " + email);
            }
        } catch (Exception e) {
            logger.error("Error al enviar correo de confirmación a " + email + ": " + e.getMessage());
        }
    }
}
