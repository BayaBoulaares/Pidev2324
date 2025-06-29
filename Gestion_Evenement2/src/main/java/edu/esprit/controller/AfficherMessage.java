package edu.esprit.controller;

import edu.esprit.entities.Messagerie;
import edu.esprit.entities.ParentE;
import edu.esprit.services.CredentialsManager;
import edu.esprit.services.ServiceMessagerie;
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
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;

import java.awt.*;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;

import static edu.esprit.services.DashboardUser.userId;
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
    @FXML
    private TextField searchField;




    public final ServiceMessagerie ps = new ServiceMessagerie();
    private Messagerie messagerie;
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
                    messagerie.setMessage(convertSymbolsToEmojis(censoredMessage));


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
                Image icon = Toolkit.getDefaultToolkit().getImage("/fxml/images/images.png");

                TrayIcon trayIcon = new TrayIcon(icon, "Notification");
                trayIcon.setImageAutoSize(true);

                trayIcon.addActionListener(e -> {
                    // Handle the tray icon click event if needed
                });

                tray.add(trayIcon);
                trayIcon.displayMessage("Warning", "Your message will not be shown because it contains bad words.\n But we will add it", TrayIcon.MessageType.WARNING);
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

    private ParentE prod;
    public void setProftoGet(ParentE prof)
    {
        System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbb");

        prod=prof;
        System.out.println(prod);

    }
    @FXML
    void modifiermessage(ActionEvent event) {
        try {
            if (validateSelection1()) {
                if (validateInput()) {

                    ServiceMessagerie ps = new ServiceMessagerie();

                    // Assuming messagerie is already initialized
                    // Modify only the message, leaving the name unchanged
                    messagerie = new Messagerie(messagerie.getId(), messagerie.getNom(), MeesageID.getText() );

                    // Automatically capitalize the first letter of the message
                    String capitalizedMessage = capitalizeFirstLetter(messagerie.getMessage());
                    messagerie.setMessage(capitalizedMessage);

                    // Convert symbols to emojis
                    messagerie.setMessage(convertSymbolsToEmojis(messagerie.getMessage()));

                    ps.modifier(messagerie);

                    // Check if the message has been modified
                    String censoredMessage = censorBadWords(messagerie.getMessage());
                    if (!messagerie.getMessage().equals(censoredMessage)) {
                        showNotification3(); // notification mtaa el bad words
                    } else {
                        showNotification1(); // notification kn jawha bhy
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


    private String capitalizeFirstLetter(String text) {
        if (text.isEmpty()) {
            return text;
        }
        return Character.toUpperCase(text.charAt(0)) + text.substring(1);
    }


    private void updateTableView() {
        try {
            List<Messagerie> messagerieList = new ArrayList<>(ps.getAll());
            ObservableList<Messagerie> observableList = FXCollections.observableList(messagerieList);

            // Censor bad words and convert symbols to emojis before displaying them
            observableList.forEach(messagerie -> {
                messagerie.setNom(censorBadWords(messagerie.getNom()));
                messagerie.setMessage(convertSymbolsToEmojis(messagerie.getMessage()));
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
    void supprimermessage(ActionEvent event) {
        try {
            if (validateSelection()) {
                ServiceMessagerie ps = new ServiceMessagerie();
                if (messagerie != null) {
                    if (showDeleteConfirmationDialog()) {
                        ps.supprimer(messagerie.getId());
                    }
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
                Image icon = Toolkit.getDefaultToolkit().getImage("/fxml/images/images.png");

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
                Image icon = Toolkit.getDefaultToolkit().getImage("/fxml/images/images.png");

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AjouterMessage.fxml"));
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
            List<Messagerie> messagerieList = new ArrayList<>(ps.getAll());  // Assuming ps.getAll() returns a List

            ObservableList<Messagerie> observableList = FXCollections.observableList(messagerieList);
            FilteredList<Messagerie> filteredList = new FilteredList<>(observableList, p -> true);

            // Bind the search field text to the filter predicate
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate(messagerie -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true; // Show all items when the filter is empty
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    // Check if any property of the Messagerie contains the filter text
                    return censorBadWords(messagerie.getNom().toLowerCase()).contains(lowerCaseFilter)
                            || censorBadWords(messagerie.getMessage().toLowerCase()).contains(lowerCaseFilter)
                            || censorBadWords(String.valueOf(messagerie.getDate()).toLowerCase()).contains(lowerCaseFilter);
                });
            });

            SortedList<Messagerie> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(TableView.comparatorProperty());

            TableView.setItems(sortedList);

        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception properly in your application
        }
    }


@FXML
    public void toMatiere(ActionEvent actionEvent) throws IOException {
    FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/AfficherMatiere.fxml"));
    Parent root=loader.load();
    /*AffichageMatiereController acm=loader.getController();
    acm.setProftoGet(matiere.getProf());*/
    DateID.getScene().setRoot(root);
    }
@FXML
    public void tomessage(ActionEvent actionEvent) {

    }
@FXML
    public void deconnexion(ActionEvent actionEvent) throws IOException {
    CredentialsManager.clearCredentials();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
    Parent root = loader.load();
    DateID.getScene().setRoot(root);
    }
    @FXML
    public void toEvaluation(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/gui/Quiz/admin/homePage.fxml"));
        Parent root=loader.load();
        DateID.getScene().setRoot(root);
    }
}

