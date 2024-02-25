package edu.esprit.controllers;

import edu.esprit.entities.Messagerie;
import edu.esprit.entities.Reclamation;
import edu.esprit.services.ServiceReclamation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.*;
import java.util.regex.Pattern;

public class AfficherReclamation {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Reclamation, Date> Date;

    @FXML
    private DatePicker DateID;

    @FXML
    private Button Modifier;

    @FXML
    private TableColumn<Reclamation, String> Nom;

    @FXML
    private TextField NomID;

    @FXML
    private TableColumn<Reclamation, String> Reclamation;

    @FXML
    private TextField ReclamationID;

    @FXML
    private Button Supprimer;

    @FXML
    private javafx.scene.control.TableView<edu.esprit.entities.Reclamation> TableView;

    @FXML
    private Label firstLabel;

    @FXML
    private Button goback;

    @FXML
    private Label lastLabel;

    @FXML
    private VBox navBar;

    @FXML
    private TextField searchField;


    @FXML
    private Rating rating;
    @FXML
    private TextField msg;




    public final ServiceReclamation ps = new ServiceReclamation();
    private Reclamation reclamation;

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


        // Add more mappings as needed
    }




    private String convertSymbolsToEmojis(String text) {
        for (Map.Entry<String, String> entry : EMOJI_MAP.entrySet()) {
            // Escape special characters in symbols and replace symbols with emojis
            text = text.replaceAll(Pattern.quote(entry.getKey()), entry.getValue());
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
                trayIcon.displayMessage("Warning", "Your reclamation will not be shown because it contains bad words.", TrayIcon.MessageType.WARNING);
            } catch (AWTException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String capitalizeFirstLetter(String text) {
        if (text.isEmpty()) {
            return text;
        }
        return Character.toUpperCase(text.charAt(0)) + text.substring(1);
    }


    private void updateTableView() {
        try {
            List<Reclamation> messagerieList = new ArrayList<>(ps.getAll());
            ObservableList<Reclamation> observableList = FXCollections.observableList(messagerieList);

            // Censor bad words and convert symbols to emojis before displaying them
            observableList.forEach(reclamation -> {
                reclamation.setNom(censorBadWords(reclamation.getNom()));
                reclamation.setReclamation(convertSymbolsToEmojis(reclamation.getReclamation()));
            });


            TableView.setItems(observableList);
        } catch (SQLException e) {
            showAlert("Erreur lors de la mise à jour du TableView : " + e.getMessage());
            e.printStackTrace(); // Handle the exception properly in your application
        }
    }

    private boolean validateInput() {
        String nom = NomID.getText();
        String message = ReclamationID.getText();

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
                Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\omarh\\IdeaProjects\\reclamation\\src\\image\\images.png");

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
                Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\omarh\\IdeaProjects\\reclamation\\src\\image\\images.png");

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


    private void showAlert(String reclamation) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText(null);
        alert.setContentText(reclamation);
        alert.showAndWait();
    }

    private String censorBadWords(String text) {
        for (String badWord : BAD_WORDS) {
            // Replace bad words with ****
            text = text.replaceAll("(?i)" + badWord, "****");
        }
        return text;
    }

    @FXML
    void goback(ActionEvent event) {
        try {
            // Load the Ajouter interface FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterReclamation.fxml"));
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
    void modifier_reclamation(ActionEvent event) {
        try {
            if (validateSelection1()) {
                if (validateInput()) {
                    ServiceReclamation ps = new ServiceReclamation();
                    reclamation = new Reclamation(reclamation.getId(), NomID.getText(), ReclamationID.getText());

                    // Automatically capitalize the first letter of the message
                    String capitalizedMessage = capitalizeFirstLetter(reclamation.getReclamation());
                    reclamation.setReclamation(capitalizedMessage);

                    // Convert symbols to emojis
                    reclamation.setReclamation(convertSymbolsToEmojis(reclamation.getReclamation()));

                    // Vérifier si le message commence par une majuscule
                    if (!capitalizedMessage.isEmpty() && !Character.isUpperCase(capitalizedMessage.charAt(0))) {
                        showAlert("Le message doit commencer par une majuscule.");
                        return;
                    }

                    ps.modifier(reclamation);
                    // Check if the message has been modified
                    String censoredMessage = censorBadWords(reclamation.getReclamation());
                    if (!reclamation.getReclamation().equals(censoredMessage)) {
                        showNotification3();//notification mtaa el bad words
                    } else {
                        showNotification1();//notification kn jawha bhy
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

    @FXML
    void mouceClicked(MouseEvent event) {
        try {
            Reclamation reclamation = TableView.getSelectionModel().getSelectedItem();
            if (reclamation != null && reclamation.getNom() != null && reclamation.getDate() != null && reclamation.getReclamation() != null) {
                this.reclamation = new Reclamation(reclamation.getId(), reclamation.getNom(), reclamation.getReclamation() ,reclamation.getDate());
                NomID.setText(reclamation.getNom());
                DateID.setValue(reclamation.getDate().toLocalDate());

                ReclamationID.setText(reclamation.getReclamation());
            } else {
                // Display an alert or handle the situation when the selected Messagerie or its attributes are null
                showAlert("Invalid Messagerie data selected.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void supprimer_reclamation(ActionEvent event) {
        try {
            if (validateSelection()) {
                ServiceReclamation ps = new ServiceReclamation();
                if (reclamation != null) {
                    if (showDeleteConfirmationDialog()) {
                        ps.supprimer(reclamation.getId());

                    }
                    showNotification2();

                    initialize();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


        @FXML
        void initialize() {
            try {
                List<Reclamation> reclamationList = new ArrayList<>(ps.getAll());

                if (reclamationList.isEmpty()) {
                    showAlert("Aucune donnée à afficher.");
                } else {
                    ObservableList<Reclamation> observableList = FXCollections.observableList(reclamationList);

                    // Censor bad words before displaying them
                    observableList.forEach(reclamation -> {
                        String censoredNom = censorBadWords(reclamation.getNom());
                        String censoredMessage = censorBadWords(reclamation.getReclamation());

                        reclamation.setNom(censoredNom);
                        reclamation.setReclamation(convertSymbolsToEmojis(censoredMessage));

                        // If the original message is different from the censored message, show the notification
                        if (!reclamation.getReclamation().equals(censoredMessage)) {
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
            Reclamation.setCellValueFactory(new PropertyValueFactory<>("reclamation"));
            Date.setCellValueFactory(new PropertyValueFactory<>("date"));  // Make sure your Reclamation class has a 'Date' property of type java.sql.Date

            // Add this method for censoring bad words

            rating.ratingProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                    msg.setText("Rating "+t1);
                }
            });

        }
    private boolean showDeleteConfirmationDialog() {
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirmation");
        confirmationDialog.setHeaderText(null);
        confirmationDialog.setContentText("Are you sure you want to delete this item?");

        Optional<ButtonType> result = confirmationDialog.showAndWait();

        return result.isPresent() && result.get() == ButtonType.OK;
    }
    public void recherche(ActionEvent actionEvent) {
        try {
            List<Reclamation> reclamationsList = new ArrayList<>(ps.getAll());  // Assuming ps.getAll() returns a List

            ObservableList<Reclamation> observableList = FXCollections.observableList(reclamationsList);
            FilteredList<Reclamation> filteredList = new FilteredList<>(observableList, p -> true);

            // Bind the search field text to the filter predicate
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate(messagerie -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true; // Show all items when the filter is empty
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    // Check if any property of the Messagerie contains the filter text
                    return messagerie.getNom().toLowerCase().contains(lowerCaseFilter)
                            || messagerie.getReclamation().toLowerCase().contains(lowerCaseFilter)
                            || String.valueOf(messagerie.getDate()).toLowerCase().contains(lowerCaseFilter);
                });
            });

            SortedList<Reclamation> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(TableView.comparatorProperty());

            TableView.setItems(sortedList);

        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception properly in your application
        }
    }





    }


