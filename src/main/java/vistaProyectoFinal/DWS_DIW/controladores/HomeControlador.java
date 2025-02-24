package vistaProyectoFinal.DWS_DIW.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeControlador {
    @GetMapping("/")
    public String home() {
        return "index"; // Ya no pongas ".jsp" porque se agrega automáticamente
    }
}
