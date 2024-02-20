package edu.esprit.controllers;

import edu.esprit.entities.Evenement;
import edu.esprit.entities.Fond;
import edu.esprit.entities.Sponsor;
import edu.esprit.entities.Status;
import edu.esprit.services.ServiceSponsor;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class AjoutSponsor {

    private Evenement evenement;

    @FXML
    private TextField sponsorName;

    @FXML
    private TextField sponsorDescription;

    @FXML
    private ComboBox<String> sponsorFond;

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }

    public Evenement getEvenement() {
        return this.evenement;
    }

    @FXML
    void ajouter() {
        try {
            if (evenement == null) {
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

            // Validation check for sponsor name
            if (nomSponsor.length() > 20 || nomSponsor.matches(".*\\d.*")) {
                afficherAlerte("Le nom du sponsor ne doit pas dépasser 20 caractères et ne doit pas contenir de chiffres !");
                return;
            }

            if (nomSponsor.isEmpty() || descriptionSponsor.isEmpty() || fondSponsor == null) {
                afficherAlerte("Veuillez remplir tous les champs !");
                return;
            }

            Sponsor nouveauSponsor = new Sponsor();
            nouveauSponsor.setNomSponsor(nomSponsor);
            nouveauSponsor.setDescription_s(descriptionSponsor);
            nouveauSponsor.setFond(fondSponsor);
            nouveauSponsor.setEvenement(evenement);

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
