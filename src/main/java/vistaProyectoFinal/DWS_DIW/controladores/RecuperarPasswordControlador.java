package vistaProyectoFinal.DWS_DIW.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import vistaProyectoFinal.DWS_DIW.servicios.RecuperarPasswordServicio;

@Controller
public class RecuperarPasswordControlador {
	@Autowired
	private RecuperarPasswordServicio recuperarPasswordServicio;
	
	@PostMapping("/recuperar")
    public String enviarCorreoRecuperacion(
            @RequestParam("emailUsuario") String email,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes) {
        try {
            // Obtener URL base del servidor
            String urlBase = request.getRequestURL().toString().replace(request.getServletPath(), "");

            // Llamar al servicio para enviar el correo
            boolean enviado = recuperarPasswordServicio.enviarCorreoRecuperacion(email, urlBase);

            if (enviado) {
                redirectAttributes.addFlashAttribute("successMessage", "Correo enviado con éxito. Revisa tu bandeja de entrada.");
                return "restablecerPassword";
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Error al enviar el correo.");
                return "recuperar";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error inesperado.");
            return "recuperar";
        }
    }
}
