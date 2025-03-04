package vistaProyectoFinal.DWS_DIW.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import vistaProyectoFinal.DWS_DIW.dtos.CuentaDto;
import vistaProyectoFinal.DWS_DIW.servicios.CuentaServicio;

import java.util.List;

@Controller
@RequestMapping("/cuentas")
public class CuentaControlador {
    
    @Autowired
    private CuentaServicio cuentaServicio;

    @GetMapping("")
    public String verCuentas(HttpServletRequest request, Model model) {
        Long idUsuario = (Long) request.getSession().getAttribute("idUsuario");
        if (idUsuario == null) {
            model.addAttribute("mensaje", "Debe iniciar sesión para ver sus cuentas.");
            return "inicioSesion";
        }
        List<CuentaDto> cuentas = cuentaServicio.obtenerCuentasPorUsuario(idUsuario);
        model.addAttribute("cuentas", cuentas);
        return "cuentas";
    }

    @PostMapping("/crear")
    public String crearCuenta(@RequestBody CuentaDto cuentaDto, HttpServletRequest request, Model model) {
        Long idUsuario = (Long) request.getSession().getAttribute("idUsuario");
        if (idUsuario == null) {
            model.addAttribute("mensaje", "Debe iniciar sesión para crear una cuenta.");
            return "inicioSesion";
        }
        cuentaDto.setIdUsuario(idUsuario);
        boolean cuentaCreada = cuentaServicio.crearCuenta(cuentaDto);
        if (cuentaCreada) {
            return "redirect:/cuentas";
        } else {
            model.addAttribute("mensaje", "Error al crear la cuenta. Inténtelo de nuevo.");
            return "crearCuenta";
        }
    }

    @PostMapping("/eliminar")
    public String eliminarCuenta(@RequestParam("idCuenta") long idCuenta, HttpServletRequest request, Model model) {
        Long idUsuario = (Long) request.getSession().getAttribute("idUsuario");
        if (idUsuario == null) {
            model.addAttribute("mensaje", "Debe iniciar sesión para eliminar una cuenta.");
            return "inicioSesion";
        }
        try {
            boolean eliminada = cuentaServicio.eliminarCuenta(idCuenta);
            if (eliminada) {
                return "redirect:/cuentas";
            } else {
                model.addAttribute("mensaje", "Error al eliminar la cuenta.");
            }
        } catch (Exception e) {
            model.addAttribute("mensaje", "Error al procesar la solicitud.");
        }
        return "cuentas";
    }
}
