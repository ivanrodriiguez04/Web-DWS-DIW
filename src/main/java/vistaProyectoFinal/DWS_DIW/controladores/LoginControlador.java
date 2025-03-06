package vistaProyectoFinal.DWS_DIW.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

import vistaProyectoFinal.DWS_DIW.configuracion.SesionLogger;
import vistaProyectoFinal.DWS_DIW.servicios.LoginServicio;

/**
 * Controlador que gestiona el inicio de sesión de los usuarios.
 * 
 * @author irodhan - 06/03/2025
 */
@Controller
@RequestMapping("/login")
public class LoginControlador {
    
    private static final SesionLogger logger = new SesionLogger(LoginControlador.class);

    @Autowired
    private LoginServicio loginservicio;

    /**
     * Maneja el proceso de inicio de sesión.
     *
     * @param emailUsuario    El correo electrónico del usuario.
     * @param passwordUsuario La contraseña del usuario.
     * @param session         La sesión HTTP para almacenar información del usuario.
     * @param response        La respuesta HTTP para redirigir al usuario.
     */
    @PostMapping
    public void login(@RequestParam String emailUsuario,
                      @RequestParam String passwordUsuario,
                      HttpSession session,
                      HttpServletResponse response) {
        try {
            boolean isValidUser = loginservicio.verificarUsuario(emailUsuario, passwordUsuario);

            if (isValidUser) {
                String rol = loginservicio.getRol();
                session.setAttribute("emailUsuario", emailUsuario);
                session.setAttribute("rolUsuario", rol);

                logger.info("Inicio de sesión exitoso para: " + emailUsuario + " con rol: " + rol);

                if ("admin".equals(rol)) {
                    response.sendRedirect("administrador.jsp");
                } else {
                    response.sendRedirect("index.jsp");
                }
            } else {
                logger.warn("Intento de inicio de sesión fallido para: " + emailUsuario);
                response.sendRedirect("inicioSesion.jsp?error=true");
            }
        } catch (IOException e) {
            logger.error("Error al redirigir después del login: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error inesperado en el proceso de login: " + e.getMessage());
        }
    }
}