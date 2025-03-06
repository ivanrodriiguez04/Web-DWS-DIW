package vistaProyectoFinal.DWS_DIW.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vistaProyectoFinal.DWS_DIW.configuracion.SesionLogger;
import vistaProyectoFinal.DWS_DIW.servicios.RestablecerPasswordServicio;

/**
 * Controlador que gestiona el proceso de restablecimiento de contraseña de los usuarios.
 * 
 * @author irodhan - 06/03/2025
 */
@Controller
public class RestablecerPasswordControlador {
    
    private static final SesionLogger logger = new SesionLogger(RestablecerPasswordControlador.class);

    @Autowired
    private RestablecerPasswordServicio restablecerServicio;

    /**
     * Maneja la solicitud para restablecer la contraseña de un usuario.
     * 
     * @param email Correo electrónico del usuario que solicita el cambio de contraseña.
     * @param token Token de validación para el restablecimiento de la contraseña.
     * @param nuevaContrasena Nueva contraseña establecida por el usuario.
     * @param redirectAttributes Atributos para redirección de mensajes.
     * @return La vista a la que se redirige el usuario después del intento de cambio de contraseña.
     */
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
                redirectAttributes.addFlashAttribute("successMessage", "Password cambiada con éxito. Inicia sesión.");
                return "redirect:/inicioSesion.jsp";
            } else {
                logger.warn("Error al restablecer la contraseña para el usuario: " + email + " (Token inválido o expirado)");
                redirectAttributes.addFlashAttribute("errorMessage", "Error al cambiar la password. Intentelo de nuevo.");
                return "redirect:/restablecerPassword.jsp?token=" + token;
            }
        } catch (Exception e) {
            logger.error("Error inesperado al restablecer contraseña para el usuario " + email + ": " + e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Error inesperado. Intentelo de nuevo.");
            return "redirect:/restablecerPassword.jsp?token=" + token;
        }
    }
}