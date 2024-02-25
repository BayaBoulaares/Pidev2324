package edu.esprit.controllers;

import edu.esprit.entities.Reclamation;
import edu.esprit.services.ServiceReclamation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AjouterReclamation {

    private final ServiceReclamation rs = new ServiceReclamation();

    @FXML
    private DatePicker DateId;

    @FXML
    private TextField ReclamationId;

    @FXML
    private TextField NomId;

    @FXML
    private ComboBox<String> comboBox;




    private void showAlert(String reclamation) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText(null);
        alert.setContentText(reclamation);
        alert.showAndWait();
    }



    @FXML
    void Goback(ActionEvent event) {
        try {
            // Load the Ajouter interface FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterMessage.fxml"));
            Parent ajouterInterface = loader.load();

            // Create a new scene
            Scene ajouterScene = new Scene(ajouterInterface);

            // Get the current stage
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the scene and show the stage
            currentStage.setScene(ajouterScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception properly in your application
        }
    }


    @FXML
    void Ajouter(ActionEvent event) {
        try {
            // Validation des champs
            if (validateFields()) {
                String nom = NomId.getText();
                String reclamation = ReclamationId.getText();
                LocalDate date = DateId.getValue();

                // Capitalize the first letter of the message
                reclamation = reclamation.substring(0, 1).toUpperCase() + reclamation.substring(1);

                rs.ajouter(new Reclamation(nom, reclamation, Date.valueOf(date)));

                // Affichez la réponse dans une alerte
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Confirmation");
                alert.setContentText("Réclamation ajoutée avec succès.\nOption sélectionnée : " + reclamation);
                alert.showAndWait();

                showNotification();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherReclamation.fxml"));
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
    private boolean isValidMessage(String reclamation) {
        // Add your custom message validation logic here
        // For example, you can check the length or specific content criteria
        return !reclamation.isEmpty();
    }

    private boolean validateFields() {
        if (NomId.getText().isEmpty() || DateId.getValue() == null || ReclamationId.getText().isEmpty() || comboBox.getValue() == null) {
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
        if (!isValidMessage(ReclamationId.getText())) {
            showAlert("Invalid message. Customize this validation based on your criteria.");
            return false;
        }

        // Validate ComboBox selection
        if (comboBox.getValue().isEmpty()) {
            showAlert("Please select a reclamation type from the dropdown.");
            return false;
        }

        return true;
    }


    @FXML
    void Afficher(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherReclamation.fxml"));
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

    public void comboboxselected(ActionEvent actionEvent) {
        // Gérez l'événement de sélection du ComboBox
        String selectedOption = comboBox.getValue();

        // Affichez l'option sélectionnée dans le champ ReclamationId
        ReclamationId.setText(selectedOption);
    }

    @FXML
    void initialize() {
        // Initialisez le ComboBox avec des réclamations spécifiques
        ObservableList<String> reclamationOptions = FXCollections.observableArrayList(
                "Problème de cours",
                "Reclamation pour proffeseur",
                "Reclamation pour etudiant",
                "Problèmes techniques",
                "Difficultés d'utilisation d'application",
                "Suggestions d'amélioration"
        );
        comboBox.setItems(reclamationOptions);
    }
}
