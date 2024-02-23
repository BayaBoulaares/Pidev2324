package edu.esprit.controller;

import edu.esprit.entities.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import edu.esprit.services.SeviceMatiere;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;

public class ModifierMatiereController implements Initializable {

    @FXML
    private ComboBox<CAT> idcat;
    @FXML
    private ComboBox<String> idannee;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
      idannee.getItems().addAll("1 ere année","2 éme année","3 éme année","4  éme annee","5  éme année","6  éme année");
      idcat.getItems().addAll(CAT.values());

    }

    public void setMatiereToModify(Matiere matiere) {
        idnom.setText(matiere.getNommatiere());
        iddesc.setText(matiere.getDescription());
        id=matiere.getId();
        idannee.getItems().add(matiere.getAnnee());
        idcat.getItems().add(matiere.getCategorie());
        // Vous pouvez également stocker la matière dans une variable de classe pour une utilisation ultérieure lors de la modification
    }

    public void ModifierMatiere(ActionEvent actionEvent) {

        if (validateInput()) {
            try {


                    this.MS.modifier(new Matiere(id,this.idnom.getText(), this.iddesc.getText(),idannee.getValue(),idcat.getValue()));
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
        String anne=idannee.getValue();
        CAT categorie=idcat.getValue();

        // Check if nom contains only letters
        boolean isNomValid = nom.matches("[a-zA-Z]+");

        return isNomValid && nom.length() >= 3 && desc.length() >= 5 && !nom.isEmpty() && !desc.isEmpty() &&  !anne.isEmpty() && categorie != null;

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