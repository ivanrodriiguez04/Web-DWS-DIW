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

/**
 * Controlador que gestiona el proceso de registro de nuevos usuarios.
 * 
 * @author irodhan - 06/03/2025
 */
@Controller
@RequestMapping("/registro")
public class RegistroControlador {
    
    private static final SesionLogger logger = new SesionLogger(RegistroControlador.class);

    @Autowired
    private RegistroServicio registroServicio;

    /**
     * Muestra el formulario de registro.
     * 
     * @return La vista del formulario de registro.
     */
    @GetMapping
    public String mostrarFormularioRegistro() {
        return "registro"; // Asegúrate de que "registro.jsp" existe en la carpeta correcta
    }

    /**
     * Maneja el registro de un nuevo usuario.
     * 
     * @param nombreCompleto Nombre completo del usuario.
     * @param telefono Número de teléfono del usuario.
     * @param email Correo electrónico del usuario.
     * @param password Contraseña del usuario.
     * @param dni Documento de identidad del usuario.
     * @param fotoDniFrontal Imagen frontal del DNI.
     * @param fotoDniTrasero Imagen trasera del DNI.
     * @param fotoUsuario Fotografía del usuario.
     * @param request Objeto de solicitud HTTP.
     * @return La vista a la que se redirige el usuario después del intento de registro.
     */
    @PostMapping
    public String registrarUsuario(
            @RequestParam("nombreCompletoUsuario") String nombreCompleto,
            @RequestParam("telefonoUsuario") String telefono,
            @RequestParam("emailUsuario") String email,
            @RequestParam("passwordUsuario") String password,
            @RequestParam("ciudadUsuario") String ciudad,
            @RequestParam("dniUsuario") String dni,
            @RequestParam("fotoDniFrontalUsuario") MultipartFile fotoDniFrontal,
            @RequestParam("fotoDniTraseroUsuario") MultipartFile fotoDniTrasero,
            @RequestParam("fotoUsuario") MultipartFile fotoUsuario,
            HttpServletRequest request) {

        try {
            // Validar que todas las imágenes fueron subidas
            if (fotoDniFrontal.isEmpty() || fotoDniTrasero.isEmpty() || fotoUsuario.isEmpty()) {
                request.setAttribute("mensaje", "Debe subir todas las imágenes.");
                logger.warn("Intento de registro fallido: faltan imágenes para el usuario " + email);
                return "registro";
            }

            // Intentar registrar al usuario
            boolean registrado = registroServicio.registrarUsuario(
                    nombreCompleto, telefono, email, password, ciudad,dni,
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
