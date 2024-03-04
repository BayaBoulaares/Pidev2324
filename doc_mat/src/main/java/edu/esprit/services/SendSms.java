package edu.esprit.services;

import edu.esprit.utils.DataSource;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private PasswordField code;
    private String generatedOtp;
    Connection cnx = DataSource.getInstance().getCnx();

    private static final String FILENAME = "credentials2.txt";


    public static void saveCredentials(String login) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILENAME))) {

            writer.println(login);

            System.out.println(login);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String[] loadCredentials() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String login = reader.readLine();

            return new String[]{login};
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @FXML
    public void sendEmailAction() throws IOException {
        String email = loginuser.getText();
        saveCredentials(email);
        if (!email.isEmpty()) {
            // Vérifier si l'email existe dans la base de données et si l'utilisateur a le rôle de parent
            if (emailExistsInDatabase(email)) {
                // Générer un nouveau code OTP aléatoire à chaque envoi d'email
                String otp = generateOtp(6); // 6 est la longueur du code OTP
                sendEmail(email, "Sujet de l'email", "Votre code OTP est : " + otp);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Validation");
                alert.setContentText("Un nouvel e-mail contenant le code OTP a été envoyé avec succès à l'adresse indiquée");
                alert.showAndWait();

                // Stocker le nouveau code OTP dans une variable globale ou un champ de classe pour y accéder plus tard
                this.generatedOtp = otp; // Stocker le nouveau code OTP généré dans une variable globale
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Validation");
                alert.setContentText("Veuillez saisir une adresse e-mail valide associée à un parent");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation");
            alert.setContentText("Champs de l'adresse-email est manquant ");
            alert.showAndWait();
        }
    }

    @FXML
    public void verifyCodeAction() throws IOException {
        String enteredCode = code.getText(); // Récupérer le code entré par l'utilisateur

        if (!enteredCode.isEmpty()) {
            // Comparer le code entré par l'utilisateur avec le code OTP généré précédemment
            if (enteredCode.equals(this.generatedOtp)) {
                // Code correct, vous pouvez autoriser l'accès à la fonctionnalité souhaitée
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Validation");
                alert.setContentText("Le code OTP entré est incorrect. Veuillez réessayer. ");
                alert.showAndWait();

                // Ajoutez ici le code pour rediriger l'utilisateur vers la fonctionnalité souhaitée
                FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/fxml/MotDePasseOublie.fxml"));
                Parent root2 = loader2.load();
                loginuser.getScene().setRoot(root2);
            } else {
                // Code incorrect, afficher un message d'erreur
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Validation");
                alert.setContentText("Le code OTP entré est incorrect. Veuillez réessayer. ");
                alert.showAndWait();
            }
        } else {
            // Si aucun code n'a été entré, afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation");
            alert.setContentText("Veuillez saisir le code OTP reçu par e-mail. ");
            alert.showAndWait();
        }
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
            message.setFrom(new InternetAddress("baya.boulaares@esprit.tn"));
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

    private boolean emailExistsInDatabase(String email) {
        // Intégrer ici la logique pour interroger la base de données et vérifier si l'email existe
        try {

            PreparedStatement statement = cnx.prepareStatement("SELECT COUNT(*) FROM utilisateurs WHERE login = ? and role='Parent'");
            ((PreparedStatement) statement).setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; // Si count > 0, l'email existe dans la base de données
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Renvoyer false en cas d'erreur ou si l'email n'existe pas
    }
}
