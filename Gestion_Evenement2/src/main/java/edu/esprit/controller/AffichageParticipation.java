package edu.esprit.controller;

import edu.esprit.entities.Evenement;
import edu.esprit.services.DashboardUser;
import edu.esprit.services.ServiceParticipation;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
    private HBox participationBox; // Change VBox to HBox

    private ServiceParticipation serviceParticipation = new ServiceParticipation();

    @FXML
    private void initialize() throws SQLException {
        int userId = DashboardUser.userId;

        List<Evenement> participatedEvents = serviceParticipation.getParticipatedEvents(userId);
        loadParticipations(participatedEvents);
        participationBox.setSpacing(30); // Ajoutez cette ligne pour définir l'espacement
    }

    public void loadParticipations(List<Evenement> participatedEvents) {
        for (Evenement evenement : participatedEvents) {
            if (!isEventAlreadyAdded(evenement)) {
                VBox eventBox = createEventBox(evenement);
                participationBox.getChildren().add(eventBox);
            }
        }
    }

    private boolean isEventAlreadyAdded(Evenement evenement) {
        for (Node node : participationBox.getChildren()) {
            if (node instanceof VBox) {
                VBox eventBox = (VBox) node;
                Label nameLabel = (Label) eventBox.getChildren().get(1);
                if (nameLabel.getText().equals(evenement.getNom_Event())) {
                    return true;
                }
            }
        }
        return false;
    }

    private VBox createEventBox(Evenement evenement) {
        VBox eventBox = new VBox();
        eventBox.getStyleClass().add("eventBox");

        // Définir une taille spécifique pour VBox
        eventBox.setMinWidth(250); // Ajustez selon vos besoins
        eventBox.setMinHeight(150); // Ajustez selon vos besoins

        // Centrer le contenu de la VBox
        eventBox.setAlignment(Pos.CENTER);

        ImageView eventImage = new ImageView();
        String imagePath = evenement.getImage();
        Image image = new Image(new File(imagePath).toURI().toString());
        eventImage.setImage(image);
        eventImage.setFitWidth(280);
        eventImage.setFitHeight(200);
        eventImage.setPreserveRatio(true);
        eventBox.getChildren().add(eventImage);

        Label nameLabel = new Label(evenement.getNom_Event());
        nameLabel.setStyle("-fx-font-family: 'DM Sans'; -fx-font-size: 18; -fx-text-fill: turquoise;"); // Set font size and color
        nameLabel.getStyleClass().add("nom");
        eventBox.getChildren().add(nameLabel);

        HBox lieuBox = new HBox();
        ImageView lieuIcon = new ImageView(new Image("/fxml/images/lieu.png"));
        lieuIcon.setFitWidth(20);
        lieuIcon.setFitHeight(20);
        Label lieuLabel = new Label(evenement.getLieu_Event());
        lieuLabel.getStyleClass().add("lieu");
        lieuBox.getChildren().addAll(new Label(), lieuIcon, lieuLabel);
        eventBox.getChildren().add(lieuBox);

        SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM", Locale.FRENCH);
        String dateDebutStr = formatter.format(evenement.getDate_Debut());
        String dateFinStr = formatter.format(evenement.getDate_Fin());
        Label dateLabel = new Label( dateDebutStr + " / " + dateFinStr);
        dateLabel.getStyleClass().add("date");
        eventBox.getChildren().add(dateLabel);

        Label descriptionLabel = new Label( evenement.getDescription());
        descriptionLabel.getStyleClass().add("description");
        descriptionLabel.setStyle("-fx-text-fill: gray; -fx-wrap-text: true; -fx-font-size: 14;"); // Set font size and color
        descriptionLabel.setWrapText(true);
        eventBox.getChildren().add(descriptionLabel);

        return eventBox;
    }


}