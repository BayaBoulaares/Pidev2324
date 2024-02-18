package edu.esprit.controllers;

import edu.esprit.entities.Evenement;
import edu.esprit.entities.Status;
import edu.esprit.services.ServiceEvenement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.SQLException;

public class AjoutEvenement {

    private final ServiceEvenement serviceEvenement = new ServiceEvenement();

    @FXML
    private Button addEventButton;

    @FXML
    private ComboBox<String> eventType;

    @FXML
    private DatePicker eventStartDate;

    @FXML
    private DatePicker eventEndDate;

    @FXML
    private TextField eventName;

    @FXML
    private TextField eventLocation;

    @FXML
    private TextField maxNumber;

    @FXML
    private TextArea eventDescription;

    @FXML
    void ajouter(ActionEvent event) {
        try {
            Evenement nouvelEvenement = new Evenement();
            nouvelEvenement.setNom_Event(eventName.getText());
            nouvelEvenement.setLieu_Event(eventLocation.getText());
            nouvelEvenement.setDate_Debut(java.sql.Date.valueOf(eventStartDate.getValue()));
            nouvelEvenement.setDate_Fin(java.sql.Date.valueOf(eventEndDate.getValue()));
            nouvelEvenement.setNb_Max(Integer.parseInt(maxNumber.getText()));
            nouvelEvenement.setDescription(eventDescription.getText());
            nouvelEvenement.setStatus(Status.valueOf(eventType.getValue().toUpperCase()));

            serviceEvenement.ajouter(nouvelEvenement);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setContentText("Événement ajouté avec succès !");
            alert.showAndWait();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEvenement.fxml"));
            Parent root = loader.load();
            eventName.getScene().setRoot(root);
        } catch (SQLException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Une erreur s'est produite : " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void afficherEvenements(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Afficher_Evenement.fxml"));
        Parent root = loader.load();
        AfficherEvenement controller = loader.getController();
        controller.initialize();

        // Set the root of the scene to the loaded FXML root
        ((Node) event.getSource()).getScene().setRoot(root);
    }



}
