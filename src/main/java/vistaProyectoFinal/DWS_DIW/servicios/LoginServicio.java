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

@Service
public class LoginServicio {
    private static final SesionLogger logger = new SesionLogger(LoginServicio.class);
    private String rol = "";

    public boolean verificarUsuario(String correo, String password) {
        boolean todoOk = false;
        try {
            URL url = new URL("http://localhost:8081/api/login/validarUsuario");
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("POST");
            conexion.setRequestProperty("Content-Type", "application/json");
            conexion.setDoOutput(true);

            LoginDto loginRequest = new LoginDto();
            loginRequest.setEmailUsuario(correo);
            loginRequest.setPasswordUsuario(password);

            ObjectMapper mapper = new ObjectMapper();
            String jsonInput = mapper.writeValueAsString(loginRequest);

            try (OutputStream ot = conexion.getOutputStream()) {
                ot.write(jsonInput.getBytes());
                ot.flush();
            }

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

                    if ("admin".equals(respuesta) || "usuario".equals(respuesta)) {
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

    public String getRol() {
        return rol;
    }
}
