package edu.esprit.crudoff.services;

import edu.esprit.crudoff.entities.ParentE;
import edu.esprit.crudoff.utilis.DataSource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;

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
            statement.setString(1, newPassword);
            statement.setString(2, email);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0; // Vérifiez si au moins une ligne a été modifiée
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Renvoyer false en cas d'erreur lors de la mise à jour
    }


}
