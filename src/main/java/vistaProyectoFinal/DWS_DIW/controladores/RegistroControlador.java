package vistaProyectoFinal.DWS_DIW.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import vistaProyectoFinal.DWS_DIW.servicios.RegistroServicio;

@Controller
@RequestMapping("/registro")
public class RegistroControlador {

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

        // 🔹 Validar que todas las imágenes fueron subidas
        if (fotoDniFrontal.isEmpty() || fotoDniTrasero.isEmpty() || fotoUsuario.isEmpty()) {
            request.setAttribute("mensaje", "Debe subir todas las imágenes.");
            return "registro";
        }

        // 🔹 Intentar registrar al usuario
        boolean registrado = registroServicio.registrarUsuario(
                nombreCompleto, telefono, email, password, dni,
                fotoDniFrontal, fotoDniTrasero, fotoUsuario);

        if (registrado) {
            request.setAttribute("mensaje", "Registro exitoso. Por favor, revisa tu email para confirmar la cuenta.");
            return "inicioSesion"; // Redirigir a inicioSesion.jsp
        } else {
            request.setAttribute("mensaje", "Error en el registro. Inténtelo de nuevo.");
            return "registro";
        }
    }
}
