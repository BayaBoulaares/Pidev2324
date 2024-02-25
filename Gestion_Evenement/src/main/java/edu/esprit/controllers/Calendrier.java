package edu.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import jfxtras.scene.control.agenda.Agenda;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

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
    }
}
