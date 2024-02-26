package edu.esprit.controllers;

import edu.esprit.entities.Evenement;
import edu.esprit.entities.Status;
import edu.esprit.services.ServiceEvenement;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ModifierEvenement {
    @FXML
    private TextField eventName;
    @FXML
    private DatePicker eventStartDate;
    @FXML
    private TextField eventLocation;
    @FXML
    private DatePicker eventEndDate;
    @FXML
    private ComboBox<String> eventType;
    @FXML
    private TextField maxNumber;
    @FXML
    private TextArea eventDescription;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ImageView eventImage;

    // Add a field to store the event's image path
    private String imagePath;

    // Add a field to store the event ID
    private int idEvenement;

    // Interface for informing AfficherEvenement that modification is complete
    public interface ModificationCallback {
        void onModificationComplete();
    }

    private ModificationCallback modificationCallback;

    // Method to set the callback
    public void setModificationCallback(ModificationCallback callback) {
        this.modificationCallback = callback;
    }

    @FXML
    public void setEventData(Evenement evenement) {
        idEvenement = evenement.getId_Event();

        eventName.setText(evenement.getNom_Event());
        eventStartDate.setValue(
                Instant.ofEpochMilli(evenement.getDate_Debut().getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
        );
        eventLocation.setText(evenement.getLieu_Event());
        eventEndDate.setValue(
                Instant.ofEpochMilli(evenement.getDate_Fin().getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
        );
        eventType.setValue(evenement.getStatus().toString());
        maxNumber.setText(String.valueOf(evenement.getNb_Max()));
        eventDescription.setText(evenement.getDescription());

        // Set the event image
        File file = new File(evenement.getImage());
        if (file.exists()) {
            try {
                // Load the image from the file
                Image image = new Image(file.toURI().toString());
                eventImage.setImage(image);
                imagePath = evenement.getImage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void saveEventData(ActionEvent event) {
        try {
            // Retrieve event details from the form fields
            String nomEvenement = eventName.getText();
            LocalDate dateDebut = eventStartDate.getValue();
            LocalDate dateFin = eventEndDate.getValue();
            String lieuEvenement = eventLocation.getText();
            String typeEvenement = eventType.getValue();
            int nombreMax = Integer.parseInt(maxNumber.getText());
            String description = eventDescription.getText();

            // Validate if any required field is empty
            if (nomEvenement.isEmpty() || lieuEvenement.isEmpty() || dateDebut == null ||
                    dateFin == null || typeEvenement == null || description.isEmpty()) {
                afficherAlerte("Veuillez remplir tous les champs !");
                return;
            }

            // Validate event name length
            if (nomEvenement.length() > 22) {
                afficherAlerte("Le nom de l'événement ne peut pas dépasser 22 caractères !");
                return;
            }

            // Validate event and description length
            if (description.length() < 3 || nomEvenement.length() < 3) {
                afficherAlerte("Le nom et la description de l'événement doivent avoir au moins 3 caractères !");
                return;
            }

            // Convert LocalDate to Date
            Date dateDebutConverted = java.sql.Date.valueOf(dateDebut);
            Date dateFinConverted = java.sql.Date.valueOf(dateFin);

            // Convert string typeEvenement to Status
            Status status = (typeEvenement != null && !typeEvenement.isEmpty()) ? Status.valueOf(typeEvenement) : null;

            // Create an Evenement object with the modified data
            Evenement evenementModifie = new Evenement(idEvenement, nomEvenement, description, lieuEvenement, dateDebutConverted, dateFinConverted, nombreMax, status, imagePath);

            // Get an instance of EvenementService and call the modify method
            ServiceEvenement evenementService = new ServiceEvenement();
            evenementService.modifier(evenementModifie);

            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setContentText("Événement modifié avec succès !");
            alert.showAndWait();

        } catch (NumberFormatException e) {
            // Show error message for invalid number format
            afficherAlerte("Veuillez entrer un nombre valide pour le nombre maximum de participants.");
        } catch (IllegalArgumentException e) {
            // Show error message for invalid event type
            afficherAlerte("Veuillez sélectionner un type d'événement.");
        } catch (SQLException e) {
            // Show error message for SQL exception
            afficherAlerte("Une erreur s'est produite lors de la mise à jour de l'événement : " + e.getMessage());
        }
    }

    @FXML
    void selectImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(((Button) event.getSource()).getScene().getWindow());
        if (selectedFile != null) {
            try {
                Image image = new Image(selectedFile.toURI().toString()); // Corrected line
                eventImage.setImage(image);
                imagePath = selectedFile.getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void afficherAlerte(String message) {
        // Display an alert with the given message
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
