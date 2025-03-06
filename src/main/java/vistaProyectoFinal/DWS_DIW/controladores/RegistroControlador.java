package vistaProyectoFinal.DWS_DIW.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import vistaProyectoFinal.DWS_DIW.configuracion.SesionLogger;
import vistaProyectoFinal.DWS_DIW.servicios.RegistroServicio;

@Controller
@RequestMapping("/registro")
public class RegistroControlador {
    private static final SesionLogger logger = new SesionLogger(RegistroControlador.class);

    @Autowired
    private RegistroServicio registroServicio;

    @GetMapping
    public String mostrarFormularioRegistro() {
        return "registro"; // Asegúrate de que "registro.jsp" existe en la carpeta correcta
    }

    @PostMapping
    public String registrarUsuario(
            @RequestParam("nombreCompletoUsuario") String nombreCompleto,
            @RequestParam("telefonoUsuario") String telefono,
            @RequestParam("emailUsuario") String email,
            @RequestParam("passwordUsuario") String password,
            @RequestParam("dniUsuario") String dni,
            @RequestParam("fotoDniFrontalUsuario") MultipartFile fotoDniFrontal,
            @RequestParam("fotoDniTraseroUsuario") MultipartFile fotoDniTrasero,
            @RequestParam("fotoUsuario") MultipartFile fotoUsuario,
            HttpServletRequest request) {

        try {
            // 🔹 Validar que todas las imágenes fueron subidas
            if (fotoDniFrontal.isEmpty() || fotoDniTrasero.isEmpty() || fotoUsuario.isEmpty()) {
                request.setAttribute("mensaje", "Debe subir todas las imágenes.");
                logger.warn("Intento de registro fallido: faltan imágenes para el usuario " + email);
                return "registro";
            }

            // 🔹 Intentar registrar al usuario
            boolean registrado = registroServicio.registrarUsuario(
                    nombreCompleto, telefono, email, password, dni,
                    fotoDniFrontal, fotoDniTrasero, fotoUsuario);

            if (registrado) {
                request.setAttribute("mensaje", "Registro exitoso. Por favor, revisa tu email para confirmar la cuenta.");
                logger.info("Registro exitoso para el usuario: " + email);
                return "inicioSesion"; // Redirigir a inicioSesion.jsp
            } else {
                request.setAttribute("mensaje", "Error en el registro. Inténtelo de nuevo.");
                logger.warn("Error en el registro para el usuario: " + email);
                return "registro";
            }
        } catch (Exception e) {
            logger.error("Error inesperado en el registro del usuario " + email + ": " + e.getMessage());
            request.setAttribute("mensaje", "Error inesperado. Inténtelo de nuevo.");
            return "registro";
        }
    }
}
