package vistaProyectoFinal.DWS_DIW.seguridad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
public class SecurityConfig {

	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
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