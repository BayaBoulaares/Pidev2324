package edu.esprit.crudoff.services;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.SplittableRandom;

public class SendSms {

    @FXML
    private Button sendEmailButton;

    @FXML
    private Button verifyCodeButton;

    @FXML
    private TextField loginuser;

    @FXML
    public void sendEmailAction() {
        String email = loginuser.getText();
        if (!email.isEmpty()) {
            // Générer un code OTP
            String otp = generateOtp(6); // 6 est la longueur du code OTP

            // Envoyer l'e-mail avec le code OTP
            sendEmail(email, "Sujet de l'email", "Votre code OTP est : " + otp);
        } else {
            System.out.println("Veuillez saisir une adresse e-mail.");
        }
    }

    @FXML
    public void verifyCodeAction() {
        // Vous devrez implémenter la logique pour vérifier le code OTP ici
        System.out.println("Vérification du code OTP...");
    }

    public static String generateOtp(int otpLength) {
        SplittableRandom splittableRandom = new SplittableRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < otpLength; i++) {
            sb.append(splittableRandom.nextInt(0, 10));
        }
        return sb.toString();
    }

    // Fonction pour envoyer un e-mail
    public static void sendEmail(String to, String subject, String body) {
        // Propriétés pour configurer l'envoi de l'e-mail
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Création de l'objet Session
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("baya.boulaares@esprit.tn", "upmi srhl uajk ugan");
            }
        });

        try {
            // Création de l'objet Message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("aya.boulaares@esprit.tn"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            // Envoi de l'e-mail
            Transport.send(message);

            System.out.println("E-mail envoyé avec succès !");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
