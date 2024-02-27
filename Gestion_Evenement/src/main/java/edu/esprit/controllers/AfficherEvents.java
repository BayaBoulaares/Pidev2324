package edu.esprit.controllers;
import edu.esprit.entities.Evenement;
import edu.esprit.entities.Participation;
import edu.esprit.entities.Sponsor;
import edu.esprit.entities.User;
import edu.esprit.services.ServiceEvenement;
import edu.esprit.services.ServiceParticipation;
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

public class AfficherEvents {

    @FXML
    private VBox eventBox;

    @FXML
    private Button calendarButton;
    @FXML
    private Button libraryButton;
    private final ServiceEvenement serviceEvenement = new ServiceEvenement();
    private final ServiceSponsor serviceSponsor = new ServiceSponsor();
    private final Map<Integer, Boolean> displayedEvents = new HashMap<>();
    private ServiceParticipation serviceParticipation = new ServiceParticipation();


    @FXML
    void initialize() {
        try {
            List<Evenement> all = serviceEvenement.getAll();
            displayEventsInEventBox(all);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void displayEventsInEventBox(List<Evenement> events) {
        HBox eventPairBox = new HBox();
        eventPairBox.setSpacing(30);
        for (Evenement evenement : events) {
            if (!displayedEvents.containsKey(evenement.getId_Event())) {
                VBox eventBox = createEventBox(evenement);
                eventBox.getStyleClass().add("eventBox");
                eventPairBox.getChildren().add(eventBox);
                displayedEvents.put(evenement.getId_Event(), true);
            }
        }
        eventBox.getChildren().add(eventPairBox);
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

        // Create the name label
        Label nameLabel = new Label(evenement.getNom_Event());
        nameLabel.setStyle("-fx-font-family: 'DM Sans'; -fx-font-size: 18;");
        nameLabel.getStyleClass().add("nom");
        eventBox.getChildren().add(nameLabel);

        HBox hbox = new HBox();
        ImageView locationIcon = new ImageView(new Image("file:///C:/Users/ameni/Downloads/Gestion_Evenement2/src/main/java/edu/esprit/image/lieu.png"));
        locationIcon.setFitWidth(20);
        locationIcon.setFitHeight(20);

        // Create the location label
        Label lieuLabel = new Label(evenement.getLieu_Event());
        lieuLabel.getStyleClass().add("lieu");
        hbox.getChildren().addAll(locationIcon, lieuLabel);
        eventBox.getChildren().add(hbox);

        SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM", Locale.FRENCH);
        String dateDebutStr = formatter.format(evenement.getDate_Debut());
        String dateFinStr = formatter.format(evenement.getDate_Fin());
        Label dateLabel = new Label(dateDebutStr + "    /   " + dateFinStr);
        dateLabel.getStyleClass().add("date");
        eventBox.getChildren().add(dateLabel);

        Label descriptionLabel = new Label(evenement.getDescription());
        descriptionLabel.getStyleClass().add("description");
        descriptionLabel.setStyle("-fx-text-fill: gray;  -fx-wrap-text: true;");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxWidth(Double.MAX_VALUE);
        descriptionLabel.setMaxHeight(Double.MAX_VALUE);
        descriptionLabel.setPrefWidth(800);
        eventBox.getChildren().add(descriptionLabel);


        int remainingParticipants = calculateRemainingParticipants(evenement);

        // Create a label to display the remaining number of participants
        Label remainingLabel = new Label("Places restantes : " + remainingParticipants);
        remainingLabel.getStyleClass().add("remaining-label");
        eventBox.getChildren().add(remainingLabel);


        HBox hboxButtons = new HBox(18);
        hboxButtons.setAlignment(Pos.CENTER);

        Button participerButton = new Button("Participer");
        participerButton.getStyleClass().add("action-button");
        participerButton.setStyle("-fx-background-color: white; -fx-text-fill:turquoise;-fx-background-radius: 5px; -fx-border-color: turquoise;");
        participerButton.setOnAction(event -> handleParticiperEvent(evenement));

        ImageView participerIcon = new ImageView(new Image("file:///C:/Users/ameni/Downloads/Gestion_Evenement2/src/main/java/edu/esprit/image/verifier.png"));
        participerIcon.setFitWidth(20);
        participerIcon.setFitHeight(20);
        participerButton.setGraphic(participerIcon);
        participerButton.setMinWidth(140);

        Button consulterSponsorsButton = new Button("Consulter Sponsors");
        consulterSponsorsButton.getStyleClass().add("action-button");
        consulterSponsorsButton.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #02024b;-fx-background-radius: 5px; -fx-border-color: #a6b0af;");
        ImageView icon = new ImageView(new Image("file:///C:/Users/ameni/Downloads/Gestion_Evenement2/src/main/java/edu/esprit/image/referer.png"));
        icon.setFitWidth(15);
        icon.setFitHeight(15);
        consulterSponsorsButton.setGraphic(icon);
        consulterSponsorsButton.setMinWidth(150);
        consulterSponsorsButton.setOnAction(event -> handleConsulterSponsors(evenement));

        hboxButtons.getChildren().addAll(participerButton, new Pane(), consulterSponsorsButton);

        eventBox.getChildren().add(hboxButtons);

        ToggleButton likeButton = new ToggleButton();
        likeButton.getStyleClass().addAll("like-button");
        likeButton.setSelected(false);
        likeButton.setStyle("-fx-background-color: transparent;");
        ImageView likeIconNotLiked = new ImageView(new Image("file:///C:/Users/ameni/Downloads/Gestion_Evenement2/src/main/java/edu/esprit/image/like2.png"));
        ImageView likeIconLiked = new ImageView(new Image("file:///C:/Users/ameni/Downloads/Gestion_Evenement2/src/main/java/edu/esprit/image/like.png"));
        likeIconNotLiked.setFitWidth(20);
        likeIconNotLiked.setFitHeight(20);
        likeIconLiked.setFitWidth(20);
        likeIconLiked.setFitHeight(20);
        likeButton.setGraphic(likeIconNotLiked);
        eventBox.getChildren().add(likeButton);
        Pane pane = new Pane();
        likeButton.setOnAction(event -> {
            if (likeButton.isSelected()) {
                likeButton.setGraphic(likeIconLiked);
                handleLikeEvent(evenement, true);
            } else {
                likeButton.setGraphic(likeIconNotLiked);
                handleLikeEvent(evenement, false);
            }
            likeButton.setLayoutX(400);
            likeButton.setLayoutY(300);
            eventBox.getChildren().add(pane);
        });

        return eventBox;
    }

    private int calculateRemainingParticipants(Evenement evenement) {
        try {
            // Get the number of participants for the event
            int currentParticipants = serviceParticipation.getNumberOfParticipants(evenement.getId_Event());
            // Calculate the remaining number of participants
            return evenement.getNb_Max() - currentParticipants;
        } catch (SQLException e) {
            // Handle the SQL exception
            e.printStackTrace();
            return -1; // Indicate an error occurred
        }
    }

    @FXML
    private void handleConsulterSponsors(Evenement evenement) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/List_Sponsor.fxml"));
            Parent root = loader.load();

            ListSponsor controller = loader.getController();
            List<Sponsor> sponsors = serviceSponsor.getByEventName(evenement.getNom_Event());
            controller.displaySponsors(sponsors);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception here, e.g., show an error message to the user
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to load sponsors");
            alert.setContentText("An error occurred while loading sponsors for the event. Please try again.");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle any other exceptions here
        }
    }



