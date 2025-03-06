package vistaProyectoFinal.DWS_DIW.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vistaProyectoFinal.DWS_DIW.configuracion.SesionLogger;
import vistaProyectoFinal.DWS_DIW.servicios.RestablecerPasswordServicio;

@Controller
public class RestablecerPasswordControlador {
    private static final SesionLogger logger = new SesionLogger(RestablecerPasswordControlador.class);

    @Autowired
    private RestablecerPasswordServicio restablecerServicio;

    @PostMapping("/restablecer")
    public String cambiarContrasena(
            @RequestParam("emailUsuario") String email,
            @RequestParam("token") String token,
            @RequestParam("passwordUsuario") String nuevaContrasena,
            RedirectAttributes redirectAttributes) {

        try {
            boolean enviado = restablecerServicio.enviarNuevaContrasena(email, token, nuevaContrasena);

            if (enviado) {
                logger.info("Contraseña restablecida con éxito para el usuario: " + email);
                redirectAttributes.addFlashAttribute("successMessage", "✅ Contraseña cambiada con éxito. Inicia sesión.");
                return "redirect:/inicioSesion.jsp";
            } else {
                logger.warn("Error al restablecer la contraseña para el usuario: " + email + " (Token inválido o expirado)");
                redirectAttributes.addFlashAttribute("errorMessage", "❌ Error al cambiar la contraseña. Inténtelo de nuevo.");
                return "redirect:/restablecerPassword.jsp?token=" + token;
            }
        } catch (Exception e) {
            logger.error("Error inesperado al restablecer contraseña para el usuario " + email + ": " + e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "❌ Error inesperado. Inténtelo de nuevo.");
            return "redirect:/restablecerPassword.jsp?token=" + token;
        }
    }
}
