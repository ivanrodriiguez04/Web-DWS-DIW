package vistaProyectoFinal.DWS_DIW.configuracion;

import java.io.IOException;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Filtro que agrega el identificador de sesión al MDC para su uso en logs.
 * 
 * @author irodhan - 06/03/2025
 */
@Component
public class ConfLog extends OncePerRequestFilter {

	/**
	 * Agrega el identificador de sesión a los logs y luego lo elimina al finalizar la solicitud.
	 *
	 * @param request  La solicitud HTTP.
	 * @param response La respuesta HTTP.
	 * @param filterChain La cadena de filtros.
	 * @throws ServletException Si ocurre un error en el filtro.
	 * @throws IOException Si ocurre un error de entrada/salida.
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		HttpSession sesion = request.getSession(true);
		String sesionId = sesion.getId();

		// Almacenar el sessionId en el MDC
		MDC.put("sessionId", sesionId);

		try {
			filterChain.doFilter(request, response);
		} finally {
			// Limpiar el MDC al finalizar la solicitud
			MDC.remove("sessionId");
			SesionManual.cerrarEscritor(sesionId);
		}
	}
}