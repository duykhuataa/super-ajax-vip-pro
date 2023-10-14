package utils;

import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {

    private Mail() {
    }

    public static void send(String toEmail, String subject, String body) {
        try {

            Properties properties = Helper.getPropertiesByFileName("constant/const.properties");
            final String fromEmail = properties.getProperty("admin.email");
            final String password = properties.getProperty("admin.email.password");

            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
            props.put("mail.smtp.port", "587"); //TLS Port
            props.put("mail.smtp.auth", "true"); //enable authentication
            props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

            Authenticator auth = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            };
            Session session = Session.getInstance(props, auth);
            MimeMessage msg = new MimeMessage(session);
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");
            msg.setFrom(new InternetAddress(fromEmail, properties.getProperty("admin.email.setFrom")));//
            msg.setReplyTo(InternetAddress.parse(fromEmail, false));
            msg.setSubject(subject, "UTF-8");
            msg.setText(body, "UTF-8");
            msg.setSentDate(new Date());
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            Transport.send(msg);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    public static void sendAcceptEmail(String toEmail,String username, String subject) {
        try {
            Properties properties = Helper.getPropertiesByFileName("constant/const.properties");
            final String fromEmail = properties.getProperty("admin.email");
            final String password = properties.getProperty("admin.email.password");

            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com"); 
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true"); 
            props.put("mail.smtp.starttls.enable", "true"); 

            Authenticator auth = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            };
            Session session = Session.getInstance(props, auth);
            MimeMessage msg = new MimeMessage(session);

            
            msg.setContent("<html><body>"
                    + "<h1>" + subject + "</h1>"
                    + "<p>Please click the following button to accept:</p>"
                    + "<a href=\"http://localhost:9999/isp392-project/active?email="+toEmail+"&username="+username+"\">Accept</a>"
                    + "</body></html>", "text/html; charset=utf-8");

            msg.setFrom(new InternetAddress(fromEmail, properties.getProperty("admin.email.setFrom")));
            msg.setReplyTo(InternetAddress.parse(fromEmail, false));
            msg.setSubject(subject, "UTF-8");
            msg.setSentDate(new Date());
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

            Transport.send(msg);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    
}
