package vistaProyectoFinal.DWS_DIW.servicios;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import vistaProyectoFinal.DWS_DIW.dtos.LoginDto;
@Service
public class LoginServicio {
	 private String rol = "";
	 
	 public boolean verificarUsuario(String correo, String password) {
	        boolean todoOk = false;

	        try {
	            // Crear la URL de la API para la verificación del usuario
	            URL url = new URL("http://localhost:8081/api/login/validarUsuario");
	            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
	            conexion.setRequestMethod("POST");
	            conexion.setRequestProperty("Content-Type", "application/json");
	            conexion.setDoOutput(true);

	            // Crear el objeto DTO con las credenciales del usuario
	            LoginDto loginRequest = new LoginDto();
	            loginRequest.setEmailUsuario(correo);
	            loginRequest.setPasswordUsuario(password);

	            // Convertir el DTO a JSON
	            ObjectMapper mapper = new ObjectMapper();
	            String jsonInput = mapper.writeValueAsString(loginRequest);

	            // Enviar la solicitud al servidor
	            try (OutputStream ot = conexion.getOutputStream()) {
	                ot.write(jsonInput.getBytes());
	                ot.flush();
	            }

	            // Procesar la respuesta del servidor
	            int responseCode = conexion.getResponseCode();
	            if (responseCode == HttpURLConnection.HTTP_OK) {
	                try (BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()))) {
	                    StringBuilder response = new StringBuilder();
	                    String inputLine;
	                    while ((inputLine = in.readLine()) != null) {
	                        response.append(inputLine);
	                    }

	                    String respuesta = response.toString();
	                    System.out.println("Respuesta del servidor: " + respuesta);

	                    if ("admin".equals(respuesta) || "usuario".equals(respuesta)) {
	                        this.rol = respuesta;
	                        todoOk = true;
	                    } else {
	                        System.out.println("Rol desconocido o error en la respuesta.");
	                    }
	                }
	            } else {
	                System.out.println("Error: Código de respuesta no OK. Código: " + responseCode);
	            }

	        } catch (Exception e) {
	            System.out.println("ERROR: " + e);
	            e.printStackTrace();
	        }

	        return todoOk;
	    }
	 public String getRol() {
	        return rol;
	    }
}
