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

public class AfficherSponsor {

    @FXML
    private VBox sponsorBox;

    private final ServiceSponsor serviceSponsor = new ServiceSponsor();
    private Evenement evenement;

    @FXML
    void initialize() {
        // Effacer la VBox avant de rafraîchir les sponsors
        sponsorBox.getChildren().clear();

        // Charger tous les sponsors lors du lancement de l'application
        refreshSponsorList();
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }

    public void displaySponsors(List<Sponsor> sponsors) {
        // Utilisez HBox pour un affichage horizontal
        HBox sponsorContainer = new HBox();
        sponsorContainer.setSpacing(20); // Ajoutez un espacement entre les sponsors

        for (Sponsor sponsor : sponsors) {
            VBox sponsorBox = createSponsorBox(sponsor);
            sponsorContainer.getChildren().add(sponsorBox);
        }

        // Remplacez l'ancien conteneur par le nouveau
        sponsorBox.getChildren().clear();
        sponsorBox.getChildren().add(sponsorContainer);
    }



    private VBox createSponsorBox(Sponsor sponsor) {
        VBox sponsorBox = new VBox();
        sponsorBox.getStyleClass().add("sponsorBox");
        sponsorBox.setSpacing(10); // Ajoutez un espacement entre les éléments

        // Image du sponsor (si disponible)
        ImageView sponsorImage = createSponsorImage(sponsor);
        if (sponsorImage != null) {
            sponsorBox.getChildren().add(sponsorImage);
        }

        // Nom du sponsor
        Label sponsorNameLabel = new Label(sponsor.getNomSponsor());
        sponsorNameLabel.getStyleClass().add("nom");
        sponsorNameLabel.setStyle("-fx-text-fill: #010133; -fx-wrap-text: true; -fx-font-family: 'DM Sans'; -fx-font-size: 18;");
        sponsorBox.getChildren().add(sponsorNameLabel);

        // Description du sponsor
        Label descriptionLabel = new Label(sponsor.getDescription_s());
        descriptionLabel.getStyleClass().add("description");
        descriptionLabel.setStyle("-fx-text-fill: #99a1a1; -fx-font-family: 'DM Sans'; -fx-font-size: 14;");
        sponsorBox.getChildren().add(descriptionLabel);

        // Autres détails du sponsor
        Label fondLabel = new Label("Ce sponsor a donné " + sponsor.getFond());
        fondLabel.getStyleClass().add("fond");
        fondLabel.setStyle("-fx-text-fill: #191941; -fx-wrap-text: true; -fx-font-family: 'DM Sans'; -fx-font-size: 12;");
        sponsorBox.getChildren().add(fondLabel);

        // Boutons d'action
        HBox actionButtonBox = new HBox();
        actionButtonBox.setSpacing(25); // Ajoutez un espacement entre les boutons

        Button modifierSponsorButton = new Button("Modifier");
        modifierSponsorButton.getStyleClass().add("action-button");
        modifierSponsorButton.setStyle("-fx-background-color: turquoise; -fx-text-fill: white; -fx-background-radius: 5px; -fx-border-color: #63e8db;");
        modifierSponsorButton.setOnAction(event -> modifierSponsor(sponsor));
        actionButtonBox.getChildren().add(modifierSponsorButton);

        Button supprimerButton = new Button("Supprimer");
        supprimerButton.getStyleClass().add("action-button");
        supprimerButton.setStyle("-fx-background-color: white; -fx-text-fill:turquoise;-fx-background-radius: 5px; -fx-border-color: turquoise;");
        supprimerButton.setOnAction(event -> handleSupprimerEvent(sponsor));
        actionButtonBox.getChildren().add(supprimerButton);

        // Ajoutez les boutons à la sponsorBox
        sponsorBox.getChildren().add(actionButtonBox);

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
        // Utilisez HBox pour un affichage horizontal
        HBox sponsorContainer = new HBox();
        sponsorContainer.setSpacing(25); // Ajoutez un espacement entre les sponsors

        try {
            List<Sponsor> updatedSponsors = serviceSponsor.getAll();
            for (Sponsor updatedSponsor : updatedSponsors) {
                VBox sponsorBoxItem = createSponsorBox(updatedSponsor);
                sponsorContainer.getChildren().add(sponsorBoxItem);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de l'actualisation de la liste des sponsors : " + ex.getMessage());
            // Affichez un message d'erreur à l'utilisateur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Une erreur s'est produite lors de l'actualisation de la liste des sponsors.");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }

        // Remplacez l'ancien conteneur par le nouveau
        sponsorBox.getChildren().clear();
        sponsorBox.getChildren().add(sponsorContainer);
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
    private ImageView createSponsorImage(Sponsor sponsor) {
        File file = new File("C:/Users/ameni/Downloads/Gestion_Evenement2/src/main/java/edu/esprit/imagesponsor/" + sponsor.getId_Sponsor() + ".jpg");
        if (file.exists()) {
            ImageView sponsorImage = new ImageView();
            Image image = new Image(file.toURI().toString());
            sponsorImage.setImage(image);
            sponsorImage.setFitWidth(240);
            sponsorImage.setFitHeight(200);
            sponsorImage.setPreserveRatio(true);
            return sponsorImage;
        } else {
            System.err.println("Le fichier d'image du sponsor n'existe pas : " + file.getAbsolutePath());
            return null;
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
