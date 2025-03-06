package vistaProyectoFinal.DWS_DIW.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import vistaProyectoFinal.DWS_DIW.configuracion.SesionLogger;
import vistaProyectoFinal.DWS_DIW.servicios.RecuperarPasswordServicio;

/**
 * Controlador que gestiona la recuperación de contraseña de los usuarios.
 * 
 * @author irodhan - 06/03/2025
 */
@Controller
public class RecuperarPasswordControlador {
    
    private static final SesionLogger logger = new SesionLogger(RecuperarPasswordControlador.class);

    @Autowired
    private RecuperarPasswordServicio recuperarPasswordServicio;
    
    /**
     * Maneja el envío del correo de recuperación de contraseña.
     * 
     * @param email El correo electrónico del usuario.
     * @param request La solicitud HTTP para obtener la URL base del servidor.
     * @param redirectAttributes Atributos para redirigir mensajes de éxito o error.
     * @return La vista a la que se redirige el usuario.
     */
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
                logger.info("Correo de recuperación enviado con éxito a: " + email);
                redirectAttributes.addFlashAttribute("successMessage", "Correo enviado con éxito. Revisa tu bandeja de entrada.");
                return "restablecerPassword";
            } else {
                logger.warn("Error al enviar correo de recuperación a: " + email);
                redirectAttributes.addFlashAttribute("errorMessage", "Error al enviar el correo.");
                return "recuperar";
            }
        } catch (Exception e) {
            logger.error("Error inesperado en la recuperación de contraseña para: " + email + " - " + e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Error inesperado.");
            return "recuperar";
        }
    }
}