package vistaProyectoFinal.DWS_DIW.configuracion;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase que gestiona la creación y almacenamiento de logs por sesión.
 * 
 * @author irodhan - 06/03/2025
 */
public class SesionManual {
	/** Mapa que almacena los escritores de logs por sesión. */
	private static final Map<String, PrintWriter> escritorSesion = new HashMap<>();
	/** Directorio donde se almacenarán los logs. */
	private static final String LOG_DIRECTORIO = "ProyectoFinal/logs";
	/** Formato de fecha y hora para los logs. */
	private static final DateTimeFormatter TIMESTAMP_FORMATO = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	static {
		// Crear el directorio de logs si no existe
		try {
			Files.createDirectories(Paths.get(LOG_DIRECTORIO));
		} catch (IOException e) {
			System.err.println("No se pudo crear el directorio de logs: " + e.getMessage());
		}
	}

	/**
	 * Obtiene el escritor de logs para una sesión específica.
	 * Si no existe, lo crea.
	 *
	 * @param sesionId El identificador de sesión.
	 * @return PrintWriter para escribir en el log de la sesión.
	 */
	public static PrintWriter recogerEscritor(String sesionId) {
		if (!escritorSesion.containsKey(sesionId)) {
			try {
				String nombreFichaLog = LOG_DIRECTORIO + "/session_" + sesionId + ".log";
				FileWriter fileWriter = new FileWriter(nombreFichaLog, true);
				PrintWriter printWriter = new PrintWriter(fileWriter, true);
				escritorSesion.put(sesionId, printWriter);
			} catch (IOException e) {
				System.err.println("Error al crear el archivo de log para la sesión " + sesionId + ": " + e.getMessage());
			}
		}
		return escritorSesion.get(sesionId);
	}

	/**
	 * Registra un mensaje en el log de la sesión especificada.
	 *
	 * @param sesionId El identificador de sesión.
	 * @param mensaje El mensaje a registrar.
	 */
	public static void log(String sesionId, String mensaje) {
		PrintWriter escritor = recogerEscritor(sesionId);
		if (escritor != null) {
			String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMATO);
			escritor.println(timestamp + " - " + mensaje);
		}
	}

	/**
	 * Cierra el escritor de logs de la sesión especificada y lo elimina del mapa.
	 *
	 * @param sessionId El identificador de sesión.
	 */
	public static void cerrarEscritor(String sessionId) {
		if (escritorSesion.containsKey(sessionId)) {
			escritorSesion.get(sessionId).close();
			escritorSesion.remove(sessionId);
		}
	}
}
