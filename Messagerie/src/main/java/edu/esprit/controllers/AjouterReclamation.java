package edu.esprit.controllers;

import edu.esprit.entities.Reclamation;
import edu.esprit.services.ServiceReclamation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.Rating;

import java.awt.*;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import javafx.scene.control.Label;




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

    @FXML
    private Rating rating;

    @FXML
    private TextField ratingID;
    Label ratingStatusLabel = new Label();

    private static final List<String> BAD_WORDS = Arrays.asList("Sick", "Bad", "Dump");
    private static final Map<String, String> EMOJI_MAP = new HashMap<>();

    static {
        EMOJI_MAP.put(":)", "😊");
        EMOJI_MAP.put(":(", "😢");
        EMOJI_MAP.put(":D", "😃");
        EMOJI_MAP.put(":-)", "😊");
        EMOJI_MAP.put(":-(", "😢");
        EMOJI_MAP.put(":p", "😛");
        EMOJI_MAP.put(";)", "😉");
        EMOJI_MAP.put("<3", "❤️");
        EMOJI_MAP.put(":/", "☹");
        EMOJI_MAP.put("-_-", "😑");
    }



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

    private String convertSymbolsToEmojis(String text) {
        for (Map.Entry<String, String> entry : EMOJI_MAP.entrySet()) {
            // Escape special characters in symbols and replace symbols with emojis
            text = text.replaceAll(Pattern.quote(entry.getKey()), entry.getValue());
        }
        return text;
    }

    private String capitalizeFirstLetter(String text) {
        if (text.isEmpty()) {
            return text;
        }
        return Character.toUpperCase(text.charAt(0)) + text.substring(1);
    }


    public void processRatingStatistics(Number t1) {
        String message;
        if (t1.doubleValue() <= 3.0) {
            // Rating is less than or equal to 3
            message = "Rating is low. Additional actions for low rating can be performed here.";
            // You can add more actions or logic specific to low ratings
        } else {
            // Rating is greater than 3
            message = "Rating is good. Additional actions for good rating can be performed here.";
            // You can add more actions or logic specific to good ratings
        }

        System.out.println(message);

        // Show notification
        Notifications.create()
                .title("Rating Notification")
                .text(message)
                .hideAfter(Duration.seconds(5))  // Duration for how long the notification should be shown
                .show();
    }

    @FXML
    void Ajouter(ActionEvent event) {
        try {
            // Validation des champs
            if (validateFields()) {
                String nom = NomId.getText();
                String reclamation = ReclamationId.getText();
                LocalDate date = DateId.getValue();
                String rating = ratingID.getText();

                // Capitalize the first letter of the message
                reclamation = reclamation.substring(0, 1).toUpperCase() + reclamation.substring(1);

                rs.ajouter(new Reclamation(nom, reclamation, Date.valueOf(date), rating));


                // Automatically capitalize the first letter of the message
                String capitalizedMessage = capitalizeFirstLetter(ratingID.getText());
                ratingID.setText(capitalizedMessage);

                // Convert symbols to emojis
                ratingID.setText(convertSymbolsToEmojis(ratingID.getText()));


                // Check if the reclamation has been modified
                String censoredMessage = censorBadWords(ReclamationId.getText());
                if (!ReclamationId.getText().equals(censoredMessage)) {
                    showNotification3();//notification mtaa el bad words
                } else {
                    showNotification();//notification kn jawha bhy
                }
                // Affichez la réponse dans une alerte
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Confirmation");
                alert.setContentText("Réclamation ajoutée avec succès.\nOption sélectionnée : " + reclamation);
                alert.showAndWait();



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
            e.printStackTrace(); // Handle the exception appropriately

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
        if (NomId.getText().isEmpty() || DateId.getValue() == null || ReclamationId.getText().isEmpty() ) {
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



        return true;
    }

    @FXML
    void Afficher(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherReclamation.fxml"));
        Parent root = loader.<Parent>load();

        // Retrieve the current scene from any control
        Scene currentScene = DateId.getScene();

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
                trayIcon.displayMessage("Success", "Reclamation successfully sent", TrayIcon.MessageType.INFO);
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

        rating.ratingProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                ratingID.setText("Rate us " + t1 + "/5");
                processRatingStatistics(t1);

            }
        });

    }

    private void showNotification3() {
        if (SystemTray.isSupported()) {
            try {
                SystemTray tray = SystemTray.getSystemTray();

                // Use the correct path
                Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\omarh\\IdeaProjects\\reclamation\\src\\image\\images.png");

                TrayIcon trayIcon = new TrayIcon(icon, "Notification");
                trayIcon.setImageAutoSize(true);

                trayIcon.addActionListener(e -> {
                    // Handle the tray icon click event if needed
                });

                tray.add(trayIcon);
                trayIcon.displayMessage("Warning", "Your reclamation will not be shown because it contains bad words.\n But we will add it", TrayIcon.MessageType.WARNING);
            } catch (AWTException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private String censorBadWords(String text) {
        if (text != null) {
            for (String badWord : BAD_WORDS) {
                // Replace bad words with ****
                text = text.replaceAll("(?i)" + badWord, "****");
            }
        }
        return text;
    }


}
