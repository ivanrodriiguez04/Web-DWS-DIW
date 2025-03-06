package vistaProyectoFinal.DWS_DIW.configuracion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class SesionLogger {
	private final Logger logger;

	public SesionLogger(Class<?> clazz) {
		this.logger = LoggerFactory.getLogger(clazz);
	}

	public void info(String mensaje) {
		String sessionId = MDC.get("sessionId");
		if (sessionId != null) {
			SesionManual.log(sessionId, "[INFO] " + mensaje);
		} else {
			logger.info(mensaje); // Si no hay sesión, escribe en el log global
		}
	}
	
	public void error(String mensaje) {
		String sessionId = MDC.get("sessionId");
		if (sessionId != null) {
			SesionManual.log(sessionId, "[ERROR] " + mensaje);
		} else {
			logger.error(mensaje); // Si no hay sesión, escribe en el log global
		}
	}

	public void warn(String mensaje) {
		String sessionId = MDC.get("sessionId");
		if (sessionId != null) {
			SesionManual.log(sessionId, "[WARN] " + mensaje);
		} else {
			logger.warn(mensaje); // Si no hay sesión, escribe en el log global
		}
	}
}
