package edu.esprit.controllers;

import edu.esprit.entities.Evenement;
import edu.esprit.entities.Fond;
import edu.esprit.entities.Sponsor;
import edu.esprit.services.ServiceEvenement;
import edu.esprit.services.ServiceSponsor;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.SQLException;

public class AjoutSponsor {

    @FXML
    private TextField sponsorName;

    @FXML
    private TextField sponsorDescription;

    @FXML
    private ComboBox<Evenement> eventComboBox;

    @FXML
    private ComboBox<String> sponsorFond;

    private String imagePath;


    @FXML
    void initialize() {
        // Remplir la ComboBox avec les événements lors du chargement du formulaire
        ServiceEvenement serviceEvenement = new ServiceEvenement();
        try {
            eventComboBox.setItems(FXCollections.observableArrayList(serviceEvenement.getAll()));
            // Remplacer la cellule par défaut pour afficher uniquement les noms des événements
            eventComboBox.setCellFactory(param -> new ListCell<Evenement>() {
                @Override
                protected void updateItem(Evenement item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getNom_Event());
                    }
                }
            });
        } catch (SQLException e) {
            afficherAlerte("Erreur lors du chargement des événements : " + e.getMessage());
        }
    }

    @FXML
    void ajouter() {
        try {
            Evenement selectedEvent = eventComboBox.getValue();
            if (selectedEvent == null) {
                setFieldAsInvalid(eventComboBox);
                return;
            }

            String nomSponsor = sponsorName.getText();
            String descriptionSponsor = sponsorDescription.getText();
            Fond fondSponsor = null;

            String fondValue = sponsorFond.getValue();
            if (fondValue != null) {
                fondSponsor = Fond.valueOf(fondValue);
            } else {
                setFieldAsInvalid(sponsorFond);
                return;
            }

            if (nomSponsor.length() < 3 || descriptionSponsor.length() < 3) {
                setFieldAsInvalid(sponsorName);
                setFieldAsInvalid(sponsorDescription);
                return;
            }

            if (nomSponsor.length() > 20 || nomSponsor.matches(".*\\d.*")) {
                setFieldAsInvalid(sponsorName);
                return;
            }

            if (nomSponsor.isEmpty() || descriptionSponsor.isEmpty() || fondSponsor == null || imagePath.isEmpty()) {
                setFieldAsInvalid(sponsorName);
                setFieldAsInvalid(sponsorDescription);
                setFieldAsInvalid(sponsorFond);
                afficherAlerte("Veuillez sélectionner une image pour le sponsor.");
                return;
            }

            setFieldAsValid(sponsorName);
            setFieldAsValid(sponsorDescription);
            setFieldAsValid(sponsorFond);

            // Créer un nouvel objet Sponsor avec les informations fournies
            Sponsor nouveauSponsor = new Sponsor();
            nouveauSponsor.setNomSponsor(nomSponsor);
            nouveauSponsor.setDescription_s(descriptionSponsor);
            nouveauSponsor.setFond(fondSponsor);
            nouveauSponsor.setEvenement(selectedEvent);
            nouveauSponsor.setImage(imagePath); // Définir le chemin de l'image

            // Appeler le service pour ajouter le nouveau sponsor
            ServiceSponsor serviceSponsor = new ServiceSponsor();
            serviceSponsor.ajouter(nouveauSponsor);

            afficherAlerte("Sponsor ajouté avec succès");

            // Réinitialiser les champs après l'ajout
            sponsorName.clear();
            sponsorDescription.clear();
            eventComboBox.getSelectionModel().clearSelection();
            sponsorFond.getSelectionModel().clearSelection();
            imagePath = ""; // Réinitialiser le chemin de l'image

        } catch (SQLException | NumberFormatException e) {
            afficherAlerte("Une erreur de type " + e.getClass().getSimpleName() + " s'est produite lors de l'ajout du sponsor : " + e.getMessage());
        }
    }

    @FXML
    void selectImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            imagePath = selectedFile.getAbsolutePath();
        }
    }

    private void setFieldAsValid(TextField field) {
        field.getStyleClass().removeAll("invalid-field");
        field.getStyleClass().add("valid-field");
    }

    private void setFieldAsInvalid(TextField field) {
        field.getStyleClass().removeAll("valid-field");
        field.getStyleClass().add("invalid-field");
        field.setPromptText("Champ invalide");
    }

    private void setFieldAsValid(ComboBox<?> field) {
        field.getStyleClass().removeAll("invalid-field");
        field.getStyleClass().add("valid-field");
    }

    private void setFieldAsInvalid(ComboBox<?> field) {
        field.getStyleClass().removeAll("valid-field");
        field.getStyleClass().add("invalid-field");
        field.setPromptText("Champ invalide");
    }

    private void afficherAlerte(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
