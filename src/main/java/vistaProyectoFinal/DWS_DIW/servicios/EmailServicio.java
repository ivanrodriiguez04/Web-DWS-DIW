package vistaProyectoFinal.DWS_DIW.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServicio {
    @Autowired
    private JavaMailSender mailSender;

    public boolean enviarCorreo(String destinatario, String asunto, String mensaje) {
        try {
            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(destinatario);
            email.setSubject(asunto);
            email.setText(mensaje);
            mailSender.send(email);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

