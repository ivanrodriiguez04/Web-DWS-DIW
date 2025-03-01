package vistaProyectoFinal.DWS_DIW.controladores;

import vistaProyectoFinal.DWS_DIW.dtos.UsuarioDto;
import vistaProyectoFinal.DWS_DIW.servicios.AdministradorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdministradorControlador {

    @Autowired
    private AdministradorServicio administradorServicio;

    @GetMapping("/usuarios")
    public List<UsuarioDto> mostrarUsuarios() {
        return administradorServicio.obtenerUsuarios();
    }

    @DeleteMapping("/eliminar/{id}")
    public Map<String, String> eliminarUsuario(@PathVariable Long id) {
        boolean eliminado = administradorServicio.eliminarUsuario(id);
        if (eliminado) {
            return Map.of("mensaje", "Usuario eliminado correctamente.");
        } else {
            return Map.of("error", "Usuario no encontrado.");
        }
    }
}
