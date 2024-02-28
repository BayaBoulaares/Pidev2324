package edu.esprit.controllers;
import edu.esprit.entities.Evenement;
import edu.esprit.entities.Sponsor;
import edu.esprit.services.ServiceEvenement;
import edu.esprit.services.ServiceSponsor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AfficherEvenement {

    @FXML
    private VBox eventVBox;


    @FXML
    private Button calendarButton;
    private final ServiceEvenement serviceEvenement = new ServiceEvenement();
    private final ServiceSponsor serviceSponsor = new ServiceSponsor();
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
        String imagePath = evenement.getImage(); // Assuming getImage() returns the path to the image file
        Image image = new Image(new File(imagePath).toURI().toString());
        eventImage.setImage(image);
        eventImage.setFitWidth(280);
        eventImage.setFitHeight(200);
        eventImage.setPreserveRatio(true);
        eventBox.getChildren().add(eventImage);
        // Créer le label du nom
        Label nameLabel = new Label(evenement.getNom_Event());
        nameLabel.setStyle("-fx-font-family: 'DM Sans'; -fx-font-size: 18;");
        nameLabel.getStyleClass().add("nom");
        eventBox.getChildren().add(nameLabel); // Ajouter cette ligne

        HBox hbox = new HBox();
        ImageView locationIcon = new ImageView(new Image("file:///C:/Users/ameni/Downloads/Gestion_Evenement2/src/main/java/edu/esprit/image/lieu.png"));
        locationIcon.setFitWidth(20);
        locationIcon.setFitHeight(20);

        // Créer le label du lieu
        Label lieuLabel = new Label(evenement.getLieu_Event());
        lieuLabel.getStyleClass().add("lieu");

        // Ajouter l'icône et le label au HBox
        hbox.getChildren().addAll(locationIcon, lieuLabel);

        // Ajouter le HBox à eventBox
        eventBox.getChildren().add(hbox);

        SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM", Locale.FRENCH);
        String dateDebutStr = formatter.format(evenement.getDate_Debut());
        String dateFinStr = formatter.format(evenement.getDate_Fin());
        Label dateLabel = new Label( dateDebutStr +"    /   "+ dateFinStr);
        dateLabel.getStyleClass().add("date");
        eventBox.getChildren().add(dateLabel);
        // Créer le label de la description
        Label descriptionLabel = new Label(evenement.getDescription());
        descriptionLabel.getStyleClass().add("description");
        descriptionLabel.setStyle("-fx-text-fill: gray;  -fx-wrap-text: true;");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxWidth(Double.MAX_VALUE);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setPrefWidth(800);
        eventBox.getChildren().add(descriptionLabel);


     Label maxLabel = new Label("Nombre Restant :" + evenement.getNb_Max());
        maxLabel.getStyleClass().add("max");
        eventBox.getChildren().add(maxLabel);



        HBox hboxButtons = new HBox(18); // 18 est l'espace entre les éléments
        hboxButtons.setAlignment(Pos.CENTER); // Centrer les éléments dans le HBox

        Button modifierButton = new Button("Modifier");
        modifierButton.getStyleClass().add("action-button");
        modifierButton.setStyle("-fx-background-color: turquoise; -fx-text-fill: white;-fx-background-radius: 5px; -fx-border-color: turquoise;");
        modifierButton.setOnAction(event -> handleModifierEvent(evenement));


        ImageView editIcon = new ImageView(new Image("file:///C:/Users/ameni/Downloads/Gestion_Evenement2/src/main/java/edu/esprit/image/editer.png"));
        editIcon.setFitWidth(20);
        editIcon.setFitHeight(20);


        modifierButton.setGraphic(editIcon);

        Button supprimerButton = new Button("Supprimer");
        supprimerButton.getStyleClass().add("action-button");
        supprimerButton.setStyle("-fx-background-color: white; -fx-text-fill:turquoise;-fx-background-radius: 5px; -fx-border-color: turquoise;");
        supprimerButton.setOnAction(event -> handleSupprimerEvent(evenement));

        // Créer l'icône pour le bouton "Supprimer"
        ImageView deleteIcon = new ImageView(new Image("file:///C:/Users/ameni/Downloads/Gestion_Evenement2/src/main/java/edu/esprit/image/supprimer.png"));
        deleteIcon.setFitWidth(21);
        deleteIcon.setFitHeight(21);


        supprimerButton.setGraphic(deleteIcon);

        hboxButtons.getChildren().addAll(modifierButton,supprimerButton);

        eventBox.getChildren().add(hboxButtons);

        Button consulterSponsorsButton = new Button("Consulter Sponsors");
        consulterSponsorsButton.getStyleClass().add("action-button");
        consulterSponsorsButton.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #02024b;-fx-background-radius: 5px; -fx-border-color: #a6b0af;");
        ImageView icon = new ImageView(new Image("file:///C:/Users/ameni/Downloads/Gestion_Evenement2/src/main/java/edu/esprit/image/referer.png"));
        icon.setFitWidth(15);
        icon.setFitHeight(15);
        consulterSponsorsButton.setGraphic(icon);
        consulterSponsorsButton.setOnAction(event -> handleConsulterSponsors(evenement));
        eventBox.getChildren().add(consulterSponsorsButton);
        return eventBox;
    }







    private void handleSupprimerEvent(Evenement evenement) {
        try {

            serviceSponsor.supprimer(evenement.getId_Event());

            serviceEvenement.supprimer(evenement.getId_Event());

            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Succès");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Événement et son sponsor ont été supprimés avec succès !");
            successAlert.show();

            eventVBox.getChildren().clear();

            List<Evenement> updatedEvents = serviceEvenement.getAll();
            HBox hbox = new HBox();
            hbox.setSpacing(30);
            for (Evenement updatedEvent : updatedEvents) {
                hbox.getChildren().add(createEventBox(updatedEvent));
            }

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

            stage.setOnHidden(windowEvent -> {
                eventVBox.getChildren().clear();
                try {
                    List<Evenement> updatedEvents = serviceEvenement.getAll();
                    HBox hbox = new HBox();
                    hbox.setSpacing(30);
                    for (Evenement updatedEvent : updatedEvents) {

                        hbox.getChildren().add(createEventBox(updatedEvent));
                    }

                    eventVBox.getChildren().add(hbox);
                } catch (SQLException ex) {
                    System.out.println("Error refreshing event list: " + ex.getMessage());

                }
            });
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    @FXML
    private void handleConsulterSponsors(Evenement evenement) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Afficher_Sponsor.fxml"));
            Parent root = loader.load();

            AfficherSponsor controller = loader.getController();
            List<Sponsor> sponsors = serviceSponsor.getByEventName(evenement.getNom_Event());
            controller.displaySponsors(sponsors);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void AjouterSponsors() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Ajout_Sponsor.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    @FXML
    private void handleRetour() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Ajout_Evenement.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = (Stage) eventVBox.getScene().getWindow();

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


   }
