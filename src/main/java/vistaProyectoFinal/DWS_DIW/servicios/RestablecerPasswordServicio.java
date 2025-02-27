package vistaProyectoFinal.DWS_DIW.servicios;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestablecerPasswordServicio {
    private final String API_URL = "http://localhost:8081/api/usuarios/restablecer";

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean enviarNuevaContrasena(String email, String token, String nuevaContrasena) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            // 🔒 Encriptar la contraseña antes de enviarla
            String contrasenaEncriptada = passwordEncoder.encode(nuevaContrasena);

            // 📌 Imprimir los datos enviados a la API
            System.out.println("🔹 Enviando a API:");
            System.out.println("Email: " + email);
            System.out.println("Token: " + token);
            System.out.println("Nueva Contraseña (Encriptada): " + contrasenaEncriptada);

            // Crear JSON con los datos correctos
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("email", email);
            requestBody.put("token", token);
            requestBody.put("nuevaContrasena", contrasenaEncriptada);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

            // Enviar la solicitud POST a la API
            ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, requestEntity, String.class);

            // 📌 Imprimir la respuesta completa de la API
            System.out.println("🔹 Código de respuesta de la API: " + response.getStatusCode());
            System.out.println("🔹 Cuerpo de la respuesta de la API: " + response.getBody());

            // ✅ Devolver true si la API responde correctamente
            return response.getStatusCode() == HttpStatus.OK;
        } catch (Exception e) {
            // 📌 Capturar cualquier error en la solicitud y mostrar detalles
            System.out.println("❌ Error en la solicitud:");
            e.printStackTrace();
            return false;
        }
    }
}
