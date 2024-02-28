import edu.esprit.entities.Evenement;
import edu.esprit.entities.Sponsor;
import edu.esprit.services.ServiceEvenement;
import edu.esprit.services.ServiceSponsor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

/*public class Statistique implements Initializable {

    public VBox statsContainer;
    public Label mostSponsoringLabel;
    public PieChart pieChart;

    private final ServiceSponsor serviceSponsor = new ServiceSponsor();
    private final ServiceEvenement serviceEvenement = new ServiceEvenement();

    private Evenement selectedEvent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize your view and load statistics here
        // For demonstration purposes, let's assume selectedEvent is set elsewhere, such as through a setter method
        selectedEvent =; // Set the selected event

        afficherStatistiques();
    }

    private void afficherStatistiques() {
        try {
            // Get sponsors for the selected event
           // List<Sponsor> sponsors = serviceSponsor.getSponsorsByEvent(selectedEvent.getId_Event());

            // Create pie chart data
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

            // Add each sponsor to the pie chart data
            for (Sponsor sponsor : sponsors) {
                pieChartData.add(new PieChart.Data(sponsor.getNomSponsor(), 1)); // Assuming each sponsor has the same weight for now
            }

            // Set the pie chart data
            pieChart.setData(pieChartData);
            pieChart.setTitle("Sponsors Activity for " + selectedEvent.getNom_Event());
        } catch (SQLException e) {
            afficherAlerte("Erreur lors de la récupération des données : " + e.getMessage());
        }
    }

    private void afficherAlerte(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
*/