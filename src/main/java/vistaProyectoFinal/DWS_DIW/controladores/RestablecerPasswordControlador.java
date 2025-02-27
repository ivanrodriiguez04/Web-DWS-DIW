package vistaProyectoFinal.DWS_DIW.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vistaProyectoFinal.DWS_DIW.servicios.RestablecerPasswordServicio;

@Controller
public class RestablecerPasswordControlador {

    @Autowired
    private RestablecerPasswordServicio restablecerServicio;

    @PostMapping("/restablecer")
    public String cambiarContrasena(
            @RequestParam("emailUsuario") String email,
            @RequestParam("token") String token,
            @RequestParam("passwordUsuario") String nuevaContrasena,
            RedirectAttributes redirectAttributes) {

        boolean enviado = restablecerServicio.enviarNuevaContrasena(email, token, nuevaContrasena);

        // 📌 Imprimir si el envío fue exitoso
        System.out.println("🔹 Resultado de enviarNuevaContrasena: " + enviado);

        if (enviado) {
            redirectAttributes.addFlashAttribute("successMessage", "✅ Contraseña cambiada con éxito. Inicia sesión.");
            return "redirect:/inicioSesion.jsp";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "❌ Error al cambiar la contraseña. Inténtelo de nuevo.");
            return "redirect:/restablecerPassword.jsp?token=" + token;
        }
    }
}
