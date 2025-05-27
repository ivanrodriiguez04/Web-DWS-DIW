package vistaProyectoFinal.DWS_DIW.controladores;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vistaProyectoFinal.DWS_DIW.dtos.TransferenciaDto;
import vistaProyectoFinal.DWS_DIW.servicios.TransferenciaServicio;

@RestController
@RequestMapping("/transferencia")
public class TransferenciaControlador {

    @Autowired
    private TransferenciaServicio transferenciaServicio;

    @PostMapping
    public void procesarTransferencia(
            @RequestParam String ibanOrigen,
            @RequestParam String ibanDestino,
            @RequestParam double cantidadTransferencia,
            HttpSession session,
            HttpServletResponse response) throws IOException {

        // ✅ Obtener email del usuario desde sesión
        String emailUsuario = (String) session.getAttribute("emailUsuario");

        // Validar que esté presente
        if (emailUsuario == null || emailUsuario.isEmpty()) {
            response.sendRedirect("transferencias.jsp?error=sesion");
            return;
        }

        // Crear DTO con datos del formulario y el email del usuario
        TransferenciaDto transferenciaDto = new TransferenciaDto();
        transferenciaDto.setIbanOrigen(ibanOrigen);
        transferenciaDto.setIbanDestino(ibanDestino);
        transferenciaDto.setCantidadTransferencia(cantidadTransferencia);
        transferenciaDto.setEmailUsuario(emailUsuario); // ✅ Asignar el email al DTO

        boolean exito = transferenciaServicio.realizarTransferencia(transferenciaDto);

        if (exito) {
            response.sendRedirect("transferencias.jsp?success=true");
        } else {
            response.sendRedirect("transferencias.jsp?error=true");
        }
    }
}
