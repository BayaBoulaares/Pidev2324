package edu.esprit.controllers;

import edu.esprit.entities.Sponsor;
import edu.esprit.services.ServiceSponsor;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.sql.SQLException;
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
            // Effacer la VBox avant de rafraîchir les sponsors
            sponsorBox.getChildren().clear();

            List<Sponsor> allSponsors = serviceSponsor.getAll();
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

    @FXML
    public void displaySponsors(List<Sponsor> sponsors) {
        HBox sponsorContainer = new HBox(); // Utilisez HBox pour un affichage horizontal
        sponsorContainer.setSpacing(25); // Ajoutez un espacement entre les sponsors

        for (Sponsor sponsor : sponsors) {
            if (!displayedSponsors.containsKey(sponsor.getId_Sponsor())) {
                VBox sponsorBox = createSponsorBox(sponsor);
                sponsorContainer.getChildren().add(sponsorBox);
                displayedSponsors.put(sponsor.getId_Sponsor(), true);
            }
        }

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

}
