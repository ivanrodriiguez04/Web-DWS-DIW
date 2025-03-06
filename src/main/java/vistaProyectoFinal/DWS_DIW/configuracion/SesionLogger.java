package vistaProyectoFinal.DWS_DIW.configuracion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * Logger de sesión que permite registrar mensajes asociados a una sesión específica.
 * 
 * @author irodhan - 06/03/2025
 */
public class SesionLogger {
	private final Logger logger;

	/**
	 * Constructor que inicializa el logger para la clase dada.
	 *
	 * @param clazz La clase para la cual se registrarán los logs.
	 */
	public SesionLogger(Class<?> clazz) {
		this.logger = LoggerFactory.getLogger(clazz);
	}

	/**
	 * Registra un mensaje de información asociado a una sesión.
	 *
	 * @param mensaje El mensaje a registrar.
	 */
	public void info(String mensaje) {
		String sessionId = MDC.get("sessionId");
		if (sessionId != null) {
			SesionManual.log(sessionId, "[INFO] " + mensaje);
		} else {
			logger.info(mensaje);
		}
	}

	/**
	 * Registra un mensaje de error asociado a una sesión.
	 *
	 * @param mensaje El mensaje de error a registrar.
	 */
	public void error(String mensaje) {
		String sessionId = MDC.get("sessionId");
		if (sessionId != null) {
			SesionManual.log(sessionId, "[ERROR] " + mensaje);
		} else {
			logger.error(mensaje);
		}
	}

	/**
	 * Registra un mensaje de advertencia asociado a una sesión.
	 *
	 * @param mensaje El mensaje de advertencia a registrar.
	 */
	public void warn(String mensaje) {
		String sessionId = MDC.get("sessionId");
		if (sessionId != null) {
			SesionManual.log(sessionId, "[WARN] " + mensaje);
		} else {
			logger.warn(mensaje);
		}
	}
}
