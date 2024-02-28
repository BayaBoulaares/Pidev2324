package edu.esprit.controllers;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.regex.Pattern;

import edu.esprit.entities.Messagerie;
import edu.esprit.services.ServiceMessagerie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene; // Import Scene class
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import java.time.LocalDate;
import java.util.regex.Pattern;

public class AjouterMessage {

    private final ServiceMessagerie ps = new ServiceMessagerie();

    @FXML
    private DatePicker DateId;

    @FXML
    private TextField MessageId;

    @FXML
    private TextField NomId;
    @FXML
    private javafx.scene.control.Button Goback;
    @FXML
    private Button ChatBot;


    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void Ajouter(ActionEvent event) {
        try {
            // Validation des champs
            if (validateFields()) {
                String nom = NomId.getText();
                LocalDate date = DateId.getValue();
                String message = MessageId.getText();

                // Capitalize the first letter of the message
                message = message.substring(0, 1).toUpperCase() + message.substring(1);

                ps.ajouter(new Messagerie(NomId.getText(), String.valueOf(DateId.getValue()), message));

                showNotification();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Validation");
                alert.setContentText("Message added successfully");
                alert.showAndWait();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherMessage.fxml"));
                Parent root = loader.load();

                // Retrieve the current scene from any control
                Scene currentScene = NomId.getScene();

                // Check if already on the "AfficherMessage" scene before setting the root
                if (currentScene.getRoot() != root) {
                    currentScene.setRoot(root);
                }
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Exception");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    // Méthode pour valider les champs
    private boolean validateFields() {
        if (NomId.getText().isEmpty() || DateId.getValue() == null || MessageId.getText().isEmpty()) {
            showAlert("Please fill in all fields.");
            return false;
        }

        // Validate date
        if (!isValidDate(DateId.getValue())) {
            showAlert("Please select a valid date (not less than the current date).");
            return false;
        }

        // Validate name (no symbols allowed)
        if (!isValidName(NomId.getText())) {
            showAlert("Invalid name. It cannot contain symbols (@ # $ *).");
            return false;
        }

        // Validate message (customize based on your criteria)
        if (!isValidMessage(MessageId.getText())) {
            showAlert("Invalid message. Customize this validation based on your criteria.");
            return false;
        }

        return true;
    }

    // Validate date method
    private boolean isValidDate(LocalDate date) {
        // Check if the selected date is not less than the current date
        return date != null && !date.isBefore(LocalDate.now());
    }

    // Validate name method (no symbols allowed)
    private boolean isValidName(String name) {
        // Check if the name contains only letters, spaces, and digits
        return Pattern.matches("[a-zA-Z\\s\\d]+", name) && !name.matches(".*[@#$*].*");
    }


    // Validate message method (customize based on your criteria)
    private boolean isValidMessage(String message) {
        // Add your custom message validation logic here
        // For example, you can check the length or specific content criteria
        return !message.isEmpty();
    }

    @FXML
    void Afficher(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherMessage.fxml"));
        Parent root = loader.load();

        // Retrieve the current scene from any control
        Scene currentScene = DateId.getScene();

        // Check if already on the "AfficherPersonne" scene before setting the root
        if (currentScene.getRoot() != root) {
            currentScene.setRoot(root);
        }
    }

    private void showNotification() {
        if (SystemTray.isSupported()) {
            try {
                SystemTray tray = SystemTray.getSystemTray();

                // Use the correct path
                Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\omarh\\IdeaProjects\\Messagerie\\src\\image\\images.png");

                TrayIcon trayIcon = new TrayIcon(icon, "Notification");
                trayIcon.setImageAutoSize(true);

                trayIcon.addActionListener(e -> {
                    // Handle the tray icon click event if needed
                });

                tray.add(trayIcon);
                trayIcon.displayMessage("Success", "Message successfully", TrayIcon.MessageType.INFO);
            } catch (AWTException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void Reclamer(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterReclamation.fxml"));
        Parent root = loader.load();

        // Retrieve the current scene from any control
        Scene currentScene = DateId.getScene();

        // Check if already on the "AfficherPersonne" scene before setting the root
        if (currentScene.getRoot() != root) {
            currentScene.setRoot(root);
        }
    }
    @FXML
    void chat(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ChatBot.fxml"));
        Parent root = loader.load();

        // Retrieve the current scene from any control
        Scene currentScene = DateId.getScene();

        // Check if already on the "AfficherPersonne" scene before setting the root
        if (currentScene.getRoot() != root) {
            currentScene.setRoot(root);
        }

    }
}








