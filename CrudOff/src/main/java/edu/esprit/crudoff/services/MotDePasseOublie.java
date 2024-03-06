package edu.esprit.crudoff.services;

import edu.esprit.crudoff.utilis.DataSource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MotDePasseOublie {

    @FXML
    private Button cnvmdp;

    @FXML
    private PasswordField confirmermdp;

    @FXML
    private PasswordField nvmdp;
    Connection cnx = DataSource.getInsatnce().getConnection();
    PasswordField passwordField = new PasswordField();
    Label strengthLabel = new Label();

    @FXML
    private Label fmdp;



    @FXML
    private ToggleButton shownmdp;
    @FXML
    void forcemdp(KeyEvent event) {

    }

    @FXML
    void toggmeButton(ActionEvent event) {
        if(shownmdp.isSelected())
        {
        }
        else {

        }

    }


    private int calculatePasswordStrength(String password) {
        int strength = 0;

        // Longueur du mot de passe
        int length = password.length();
        if (length >= 8) {
            strength += 10;
        } else if (length >= 6) {
            strength += 5;
        }

        // Vérifier la présence de chiffres
        if (password.matches(".*\\d.*")) {
            strength += 10;
        }

        // Vérifier la présence de caractères spéciaux
        if (password.matches(".*[!@#$%^&*()-_+=?/<>,.].*")) {
            strength += 10;
        }

        // Vérifier la présence de lettres majuscules et minuscules
        if (password.matches(".*[A-Z].*") && password.matches(".*[a-z].*")) {
            strength += 10;
        }

        return strength;
    }
    @FXML
    private void initialize() {
        // Ajout de l'événement setOnKeyReleased pour appeler updatePasswordFieldStyle
        //passwordField.setOnKeyTyped(event -> updatePasswordFieldStyle(passwordField));
        // Création d'une liaison bidirectionnelle avec la propriété text du champ de mot de passe
        // Ajout d'un écouteur sur la propriété text du champ de mot de passe
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            updatePasswordFieldStyle(passwordField);
        });


    }
    // Méthode pour mettre à jour le style du champ de mot de passe en fonction de la force du mot de passe
    private void updatePasswordFieldStyle(PasswordField passwordField) {
        String password = passwordField.getText();
        int strength = calculatePasswordStrength(password);

        if (strength < 50) {
            passwordField.setStyle("-fx-control-inner-background: #FFC0CB;"); // Couleur rouge pour un mot de passe faible
            strengthLabel.setText("Faible");
        } else if (strength < 80) {
            passwordField.setStyle("-fx-control-inner-background: #FFA500;"); // Couleur orange pour un mot de passe moyen
            strengthLabel.setText("Moyen");
        } else {
            passwordField.setStyle("-fx-control-inner-background: #98FB98;"); // Couleur verte pour un mot de passe fort
            strengthLabel.setText("Fort");
        }
    }

    @FXML
    void confirmernvmdp(ActionEvent event) {
        CredentialsManager crd = new CredentialsManager();
        String[] crds = SendSms.loadCredentials();
      String nouvmdp =nvmdp.getText();
      String cnouvmdp=confirmermdp.getText();

        if (nouvmdp.equals(cnouvmdp)) {

            SendSms sm = new SendSms();
            String email = SendSms.loadCredentials()[0];
            if (!email.isEmpty()) {
                // Mettre à jour le mot de passe dans la base de données
                if (updatePasswordInDatabase(email, nouvmdp)) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Validation");
                    alert.setContentText("Votre mot de passe a été changé avec succès.");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Validation");
                    alert.setContentText("Une erreur s'est produite lors du changement de mot de passe. Veuillez réessayer.");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Validation");
                alert.setContentText("Impossible de récupérer votre adresse e-mail.");
                alert.showAndWait();
            }
        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation");
            alert.setContentText("Les deux mots de passe saisis ne correspondent pas. Veuillez réessayer.");
            alert.showAndWait();
        }

    }
    private boolean updatePasswordInDatabase(String email, String newPassword) {
        try {
            PreparedStatement statement = cnx.prepareStatement("UPDATE utilisateurs SET mdp = ? WHERE login = ?");
            String hashedPassword = newPassword;
            try {
                hashedPassword = hashString("password");
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }

            statement.setString(1, hashedPassword);
            statement.setString(2, email);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0; // Vérifiez si au moins une ligne a été modifiée
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Renvoyer false en cas d'erreur lors de la mise à jour
    }
    public static String hashString(String input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(input.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }


}
