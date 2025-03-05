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

    @GetMapping("")
    public String verCuentas(HttpServletRequest request, Model model) {
        String emailUsuario = (String) request.getSession().getAttribute("emailUsuario");

        if (emailUsuario == null) {
            return "inicioSesion";
        }

        List<CuentaDto> cuentas = cuentaServicio.obtenerCuentasPorEmail(emailUsuario);
        model.addAttribute("cuentas", cuentas);
        return "cuentas";
    }

    @GetMapping("/obtener")
    @ResponseBody
    public List<CuentaDto> obtenerCuentas(HttpServletRequest request) {
        String emailUsuario = (String) request.getSession().getAttribute("emailUsuario");

        if (emailUsuario == null) {
            return List.of(); // Devuelve una lista vacía si no hay usuario autenticado
        }

        return cuentaServicio.obtenerCuentasPorEmail(emailUsuario);
    }

    @PostMapping("/crear")
    public String crearCuenta(@ModelAttribute CuentaDto cuentaDto, HttpServletRequest request, Model model) {
        String emailUsuario = (String) request.getSession().getAttribute("emailUsuario");

        if (emailUsuario == null) {
            return "inicioSesion";
        }

        cuentaDto.setEmailUsuario(emailUsuario); // 🔹 Solo se asigna el emailUsuario aquí

        boolean creada = cuentaServicio.crearCuenta(cuentaDto);

        if (!creada) {
            model.addAttribute("mensaje", "⚠️ El IBAN ya está en uso o hubo un error al crear la cuenta.");
        }
        return "cuentas";
    }

    @PostMapping("/eliminar")
    @ResponseBody
    public String eliminarCuenta(@RequestParam("idCuenta") long idCuenta, HttpServletRequest request) {
        String emailUsuario = (String) request.getSession().getAttribute("emailUsuario");

        if (emailUsuario == null) {
            return "error"; // Indica que el usuario no está autenticado
        }

        boolean eliminada = cuentaServicio.eliminarCuenta(idCuenta);
        return eliminada ? "success" : "error";
    }
}
