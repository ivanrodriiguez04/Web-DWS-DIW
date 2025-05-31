package vistaProyectoFinal.DWS_DIW.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import vistaProyectoFinal.DWS_DIW.configuracion.SesionLogger;
import vistaProyectoFinal.DWS_DIW.dtos.CuentaDto;
import vistaProyectoFinal.DWS_DIW.servicios.CuentaServicio;

import java.util.List;

/**
 * Controlador encargado de la gestión de cuentas en la aplicación web.
 * 
 * @author irodhan - 06/03/2025
 */
@Controller
@RequestMapping("/cuentas")
public class CuentaControlador {

    private static final SesionLogger logger = new SesionLogger(CuentaControlador.class);
    private final CuentaServicio cuentaServicio;

    /**
     * Constructor que inyecta el servicio de cuentas.
     * 
     * @param cuentaServicio Servicio de cuentas.
     */
    public CuentaControlador(CuentaServicio cuentaServicio) {
        this.cuentaServicio = cuentaServicio;
    }

    /**
     * Muestra la vista de las cuentas del usuario.
     * 
     * @param request Solicitud HTTP para obtener la sesión del usuario.
     * @param model Modelo para pasar atributos a la vista.
     * @return Página de cuentas o redirección a inicio de sesión si el usuario no está autenticado.
     */
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

    /**
     * Obtiene las cuentas del usuario autenticado en formato JSON.
     * 
     * @param request Solicitud HTTP para obtener la sesión del usuario.
     * @return Lista de cuentas en formato JSON o lista vacía si hay un error.
     */
    @GetMapping("/obtener")
    @ResponseBody
    public List<CuentaDto> obtenerCuentas(HttpServletRequest request) {
        try {
            String emailUsuario = (String) request.getSession().getAttribute("emailUsuario");
            if (emailUsuario == null) return List.of();

            return cuentaServicio.obtenerCuentasPorEmail(emailUsuario);
        } catch (Exception e) {
            logger.error("Error obteniendo cuentas: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Crea una nueva cuenta asociada al usuario autenticado.
     * 
     * @param cuentaDto Datos de la cuenta.
     * @param request Solicitud HTTP para obtener la sesión del usuario.
     * @param model Modelo para pasar atributos a la vista.
     * @return Página de cuentas o redirección a inicio de sesión si el usuario no está autenticado.
     */
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
            return "redirect:/cuentas";
        } catch (Exception e) {
            logger.error("Error al crear cuenta: " + e.getMessage());
            return "error";
        }
    }

    /**
     * Elimina una cuenta específica del usuario autenticado.
     * 
     * @param idCuenta ID de la cuenta a eliminar.
     * @param request Solicitud HTTP para obtener la sesión del usuario.
     * @return Respuesta de éxito o error en formato JSON.
     */
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
