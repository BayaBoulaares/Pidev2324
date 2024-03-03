package edu.esprit.controller;

import edu.esprit.services.CredentialsManager;
import edu.esprit.services.ServiceSponsor;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class AfficherStatistique {

    @FXML
    private VBox pieChartContainer;

    @FXML
    void initialize() {
        ServiceSponsor serviceSponsor = new ServiceSponsor();

        PieChart pieChart = new PieChart();
        pieChart.setLegendVisible(true);

        try {
            Map<String, Integer> nombreEvenementsParSponsor = serviceSponsor.getNombreEvenementsParSponsor();

            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

            // Define your colors
            Color[] pieColors = {
                    Color.web("#40E0D0"),  // Turquoise
                    Color.web("#00008B"),  // Dark Blue
                    Color.web("#ADD8E6"),  // Light Blue
                    Color.web("#808080")   // Gray
            };

            AtomicInteger colorIndex = new AtomicInteger(0);

            for (Map.Entry<String, Integer> entry : nombreEvenementsParSponsor.entrySet()) {
                PieChart.Data data = new PieChart.Data(entry.getKey(), entry.getValue());
                pieChartData.add(data);

                // Set the color of the pie slice
                data.nodeProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        newValue.setStyle("-fx-pie-color: " + toRGBCode(pieColors[colorIndex.get() % pieColors.length]) + ";");
                        colorIndex.getAndIncrement();
                    }
                });
            }

            pieChart.setData(pieChartData);

            // Add animation
            Timeline timeline = new Timeline();
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.setAutoReverse(false);
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(100), (e) -> {
                pieChart.setStartAngle(pieChart.getStartAngle() + 1);
            }));
            timeline.play();

            pieChartContainer.getChildren().add(pieChart);
        } catch (SQLException ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur lors de la récupération des statistiques");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    private static String toRGBCode(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }
    @FXML
    public void deconnexion(ActionEvent actionEvent) throws IOException {
        CredentialsManager.clearCredentials();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Parent root = loader.load();
        // Utilisez getRoot() au lieu de getScene()
        ((Node) actionEvent.getSource()).getScene().setRoot(root);
    }

    @FXML
    public void toMatiere(ActionEvent actionEvent) throws IOException {
        CredentialsManager.clearCredentials();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AfficherMatierAdmin.fxml"));
        Parent root = loader.load();
        // Utilisez getRoot() au lieu de getScene()
        ((Node) actionEvent.getSource()).getScene().setRoot(root);
    }
    @FXML
    public void tohisprofile(ActionEvent actionEvent) throws IOException {
        CredentialsManager.clearCredentials();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ProfileUser.fxml"));
        Parent root = loader.load();
        // Utilisez getRoot() au lieu de getScene()
        ((Node) actionEvent.getSource()).getScene().setRoot(root);
    }
    @FXML
    public void toacceuiel(ActionEvent actionEvent) throws IOException {
        CredentialsManager.clearCredentials();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DashobardAdmin.fxml"));
        Parent root = loader.load();
        // Utilisez getRoot() au lieu de getScene()
        ((Node) actionEvent.getSource()).getScene().setRoot(root);
    }

}
