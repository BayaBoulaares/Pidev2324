package edu.esprit.controllers;

import edu.esprit.entities.Messagerie;
import edu.esprit.services.ServiceMessagerie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import static java.awt.SystemColor.text;

public class AfficherMessage {

    @FXML
    private ResourceBundle resources;

    @FXML
    private TableColumn<Messagerie, Date> Date;

    @FXML
    private DatePicker DateID;

    @FXML
    private TextField MeesageID;

    @FXML
    private TableColumn<Messagerie, String> Message;

    @FXML
    private TableColumn<Messagerie, String> Nom;

    @FXML
    private TextField NomID;

    @FXML
    private TableView<Messagerie> TableView;

    @FXML
    private Button Modifier;

    @FXML
    private Button Supprimer;
    @FXML
    private Button Goback;

    public final ServiceMessagerie ps = new ServiceMessagerie();
    private Messagerie messagerie;
    private static final List<String> BAD_WORDS = Arrays.asList("Sick", "Bad", "Dump");


    @FXML
    void initialize() {
        try {
            List<Messagerie> messagerieList = new ArrayList<>(ps.getAll());

            if (messagerieList.isEmpty()) {
                showAlert("Aucune donnée à afficher.");
            } else {
                ObservableList<Messagerie> observableList = FXCollections.observableList(messagerieList);

                // Censor bad words before displaying them
                observableList.forEach(messagerie -> {
                    String censoredNom = censorBadWords(messagerie.getNom());
                    String censoredMessage = censorBadWords(messagerie.getMessage());


                    messagerie.setNom(censoredNom);
                    messagerie.setMessage(censoredMessage);

                    // If the original message is different from the censored message, show the notification
                    if (!messagerie.getMessage().equals(censoredMessage)) {
                        showNotification3();
                    }
                });


                TableView.setItems(observableList);
            }
        } catch (SQLException e) {
            showAlert("Erreur lors du chargement des données : " + e.getMessage());
            e.printStackTrace(); // Handle the exception properly in your application
        }

        Nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        Date.setCellValueFactory(new PropertyValueFactory<>("date"));
        Message.setCellValueFactory(new PropertyValueFactory<>("message"));
        // Add this method for censoring bad words
    }
    private void showNotification3() {
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
                trayIcon.displayMessage("Warning", "Your message will not be shown because it contains bad words.", TrayIcon.MessageType.WARNING);
            } catch (AWTException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String censorBadWords(String text) {
        for (String badWord : BAD_WORDS) {
            // Replace bad words with ****
            text = text.replaceAll("(?i)" + badWord, "****");
        }
        return text;
    }


    @FXML
    public void mouceClicked(javafx.scene.input.MouseEvent mouseEvent) {
        try {
            Messagerie messagerie = TableView.getSelectionModel().getSelectedItem();
            if (messagerie != null && messagerie.getNom() != null && messagerie.getDate() != null && messagerie.getMessage() != null) {
                this.messagerie = new Messagerie(messagerie.getId(), messagerie.getNom(), messagerie.getDate(), messagerie.getMessage());
                NomID.setText(messagerie.getNom());
                MeesageID.setText(messagerie.getMessage());
                DateID.setValue(messagerie.getDate().toLocalDate());
            } else {
                // Display an alert or handle the situation when the selected Messagerie or its attributes are null
                showAlert("Invalid Messagerie data selected.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void modifiermessage(ActionEvent event) {
        try {
            if (validateSelection1()) {
                if (validateInput()) {
                    ServiceMessagerie ps = new ServiceMessagerie();
                    messagerie = new Messagerie(messagerie.getId(), NomID.getText(), MeesageID.getText());

                    // Vérifier si le message commence par une majuscule
                    if (!MeesageID.getText().isEmpty() && !Character.isUpperCase(MeesageID.getText().charAt(0))) {
                        showAlert("Le message doit commencer par une majuscule.");
                        return;
                    }

                    ps.modifier(messagerie);
                    // Check if the message has been modified
                    String censoredMessage = censorBadWords(messagerie.getMessage());
                    if (!messagerie.getMessage().equals(censoredMessage)) {
                        showNotification3();
                    } else {
                        showNotification1();
                    }

                    // After modifying the data, update the TableView
                    updateTableView();

                    initialize(); // You may not need to call initialize() again, depending on your requirements
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateTableView() {
        try {
            List<Messagerie> messagerieList = new ArrayList<>(ps.getAll());
            ObservableList<Messagerie> observableList = FXCollections.observableList(messagerieList);

            // Censor bad words before displaying them
            observableList.forEach(messagerie -> {
                messagerie.setNom(censorBadWords(messagerie.getNom()));
                messagerie.setMessage(censorBadWords(messagerie.getMessage()));
            });

            TableView.setItems(observableList);
        } catch (SQLException e) {
            showAlert("Erreur lors de la mise à jour du TableView : " + e.getMessage());
            e.printStackTrace(); // Handle the exception properly in your application
        }
    }

    private boolean validateInput() {
        String nom = NomID.getText();
        String message = MeesageID.getText();

        if (nom.isEmpty() || message.isEmpty()) {
            showAlert("Veuillez remplir tous les champs.");
            return false;
        }

        // Valider le format du nom (uniquement des lettres et des espaces)
        if (!nom.matches("^[a-zA-Z]+(\\s[a-zA-Z]+)?$")) {
            showAlert("Le nom doit contenir uniquement des lettres et des espaces.");
            return false;
        }

        // Vous pouvez ajouter d'autres validations selon vos besoins

        return true;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    void supprimermessage(javafx.event.ActionEvent event) {
        try {
            if (validateSelection()) {
                ServiceMessagerie ps = new ServiceMessagerie();
                if (messagerie != null) {
                    ps.supprimer(messagerie.getId());
                    showNotification2();

                    initialize();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validateSelection() {
        if (TableView.getSelectionModel().getSelectedItem() == null) {
            showAlert("Veuillez sélectionner un élément à supprimer.");
            return false;
        }
        return true;
    }
    private boolean validateSelection1() {
        if (TableView.getSelectionModel().getSelectedItem() == null) {
            showAlert("Veuillez sélectionner un élément à modifier.");
            return false;
        }
        return true;
    }
    private void showNotification1() {
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
                trayIcon.displayMessage("Success", "Modifier successfully", TrayIcon.MessageType.INFO);
            } catch (AWTException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void showNotification2() {
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
                trayIcon.displayMessage("Success", "Supprimer successfully", TrayIcon.MessageType.INFO);
            } catch (AWTException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
