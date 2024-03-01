package edu.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import jfxtras.scene.control.agenda.Agenda;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Locale;
import java.sql.SQLException;
import java.util.List;
import edu.esprit.entities.Evenement;
import edu.esprit.services.ServiceEvenement;
import javafx.application.Platform;

public class Calendrier {

    @FXML
    private VBox calendrierBox;



    public void initialize() {
        // Create a new instance of the agenda
        Agenda agenda = new Agenda();

        // Apply CSS styles to the agenda
        agenda.getStyleClass().addAll("agenda", "style1");

        // Create a label for the current month
        LocalDate currentDate = LocalDate.now();
        Label monthLabel = new Label(currentDate.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + currentDate.getYear());
        monthLabel.getStyleClass().add("month-label");

        // Set styles for the month label
        monthLabel.setStyle("-fx-font-family: DM Sans, sans-serif; -fx-font-size: 16px;  -fx-text-fill: #010133;");

        // Add the month label and the agenda to the VBox
        calendrierBox.getChildren().addAll(monthLabel, agenda);
        calendrierBox.setStyle("-fx-background-color: #6ce3d6;"); // Set background color

        // Get events for the current week and add them to the agenda
        Platform.runLater(() -> {
            System.out.println("Inside Platform.runLater block"); // Print statement
            try {
                ServiceEvenement serviceEvenement = new ServiceEvenement();
                List<Evenement> evenementsSemaine = serviceEvenement.getEventsForCurrentWeek();
                for (Evenement evenement : evenementsSemaine) {
                    // Add event to agenda
                    agenda.appointments().add(new Agenda.AppointmentImplLocal()
                            .withStartLocalDateTime(evenement.getDate_Debut().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().withHour(9).withMinute(0))
                            .withEndLocalDateTime(evenement.getDate_Fin().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                            .withSummary(evenement.getNom_Event())
                            .withDescription(evenement.getDescription()));
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                // Handle exception
                // Show an error dialog with the exception message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Une erreur s'est produite lors de la récupération des événements.");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        });
    }

}
