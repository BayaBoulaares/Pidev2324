package edu.esprit.controllers;

import edu.esprit.entities.Evenement;
import edu.esprit.entities.Sponsor;
import edu.esprit.services.ServiceSponsor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.scene.control.TextField;

public class AfficherSponsor {

    @FXML
    private VBox sponsorVBox;

    @FXML
    private TextField searchField;

    private final ServiceSponsor serviceSponsor = new ServiceSponsor();
    private Evenement evenement;

    @FXML
    void initialize() {
        // Effacer la VBox avant de rafraîchir les sponsors
        sponsorVBox.getChildren().clear();

        // Charger tous les sponsors lors du lancement de l'application
        refreshSponsorList();
    }
    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }
    @FXML
    void searchSponsor() {
        String eventName = searchField.getText().trim();

        if (eventName.isEmpty()) {
            // Si le champ de recherche est vide, affichez tous les sponsors
            refreshSponsorList();
        } else {
            // Sinon, recherchez les sponsors par nom d'événement
            List<Sponsor> foundSponsors = serviceSponsor.getByEventName(eventName);

            if (foundSponsors != null && !foundSponsors.isEmpty()) {
                displaySponsors(foundSponsors);
            } else {
                afficherAlerte("Aucun sponsor correspondant trouvé.");
            }
        }
    }



    public void displaySponsors(List<Sponsor> sponsors) {
        // Effacer la liste actuelle des sponsors
        sponsorVBox.getChildren().clear();

        // Afficher les sponsors trouvés
        for (Sponsor sponsor : sponsors) {
            HBox sponsorBox = createSponsorBox(sponsor);
            sponsorVBox.getChildren().add(sponsorBox);
        }
    }

    private HBox createSponsorBox(Sponsor sponsor) {
        HBox sponsorBox = new HBox();
        sponsorBox.setStyle("-fx-border-color: #a5e7dc;-fx-background-color: #ffffff; -fx-border-width: 1px; -fx-border-radius: 5px;"); // Ajoutez une bordure bleue
        sponsorBox.setSpacing(20);
        // Image du sponsor (si disponible)
        ImageView sponsorImage = new ImageView();
        File file = new File("C:/Users/ameni/Downloads/Gestion_Evenement2/src/main/java/edu/esprit/imagesponsor/" + sponsor.getId_Sponsor() + ".jpg");
        Image image = new Image(file.toURI().toString());
        sponsorImage.setImage(image);
        sponsorImage.setFitWidth(220);
        sponsorImage.setFitHeight(150);
        sponsorImage.setPreserveRatio(true);
        sponsorBox.getChildren().add(sponsorImage);

        // Informations du sponsor
        VBox sponsorInfoBox = new VBox();
        sponsorInfoBox.setSpacing(28);

        // Nom du sponsor
        Label sponsorNameLabel = new Label( sponsor.getNomSponsor());
        sponsorNameLabel.setStyle("-fx-text-fill: darkblue; -fx-font-family: 'DM Sans'; -fx-font-size: 18px;"); // Texte en bleu foncé avec la police DM Sans et taille de l'écriture 14
        sponsorInfoBox.getChildren().add(sponsorNameLabel);

        // Description du sponsor
        Label descriptionLabel = new Label(sponsor.getDescription_s());
        descriptionLabel.setStyle("-fx-text-fill: #a7b2b1; -fx-font-family: 'DM Sans'; -fx-font-size: 14px;"); // Texte en bleu foncé avec la police DM Sans et taille de l'écriture 14
        sponsorInfoBox.getChildren().add(descriptionLabel);

        // Autres détails du sponsor
        Label fondLabel = new Label("Fond: " + sponsor.getFond());
        fondLabel.setStyle("-fx-text-fill: darkblue; -fx-font-family: 'DM Sans'; -fx-font-size: 14px;"); // Texte en bleu foncé avec la police DM Sans et taille de l'écriture 14
        sponsorInfoBox.getChildren().add(fondLabel);

        Label evenementLabel = new Label("Evenement Sponsorisé: " + sponsor.getEvenement().getNom_Event());
        evenementLabel.setStyle("-fx-text-fill: darkblue; -fx-font-family: 'DM Sans'; -fx-font-size: 14px;"); // Texte en bleu foncé avec la police DM Sans et taille de l'écriture 14
        sponsorInfoBox.getChildren().add(evenementLabel);

        sponsorBox.getChildren().add(sponsorInfoBox);
        Button modifierSponsorButton = new Button("Modifier");
        modifierSponsorButton.getStyleClass().add("action-button");
        modifierSponsorButton.setStyle("-fx-background-color: turquoise; -fx-text-fill: white; -fx-background-radius: 5px; -fx-border-color: #63e8db;");
        modifierSponsorButton.setOnAction(event -> modifierSponsor(sponsor));
        sponsorBox.getChildren().add(modifierSponsorButton);

        Button supprimerButton = new Button("Supprimer");
        supprimerButton.getStyleClass().add("action-button");
        supprimerButton.setStyle("-fx-background-color: white; -fx-text-fill:turquoise;-fx-background-radius: 5px; -fx-border-color: turquoise;");
        supprimerButton.setOnAction(event -> handleSupprimerEvent(sponsor));
        sponsorBox.getChildren().add(supprimerButton);
        return sponsorBox;
    }

    private void handleSupprimerEvent(Sponsor sponsor) {
        try {
            serviceSponsor.supprimer(sponsor.getId_Sponsor());
            // Rafraîchir la liste des sponsors après suppression
            refreshSponsorList();
            afficherAlerte("Sponsor supprimé avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
            afficherAlerte("Erreur lors de la suppression du sponsor : " + e.getMessage());
        }
    }

    private void refreshSponsorList() {
        sponsorVBox.getChildren().clear();
        try {
            List<Sponsor> updatedSponsors = serviceSponsor.getAll();
            for (Sponsor updatedSponsor : updatedSponsors) {
                HBox sponsorBox = createSponsorBox(updatedSponsor);
                sponsorBox.setSpacing(20);
                sponsorVBox.getChildren().add(sponsorBox);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de l'actualisation de la liste des sponsors : " + ex.getMessage());
            // Gérez l'exception de manière appropriée, par exemple en affichant un message d'erreur
        }
    }

    private void modifierSponsor(Sponsor sponsor) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Modifier_Sponsor.fxml"));
            Parent root = loader.load();
            ModifierSponsor controller = loader.getController();
            controller.setSponsorData(sponsor);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Ajoutez un listener pour rafraîchir la liste lorsque la fenêtre de modification est fermée
            stage.setOnHidden(windowEvent -> refreshSponsorList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void afficherAlerte(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
}

