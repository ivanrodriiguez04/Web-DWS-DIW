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
        return "registro"; // Asegúrate de que registro.jsp está en /WEB-INF/views/
    }

    @GetMapping("/registro") // Permitir GET en "/registro/registro"
    public String mostrarRegistro() {
        return "registro";
    }

    @PostMapping("/registro")
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

        if (fotoDniFrontal.isEmpty() || fotoDniTrasero.isEmpty() || fotoUsuario.isEmpty()) {
            request.setAttribute("mensaje", "Debe subir todas las imágenes.");
            return "registro";
        }

        boolean registrado = registroServicio.registrarUsuario(
                nombreCompleto, telefono, email, password, dni,
                fotoDniFrontal, fotoDniTrasero, fotoUsuario);

        if (registrado) {
            return "inicioSesion"; 
        } else {
            request.setAttribute("mensaje", "Error en el registro.");
            return "registro";
        }
    }
}
