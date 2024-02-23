package edu.esprit.controller;

import edu.esprit.entities.CAT;
import edu.esprit.entities.ExistanteException;
import edu.esprit.entities.Matiere;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import edu.esprit.services.SeviceMatiere;

import java.io.IOException;
import java.sql.SQLException;

public class MatiereController {
    @FXML
    private ComboBox<CAT> idcat;
    @FXML
    private ComboBox<String> idannee;

    @FXML
    private Button idc;
    @FXML
    private Button idcon;

    @FXML
    private TextArea iddesc;

    @FXML
    private TextField idnom;
    private final SeviceMatiere MS = new SeviceMatiere();
    @FXML
    public void initialize() {
        // Initialize the ComboBox with the desired items
        idannee.getItems().addAll("1 ere année", "2 éme année", "3 éme année", "4 éme annee", "5 éme année", "6 éme année");
        idcat.getItems().addAll(CAT.SCIENTIFIQUE, CAT.LANGUE, CAT.HISTOIRE, CAT.GEOGRAPHIE);
    }

    public  MatiereController(){

    }
    @FXML
    public void RetourAffichage(javafx.event.ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/AfficherMatiere.fxml"));
            Parent root = (Parent) loader.load();
            this.idnom.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur de chargement", "Impossible de charger la page d'affichage");
        }
    }


    @FXML
    public void ajouterMatiere(javafx.event.ActionEvent actionEvent) {
        if (validateInput()) {
            try {
                this.MS.ajouter(new Matiere(this.idnom.getText(), this.iddesc.getText(),this.idannee.getValue(),this.idcat.getValue()));

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Validation");
                alert.setContentText("Person added succesfully");
                alert.showAndWait();
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/AfficherMatiere.fxml"));
                Parent root = (Parent)loader.load();
                this.idnom.getScene().setRoot(root);
            } catch (ExistanteException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", e.getMessage());
            }catch (SQLException var5) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("SQL Exeption");
                alert.setContentText(var5.getMessage());
                alert.showAndWait();
            }  catch (IOException var6) {
                throw new RuntimeException(var6);
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


    public void consulterMatiere(ActionEvent actionEvent) {
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherMatiere.fxml"));
        Parent root = loader.load();
        idc.getScene().setRoot(root);
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
}
