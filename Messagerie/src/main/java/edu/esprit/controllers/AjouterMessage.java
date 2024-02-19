package edu.esprit.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import edu.esprit.entities.Messagerie;
import edu.esprit.services.ServiceMessagerie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene; // Import Scene class
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AjouterMessage {

    private final ServiceMessagerie ps = new ServiceMessagerie();

    @FXML
    private DatePicker DateId;

    @FXML
    private TextField MessageId;

    @FXML
    private TextField NomId;
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

                // Vérifier si le message commence par une majuscule
                if (!message.isEmpty() && !Character.isUpperCase(message.charAt(0))) {
                    showAlert("Le message doit commencer par une majuscule.");
                    return;
                }

                ps.ajouter(new Messagerie(NomId.getText(), String.valueOf(DateId.getValue()), MessageId.getText()));
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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Validation Error");
            alert.setContentText("Please fill in all fields.");
            alert.showAndWait();
            return false;
        }
        return true;
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
}