    @FXML
    private void handleRetour() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Ajout_Evenement.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = (Stage) eventBox.getScene().getWindow();

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleCalendarButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Calendrier.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleLikeEvent(Evenement evenement, boolean liked) {
        // Implement your logic here to handle the like event
        if (liked) {
            // The event was liked
            System.out.println("Event liked: " + evenement.getNom_Event());
            // Add your code to update the database or perform any other actions
        } else {
            // The like was removed
            System.out.println("Event unliked: " + evenement.getNom_Event());
            // Add your code to update the database or perform any other actions
        }
    }
    @FXML
    void handleParticiperEvent(Evenement evenement) {
        try {
            // Utilisez l'ID de l'utilisateur connu
            int userId = 1;

            // Obtenez l'ID de l'événement
            int eventId = evenement.getId_Event();

            // Check if the user has already participated in the event
            if (serviceParticipation.hasParticipated(eventId, userId)) {
                // If the user has already participated, display an alert
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Déjà participé");
                alert.setHeaderText(null);
                alert.setContentText("Vous avez déjà participé à cet événement.");
                alert.showAndWait();
                return; // Exit the method
            }

            // Insérez la participation dans la base de données
            serviceParticipation.insertParticipation(eventId, userId);

            System.out.println("Participation ajoutée avec succès!");

            // Affichez une alerte indiquant que la participation a été ajoutée avec succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Participation ajoutée");
            alert.setHeaderText(null);
            alert.setContentText("La participation a été ajoutée avec succès!");
            alert.showAndWait();
        } catch (SQLException ex) {
            System.err.println("Erreur lors de l'ajout de la participation : " + ex.getMessage());
        }
    }



    @FXML
    void handleLibraryButtonAction(ActionEvent event) {
        try {
            // Récupérer les événements auxquels l'utilisateur a participé
            List<Evenement> participatedEvents = serviceParticipation.getParticipatedEvents(1); // Utilisateur avec ID 1

            // Charger la page de participation
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Affichage_Participation.fxml"));
            Parent root = loader.load();

            // Obtenir le contrôleur de la page de participation
            AffichageParticipation controller = loader.getController();

// Appeler la méthode loadParticipations
            controller.loadParticipations(participatedEvents);

            // Créer une nouvelle scène avec la page de participation et l'afficher dans un nouveau stage
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error loading participation page: " + e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}






