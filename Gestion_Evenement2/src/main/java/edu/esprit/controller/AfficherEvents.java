package edu.esprit.controller;
import edu.esprit.entities.Evenement;
import edu.esprit.entities.Participation;
import edu.esprit.entities.Sponsor;
import edu.esprit.entities.User;
import edu.esprit.services.*;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
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
  public  void initialize() {
        try {
            Collection<Evenement> all = serviceEvenement.getAll();
            displayEventsInEventBox(all);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void displayEventsInEventBox(Collection<Evenement> events) throws SQLException {
        HBox eventPairBox = new HBox();
        eventPairBox.setSpacing(30);
        for (Evenement evenement : events) {
            if (!displayedEvents.containsKey(evenement.getId_Event())) {
                if (isEventActive(evenement)) {
                    VBox eventBox = createEventBox(evenement);
                    eventBox.getStyleClass().add("eventBox");
                    eventPairBox.getChildren().add(eventBox);
                    displayedEvents.put(evenement.getId_Event(), true);
                } else {
                    try {
                        // Remove the event from the database
                        serviceEvenement.supprimer(evenement.getId_Event());
                        System.out.println("Event removed because its end date has passed: " + evenement.getNom_Event());
                    } catch (SQLException ex) {
                        System.err.println("Error removing event from database: " + ex.getMessage());
                    }
                }
            }
        }
        eventBox.getChildren().add(eventPairBox);
    }


    private boolean isEventActive(Evenement evenement) {
        // Get the current date
        Date currentDate = new Date();

        // Check if the current date is before the end date of the event
        return currentDate.before(evenement.getDate_Fin());
    }

    private VBox createEventBox(Evenement evenement) throws SQLException {
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
        ImageView locationIcon = new ImageView(new Image("/fxml/images/lieu.png"));
        locationIcon.setFitWidth(20);
        locationIcon.setFitHeight(20);

        // Create the location button
        Button locationButton = new Button();
        locationButton.setGraphic(locationIcon);
        locationButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        locationButton.setOnAction(event -> handleLocationButtonClick(evenement));

        // Create the location label
        Label lieuLabel = new Label(evenement.getLieu_Event());
        lieuLabel.getStyleClass().add("lieu");
        hbox.getChildren().addAll(locationButton, lieuLabel);
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

        Button participerButton = new Button();
        ImageView participerIcon = new ImageView(new Image("/fxml/images/verifier.png"));
        participerIcon.setFitWidth(20);
        participerIcon.setFitHeight(20);
        participerButton.setGraphic(participerIcon);
        participerButton.setMinWidth(140);

        int userId = DashboardUser.userId; // Assuming this is the ID of the current user
        int eventId = evenement.getId_Event();
        if (serviceParticipation.hasParticipated(eventId, userId)) {
            participerButton.setText("Participated");
            participerButton.getStyleClass().clear(); // Clear existing styles
            participerButton.getStyleClass().add("participated-button"); // Add new style
            participerButton.setDisable(true); // Disable the button to prevent further participation
        } else {
            participerButton.setText("Participer");
            participerButton.getStyleClass().add("action-button");
            participerButton.setStyle("-fx-background-color: white; -fx-text-fill:turquoise;-fx-background-radius: 5px; -fx-border-color: turquoise;");
            participerButton.setOnAction(event -> handleParticiperEvent(participerButton, evenement));
        }

        hboxButtons.getChildren().addAll(participerButton, new Pane());

        Button consulterSponsorsButton = new Button("Consulter Sponsors");
        consulterSponsorsButton.getStyleClass().add("action-button");
        consulterSponsorsButton.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #02024b;-fx-background-radius: 5px; -fx-border-color: #a6b0af;");
        ImageView icon = new ImageView(new Image("/fxml/images/referer.png"));
        icon.setFitWidth(15);
        icon.setFitHeight(15);
        consulterSponsorsButton.setGraphic(icon);
        consulterSponsorsButton.setMinWidth(150);
        consulterSponsorsButton.setOnAction(event -> handleConsulterSponsors(evenement));

        hboxButtons.getChildren().add(consulterSponsorsButton);

        eventBox.getChildren().add(hboxButtons);

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/List_Sponsor.fxml"));
            Parent root = loader.load();

            ListSponsor controller = loader.getController();
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
    void handleCalendarButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Calendrier.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleParticiperEvent(Button participerButton, Evenement evenement) {
        try {
            // Utilize the ID of the known user
            int userId = DashboardUser.userId;
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

            // Check if the remaining number of participants is 0
            int remainingParticipants = calculateRemainingParticipants(evenement);
            if (remainingParticipants <= 0) {
                // If the remaining number of participants is 0, remove the event from the view
                removeEventFromView(participerButton);
                // Display an alert
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Nombre maximum atteint");
                alert.setHeaderText(null);
                alert.setContentText("Désolé, le nombre maximum de participants pour cet événement est atteint.");
                alert.showAndWait();
                return; // Exit the method
            }

            // Check if the maximum number of participants is reached
            if (serviceParticipation.isMaxParticipantsReached(eventId)) {
                // Display an alert
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Maximum Participants Reached");
                alert.setHeaderText(null);
                alert.setContentText("Désolé, le nombre maximum de participants pour cet événement a été atteint.");
                alert.showAndWait();

                // Remove the event from the view
                removeEventFromView(participerButton);

                // Remove the event from the database
                ServiceEvenement serviceEvenement = new ServiceEvenement();
                serviceEvenement.supprimer(eventId);

                return; // Exit the method
            }

            // Insert the participation into the database
            serviceParticipation.insertParticipation(eventId, userId);

            System.out.println("Participation successfully added!");

            // Display an alert indicating that the participation has been successfully added
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Participation Ajoutée");
            alert.setHeaderText(null);
            alert.setContentText("Participation ajoutée avec succès!");
            alert.showAndWait();

            // Update the remaining number of participants
            remainingParticipants = calculateRemainingParticipants(evenement);
            // Update the label displaying the remaining number of participants
            for (Node node : participerButton.getParent().getParent().getChildrenUnmodifiable()) {
                if (node instanceof Label && node.getStyleClass().contains("remaining-label")) {
                    ((Label) node).setText("Places restantes : " + remainingParticipants);
                    break;
                }
            }

            // Change the button text and style to "Participated"
            participerButton.setText("Participated");
            participerButton.getStyleClass().clear(); // Clear existing styles
            participerButton.getStyleClass().add("participated-button"); // Add new style
            // Set the width and height of the button to match "Participer" button
            participerButton.setMinWidth(participerButton.getWidth());
            participerButton.setPrefWidth(participerButton.getWidth());
            participerButton.setMinHeight(participerButton.getHeight());
            participerButton.setPrefHeight(participerButton.getHeight());
            participerButton.setDisable(true);
        } catch (SQLException ex) {
            System.err.println("Erreur lors de l'ajout de la participation: " + ex.getMessage());
        }
    }



    private void removeEventFromView(Button participerButton) {
        // Find the parent node of the button (the event box) and remove it
        Node eventBox = participerButton.getParent().getParent();
        if (eventBox instanceof VBox) {
            VBox parentVBox = (VBox) eventBox;
            parentVBox.getChildren().remove(eventBox);
        }
    }

    @FXML
    void handleLibraryButtonAction(ActionEvent event) {
        try {
            int userId = DashboardUser.userId;
            List<Evenement> participatedEvents = serviceParticipation.getParticipatedEvents(userId); // Utilisateur avec ID 1

            // Charger la page de participation
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Affichage_Participation.fxml"));
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
    private void handleLocationButtonClick(Evenement evenement) {
        // Assuming you have a method to open the map for the given location
        openMapForLocation(evenement.getLieu_Event());
    }
    private void openMapForLocation(String location) {
        // Create a WebView
        WebView webView = new WebView();

        WebEngine webEngine = webView.getEngine();

        // Encode the location string to be used in a URL
        String encodedLocation = URLEncoder.encode(location, StandardCharsets.UTF_8);

        // Create a Google Maps URL with the encoded location
        String googleMapsUrl = "https://www.google.com/maps/search/?api=1&query=" + encodedLocation;

        // Load the Google Maps URL into the WebView
        webEngine.load(googleMapsUrl);

        // Create a new stage to display the map
        Stage stage = new Stage();
        stage.setTitle("Map for Location: " + location);
        stage.setScene(new Scene(webView, 800, 600));
        stage.show();
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
@FXML
    public void toReclamation(ActionEvent actionEvent) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AjouterReclamation.fxml"));
    Parent root = loader.load();
   /* AjouterReclamation controller = loader.getController();

    controller.setProftoGet(ep);*/
    ((Node) actionEvent.getSource()).getScene().setRoot(root);
    }
    @FXML
    public void toEvaluation(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/gui/Quiz/user/homePage.fxml"));
        Parent root=loader.load();
        eventBox.getScene().setRoot(root);
    }
}









