package vistaProyectoFinal.DWS_DIW.servicios;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import vistaProyectoFinal.DWS_DIW.configuracion.SesionLogger;
import vistaProyectoFinal.DWS_DIW.dtos.RegistroDto;

/**
 * Clase de servicio encargada de gestionar los tokens temporales asociados a registros de usuarios.
 * 
 * @author irodhan - 06/03/2025
 */
@Service
public class TokenServicio {
    private static final SesionLogger logger = new SesionLogger(TokenServicio.class);
    private Map<String, RegistroDto> tokensPendientes = new HashMap<>();

    /**
     * Guarda un token temporal asociado a un registro de usuario.
     * 
     * @param registroDto Datos del usuario en proceso de registro.
     * @param token Token único generado para la confirmación del registro.
     */
    public void guardarTokenTemporal(RegistroDto registroDto, String token) {
        try {
            tokensPendientes.put(token, registroDto);
            logger.info("Token guardado exitosamente: " + token);
        } catch (Exception e) {
            logger.error("Error al guardar token: " + token + " - " + e.getMessage());
        }
    }

    /**
     * Obtiene el registro de usuario asociado a un token específico.
     * 
     * @param token Token asociado al registro del usuario.
     * @return RegistroDto con los datos del usuario si el token existe, null en caso contrario.
     */
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

    /**
     * Elimina un token temporal del almacenamiento.
     * 
     * @param token Token que se desea eliminar.
     */
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
