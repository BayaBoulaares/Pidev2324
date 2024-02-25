package edu.esprit.crudoff.services;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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
            //sendEmail(email, "Sujet de l'email", "Votre code OTP est : " + otp);
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

}
