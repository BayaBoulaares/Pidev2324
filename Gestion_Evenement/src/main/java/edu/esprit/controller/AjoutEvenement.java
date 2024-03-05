package edu.esprit.controller;

import edu.esprit.entities.Evenement;
import edu.esprit.services.CredentialsManager;
import edu.esprit.services.ServiceEvenement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;

public class AjoutEvenement {

    private final ServiceEvenement serviceEvenement = new ServiceEvenement();

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
    private ImageView eventImage;

    private String imagePath;
    @FXML
    void ajouter(ActionEvent event) {
        try {
            String nomEvenement = eventName.getText();
            String lieuEvenement = eventLocation.getText();
            LocalDate dateDebut = eventStartDate.getValue();
            LocalDate dateFin = eventEndDate.getValue();
            String maxParticipantsText = maxNumber.getText();
            String descriptionEvenement = eventDescription.getText();

            if (nomEvenement.isEmpty() || lieuEvenement.isEmpty() || dateDebut == null ||
                    dateFin == null || maxParticipantsText.isEmpty() || descriptionEvenement.isEmpty()) {
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

            if (!lieuEvenement.matches("^[a-zA-ZÀ-ÿ0-9\\s]{3,}$")) {
                afficherAlerte("Le lieu de l'événement doit avoir au moins 3 caractères et ne doit pas contenir de caractères spéciaux !");
                return;
            }

            if (contientCaracteresSpeciaux(nomEvenement) || contientCaracteresSpeciaux(descriptionEvenement)) {
                afficherAlerte("Le nom et la description de l'événement ne doivent pas contenir de caractères spéciaux !");
                return;
            }

            if (imagePath == null || imagePath.isEmpty()) {
                afficherAlerte("Veuillez choisir une image !");
                return;
            }

            // Validate if the start date is before the end date
            if (dateDebut.isAfter(dateFin)) {
                afficherAlerte("La date de début doit être avant la date de fin !");
                return;
            }

            // Check if the event already exists
            boolean eventExists = serviceEvenement.eventExists(nomEvenement);
            if (eventExists) {
                afficherAlerte("Cet événement existe déjà !");
                return;
            }

            Evenement nouvelEvenement = new Evenement();
            nouvelEvenement.setNom_Event(nomEvenement);
            nouvelEvenement.setLieu_Event(lieuEvenement);
            nouvelEvenement.setDate_Debut(java.sql.Date.valueOf(dateDebut));
            nouvelEvenement.setDate_Fin(java.sql.Date.valueOf(dateFin));
            nouvelEvenement.setNb_Max(Integer.parseInt(maxParticipantsText));
            nouvelEvenement.setDescription(descriptionEvenement);
            nouvelEvenement.setImage(imagePath);

            serviceEvenement.ajouter(nouvelEvenement);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setContentText("Événement ajouté avec succès !");
            alert.showAndWait();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AfficherEvenement.fxml"));
            Parent root = loader.load();
            eventName.getScene().setRoot(root);

        } catch (SQLException | IOException e) {
            afficherAlerte("Une erreur s'est produite : " + e.getMessage());
        }
    }

    private boolean contientCaracteresSpeciaux(String text) {
        if (text.matches(".*\\d.*")) {
            afficherAlerte("Le nom de l'événement ne peut pas contenir de chiffres !");
            return true; // Contains digits
        } else if (!text.matches("[a-zA-ZÀ-ÿ\\s]*")) {
            afficherAlerte("Le nom de l'événement ne doit pas contenir de caractères spéciaux !");
            return true; // Contains special characters
        }
        return false; // No digits or special characters
    }



    @FXML
    void afficherEvenements() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Afficher_Evenement.fxml")));
        eventStartDate.getScene().setRoot(root);
    }

    @FXML
    void afficherListEvenements() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Liste_Evenement.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
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
    @FXML

    public void toReclamation(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AfficherReclamation.fxml"));
        Parent root = loader.load();

        ((Node) actionEvent.getSource()).getScene().setRoot(root);
    }
@FXML
    public void toProfe(ActionEvent actionEvent) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CrudAdmins.fxml"));
    Parent root = loader.load();
    ((Node) actionEvent.getSource()).getScene().setRoot(root);
    }
}

