package edu.esprit.controllers;
import edu.esprit.entities.Evenement;
import edu.esprit.entities.Status;
import edu.esprit.services.ServiceEvenement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class AjoutEvenement {

    private final ServiceEvenement serviceEvenement = new ServiceEvenement();

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
            String nomEvenement = eventName.getText();
            String lieuEvenement = eventLocation.getText();
            LocalDate dateDebut = eventStartDate.getValue();
            LocalDate dateFin = eventEndDate.getValue();
            String maxParticipantsText = maxNumber.getText();
            String descriptionEvenement = eventDescription.getText();
            String typeEvenement = eventType.getValue();

            if (nomEvenement.isEmpty() || lieuEvenement.isEmpty() || dateDebut == null ||
                    dateFin == null || maxParticipantsText.isEmpty() || descriptionEvenement.isEmpty() ||
                    typeEvenement == null) {
                afficherAlerte("Veuillez remplir tous les champs !");
                return;
            }

            if (nomEvenement.length() > 22) {
                afficherAlerte("Le nom de l'événement ne peut pas dépasser 22 caractères !");
                return;
            }

            if (descriptionEvenement.length() < 3 || nomEvenement.length() < 3) {
                afficherAlerte("Le nom et la description de l'événement doivent avoir au moins 3 caractères !");
                return;
            }

            if (contientChiffres(nomEvenement)) {
                afficherAlerte("Le nom de l'événement ne peut pas contenir de chiffres !");
                return;
            }

            // Le reste de votre logique d'ajout d'événement ici...
            Evenement nouvelEvenement = new Evenement();
            nouvelEvenement.setNom_Event(nomEvenement);
            nouvelEvenement.setLieu_Event(lieuEvenement);
            nouvelEvenement.setDate_Debut(java.sql.Date.valueOf(dateDebut));
            nouvelEvenement.setDate_Fin(java.sql.Date.valueOf(dateFin));
            nouvelEvenement.setNb_Max(Integer.parseInt(maxParticipantsText));
            nouvelEvenement.setDescription(descriptionEvenement);
            nouvelEvenement.setStatus(Status.valueOf(typeEvenement.toUpperCase()));

            serviceEvenement.ajouter(nouvelEvenement);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setContentText("Événement ajouté avec succès !");
            alert.showAndWait();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEvenement.fxml"));
            Parent root = loader.load();
            eventName.getScene().setRoot(root);

        } catch (SQLException | IOException e) {
            afficherAlerte("Une erreur s'est produite : " + e.getMessage());
        }
    }

    @FXML
    void afficherEvenements() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Afficher_Evenement.fxml"));
        eventStartDate.getScene().setRoot(root);
    }
    @FXML
    void afficherListEvenements() {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Liste_Evenement.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private boolean contientChiffres(String str) {
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    private void afficherAlerte(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText(message);
        alert.showAndWait();
    }
}




