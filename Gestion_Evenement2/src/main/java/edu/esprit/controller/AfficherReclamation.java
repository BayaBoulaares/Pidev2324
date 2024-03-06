package edu.esprit.controller;

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
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import javafx.scene.control.Label;
import org.controlsfx.control.Rating;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.*;
import java.util.List;
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
    private TableColumn<Reclamation, String> Rating;

    @FXML
    private TextField ReclamationID;

    @FXML
    private Button Supprimer;

    @FXML
    private javafx.scene.control.TableView<Reclamation> TableView;

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
    private ComboBox<String> comboBox;

    @FXML
    private Rating rating;

    @FXML
    private TextField ratingID;


    @FXML
    private PieChart piechart;


    @FXML
    private Pane statisticsPane = new Pane();
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
    }

    private String convertSymbolsToEmojis(String text) {
        if (text != null) {
            for (Map.Entry<String, String> entry : EMOJI_MAP.entrySet()) {
                // Escape special characters in symbols and replace symbols with emojis
                text = text.replaceAll(Pattern.quote(entry.getKey()), entry.getValue());
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

    private void updateTableView() throws SQLException {
        List<Reclamation> reclamationList = new ArrayList<>(ps.getAll());
        ObservableList<Reclamation> observableList = FXCollections.observableList(reclamationList);

        // Censor bad words and convert symbols to emojis before displaying them
        observableList.forEach(reclamation -> {
            reclamation.setNom(censorBadWords(reclamation.getNom()));
            reclamation.setReclamation(convertSymbolsToEmojis(reclamation.getReclamation()));
        });

        TableView.setItems(observableList);
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
        if (text != null) {
            for (String badWord : BAD_WORDS) {
                // Replace bad words with ****
                text = text.replaceAll("(?i)" + badWord, "****");
            }
        }
        return text;
    }


    @FXML
    void goback(ActionEvent event) {
        try {
            // Load the Ajouter interface FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DashobardAdmin.fxml"));
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
            if (validateSelection1() && validateInput()) {
                ServiceReclamation ps = new ServiceReclamation();

                // Assuming reclamation is already initialized
                // Modify only the reclamation details, leaving the name unchanged
                reclamation = new Reclamation(reclamation.getId(), reclamation.getNom(), ReclamationID.getText(), DateID.getValue(), ratingID.getText());

                // Automatically capitalize the first letter of the message
                String capitalizedMessage = capitalizeFirstLetter(reclamation.getReclamation());
                reclamation.setReclamation(capitalizedMessage);

                // Convert symbols to emojis
                reclamation.setReclamation(convertSymbolsToEmojis(reclamation.getReclamation()));

                // Update the data in the database
                ps.modifier(reclamation);

                // Check if the message has been modified
                String censoredMessage = censorBadWords(reclamation.getReclamation());
                if (!reclamation.getReclamation().equals(censoredMessage)) {
                    showNotification3(); // notification mtaa el bad words
                } else {
                    showNotification1(); // notification kn jawha bhy
                }

                // Refresh the TableView
                updateTableView();
                initialize();
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur lors de la modification : " + e.getMessage());
        }
    }



    @FXML
    void mouceClicked(MouseEvent event) {
        try {
            Reclamation reclamation = TableView.getSelectionModel().getSelectedItem();
            if (reclamation != null && reclamation.getNom() != null && reclamation.getDate() != null && reclamation.getReclamation() != null) {
                this.reclamation = new Reclamation(reclamation.getId(), reclamation.getNom(), reclamation.getReclamation(), reclamation.getDate().toLocalDate(),reclamation.getRating());
                NomID.setText(reclamation.getNom());
                DateID.setValue(reclamation.getDate().toLocalDate());

                ReclamationID.setText(reclamation.getReclamation());
            } else {
                // Display an alert or handle the situation when the selected reclamation or its attributes are null
                showAlert("Invalid reclamation data selected.");
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
    public void comboboxselected(ActionEvent actionEvent) {
        // Gérez l'événement de sélection du ComboBox
        String selectedOption = comboBox.getValue();

        // Affichez l'option sélectionnée dans le champ ReclamationId
        ReclamationID.setText(selectedOption);
    }


    @FXML
    void initialize() throws SQLException {
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

            // Initialize the ComboBox with specific reclamations
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

                    // Call loadStatistics with the current rating value (t1)
                    loadStatistics();
                }
            });

            TableView.setItems(observableList);

            // Call loadStatistics with a default value or any specific value you want
            loadStatistics();
        }

        Nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        Date.setCellValueFactory(new PropertyValueFactory<>("date"));
        Reclamation.setCellValueFactory(new PropertyValueFactory<>("reclamation"));
        Rating.setCellValueFactory(new PropertyValueFactory<>("rating"));
    }


    public void processRatingStatistics(Number t1) {
        if (t1.doubleValue() <= 3.0) {
            // Rating is less than or equal to 3
            System.out.println("Rating is low. Additional actions for low rating can be performed here.");
            // You can add more actions or logic specific to low ratings
        } else {
            // Rating is greater than 3
            System.out.println("Rating is good. Additional actions for good rating can be performed here.");
            // You can add more actions or logic specific to good ratings
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
            List<Reclamation> reclamationsList = new ArrayList<>(ps.getAll());  // Assuming ps.getAll() returns a List

            ObservableList<Reclamation> observableList = FXCollections.observableList(reclamationsList);
            FilteredList<Reclamation> filteredList = new FilteredList<>(observableList, p -> true);

            // Bind the search field text to the filter predicate
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate(reclamation -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true; // Show all items when the filter is empty
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    // Check if any property of the Reclamation contains the filter text
                    return reclamation.getNom().toLowerCase().contains(lowerCaseFilter)
                            || reclamation.getReclamation().toLowerCase().contains(lowerCaseFilter)
                            || String.valueOf(reclamation.getDate()).toLowerCase().contains(lowerCaseFilter);
                });
            });

            SortedList<Reclamation> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(TableView.comparatorProperty());

            TableView.setItems(sortedList);

        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception properly in your application
        }
    }



    private void loadStatistics() {
        try {
            // Fetch ratings from Reclamation Service with associated event IDs
            List<Reclamation> ratings = new ArrayList<>(ServiceReclamation.getInstance().getAll());

            // Count the total number of ratings
            int totalRatings = ratings.size();

            // Create a map to store the count of each rating category
            Map<String, Integer> ratingCountMap = new HashMap<>();

            // Count the occurrences of each rating category
            for (Reclamation rating : ratings) {
                String ratingValue = rating.getRating();

                // Update the count for the specific rating category
                ratingCountMap.put(ratingValue, ratingCountMap.getOrDefault(ratingValue, 0) + 1);
            }

            // Create data for the PieChart
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

            // Calculate percentage for each rating category and add it to the PieChart data
            for (Map.Entry<String, Integer> entry : ratingCountMap.entrySet()) {
                String ratingValue = entry.getKey();
                int count = entry.getValue();

                double percentage = (count * 100.0) / totalRatings;

                // Add data with explicit title to the PieChart
                String title = ratingValue.isEmpty() ? "No Rating" : ratingValue;
                title += " (" + String.format("%.2f", percentage) + "%)";

// Use PieChart.Data constructor with explicit title
                PieChart.Data data = new PieChart.Data(title, count);
                pieChartData.add(data);

            }

            // Print the titles and counts for verification
            for (PieChart.Data data : pieChartData) {
                System.out.println("Title: " + data.getName() + ", Count: " + data.getPieValue());
            }

            // Configure the PieChart
            piechart.setData(pieChartData);
            piechart.setTitle("Rating Statistics");
            piechart.setLegendVisible(true); // Show legend
            piechart.setLabelsVisible(true); // Show labels

            // Set custom data labels with titles and percentages
            for (PieChart.Data data : piechart.getData()) {
                String originalTitle = data.getName();
                double count = data.getPieValue();

                // Extract the rating value from the title
                String ratingValue = originalTitle.split("\\s+")[0]; // Assuming rating is the first word

                // Calculate percentage for the current data
                double percentage = (count * 100.0) / totalRatings;

                // Set the label with the rating value and percentage
                String label = ratingValue + " (" + String.format("%.2f", percentage) + "%)";
                data.setName(label);
                // Additional configurations
                piechart.setLabelLineLength(10);
                piechart.setLegendSide(Side.LEFT); // Import javafx.geometry.Side if not already imported
            }

        } catch (Exception e) {
            // Handle specific exceptions if needed
            e.printStackTrace();
        }
    }

    private static String toRGBCode(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }
@FXML
    public void toacceuiel(ActionEvent actionEvent) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DashobardAdmin.fxml"));
    Parent root = loader.load();
    DateID.getScene().setRoot(root);
    }
@FXML
    public void tohisprofile(ActionEvent actionEvent) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AffichageParent.fxml"));
    Parent root = loader.load();
    DateID.getScene().setRoot(root);
    }
@FXML
    public void toMatiere(ActionEvent actionEvent) throws IOException {

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AfficherMatierAdmin.fxml"));
    Parent root = loader.load();
    DateID.getScene().setRoot(root);

    }
@FXML
    public void toProfe(ActionEvent actionEvent) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CrudAdmins.fxml"));
    Parent root = loader.load();
    DateID.getScene().setRoot(root);

}
@FXML
    public void toReclamation(ActionEvent actionEvent) {
    }
@FXML
    public void deconnexion(ActionEvent actionEvent) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
    Parent root = loader.load();
    DateID.getScene().setRoot(root);
    }
@FXML
    public void toEvente(ActionEvent actionEvent) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Ajout_Evenement.fxml"));
    Parent root = loader.load();
    DateID.getScene().setRoot(root);
    }
}