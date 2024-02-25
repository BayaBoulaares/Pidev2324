package edu.esprit.controllers;

import edu.esprit.entities.Sponsor;
import edu.esprit.entities.Fond;
import edu.esprit.entities.Evenement; // Importez la classe Evenement
import edu.esprit.services.ServiceSponsor;
import edu.esprit.services.ServiceEvenement; // Importez le service pour les événements
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

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

    private int sponsorId;

    public void setSponsorData(Sponsor sponsor) {
        sponsorId = sponsor.getId_Sponsor();

        sponsorName.setText(sponsor.getNomSponsor());
        sponsorDescription.setText(sponsor.getDescription_s());

        sponsorFond.setValue(sponsor.getFond());

        populateEventComboBox();
    }

    private void populateEventComboBox() {
        try {
            ServiceEvenement serviceEvent = new ServiceEvenement();
            List<Evenement> events = serviceEvent.getAll();

            // Créez une liste pour stocker les noms des événements
            ObservableList<String> eventNames = FXCollections.observableArrayList();

            // Ajoutez les noms des événements à la liste
            for (Evenement event : events) {
                eventNames.add(event.getNom_Event());
            }

            eventComboBox.setItems(eventNames);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void saveSponsorData() {
        try {
            String newName = sponsorName.getText();
            String newDescription = sponsorDescription.getText();
            Fond newFond = sponsorFond.getValue();
            String selectedEventName = eventComboBox.getValue();

            if (newName == null || newName.isEmpty() || newFond == null || selectedEventName == null) {
                afficherAlerte("Veuillez remplir tous les champs obligatoires.");
                return;
            }

            if (newName.length() < 3 || newDescription.length() < 3) {
                afficherAlerte("Le nom et la description du sponsor doivent avoir au moins 3 caractères !");
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
                // If the selected event is not found, show an error message
                afficherAlerte("L'événement sélectionné est introuvable.");
                return;
            }

            Sponsor updatedSponsor = new Sponsor(sponsorId, newName, newDescription, newFond, selectedEvent);

            ServiceSponsor sponsorService = new ServiceSponsor();
            sponsorService.modifier(updatedSponsor);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setContentText("Sponsor mis à jour avec succès !");
            alert.showAndWait();
        } catch (SQLException e) {
            afficherAlerte("Erreur lors de la mise à jour du sponsor : " + e.getMessage());
        }
    }

    private void afficherAlerte(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText(message);
        alert.showAndWait();
    }


}
