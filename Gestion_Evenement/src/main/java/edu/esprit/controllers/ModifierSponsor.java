package edu.esprit.controllers;

import edu.esprit.entities.Sponsor;
import edu.esprit.entities.Fond;
import edu.esprit.entities.Evenement; // Import the Evenement class
import edu.esprit.services.ServiceSponsor;
import edu.esprit.services.ServiceEvenement; // Import the service for events
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class ModifierSponsor {

    @FXML
    private TextField sponsorName;
    @FXML
    private TextField sponsorDescription;
    @FXML
    private ComboBox<Fond> sponsorFond;
    @FXML
    private ComboBox<String> eventComboBox;
    @FXML
    private ImageView sponsorImage; // ImageView for displaying the sponsor image


    private int sponsorId;
    private String imagePath; // Store the selected image path

    public void setSponsorData(Sponsor sponsor) {
        sponsorId = sponsor.getId_Sponsor();

        sponsorName.setText(sponsor.getNomSponsor());
        sponsorDescription.setText(sponsor.getDescription_s());
        sponsorFond.setValue(sponsor.getFond());

        // Set the selected image
        imagePath = sponsor.getImage();
        if (imagePath != null && !imagePath.isEmpty()) {
            File file = new File(imagePath);
            if (file.exists()) {
                Image image = new Image(file.toURI().toString());
                sponsorImage.setImage(image);
            }
        }

        populateEventComboBox();
    }

    private void populateEventComboBox() {
        try {
            ServiceEvenement serviceEvent = new ServiceEvenement();
            List<Evenement> events = serviceEvent.getAll();

            // Create a list to store event names
            ObservableList<String> eventNames = FXCollections.observableArrayList();

            // Add event names to the list
            for (Evenement event : events) {
                eventNames.add(event.getNom_Event());
            }

            eventComboBox.setItems(eventNames);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void selectSponsorImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Sponsor Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                Image image = new Image(selectedFile.toURI().toString());
                sponsorImage.setImage(image);
                imagePath = selectedFile.getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
                afficherAlerte("Error loading image: " + e.getMessage());
            }
        }
    }

    @FXML
    private void saveSponsorData() {
        try {
            String newName = sponsorName.getText();
            String newDescription = sponsorDescription.getText();
            Fond newFond = sponsorFond.getValue();
            String selectedEventName = eventComboBox.getValue();

            // Check for empty fields
            if (newName == null || newName.isEmpty() || newFond == null || selectedEventName == null) {
                afficherAlerte("Please fill in all required fields.");
                return;
            }

            // Check name and description length
            if (newName.length() < 3 || newDescription.length() < 3) {
                afficherAlerte("Sponsor name and description must be at least 3 characters long!");
                return;
            }

            // Find the selected event by name
            Evenement selectedEvent = null;
            ServiceEvenement serviceEvent = new ServiceEvenement();
            List<Evenement> events = serviceEvent.getAll();
            for (Evenement event : events) {
                if (event.getNom_Event().equals(selectedEventName)) {
                    selectedEvent = event;
                    break;
                }
            }

            if (selectedEvent == null) {
                afficherAlerte("The selected event could not be found.");
                return;
            }

            Sponsor updatedSponsor = new Sponsor(sponsorId, newName, newDescription, newFond, selectedEvent);
            updatedSponsor.setImage(imagePath); // Set the updated image path

            ServiceSponsor sponsorService = new ServiceSponsor();
            sponsorService.modifier(updatedSponsor);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Sponsor updated successfully!");
            alert.showAndWait();
        } catch (SQLException e) {
            afficherAlerte("Error updating sponsor: " + e.getMessage());
        }
    }

    private void afficherAlerte(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
