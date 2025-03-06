package vistaProyectoFinal.DWS_DIW.configuracion;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * Configuración de correo para la aplicación.
 * Define un bean para el envío de correos electrónicos mediante SMTP.
 * 
 * @author irodhan - 06/03/2025
 */
@Configuration
public class MailConfig {
    /**
     * Configura el JavaMailSender para enviar correos electrónicos.
     *
     * @return JavaMailSender configurado.
     */
    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("ivansio1004@gmail.com");
        mailSender.setPassword("hmvh fzzz esir pnje");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}