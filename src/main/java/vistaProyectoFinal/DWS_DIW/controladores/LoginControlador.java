package vistaProyectoFinal.DWS_DIW.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import vistaProyectoFinal.DWS_DIW.servicios.LoginServicio;

@Controller
@RequestMapping("/login")
public class LoginControlador {

    @Autowired
    private LoginServicio loginservicio;

    @PostMapping
    public void login(@RequestParam String emailUsuario,
                      @RequestParam String passwordUsuario,
                      HttpSession session,
                      HttpServletResponse response) throws IOException {

        boolean isValidUser = loginservicio.verificarUsuario(emailUsuario, passwordUsuario);

        if (isValidUser) {
            String rol = loginservicio.getRol();
            session.setAttribute("emailUsuario", emailUsuario);
            session.setAttribute("rolUsuario", rol);

            if ("admin".equals(rol)) {
                response.sendRedirect("administrador.jsp");
            } else {
                response.sendRedirect("index.jsp");
            }
        } else {
            response.sendRedirect("inicioSesion.jsp?error=true");
        }
    }
}
