package vistaProyectoFinal.DWS_DIW.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import vistaProyectoFinal.DWS_DIW.configuracion.SesionLogger;
import vistaProyectoFinal.DWS_DIW.dtos.CuentaDto;
import vistaProyectoFinal.DWS_DIW.servicios.CuentaServicio;

import java.util.List;

@Controller
@RequestMapping("/cuentas")
public class CuentaControlador {

    private static final SesionLogger logger = new SesionLogger(CuentaControlador.class);
    private final CuentaServicio cuentaServicio;

    public CuentaControlador(CuentaServicio cuentaServicio) {
        this.cuentaServicio = cuentaServicio;
    }

    @GetMapping("")
    public String verCuentas(HttpServletRequest request, Model model) {
        try {
            String emailUsuario = (String) request.getSession().getAttribute("emailUsuario");
            if (emailUsuario == null) return "inicioSesion";

            List<CuentaDto> cuentas = cuentaServicio.obtenerCuentasPorEmail(emailUsuario);
            model.addAttribute("cuentas", cuentas);
            return "cuentas";
        } catch (Exception e) {
            logger.error("Error al obtener cuentas: " + e.getMessage());
            return "error";
        }
    }

    // ✅ Nuevo método para devolver cuentas en formato JSON
    @GetMapping("/obtener")
    @ResponseBody
    public List<CuentaDto> obtenerCuentas(HttpServletRequest request) {
        try {
            String emailUsuario = (String) request.getSession().getAttribute("emailUsuario");
            if (emailUsuario == null) return List.of(); // Devolver lista vacía si no hay sesión

            return cuentaServicio.obtenerCuentasPorEmail(emailUsuario);
        } catch (Exception e) {
            logger.error("Error obteniendo cuentas: " + e.getMessage());
            return List.of(); // Devolver lista vacía en caso de error
        }
    }

    @PostMapping("/crear")
    public String crearCuenta(@ModelAttribute CuentaDto cuentaDto, HttpServletRequest request, Model model) {
        try {
            String emailUsuario = (String) request.getSession().getAttribute("emailUsuario");
            if (emailUsuario == null) return "inicioSesion";

            cuentaDto.setEmailUsuario(emailUsuario);
            boolean creada = cuentaServicio.crearCuenta(cuentaDto);
            if (!creada) {
                model.addAttribute("mensaje", "⚠️ El IBAN ya está en uso o hubo un error.");
                logger.warn("Intento fallido de crear cuenta con IBAN duplicado.");
            }
            return "cuentas";
        } catch (Exception e) {
            logger.error("Error al crear cuenta: " + e.getMessage());
            return "error";
        }
    }

    @PostMapping("/eliminar")
    @ResponseBody
    public String eliminarCuenta(@RequestParam("idCuenta") long idCuenta, HttpServletRequest request) {
        try {
            String emailUsuario = (String) request.getSession().getAttribute("emailUsuario");
            if (emailUsuario == null) return "error";

            boolean eliminada = cuentaServicio.eliminarCuenta(idCuenta);
            if (eliminada) {
                logger.info("Cuenta eliminada correctamente: " + idCuenta);
                return "success";
            } else {
                logger.warn("Cuenta no encontrada: " + idCuenta);
                return "error";
            }
        } catch (Exception e) {
            logger.error("Error al eliminar cuenta: " + e.getMessage());
            return "error";
        }
    }
}
