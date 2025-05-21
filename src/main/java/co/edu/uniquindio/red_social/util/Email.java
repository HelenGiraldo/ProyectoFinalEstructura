package co.edu.uniquindio.red_social.util;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;
import java.io.File;
import java.util.ResourceBundle;

public class Email {
    static ResourceBundle bundle = ResourceBundle.getBundle("credenciales");
    static boolean isEmailSent = false;
    public static void sendEmail(String to, String subject, String bodyText) {
        if(!isEmailSent) {
            return;
        }
        new Thread(() -> {
            String from = bundle.getString("email");
            String password = bundle.getString("password");

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props,
                    new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(from, password);
                        }
                    });

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                message.setSubject(subject);

                // Parte HTML
                MimeBodyPart htmlPart = new MimeBodyPart();
                String htmlContent = "<h2>" + bodyText + "</h2><br><img src='cid:logoimg' width='200'/>";
                htmlPart.setContent(htmlContent, "text/html");

                String imagePath = bundle.getString("image");
                // Parte de la imagen incrustada
                MimeBodyPart imagePart = new MimeBodyPart();
                imagePart.attachFile(new File(imagePath));
                imagePart.setHeader("Content-ID", "<logoimg>");
                imagePart.setDisposition(MimeBodyPart.INLINE);

                // Combinar las partes
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(htmlPart);
                multipart.addBodyPart(imagePart);

                message.setContent(multipart);

                Transport.send(message);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }

    public static void saludoBienvenida(String to, String nombre) {
        String subject = "Bienvenido a la Red Social";
        String bodyText = "Hola " + nombre + ", bienvenido a nuestra red social. Esperamos que disfrutes de tu experiencia.";
        sendEmail(to, subject, bodyText);
    }

    public static void olvidasteContrasena(String to, String nombre, String contrasena) {
        String subject = "Recuperación de Contraseña";
        String bodyText = "Hola " + nombre + ", hemos recibido una solicitud para restablecer tu contraseña. Si no fuiste tú, ignora este mensaje. \nTu contraseña es: " + contrasena;
        sendEmail(to, subject, bodyText);
    }
}