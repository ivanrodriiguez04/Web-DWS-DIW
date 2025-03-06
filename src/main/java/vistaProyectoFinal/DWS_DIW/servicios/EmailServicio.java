package vistaProyectoFinal.DWS_DIW.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.internet.MimeMessage;
import vistaProyectoFinal.DWS_DIW.configuracion.SesionLogger;
/**
 * Servicio para el envío de correos electrónicos.
 * 
 * @author irodhan - 06/03/2025
 */
@Service
public class EmailServicio {
    private static final SesionLogger logger = new SesionLogger(EmailServicio.class);

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Envía un correo electrónico al destinatario con el asunto y mensaje especificados.
     * @param destinatario Dirección de correo del destinatario.
     * @param asunto Asunto del correo.
     * @param mensaje Contenido del mensaje (puede contener HTML).
     * @return true si el correo fue enviado exitosamente, false en caso de error.
     */
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
