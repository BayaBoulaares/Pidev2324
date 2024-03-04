package edu.esprit.controller;

import edu.esprit.entities.Evenement;
import edu.esprit.entities.Sponsor;
import edu.esprit.services.CredentialsManager;
import edu.esprit.services.ServiceSponsor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class AfficherSponsor {

    @FXML
    private VBox sponsorBox;

    private final ServiceSponsor serviceSponsor = new ServiceSponsor();
    private Evenement evenement;

    @FXML
    void initialize() {
        refreshSponsorList();
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }

    public void displaySponsors(List<Sponsor> sponsors) {
        HBox sponsorContainer = new HBox();
        sponsorContainer.setSpacing(20);

        for (Sponsor sponsor : sponsors) {
            VBox sponsorBox = createSponsorBox(sponsor);
            sponsorContainer.getChildren().add(sponsorBox);
        }

        sponsorBox.getChildren().clear();
        sponsorBox.getChildren().add(sponsorContainer);
    }

    private VBox createSponsorBox(Sponsor sponsor) {
        VBox sponsorBox = new VBox();

        sponsorBox.getStyleClass().add("sponsorBox");
        ImageView sponsorImage = new ImageView();
        String imagePath = sponsor.getImage(); // Assuming getImage() returns the path to the image file
        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                File file = new File(imagePath);
                if (file.exists()) {
                    Image image = new Image(file.toURI().toString());
                    if (image.isError()) {
                        System.err.println("Error loading image: " + image.getException());
                    } else {
                        sponsorImage.setImage(image);
                        sponsorImage.setFitWidth(250);
                        sponsorImage.setFitHeight(200);
                        sponsorImage.setPreserveRatio(true);
                        sponsorBox.getChildren().add(sponsorImage);
                    }
                } else {
                    System.err.println("Image file does not exist: " + imagePath);
                }
            } catch (Exception e) {
                System.err.println("Error loading image: " + e.getMessage());
            }
        } else {
            System.err.println("Image path is null or empty.");
        }

        Label sponsorNameLabel = new Label(sponsor.getNomSponsor());
        sponsorNameLabel.getStyleClass().add("nom");
        sponsorNameLabel.setStyle("-fx-text-fill: #010133; -fx-wrap-text: true; -fx-font-family: 'DM Sans'; -fx-font-size: 18;");
        sponsorBox.getChildren().add(sponsorNameLabel);

        Label descriptionLabel = new Label(sponsor.getDescription_s());
        descriptionLabel.getStyleClass().add("description");
        descriptionLabel.setStyle("-fx-text-fill: #99a1a1; -fx-font-family: 'DM Sans'; -fx-font-size: 14;");
        sponsorBox.getChildren().add(descriptionLabel);

        Label fondLabel = new Label("Ce sponsor a donné " + sponsor.getFond());
        fondLabel.getStyleClass().add("fond");
        fondLabel.setStyle("-fx-text-fill: #191941; -fx-wrap-text: true; -fx-font-family: 'DM Sans'; -fx-font-size: 12;");
        sponsorBox.getChildren().add(fondLabel);

        HBox actionButtonBox = new HBox();
        actionButtonBox.setSpacing(25);

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

        sponsorBox.getChildren().add(actionButtonBox);

        return sponsorBox;
    }


    private void handleSupprimerEvent(Sponsor sponsor) {
        try {
            serviceSponsor.supprimer(sponsor.getId_Sponsor());
            refreshSponsorList();
            afficherAlerte("Sponsor supprimé avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
            afficherAlerte("Erreur lors de la suppression du sponsor : " + e.getMessage());
        }
    }

    private void refreshSponsorList() {
        HBox sponsorContainer = new HBox();
        sponsorContainer.setSpacing(25);

        try {
            Collection<Sponsor> updatedSponsors = serviceSponsor.getAll();
            for (Sponsor updatedSponsor : updatedSponsors) {
                VBox sponsorBoxItem = createSponsorBox(updatedSponsor);
                sponsorContainer.getChildren().add(sponsorBoxItem);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de l'actualisation de la liste des sponsors : " + ex.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Une erreur s'est produite lors de l'actualisation de la liste des sponsors.");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }

        sponsorBox.getChildren().clear();
        sponsorBox.getChildren().add(sponsorContainer);
    }


    private void modifierSponsor(Sponsor sponsor) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Modifier_Sponsor.fxml"));
            Parent root = loader.load();
            ModifierSponsor controller = loader.getController();
            controller.setSponsorData(sponsor);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

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
