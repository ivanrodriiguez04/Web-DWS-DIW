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

public class SesionManual {
	private static final Map<String, PrintWriter> escritorSesion = new HashMap<>();
	private static final String LOG_DIRECTORIO = "ProyectoFinal/logs";
	private static final DateTimeFormatter TIMESTAMP_FORMATO = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	static {
		// Crear el directorio de logs si no existe
		try {
			Files.createDirectories(Paths.get(LOG_DIRECTORIO));
		} catch (IOException e) {
			System.err.println("No se pudo crear el directorio de logs: " + e.getMessage());
		}
	}

	public static PrintWriter recogerEscritor(String sesionId) {
		if (!escritorSesion.containsKey(sesionId)) {
			try {
				// Crear un archivo de log para la sesión
				String nombreFichaLog = LOG_DIRECTORIO + "/session_" + sesionId + ".log";
				FileWriter fileWriter = new FileWriter(nombreFichaLog, true); // true para append
				PrintWriter printWriter = new PrintWriter(fileWriter, true); // auto-flush
				escritorSesion.put(sesionId, printWriter);
			} catch (IOException e) {
				System.err
						.println("Error al crear el archivo de log para la sesión " + sesionId + ": " + e.getMessage());
			}
		}
		return escritorSesion.get(sesionId);
	}

	
	public static void log(String sesionId, String mensaje) {
		PrintWriter escritor = recogerEscritor(sesionId);
		if (escritor != null) {
			String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMATO);
			escritor.println(timestamp + " - " + mensaje);
		}
	}

	public static void cerrarEscritor(String sessionId) {
		if (escritorSesion.containsKey(sessionId)) {
			escritorSesion.get(sessionId).close();
			escritorSesion.remove(sessionId);
		}
	}
}