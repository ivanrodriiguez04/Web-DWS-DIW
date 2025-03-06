package vistaProyectoFinal.DWS_DIW.controladores;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import vistaProyectoFinal.DWS_DIW.configuracion.SesionLogger;
import vistaProyectoFinal.DWS_DIW.dtos.UsuarioDto;
import vistaProyectoFinal.DWS_DIW.servicios.AdministradorServicio;

@RestController
@RequestMapping("/admin")
public class AdministradorControlador {

    private static final SesionLogger logger = new SesionLogger(AdministradorControlador.class);

    @Autowired
    private AdministradorServicio administradorServicio;

    @GetMapping("/usuarios")
    public List<UsuarioDto> mostrarUsuarios() {
        try {
            return administradorServicio.obtenerUsuarios();
        } catch (Exception e) {
            logger.error("Error al obtener usuarios: " + e.getMessage());
            return List.of();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public Map<String, String> eliminarUsuario(@PathVariable Long id) {
        try {
            boolean eliminado = administradorServicio.eliminarUsuario(id);
            if (eliminado) {
                logger.info("Usuario eliminado correctamente: " + id);
                return Map.of("mensaje", "Usuario eliminado correctamente.");
            } else {
                logger.warn("Usuario no encontrado: " + id);
                return Map.of("error", "Usuario no encontrado.");
            }
        } catch (Exception e) {
            logger.error("Error al eliminar usuario: " + e.getMessage());
            return Map.of("error", "Ocurrió un error.");
        }
    }
}
