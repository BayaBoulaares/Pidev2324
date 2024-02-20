package edu.esprit.controllers;
import edu.esprit.entities.Evenement;
import edu.esprit.services.ServiceEvenement;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
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
            // Effacer la VBox avant de rafraîchir les événements
            eventVBox.getChildren().clear();

            List<Evenement> allEvents = serviceEvenement.getAll();
            displayEvents(allEvents);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void displayEvents(List<Evenement> events) {
        HBox eventPairBox = new HBox();
        eventPairBox.setSpacing(30);

        for (Evenement evenement : events) {
            if (!displayedEvents.containsKey(evenement.getId_Event())) {
                VBox eventBox = createEventBox(evenement);
                eventPairBox.getChildren().add(eventBox);
                displayedEvents.put(evenement.getId_Event(), true);
            }
        }

        eventVBox.getChildren().add(eventPairBox);
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

        Button modifierButton = new Button("Modifier");
        modifierButton.getStyleClass().add("action-button");
        modifierButton.setStyle("-fx-background-color: turquoise; -fx-text-fill: white;-fx-background-radius: 5px; -fx-border-color: turquoise;");
        modifierButton.setOnAction(event -> handleModifierEvent(evenement));
        eventBox.getChildren().add(modifierButton);

        Button supprimerButton = new Button("Supprimer");
        supprimerButton.getStyleClass().add("action-button");
        supprimerButton.setStyle("-fx-background-color: white; -fx-text-fill:turquoise;-fx-background-radius: 5px; -fx-border-color: turquoise;");
        supprimerButton.setOnAction(event -> handleSupprimerEvent(evenement));
        eventBox.getChildren().add(supprimerButton);

        Button AjouterSponsorsButton = new Button("Ajouter Sponsors");
        AjouterSponsorsButton.getStyleClass().add("action-button");
        AjouterSponsorsButton.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #02024b;-fx-background-radius: 5px; -fx-border-color: #a6b0af;");
        AjouterSponsorsButton.setOnAction(event -> AjouterSponsors(evenement));
        eventBox.getChildren().add(AjouterSponsorsButton);
        return eventBox;
    }

    private void handleSupprimerEvent(Evenement evenement) {
        try {
            serviceEvenement.supprimer(evenement.getId_Event());

            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Succès");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Événement supprimé avec succès !");
            successAlert.show();

            // Clear the VBox
            eventVBox.getChildren().clear();

            // Repopulate the VBox with the updated list of events from the database
            List<Evenement> updatedEvents = serviceEvenement.getAll();
            HBox hbox = new HBox();
            hbox.setSpacing(20);
            for (Evenement updatedEvent : updatedEvents) {
                // Add each event to the HBox
                hbox.getChildren().add(createEventBox(updatedEvent));
            }
            // Add the HBox to the VBox
            eventVBox.getChildren().add(hbox);
        } catch (SQLException e) {
            e.printStackTrace();
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur");
            errorAlert.setContentText("Une erreur s'est produite : " + e.getMessage());
            errorAlert.showAndWait();
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

            // Add a listener to refresh the list when the modification window is closed
            stage.setOnHidden(windowEvent -> {
                eventVBox.getChildren().clear();
                try {
                    List<Evenement> updatedEvents = serviceEvenement.getAll();
                    HBox hbox = new HBox();
                    hbox.setSpacing(20);
                    for (Evenement updatedEvent : updatedEvents) {
                        // Create an HBox for each event
                        hbox.getChildren().add(createEventBox(updatedEvent));
                    }
                    // Add the HBox to the VBox
                    eventVBox.getChildren().add(hbox);
                } catch (SQLException ex) {
                    System.out.println("Error refreshing event list: " + ex.getMessage());
                    // Handle the exception appropriately, such as displaying an error message
                }
            });
        } catch (IOException e) {
            e.printStackTrace();

        }
    }


    @FXML
    private void handleConsulterSponsors() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Afficher_Sponsor.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer l'exception appropriée, par exemple en affichant un message d'erreur
        }
    }



    private void AjouterSponsors(Evenement evenement) {
        try {
            URL url = getClass().getResource("/Ajout_Sponsor.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            AjoutSponsor ajoutSponsorController = loader.getController();
            if (ajoutSponsorController != null) {
                ajoutSponsorController.setEvenement(evenement);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.showAndWait(); // Change this line to make sure the stage is closed before continuing
                if (ajoutSponsorController.getEvenement() != null) {
                    // Handle the event here or refresh your UI
                }
            } else {
                afficherAlerte("Erreur: Contrôleur introuvable pour la fenêtre Ajout Sponsor.");
            }
        } catch (IOException e) {
            afficherAlerte("Erreur lors du chargement de la fenêtre Ajout Sponsor : " + e.getMessage());
        }
    }


    private void afficherAlerte(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleRetour() {
        try {
            // Charger la vue de la page d'ajout d'événement
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Ajout_Evenement.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Obtenir la référence de la scène actuelle
            Stage stage = (Stage) eventVBox.getScene().getWindow();

            // Mettre à jour la scène avec la nouvelle vue
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer l'exception, par exemple afficher une alerte
        }
    }

}


