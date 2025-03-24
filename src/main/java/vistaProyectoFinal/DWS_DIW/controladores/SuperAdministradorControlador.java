package vistaProyectoFinal.DWS_DIW.controladores;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import vistaProyectoFinal.DWS_DIW.configuracion.SesionLogger;
import vistaProyectoFinal.DWS_DIW.dtos.UsuarioDto;
import vistaProyectoFinal.DWS_DIW.servicios.SuperAdministradorServicio;

/**
 * Controlador REST para la gestión de usuarios por parte del superadministrador.
 * Permite visualizar usuarios con distintos roles.
 * 
 * @author irodhan - 06/03/2025
 */
@RestController
@RequestMapping("/superadmin")
public class SuperAdministradorControlador {

    private static final SesionLogger logger = new SesionLogger(SuperAdministradorControlador.class);

    @Autowired
    private SuperAdministradorServicio superAdministradorServicio;

    /**
     * Obtiene la lista de todos los usuarios.
     * 
     * @return una lista de objetos UsuarioDto que representan a los usuarios del sistema.
     */
    @GetMapping("/usuarios")
    public List<UsuarioDto> mostrarUsuarios() {
        try {
            return superAdministradorServicio.obtenerUsuarios();
        } catch (Exception e) {
            logger.error("Error al obtener usuarios: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Elimina un usuario del sistema.
     * 
     * @param id Identificador único del usuario a eliminar.
     * @return Un mapa con un mensaje de éxito o error según el resultado de la operación.
     */
    @DeleteMapping("/eliminar/{id}")
    public Map<String, String> eliminarUsuario(@PathVariable Long id) {
        try {
            boolean eliminado = superAdministradorServicio.eliminarUsuario(id);
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