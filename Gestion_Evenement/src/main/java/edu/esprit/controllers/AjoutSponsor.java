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
            // Check if an event is selected
            Evenement selectedEvent = eventComboBox.getValue();
            if (selectedEvent == null) {
                afficherAlerte("Veuillez sélectionner un événement !");
                return;
            }

            String nomSponsor = sponsorName.getText();
            String descriptionSponsor = sponsorDescription.getText();
            Fond fondSponsor = null;

            // Vérifier que la valeur sélectionnée dans la ComboBox est valide
            String fondValue = sponsorFond.getValue();
            if (fondValue != null) {
                fondSponsor = Fond.valueOf(fondValue);
            } else {
                afficherAlerte("Veuillez sélectionner un fond pour le sponsor !");
                return;
            }

            // Validation check for sponsor name and description
            if (nomSponsor.length() < 3 || descriptionSponsor.length() < 3) {
                afficherAlerte("Le nom et la description du sponsor doivent avoir au moins 3 caractères !");
                return;
            }

            if (nomSponsor.length() > 20 || nomSponsor.matches(".*\\d.*")) {
                afficherAlerte("Le nom du sponsor ne doit pas dépasser 20 caractères et ne doit pas contenir de chiffres !");
                return;
            }

            if (nomSponsor.isEmpty() || descriptionSponsor.isEmpty() || fondSponsor == null) {
                afficherAlerte("Veuillez remplir tous les champs !");
                return;
            }

            // Create a new sponsor object
            Sponsor nouveauSponsor = new Sponsor();
            nouveauSponsor.setNomSponsor(nomSponsor);
            nouveauSponsor.setDescription_s(descriptionSponsor);
            nouveauSponsor.setFond(fondSponsor);
            nouveauSponsor.setEvenement(selectedEvent);

            // Add the sponsor to the database
            ServiceSponsor serviceSponsor = new ServiceSponsor();
            serviceSponsor.ajouter(nouveauSponsor);

            afficherAlerte("Sponsor ajouté avec succès !");

        } catch (SQLException | NumberFormatException e) {
            afficherAlerte("Une erreur de type " + e.getClass().getSimpleName() + " s'est produite lors de l'ajout du sponsor : " + e.getMessage());
        }
    }

    private void afficherAlerte(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
