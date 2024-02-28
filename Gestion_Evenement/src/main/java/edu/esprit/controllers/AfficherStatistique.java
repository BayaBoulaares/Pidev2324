package edu.esprit.controllers;


import edu.esprit.services.ServiceSponsor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.Map;
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


            for (Map.Entry<String, Integer> entry : nombreEvenementsParSponsor.entrySet()) {
                pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
            }

            pieChart.setData(pieChartData);

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
}
