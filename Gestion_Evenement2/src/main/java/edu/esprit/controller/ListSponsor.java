package edu.esprit.controller;

import edu.esprit.entities.Sponsor;
import edu.esprit.services.CredentialsManager;
import edu.esprit.services.ServiceSponsor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListSponsor {
    @FXML
    private VBox sponsorBox; // Changer le nom de la VBox à sponsorVBox pour correspondre au nom de la variable FXML

    private final ServiceSponsor serviceSponsor = new ServiceSponsor();
    private final Map<Integer, Boolean> displayedSponsors = new HashMap<>();

    @FXML
    public void initialize() {
        try {
            // Clear the VBox before refreshing sponsors
            sponsorBox.getChildren().clear();

            Collection<Sponsor> allSponsors = serviceSponsor.getAll();
            if (allSponsors.isEmpty()) {
                Label noSponsorsLabel = new Label("Aucun sponsor disponible.");
                sponsorBox.getChildren().add(noSponsorsLabel);
            } else {
                displaySponsors(allSponsors);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void displaySponsors(Collection<Sponsor> sponsors) {
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

        // Sponsor image
        ImageView sponsorImage = new ImageView();
        String imagePath = sponsor.getImage(); // Assuming getImage() returns the path to the image file

        try {
            if (imagePath != null && !imagePath.isEmpty()) {
                File file = new File(imagePath);
                if (file.exists()) {
                    Image image = new Image(file.toURI().toString());
                    sponsorImage.setImage(image);
                    sponsorImage.setFitWidth(280);
                    sponsorImage.setFitHeight(200);
                    sponsorImage.setPreserveRatio(true);
                    sponsorBox.getChildren().add(sponsorImage);
                } else {
                    System.err.println("Image file does not exist: " + imagePath);
                }
            } else {
                System.err.println("Image path is null or empty");
            }

            // Sponsor name
            Label sponsorNameLabel = new Label(sponsor.getNomSponsor());
            sponsorNameLabel.getStyleClass().add("nom");
            sponsorNameLabel.setStyle("-fx-text-fill: #010133; -fx-wrap-text: true; -fx-font-family: 'DM Sans'; -fx-font-size: 18;");
            sponsorBox.getChildren().add(sponsorNameLabel);

            // Sponsor description
            Label descriptionLabel = new Label(sponsor.getDescription_s());
            descriptionLabel.getStyleClass().add("description");
            descriptionLabel.setStyle("-fx-text-fill: #99a1a1; -fx-font-family: 'DM Sans'; -fx-font-size: 14;");
            sponsorBox.getChildren().add(descriptionLabel);

            // Sponsor's contribution
            Label fondLabel = new Label("Ce sponsor a donné " + sponsor.getFond());
            fondLabel.getStyleClass().add("fond");
            fondLabel.setStyle("-fx-text-fill: #191941; -fx-wrap-text: true; -fx-font-family: 'DM Sans'; -fx-font-size: 12;");
            sponsorBox.getChildren().add(fondLabel);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error creating sponsor box: " + e.getMessage());
        }

        return sponsorBox;
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/acceuilMatiere.fxml"));
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DasboardUser.fxml"));
        Parent root = loader.load();
        // Utilisez getRoot() au lieu de getScene()
        ((Node) actionEvent.getSource()).getScene().setRoot(root);
    }

}
