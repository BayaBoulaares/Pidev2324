package edu.esprit.controller;

import edu.esprit.entities.ExistanteException;
import edu.esprit.entities.Matiere;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import edu.esprit.services.SeviceMatiere;

import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;

public class ModifierMatiereController {

    @FXML
    private Button idcon;

    @FXML
    private TextArea iddesc;

    @FXML
    private TextField idnom;

    @FXML
    private Button idretour;
    private  int id;
    private final SeviceMatiere MS = new SeviceMatiere();

    public void setMatiereToModify(Matiere matiere) {
        idnom.setText(matiere.getNommatiere());
        iddesc.setText(matiere.getDescription());
        id=matiere.getId();


        // Vous pouvez également stocker la matière dans une variable de classe pour une utilisation ultérieure lors de la modification
    }

    public void ModifierMatiere(ActionEvent actionEvent) {

        if (validateInput()) {
            try {


                    this.MS.modifier(new Matiere(id,this.idnom.getText(), this.iddesc.getText()));
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Validation");
                    alert.setContentText("Matiere updated successfully");
                    alert.showAndWait();
                    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/AfficherMatiere.fxml"));
                    Parent root = (Parent) loader.load();
                    this.idnom.getScene().setRoot(root);

            } catch (SQLException | IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erreur", e.getMessage());
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Veuillez remplir tous les champs!");
        }
    }

    @FXML
    private boolean validateInput() {
        String nom = idnom.getText();
        String desc = iddesc.getText();

        return nom.length() >= 3 && desc.length() >= 5 && !nom.isEmpty() && !desc.isEmpty();
    }

    @FXML
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    public void RetourAffichage(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/AfficherMatiere.fxml"));
            Parent root = (Parent) loader.load();
            this.idnom.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur de chargement", "Impossible de charger la page d'affichage");
        }
    }

}