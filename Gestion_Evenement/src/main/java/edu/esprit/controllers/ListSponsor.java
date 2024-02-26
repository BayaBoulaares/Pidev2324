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
        ImageView sponsorImage = createSponsorImage(sponsor);
        if (sponsorImage != null) {
            sponsorBox.getChildren().add(sponsorImage);
        }

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

        return sponsorBox;
    }

    private ImageView createSponsorImage(Sponsor sponsor) {
        File file = new File("C:/Users/ameni/Downloads/Gestion_Evenement2/src/main/java/edu/esprit/imagesponsor/" + sponsor.getId_Sponsor() + ".jpg");
        if (file.exists()) {
            ImageView sponsorImage = new ImageView();
            Image image = new Image(file.toURI().toString());
            sponsorImage.setImage(image);
            sponsorImage.setFitWidth(140);
            sponsorImage.setFitHeight(100);
            sponsorImage.setPreserveRatio(true);
            return sponsorImage;
        } else {
            System.err.println("Le fichier d'image du sponsor n'existe pas : " + file.getAbsolutePath());
            return null;
        }
    }}

