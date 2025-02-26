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

@Service
public class RegistroServicio {

	private final String API_URL = "http://localhost:8081/api/registro/usuario";

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private EmailServicio emailServicio;

	@Autowired
	private PasswordEncoder passwordEncoder; // Inyectamos el encriptador

	public boolean registrarUsuario(String nombreCompleto, String telefono, String email, String password, String dni,
			MultipartFile fotoDniFrontal, MultipartFile fotoDniTrasero, MultipartFile fotoUsuario) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

// 🔹 Generar token en la web
		String token = UUID.randomUUID().toString();

// 🔹 Encriptar la contraseña antes de enviarla a la API
		String passwordEncriptada = passwordEncoder.encode(password);

// 🔹 Crear JSON con datos del usuario + token
		Map<String, Object> datosUsuario = new HashMap<>();
		datosUsuario.put("nombreCompletoUsuario", nombreCompleto);
		datosUsuario.put("telefonoUsuario", telefono);
		datosUsuario.put("emailUsuario", email);
		datosUsuario.put("passwordUsuario", passwordEncriptada);
		datosUsuario.put("dniUsuario", dni);
		datosUsuario.put("token", token); // 🔹 Enviar el token a la API

		try {
			datosUsuario.put("fotoDniFrontalUsuario", fotoDniFrontal.getBytes());
			datosUsuario.put("fotoDniTraseroUsuario", fotoDniTrasero.getBytes());
			datosUsuario.put("fotoUsuario", fotoUsuario.getBytes());
		} catch (Exception e) {
			return false;
		}

		HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(datosUsuario, headers);
		ResponseEntity<Void> response = restTemplate.postForEntity(API_URL, requestEntity, Void.class);

		if (response.getStatusCode() == HttpStatus.CREATED) {
			enviarCorreoConfirmacion(email, token); // 🔹 Enviar correo con el token
			return true;
		}
		return false;
	}

	private void enviarCorreoConfirmacion(String email, String token) {
	    String asunto = "Confirma tu cuenta en InnovaBank";
	    String enlaceConfirmacion = "http://localhost:8080/confirmar?token=" + token; // 🔹 Asegurar que el puerto sea correcto

	    String mensaje = "<html><body>"
	            + "<h2>¡Bienvenido a InnovaBank!</h2>"
	            + "<p>Gracias por registrarte. Para activar tu cuenta, haz clic en el siguiente enlace:</p>"
	            + "<a href='" + enlaceConfirmacion + "' style='padding:10px; background:#28a745; color:white; text-decoration:none;'>Confirmar cuenta</a>"
	            + "<p>Si no solicitaste esta cuenta, ignora este correo.</p>"
	            + "</body></html>";

	    emailServicio.enviarCorreo(email, asunto, mensaje);
	}

}
