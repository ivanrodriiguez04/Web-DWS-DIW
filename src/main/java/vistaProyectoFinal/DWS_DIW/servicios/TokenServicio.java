package vistaProyectoFinal.DWS_DIW.servicios;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import vistaProyectoFinal.DWS_DIW.configuracion.SesionLogger;
import vistaProyectoFinal.DWS_DIW.dtos.RegistroDto;

@Service
public class TokenServicio {
    private static final SesionLogger logger = new SesionLogger(TokenServicio.class);
    private Map<String, RegistroDto> tokensPendientes = new HashMap<>();

    public void guardarTokenTemporal(RegistroDto registroDto, String token) {
        try {
            tokensPendientes.put(token, registroDto);
            logger.info("Token guardado exitosamente: " + token);
        } catch (Exception e) {
            logger.error("Error al guardar token: " + token + " - " + e.getMessage());
        }
    }

    public RegistroDto obtenerRegistroPorToken(String token) {
        try {
            RegistroDto registro = tokensPendientes.get(token);
            if (registro != null) {
                logger.info("Registro encontrado para token: " + token);
            } else {
                logger.warn("No se encontró un registro para el token: " + token);
            }
            return registro;
        } catch (Exception e) {
            logger.error("Error al obtener registro por token: " + token + " - " + e.getMessage());
            return null;
        }
    }

    public void eliminarToken(String token) {
        try {
            if (tokensPendientes.containsKey(token)) {
                tokensPendientes.remove(token);
                logger.info("Token eliminado exitosamente: " + token);
            } else {
                logger.warn("Intento de eliminar un token inexistente: " + token);
            }
        } catch (Exception e) {
            logger.error("Error al eliminar token: " + token + " - " + e.getMessage());
        }
    }
}
