package edu.esprit.controllers;

import edu.esprit.entities.Evenement;
import edu.esprit.entities.Status;
import edu.esprit.services.ServiceEvenement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import javafx.event.ActionEvent;
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

    // Ajoutez un champ pour stocker l'ID de l'événement
    private int idEvenement;

    // Interface de rappel pour informer AfficherEvenement que la modification est terminée
    public interface ModificationCallback {
        void onModificationComplete();
    }

    private ModificationCallback modificationCallback;

    // Méthode pour définir le callback
    public void setModificationCallback(ModificationCallback callback) {
        this.modificationCallback = callback;
    }

    @FXML
    public void setEventData(Evenement evenement) {
        // Mettez à jour l'ID de l'événement
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
    }

    @FXML
    private void saveEventData(ActionEvent event) {
        try {
            // Récupére
            // r les données modifiées depuis les champs
            String nomEvenement = eventName.getText();
            LocalDate dateDebut = eventStartDate.getValue();
            LocalDate dateFin = eventEndDate.getValue();
            String lieuEvenement = eventLocation.getText();
            String typeEvenement = eventType.getValue();
            int nombreMax = Integer.parseInt(maxNumber.getText());
            String description = eventDescription.getText();

            // Convertir les LocalDate en Date
            Date dateDebutConverted = java.sql.Date.valueOf(dateDebut);
            Date dateFinConverted = java.sql.Date.valueOf(dateFin);

            // Convertir le String typeEvenement en Status
            Status status = (typeEvenement != null && !typeEvenement.isEmpty()) ? Status.valueOf(typeEvenement) : null;

            // Créer un nouvel objet Evenement avec les données modifiées
            Evenement evenementModifie = new Evenement(idEvenement, nomEvenement, description, lieuEvenement, dateDebutConverted, dateFinConverted, nombreMax, status);

            // Obtenir une instance de EvenementService et appeler la méthode modifier
            ServiceEvenement evenementService = new ServiceEvenement();
            evenementService.modifier(evenementModifie);

            // Afficher un message d'information
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setContentText("Événement modifié avec succès !");
            alert.showAndWait();

        } catch (NumberFormatException e) {
            // Afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez entrer un nombre valide pour le nombre maximum de participants.");
            alert.showAndWait();
        } catch (IllegalArgumentException e) {
            // Afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez sélectionner un type d'événement.");
            alert.showAndWait();
        } catch (SQLException e) {
            // Afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Une erreur s'est produite lors de la mise à jour de l'événement : " + e.getMessage());
            alert.showAndWait();
        }
    }

}
