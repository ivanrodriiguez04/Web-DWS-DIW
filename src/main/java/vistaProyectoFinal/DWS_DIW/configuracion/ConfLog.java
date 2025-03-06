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

@Component
public class ConfLog extends OncePerRequestFilter {

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