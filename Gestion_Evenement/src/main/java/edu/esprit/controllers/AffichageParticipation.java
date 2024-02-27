package edu.esprit.controllers;

import edu.esprit.entities.Evenement;
import edu.esprit.services.ServiceParticipation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class AffichageParticipation {

    @FXML
    private VBox participationBox;

    private ServiceParticipation serviceParticipation = new ServiceParticipation();

    @FXML
    private void initialize() throws SQLException {
        List<Evenement> participatedEvents = serviceParticipation.getParticipatedEvents(1);
        loadParticipations(participatedEvents);
    }

    public void loadParticipations(List<Evenement> participatedEvents) {
        for (Evenement evenement : participatedEvents) {
            VBox eventBox = createEventBox(evenement);
            participationBox.getChildren().add(eventBox);
        }
    }

    private VBox createEventBox(Evenement evenement) {
        VBox eventBox = new VBox();
        eventBox.getStyleClass().add("eventBox");

        ImageView eventImage = new ImageView();
        String imagePath = evenement.getImage();
        Image image = new Image(new File(imagePath).toURI().toString());
        eventImage.setImage(image);
        eventImage.setFitWidth(280);
        eventImage.setFitHeight(200);
        eventImage.setPreserveRatio(true);
        eventBox.getChildren().add(eventImage);

        Label nameLabel = new Label(evenement.getNom_Event());
        nameLabel.setStyle("-fx-font-family: 'DM Sans'; -fx-font-size: 18;");
        nameLabel.getStyleClass().add("nom");
        eventBox.getChildren().add(nameLabel);

        HBox lieuBox = new HBox();
        ImageView lieuIcon = new ImageView(new Image("file:///C:/Users/ameni/Downloads/Gestion_Evenement2/src/main/java/edu/esprit/image/lieu.png"));
        lieuIcon.setFitWidth(20);
        lieuIcon.setFitHeight(20);
        Label lieuLabel = new Label(evenement.getLieu_Event());
        lieuLabel.getStyleClass().add("lieu");
        lieuBox.getChildren().addAll(new Label("Lieu: "), lieuIcon, lieuLabel);
        eventBox.getChildren().add(lieuBox);

        SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM", Locale.FRENCH);
        String dateDebutStr = formatter.format(evenement.getDate_Debut());
        String dateFinStr = formatter.format(evenement.getDate_Fin());
        Label dateLabel = new Label("Date: " + dateDebutStr + " / " + dateFinStr);
        dateLabel.getStyleClass().add("date");
        eventBox.getChildren().add(dateLabel);

        Label descriptionLabel = new Label("Description: " + evenement.getDescription());
        descriptionLabel.getStyleClass().add("description");
        descriptionLabel.setWrapText(true);
        eventBox.getChildren().add(descriptionLabel);

        Label maxLabel = new Label("Nombre Restant: " + evenement.getNb_Max());
        maxLabel.getStyleClass().add("max");
        eventBox.getChildren().add(maxLabel);

        return eventBox;
    }


}
