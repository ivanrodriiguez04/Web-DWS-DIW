package vistaProyectoFinal.DWS_DIW.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.internet.MimeMessage;
import vistaProyectoFinal.DWS_DIW.configuracion.SesionLogger;

@Service
public class EmailServicio {
    private static final SesionLogger logger = new SesionLogger(EmailServicio.class);

    @Autowired
    private JavaMailSender mailSender;

    public boolean enviarCorreo(String destinatario, String asunto, String mensaje) {
        try {
            MimeMessage email = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(email, true, "UTF-8");

            helper.setTo(destinatario);
            helper.setSubject(asunto);
            helper.setText(mensaje, true); // 🔹 El "true" permite HTML

            mailSender.send(email);
            logger.info("Correo enviado exitosamente a: " + destinatario);
            return true;
        } catch (Exception e) {
            logger.error("Error al enviar correo a " + destinatario + ": " + e.getMessage());
            return false;
        }
    }
}
