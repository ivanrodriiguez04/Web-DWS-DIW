package vistaProyectoFinal.DWS_DIW.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador encargado de gestionar la página de inicio.
 * 
 * @author irodhan - 06/03/2025
 */
@Controller
public class HomeControlador {
    
    /**
     * Maneja las solicitudes a la ruta raíz ("/") y redirige a la vista de inicio.
     *
     * @return La vista "index".
     */
    @GetMapping("/")
    public String home() {
        return "index"; // Ya no pongas ".jsp" porque se agrega automáticamente
    }
}
