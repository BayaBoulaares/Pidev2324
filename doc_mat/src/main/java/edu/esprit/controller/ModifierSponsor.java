package edu.esprit.controller;

import edu.esprit.entities.Sponsor;
import edu.esprit.entities.Fond;
import edu.esprit.entities.Evenement; // Import the Evenement class
import edu.esprit.services.CredentialsManager;
import edu.esprit.services.ServiceSponsor;
import edu.esprit.services.ServiceEvenement; // Import the service for events
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
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
    private ImageView sponsorImage;


    private int sponsorId;
    private String imagePath;

    public void setSponsorData(Sponsor sponsor) {
        sponsorId = sponsor.getId_Sponsor();

        sponsorName.setText(sponsor.getNomSponsor());
        sponsorDescription.setText(sponsor.getDescription_s());
        sponsorFond.setValue(sponsor.getFond());

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
            Collection<Evenement> events = serviceEvent.getAll();

            ObservableList<String> eventNames = FXCollections.observableArrayList();

            for (Evenement event : events) {
                eventNames.add(event.getNom_Event());
            }

            eventComboBox.setItems(eventNames);
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des événements : " + e.getMessage());
            e.printStackTrace(); // Optional: Print stack trace for debugging
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
                afficherAlerte("Remplir tout les champs.");
                return;
            }

            // Validate name and description for length and special characters
            if (!validateInput(newName, newDescription)) {
                return;
            }

            Evenement selectedEvent = null;
            ServiceEvenement serviceEvent = new ServiceEvenement();
            Collection<Evenement> events = serviceEvent.getAll();
            for (Evenement event : events) {
                if (event.getNom_Event().equals(selectedEventName)) {
                    selectedEvent = event;
                    break;
                }
            }

            if (selectedEvent == null) {
                afficherAlerte("l'élément n'est pas trouvé.");
                return;
            }

            Sponsor updatedSponsor = new Sponsor(sponsorId, newName, newDescription, newFond, selectedEvent);
            updatedSponsor.setImage(imagePath);

            ServiceSponsor sponsorService = new ServiceSponsor();
            sponsorService.modifier(updatedSponsor);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setContentText("Sponsor Modifié!");
            alert.showAndWait();
        } catch (SQLException e) {
            afficherAlerte("Erreur lors de la modification du sponsor : " + e.getMessage());
        }
    }


    private boolean validateInput(String name, String description) {
        if (name.matches(".*\\d.*")) {
            afficherAlerte("Le nom de sponsor ne peut pas contenir de chiffres !");
            return false; // Contains digits
        } else if (!name.matches("[a-zA-ZÀ-ÿ\\s]*")) {
            afficherAlerte("Le nom de sponsor ne doit pas contenir de caractères spéciaux !");
            return false; // Contains special characters
        } else if (description.matches(".*\\d.*")) {
            afficherAlerte("La description de sponsor ne peut pas contenir de chiffres !");
            return false; // Contains digits
        } else if (!description.matches("[a-zA-ZÀ-ÿ\\s]*")) {
            afficherAlerte("La description de sponsor ne doit pas contenir de caractères spéciaux !");
            return false; // Contains special characters
        }
        return true; // Valid input
    }



    private void afficherAlerte(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
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
