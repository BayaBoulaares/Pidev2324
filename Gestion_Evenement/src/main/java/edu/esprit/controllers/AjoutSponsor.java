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

    @FXML
    void initialize() {
        // Fill the ComboBox with events on form load
        ServiceEvenement serviceEvenement = new ServiceEvenement();
        try {
            eventComboBox.setItems(FXCollections.observableArrayList(serviceEvenement.getAll()));
            // Override the default cell factory to display only event names
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
            afficherAlerte("Error loading events: " + e.getMessage());
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

            if (nomSponsor.isEmpty() || descriptionSponsor.isEmpty() || fondSponsor == null) {
                setFieldAsInvalid(sponsorName);
                setFieldAsInvalid(sponsorDescription);
                setFieldAsInvalid(sponsorFond);
                return;
            }

            setFieldAsValid(sponsorName);
            setFieldAsValid(sponsorDescription);
            setFieldAsValid(sponsorFond);

            Sponsor nouveauSponsor = new Sponsor();
            nouveauSponsor.setNomSponsor(nomSponsor);
            nouveauSponsor.setDescription_s(descriptionSponsor);
            nouveauSponsor.setFond(fondSponsor);
            nouveauSponsor.setEvenement(selectedEvent);

            ServiceSponsor serviceSponsor = new ServiceSponsor();
            serviceSponsor.ajouter(nouveauSponsor);

        } catch (SQLException | NumberFormatException e) {
            afficherAlerte("Une erreur de type " + e.getClass().getSimpleName() + " s'est produite lors de l'ajout du sponsor : " + e.getMessage());
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
