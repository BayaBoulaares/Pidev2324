package edu.esprit.controller;

import edu.esprit.entities.Evenement;
import edu.esprit.services.CredentialsManager;
import edu.esprit.services.ServiceEvenement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

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
        // eventType.setValue(evenement.getStatus().toString()); // Remove this line
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
            int nombreMax;
            try {
                nombreMax = Integer.parseInt(maxNumber.getText());
            } catch (NumberFormatException ex) {
                afficherAlerte("Veuillez entrer un nombre valide pour le nombre maximum de participants.");
                return;
            }
            String description = eventDescription.getText();

            // Validate if any required field is empty
            if (nomEvenement.isEmpty() || lieuEvenement.isEmpty() || dateDebut == null ||
                    dateFin == null || description.isEmpty()) {
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

            // Validate if the start date is before the end date
            if (dateDebut.isAfter(dateFin)) {
                afficherAlerte("La date de début doit être avant la date de fin !");
                return;
            }

            // Validate lieuEvenement
            if (!lieuEvenement.matches("^[a-zA-ZÀ-ÿ0-9\\s]{3,}$")) {
                afficherAlerte("Le lieu de l'événement doit avoir au moins 3 caractères et ne doit pas contenir de caractères spéciaux !");
                return;
            }

            // Validate if the event name contains special characters or digits
            if (contientCaracteresSpeciaux(nomEvenement)) {
                return; // Stop execution if the event name contains special characters
            }

            // Validate if the description contains special characters
            if (contientCaracteresSpeciaux(description)) {
                return; // Stop execution if the description contains special characters
            }

            // Create Date objects from LocalDate
            Date dateDebutConverted = java.sql.Date.valueOf(dateDebut);
            Date dateFinConverted = java.sql.Date.valueOf(dateFin);

            // Create an Evenement object with the modified data
            Evenement evenementModifie = new Evenement(idEvenement, nomEvenement, description, lieuEvenement, dateDebutConverted, dateFinConverted, nombreMax, imagePath);

            // Get an instance of EvenementService and call the modify method
            ServiceEvenement evenementService = new ServiceEvenement();
            evenementService.modifier(evenementModifie);

            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setContentText("Événement modifié avec succès !");
            alert.showAndWait();

        } catch (SQLException e) {
            // Show error message for SQL exception
            afficherAlerte("Une erreur s'est produite lors de la mise à jour de l'événement : " + e.getMessage());
        }
    }

    private boolean contientCaracteresSpeciaux(String text) {
        if (text.matches(".*\\d.*")) {
            afficherAlerte("Le nom de l'événement et la description ne peuvent pas contenir de chiffres !");
            return true; // Contains digits
        } else if (!text.matches("[a-zA-ZÀ-ÿ\\s]*")) {
            afficherAlerte("Le nom de l'événement et la description ne peuvent pas contenirde caractères spéciaux !");
            return true; // Contains special characters
        }
        return false; // No digits or special characters
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
    @FXML
    public void deconnexion(ActionEvent actionEvent) throws IOException {
        CredentialsManager.clearCredentials();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Parent root = loader.load();
        // Utilisez getRoot() au lieu de getScene()
        ((Node) actionEvent.getSource()).getScene().setRoot(root);
    }

    @FXML
    public void toMatiere(ActionEvent actionEvent) throws IOException {
        CredentialsManager.clearCredentials();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AfficherMatierAdmin.fxml"));
        Parent root = loader.load();
        // Utilisez getRoot() au lieu de getScene()
        ((Node) actionEvent.getSource()).getScene().setRoot(root);
    }
    @FXML
    public void tohisprofile(ActionEvent actionEvent) throws IOException {
        CredentialsManager.clearCredentials();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ProfileUser.fxml"));
        Parent root = loader.load();
        // Utilisez getRoot() au lieu de getScene()
        ((Node) actionEvent.getSource()).getScene().setRoot(root);
    }
    @FXML
    public void toacceuiel(ActionEvent actionEvent) throws IOException {
        CredentialsManager.clearCredentials();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DashobardAdmin.fxml"));
        Parent root = loader.load();
        // Utilisez getRoot() au lieu de getScene()
        ((Node) actionEvent.getSource()).getScene().setRoot(root);
    }
}

