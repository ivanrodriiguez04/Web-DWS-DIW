package vistaProyectoFinal.DWS_DIW.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServicio {
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
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
