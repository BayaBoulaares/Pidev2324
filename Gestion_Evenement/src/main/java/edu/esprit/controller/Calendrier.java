package edu.esprit.controller;

import edu.esprit.entities.Evenement;
import edu.esprit.services.ServiceEvenement;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import jfxtras.scene.control.agenda.Agenda;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Collection;
import java.util.List;
import java.util.Locale;


public class Calendrier {

    @FXML
    private VBox calendrierBox;
    private ServiceEvenement serviceEvenement; // Assuming you have a ServiceEvenement instance

    public Calendrier() {
        serviceEvenement = new ServiceEvenement(); // Initialize serviceEvenement in the constructor
    }

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


        try {
            Collection<Evenement> events = serviceEvenement.getAll(); // Get events using your existing method
            addEventsToAgenda(events, agenda);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void addEventsToAgenda(Collection<Evenement> events, Agenda agenda) {
        for (Evenement event : events) {
            LocalDate startDate = new java.util.Date(event.getDate_Debut().getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDateTime start = startDate.atTime(9, 0); // Set start time at 9 o'clock
            LocalDate endDate = new java.util.Date(event.getDate_Fin().getTime()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDateTime end = endDate.atTime(13, 0); // Set end time at 13 o'clock

            agenda.appointments().add(new Agenda.AppointmentImplLocal()
                    .withStartLocalDateTime(start)
                    .withEndLocalDateTime(end)
                    .withSummary(event.getNom_Event()));
        }
    }



}
