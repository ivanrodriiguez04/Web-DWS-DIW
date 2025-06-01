package vistaProyectoFinal.DWS_DIW.servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import vistaProyectoFinal.DWS_DIW.configuracion.SesionLogger;
import vistaProyectoFinal.DWS_DIW.dtos.LoginDto;

/**
 * Clase de servicio encargada de verificar las credenciales de los usuarios a través de la API.
 * 
 * @author irodhan - 06/03/2025
 */
@Service
public class LoginServicio {
    private static final SesionLogger logger = new SesionLogger(LoginServicio.class);
    private String rol = "";

    /**
     * Verifica si un usuario con el correo y contraseña proporcionados es válido.
     * @param correo Email del usuario.
     * @param password Contraseña del usuario.
     * @return true si las credenciales son correctas, false en caso contrario.
     */
    public boolean verificarUsuario(String correo, String password) {
        boolean todoOk = false;
        try {
            URL url = new URL("http://localhost:8081/apiProyectoFinal/api/login/validarUsuario");
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("POST");
            conexion.setRequestProperty("Content-Type", "application/json");
            conexion.setDoOutput(true);

            // Creación del objeto LoginDto con las credenciales del usuario
            LoginDto loginRequest = new LoginDto();
            loginRequest.setEmailUsuario(correo);
            loginRequest.setPasswordUsuario(password);

            // Conversión del objeto a formato JSON
            ObjectMapper mapper = new ObjectMapper();
            String jsonInput = mapper.writeValueAsString(loginRequest);

            // Envío de la solicitud con el JSON en el cuerpo
            try (OutputStream ot = conexion.getOutputStream()) {
                ot.write(jsonInput.getBytes());
                ot.flush();
            }

            // Verificación de la respuesta del servidor
            int responseCode = conexion.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }

                    String respuesta = response.toString();
                    logger.info("Respuesta del servidor: " + respuesta);

                    // Verificación del rol del usuario
                    if ("superAdmin".equals(respuesta) ||"admin".equals(respuesta) || "usuario".equals(respuesta)) {
                        this.rol = respuesta;
                        todoOk = true;
                    } else {
                        logger.warn("Rol desconocido o error en la respuesta.");
                    }
                }
            } else {
                logger.warn("Error: Código de respuesta no OK. Código: " + responseCode);
            }
        } catch (Exception e) {
            logger.error("ERROR al verificar usuario: " + e.getMessage());
        }
        return todoOk;
    }

    /**
     * Obtiene el rol del usuario autenticado.
     * @return Rol del usuario ("admin" o "usuario").
     */
    public String getRol() {
        return rol;
    }
}
