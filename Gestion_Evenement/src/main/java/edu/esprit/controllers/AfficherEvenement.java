package edu.esprit.controllers;

import edu.esprit.entities.Evenement;
import edu.esprit.services.ServiceEvenement;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AfficherEvenement {

    @FXML
    private VBox eventVBox;

    private final ServiceEvenement serviceEvenement = new ServiceEvenement();
    private final Map<Integer, Boolean> displayedEvents = new HashMap<>();

    @FXML
    void initialize() {
        try {
            List<Evenement> allEvents = serviceEvenement.getAll(); // Store all events in a variable
            HBox eventPairBox = new HBox();
            eventPairBox.setSpacing(30); // Set horizontal spacing between events

            for (Evenement evenement : allEvents) {
                if (!displayedEvents.containsKey(evenement.getId_Event())) {
                    VBox eventBox = createEventBox(evenement);
                    eventPairBox.getChildren().add(eventBox); // Add each event to the HBox
                    displayedEvents.put(evenement.getId_Event(), true); // Mark event as already displayed
                }
            }

            eventVBox.getChildren().add(eventPairBox);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private VBox createEventBox(Evenement evenement) {
        VBox eventBox = new VBox();
        eventBox.getStyleClass().add("eventBox");

        ImageView eventImage = new ImageView();
        File file = new File("C:/Users/ameni/Downloads/Gestion_Evenement2/src/main/java/edu/esprit/image/" + evenement.getId_Event() + ".jpg");
        Image image = new Image(file.toURI().toString());
        eventImage.setImage(image);
        eventImage.setFitWidth(280);
        eventImage.setFitHeight(200);
        eventImage.setPreserveRatio(true);
        eventBox.getChildren().add(eventImage);

        Label titleLabel = new Label("Nom: " + evenement.getNom_Event());
        titleLabel.getStyleClass().add("title");
        eventBox.getChildren().add(titleLabel);

        Label lieuLabel = new Label("Lieu: " + evenement.getLieu_Event());
        lieuLabel.getStyleClass().add("lieu");
        eventBox.getChildren().add(lieuLabel);

        Label dateDebutLabel = new Label("Date début: " + evenement.getDate_Debut());
        dateDebutLabel.getStyleClass().add("date");
        eventBox.getChildren().add(dateDebutLabel);

        Label dateFinLabel = new Label("Date fin: " + evenement.getDate_Fin());
        dateFinLabel.getStyleClass().add("date");
        eventBox.getChildren().add(dateFinLabel);

        Label maxLabel = new Label("Nombre max: " + evenement.getNb_Max());
        maxLabel.getStyleClass().add("max");
        eventBox.getChildren().add(maxLabel);

        Label descriptionLabel = new Label("Description: " + evenement.getDescription());
        descriptionLabel.getStyleClass().add("description");
        eventBox.getChildren().add(descriptionLabel);

        Label statusLabel = new Label("Status: " + evenement.getStatus());
        statusLabel.getStyleClass().add("status");
        eventBox.getChildren().add(statusLabel);

        // Button Modifier
        Button modifierButton = new Button("Modifier");
        modifierButton.getStyleClass().add("action-button");
        modifierButton.setStyle("-fx-background-color: turquoise; -fx-text-fill: white;-fx-background-radius: 5px; -fx-border-color: turquoise;");
        modifierButton.setOnAction(event -> handleModifierEvent(evenement));
        eventBox.getChildren().add(modifierButton);


        // Button Supprimer
        Button supprimerButton = new Button("Supprimer");
        supprimerButton.getStyleClass().add("action-button");
        supprimerButton.setStyle("-fx-background-color: white; -fx-text-fill:turquoise;-fx-background-radius: 5px; -fx-border-color: turquoise;");
        supprimerButton.setOnAction(event -> handleSupprimerEvent(evenement));
        eventBox.getChildren().add(supprimerButton);

        return eventBox;
    }


    private void handleSupprimerEvent(Evenement evenement) {
        try {
            // Utiliser le service pour supprimer l'événement de la base de données
            serviceEvenement.supprimer(evenement.getId_Event());

            // Afficher un message d'information pour indiquer que la suppression a réussi
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Succès");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Événement supprimé avec succès !");
            successAlert.show();

            // Rafraîchir l'affichage en enlevant l'événement supprimé de l'affichage
            Platform.runLater(() -> {
                eventVBox.getChildren().clear(); // Effacer tous les événements affichés actuellement

                // Recharger la liste des événements depuis la base de données et les afficher à nouveau
                List<Evenement> allEvents = null;
                try {
                    allEvents = serviceEvenement.getAll();
                    for (Evenement event : allEvents) {
                        VBox eventBox = createEventBox(event);
                        eventVBox.getChildren().add(eventBox);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs de suppression de l'événement
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur");
            errorAlert.setContentText("Une erreur s'est produite : " + e.getMessage());
            errorAlert.showAndWait();
        }
    }


    @FXML
    private void Retour() {
        try {
            // Load the FXML file of the add page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Ajout_Evenement.fxml"));
            Parent root = loader.load();

            // Get the controller of the add page
            AjoutEvenement controller = loader.getController();

            // Create a new window (stage)
            Stage stage = new Stage();

            // Set the scene with the content loaded from the FXML file
            Scene scene = new Scene(root);

            // Set the scene for the window (stage)
            stage.setScene(scene);

            // Show the window (stage)
            stage.show();

            // Close the current window (display page)
            Stage currentStage = (Stage) eventVBox.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void handleModifierEvent(Evenement evenement) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Modifier_Evenement.fxml"));
            Parent root = loader.load();

            ModifierEvenement controller = loader.getController();
            controller.setEventData(evenement);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}