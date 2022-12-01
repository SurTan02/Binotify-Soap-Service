package com.binotifysoap.security;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.binotifysoap.config.Config;

public class Mail {

    private static String SENDER = Config.getConfig("EMAIL");
    private static String PASSWORD = Config.getConfig("EMAIL_PASSWORD");
    private static String REST_HOST = Config.getConfig("REST_HOST");
    private static String REST_PORT = Config.getConfig("REST_PORT");
    private static List<String> receiver = new ArrayList<String>();
    private static Boolean hasRun = false;
    
    // public static String SENDER = "976db0a9e68ec7";
    // public static String PASSWORD = "f08696f3955d3a";

    /**
     * @param receiver
     * @param receiver2
     * @param emailBody
     */
    public static void sendEmail(String emailBody) {
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
            try {
                generateReceiver();
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Harusnya loop array, mager :v
            for (String el : receiver) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(el));
            }
            message.setSubject("New Subscription!");
            message.setText(emailBody);
            // send to all
            Transport.send(message, message.getAllRecipients());
        } catch (MessagingException error) {
            // error.printStackTrace();
            System.out.println("Failed to send email notification to admin");
        }
    }

    private static void generateReceiver() throws Exception{
        if (hasRun){
            return;
        }
        
        hasRun=true;
        try {
            System.out.println("==========================================");
            System.out.println("http://"+ REST_HOST + ":" + REST_PORT + "/admin");
            URL url = new URL("http://"+ REST_HOST + ":" + REST_PORT + "/admin");
            HttpURLConnection rest_conn = (HttpURLConnection) url.openConnection();
            rest_conn.setRequestMethod("GET");
            int responseCode = rest_conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(rest_conn.getInputStream()));) {
                    String inputLine;
                    StringBuffer response = new StringBuffer();
    
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    JSONParser parse = new JSONParser();
                    JSONArray data_obj = (JSONArray) parse.parse(response.toString());
                    Object[] arr = data_obj.toArray();
                    String email;
                    for (int i = 0; i < arr.length; i++) {
                        JSONObject new_obj = (JSONObject) arr[i];
                        email = new_obj.get("email").toString();
                        receiver.add(email);
                    }
                } catch (Exception e) {
                    System.out.println("[ERROR] " + e.getMessage());
                    throw new Exception(e.getMessage());
                }
            } else {
                System.out.println("[ERROR] HTTP Error Code: " + Integer.toString(responseCode));
            }
        } catch (Exception e) {
            System.out.println("Cant connect to Rest");
        }   
    }
}