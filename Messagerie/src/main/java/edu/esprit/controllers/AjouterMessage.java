package edu.esprit.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import edu.esprit.entities.Messagerie;
import edu.esprit.services.ServiceMessagerie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AjouterMessage {

    private final ServiceMessagerie ps = new ServiceMessagerie();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker DateId;

    @FXML
    private TextField MessageId;

    @FXML
    private TextField NomId;

    @FXML
    void Afficher(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPersonne.fxml"));
        Parent root = loader.load();
        NomId.getScene().setRoot(root);
    }

    @FXML
    void Ajouter(ActionEvent event) {
        try {
            ps.ajouter(new Messagerie(NomId.getText(), String.valueOf(DateId.getValue()), MessageId.getText())); // Corrected parameter name
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Validation");
            alert.setContentText("Message added successfully"); // Updated content text
            alert.showAndWait();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherMessage.fxml"));
            Parent root = loader.load();
            NomId.getScene().setRoot(root);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Exception");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
