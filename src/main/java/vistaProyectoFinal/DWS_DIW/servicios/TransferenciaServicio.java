package vistaProyectoFinal.DWS_DIW.servicios;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import vistaProyectoFinal.DWS_DIW.dtos.TransferenciaDto;

@Service
public class TransferenciaServicio {
	public boolean realizarTransferencia(TransferenciaDto transferenciaDto) {
        try {
            URL url = new URL("http://localhost:8081/apiProyectoFinal/api/transferencias/enviarDinero");
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("POST");
            conexion.setRequestProperty("Content-Type", "application/json");
            conexion.setDoOutput(true);

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(transferenciaDto);

            try (OutputStream os = conexion.getOutputStream()) {
                os.write(json.getBytes());
                os.flush();
            }

            int responseCode = conexion.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK;

        } catch (Exception e) {
            e.printStackTrace(); // o logger
            return false;
        }
    }
}
