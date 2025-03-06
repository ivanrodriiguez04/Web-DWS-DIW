package vistaProyectoFinal.DWS_DIW.seguridad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Clase de configuración de seguridad que define la gestión de autenticación y autorización.
 * 
 * @author irodhan - 06/03/2025
 */
@Configuration
public class SecurityConfig {

    /**
     * Bean para la codificación de contraseñas usando BCrypt.
     * @return una instancia de BCryptPasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    /**
     * Configuración de la cadena de filtros de seguridad.
     * @param http el objeto HttpSecurity que permite la configuración de seguridad.
     * @return la configuración de seguridad aplicada.
     * @throws Exception si ocurre un error en la configuración.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/**").permitAll() // Permitir acceso a todas las rutas
            )
            .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF (opcional)
            .formLogin(login -> login.disable()) // Deshabilitar el formulario de login
            .logout(logout -> logout.disable()); // Deshabilitar logout (opcional)

        return http.build();
    }
}
