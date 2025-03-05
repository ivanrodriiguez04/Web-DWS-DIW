package vistaProyectoFinal.DWS_DIW.controladores;

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

    private final CuentaServicio cuentaServicio;

    public CuentaControlador(CuentaServicio cuentaServicio) {
        this.cuentaServicio = cuentaServicio;
    }

    /**
     * Obtiene todas las cuentas del usuario actual.
     */
    @GetMapping("")
    public String verCuentas(HttpServletRequest request, Model model) {
        Long idUsuario = (Long) request.getSession().getAttribute("idUsuario");
        if (idUsuario == null) {
            return "inicioSesion";
        }
        
        List<CuentaDto> cuentas = cuentaServicio.obtenerCuentasPorUsuario(idUsuario);
        model.addAttribute("cuentas", cuentas);
        return "cuentas";
    }

    /**
     * Crea una nueva cuenta asociada al usuario en sesión.
     */
    @PostMapping("/crear")
    public String crearCuenta(@ModelAttribute CuentaDto cuentaDto, HttpServletRequest request) {
        Long idUsuario = (Long) request.getSession().getAttribute("idUsuario");
        if (idUsuario == null) {
            return "inicioSesion";
        }
        
        cuentaDto.setIdUsuario(idUsuario);
        cuentaDto.setDineroCuenta(0.0);
        boolean creada = cuentaServicio.crearCuenta(cuentaDto);
        return "cuentas";
    }

    /**
     * Elimina una cuenta específica si pertenece al usuario en sesión.
     */
    @PostMapping("/eliminar")
    public String eliminarCuenta(@RequestParam("idCuenta") long idCuenta, HttpServletRequest request) {
        Long idUsuario = (Long) request.getSession().getAttribute("idUsuario");
        if (idUsuario == null) {
            return "inicioSesion";
        }
        
        cuentaServicio.eliminarCuenta(idCuenta);
        return "cuentas";
    }
}
