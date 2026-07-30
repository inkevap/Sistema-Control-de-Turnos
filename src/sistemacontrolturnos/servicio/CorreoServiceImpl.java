package sistemacontrolturnos.servicio;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import sistemacontrolturnos.util.Constantes;

public class CorreoServiceImpl implements ICorreoService {

    @Override
    public void enviarCorreo(String destinatario, String asunto, String cuerpo) {
        Properties propiedades = new Properties();
        propiedades.put("mail.smtp.auth", "true");
        propiedades.put("mail.smtp.starttls.enable", "true");
        propiedades.put("mail.smtp.host", Constantes.SMTP_HOST);
        propiedades.put("mail.smtp.port", Constantes.SMTP_PUERTO);

        Session sesion = Session.getInstance(propiedades, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Constantes.SMTP_USUARIO, Constantes.SMTP_CONTRASENA);
            }
        });

        try {
            Message mensaje = new MimeMessage(sesion);
            mensaje.setFrom(new InternetAddress(Constantes.SMTP_USUARIO));
            mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            mensaje.setSubject(asunto);
            mensaje.setText(cuerpo);
            Transport.send(mensaje);
        } catch (MessagingException e) {
            // No se detiene el flujo de negocio si el correo falla (ej. credenciales de prueba sin configurar).
            System.err.println("No se pudo enviar el correo a " + destinatario + ": " + e.getMessage());
        }
    }
}
