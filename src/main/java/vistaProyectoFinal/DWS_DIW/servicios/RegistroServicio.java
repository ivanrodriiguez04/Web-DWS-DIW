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
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
public class RegistroServicio {

    private final String API_URL = "http://localhost:8081/api/registro/usuario"; 

    @Autowired
    private EmailServicio emailServicio;

    public boolean registrarUsuario(String nombreCompleto, String telefono, String email,
                                    String password, String dni, MultipartFile fotoDniFrontal, 
                                    MultipartFile fotoDniTrasero, MultipartFile fotoUsuario) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> datosUsuario = new HashMap<>();
        datosUsuario.put("nombreCompletoUsuario", nombreCompleto);
        datosUsuario.put("telefonoUsuario", telefono);
        datosUsuario.put("emailUsuario", email);
        datosUsuario.put("passwordUsuario", password);
        datosUsuario.put("dniUsuario", dni);

        try {
            datosUsuario.put("fotoDniFrontalUsuario", fotoDniFrontal.getBytes());
            datosUsuario.put("fotoDniTraseroUsuario", fotoDniTrasero.getBytes());
            datosUsuario.put("fotoUsuario", fotoUsuario.getBytes());
        } catch (Exception e) {
            return false;
        }

        // Generar token en el proyecto web
        String token = UUID.randomUUID().toString();
        datosUsuario.put("token", token); // Enviar el token a la API

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(datosUsuario, headers);
        ResponseEntity<Void> response = restTemplate.postForEntity(API_URL, requestEntity, Void.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            // Enviar correo con el enlace de confirmación
            String asunto = "Confirma tu cuenta";
            String enlaceConfirmacion = "http://localhost:8080/confirmar?token=" + token; 
            String mensaje = "Hola,\n\nPor favor confirma tu cuenta haciendo clic en el siguiente enlace:\n" + enlaceConfirmacion +
                             "\n\nSi no solicitaste esto, ignora este mensaje.";

            return emailServicio.enviarCorreo(email, asunto, mensaje);
        }
        
        return false;
    }
}
