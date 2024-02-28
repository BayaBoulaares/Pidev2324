package edu.esprit.controllers;

import edu.esprit.entities.ChatBot;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author MSI
 */
public class ChatBotController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField inputTextField;

    @FXML
    private TextArea outputLabel;


    @FXML
    private void processInput(ActionEvent event) {
        String input = inputTextField.getText();
        String output = ChatBot.processInput(input);
        outputLabel.setText(output);
        inputTextField.clear();
    }

    @FXML
    private void Accueil(ActionEvent event) throws IOException {
        Parent AjouterParent = FXMLLoader.load(getClass().getResource("/ChatBot.fxml"));
        Scene AjouterScene = new Scene(AjouterParent);

        //this line gets the stage by force
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(AjouterScene);
        window.show();
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
}
