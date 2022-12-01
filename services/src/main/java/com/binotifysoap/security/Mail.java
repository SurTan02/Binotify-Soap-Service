package com.binotifysoap.security;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.binotifysoap.config.Config;

public class Mail {

    public static String SENDER = Config.getConfig("EMAIL");
    public static String PASSWORD = Config.getConfig("EMAIL_PASSWORD");
    
    // public static String SENDER = "976db0a9e68ec7";
    // public static String PASSWORD = "f08696f3955d3a";

    /** MAGER GAN hrusnya array :v
     * @param receiver
     * @param receiver2
     * @param emailBody
     */
    public static void sendEmail(String receiver, String receiver2, String emailBody) {
        String sender = SENDER;
        String host = "smtp.gmail.com";
        // String host = "smtp.mailtrap.io";
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        // properties.put("mail.smtp.port", "2525");
        properties.put("mail.smtp.ssl.enable", "true");
        // properties.put("mail.smtp.ssl.enable", "false");
        properties.put("mail.smtp.auth", "true");

        // Get the Session 
        // Intinya biar bisa ngirim dari SENDER
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER, PASSWORD);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(sender));

            // Harusnya loop array, mager :v
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver2));

            message.setSubject("New Subscription!");

            message.setText(emailBody);
            
            // send to all
            Transport.send(message, message.getAllRecipients());
        } catch (MessagingException error) {
            error.printStackTrace();
        }

    }

}