package edu.esprit.controllers;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import edu.esprit.entities.Messagerie;
import edu.esprit.entities.User;
import edu.esprit.services.ServiceMessagerie;
import edu.esprit.services.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.application.Platform;
import server.Launcher;
import server.ServerLauncher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;




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
    private static final List<String> BAD_WORDS = Arrays.asList("Sick", "Bad", "Dump");
    private static final Map<String, String> EMOJI_MAP = new HashMap<>();
    private static boolean javaFXApplicationLaunched = false;


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



    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private String capitalizeFirstLetter(String text) {
        if (text.isEmpty()) {
            return text;
        }
        return Character.toUpperCase(text.charAt(0)) + text.substring(1);
    }

    private String convertSymbolsToEmojis(String text) {
        for (Map.Entry<String, String> entry : EMOJI_MAP.entrySet()) {
            // Escape special characters in symbols and replace symbols with emojis
            text = text.replaceAll(Pattern.quote(entry.getKey()), entry.getValue());
        }
        return text;
    }


    @FXML
    void Ajouter(ActionEvent event) {
        try {

            // Validation des champs
            if (validateFields()) {
                String userName = NomId.getText();;

                NomId.setText(userName);

                LocalDate date = DateId.getValue();
                String message = MessageId.getText();

                // Capitalize the first letter of the message
                message = message.substring(0, 1).toUpperCase() + message.substring(1);

                // Assuming you have a UserService instance named userService
                ServiceUser userService = new ServiceUser();

                // Replace 1 with the actual user ID you want to retrieve
                int userId = 1;

                // Fetch the user by ID
                User user = userService.getUserById(userId);

                // Get the name from the user object
                userName = user.getNom();
                NomId.setText(userName);


                // Use the userName as needed in your code
                System.out.println("User Name: " + userName);

                // Rest of the existing code...

                ps.ajouter(new Messagerie(userName, Date.valueOf(date), message, 1));

                // Automatically capitalize the first letter of the message
                String capitalizedMessage = capitalizeFirstLetter(MessageId.getText());
                MessageId.setText(capitalizedMessage);

                // Convert symbols to emojis
                MessageId.setText(convertSymbolsToEmojis(MessageId.getText()));

                String censoredMessage = censorBadWords(MessageId.getText());
                if (!MessageId.getText().equals(censoredMessage)) {
                    showNotification3(); // notification mtaa el bad words
                } else {
                    showNotification(); // notification kn jawha bhy
                }

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

        // Validate date
        if (!isValidDate(DateId.getValue())) {
            showAlert("Please select a valid date (not less than the current date).");
            return false;
        }

        // Validate name (no symbols allowed)
        if (!isValidName(NomId.getText())) {
            showAlert("Invalid name. It cannot contain symbols (@ # $ *).or write u'r correct name");
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

        return !message.isEmpty();
    }

    @FXML
    void Afficher(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherMessage.fxml"));
        Parent root = loader.load();

        // Retrieve the current scene from any control
        Scene currentScene = DateId.getScene();

        // Check if already on the "AfficherMessage" scene before setting the root
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

        // Check if already on the "AfficherMessage" scene before setting the root
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

        if (currentScene.getRoot() != root) {
            currentScene.setRoot(root);
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
                trayIcon.displayMessage("Warning", "Your Message will not be shown because it contains bad words.\n But we will add it", TrayIcon.MessageType.WARNING);
            } catch (AWTException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }





}










