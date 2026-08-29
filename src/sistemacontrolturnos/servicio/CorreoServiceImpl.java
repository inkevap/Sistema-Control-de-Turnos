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
import sistemacontrolturnos.util.ConfiguracionApp;

public class CorreoServiceImpl implements ICorreoService {

    @Override
    public void enviarCorreo(String destinatario, String asunto, String cuerpo) {
        // Estos valores tienen que venir de un objeto final (Constante)
        // porque sino no va a funcionar la clase anonima que se crea despues
        String usuarioSmtp = ConfiguracionApp.getSmtpUsuario();
        String contrasenaSmtp = ConfiguracionApp.getSmtpContrasena();

        // Este properties es el requerido para usar la libreria mail,
        // se usa para poder configurar el SMTP
        Properties propiedades = new Properties();
        propiedades.put("mail.smtp.auth", "true");
        propiedades.put("mail.smtp.starttls.enable", "true");
        propiedades.put("mail.smtp.host", ConfiguracionApp.getSmtpHost());
        propiedades.put("mail.smtp.port", ConfiguracionApp.getSmtpPuerto());
        
        // Creamos un objeto para iniciar sesion, le pasamos las configuraciones
        // junto con una clase anonima donde sobre escribimos el unico metodo que 
        // se utiliza para mandar las credenciales y validar el inicio de sesion
        // en el servidor smtp
        
        // | - Esta es la clase anonima que se fresea plpm (Por la paciencia mia)
        // V
        Session sesion = Session.getInstance(propiedades, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuarioSmtp, contrasenaSmtp);
            }
        });

        try {
            //Aqui se construye el mensaje
            Message mensaje = new MimeMessage(sesion); // Se usa la sesion iniciada
            mensaje.setFrom(new InternetAddress(usuarioSmtp)); // se usa el email o alias a usar
            mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario)); // Aqui configuramos al destinatario
            mensaje.setSubject(asunto); // Ponemos el asunto
            mensaje.setText(cuerpo); // y el cuerpo del mensaje
            Transport.send(mensaje); // mandamos el mensaje | se loguea, se conecta al servidor, empaqueta el correo e intenta enviar.
        } catch (MessagingException e) { // y con fe, no da error, pero si da error
            // No se detiene el flujo de negocio si el correo falla (ej. credenciales de prueba sin configurar).
            // porque el mr Inge no definio un Flujo para esto, pero pregunta Kev, no seas mamon.
            System.err.println("No se pudo enviar el correo a " + destinatario + ": " + e.getMessage());
        }
    }
}
