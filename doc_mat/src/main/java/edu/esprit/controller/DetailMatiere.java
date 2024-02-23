package edu.esprit.controller;

import edu.esprit.entities.Matiere;
import edu.esprit.services.ServiceDocument;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class DetailMatiere {
    @FXML
    private TextField idcat;
    @FXML
    private TextField idanne;

    @FXML
    private TextArea iddesc;

    @FXML
    private TextField idnom;
    @FXML
    private TextField idnembre;

    @FXML
    private Button idret;



    public void setMatiereToShow(Matiere ma)
    {
        ServiceDocument ds=new ServiceDocument();
        idnom.setText(ma.getNommatiere());
        iddesc.setText(ma.getDescription());
        idnembre.setText(String.valueOf(ds.getDocumentCountPerMatiere(ma)));
        idanne.setText(ma.getAnnee());
       System.out.println("Category value: " + ma.getCategorie());
       idcat.setText(ma.getCategorie().toString());
    }
    @FXML
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Renommez la méthode pour respecter la convention camelCase
    @FXML

    public void retourAffichage(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/AfficherMatiere.fxml"));
            Parent root = (Parent) loader.load();
            this.idnom.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur de chargement", "Impossible de charger la page d'affichage");
        }

    }
}