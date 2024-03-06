package edu.esprit.controller;

import edu.esprit.APIapploadfichier.Statistique;
import edu.esprit.APIapploadfichier.StatistiquesAPI;
import edu.esprit.entities.Matiere;
import edu.esprit.services.CredentialsManager;
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
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import javafx.stage.Stage;


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
    private Button idstate;

    @FXML
    private Button idret;
 private  Matiere me;


    public void setMatiereToShow(Matiere ma)
    {  me=ma;
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
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/AfficherMatiere.fxml"));
            Parent root = (Parent) loader.load();
            AffichageMatiereController acm=loader.getController();
            acm.setProftoGet(me.getProf());
            this.idnom.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur de chargement", "Impossible de charger la page d'affichage");
        }

    }
@FXML
    public void tohisprofile(ActionEvent actionEvent) {
    }
@FXML
    public void toMatiere(ActionEvent actionEvent) throws IOException {
    FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/acceuilMatiere.fxml"));
    Parent root=loader.load();
    AffichageMatiereController acm=loader.getController();
    acm.setProftoGet(me.getProf());
    idret.getScene().setRoot(root);
    }
@FXML
    public void deconnexion(ActionEvent actionEvent) throws IOException {
    CredentialsManager.clearCredentials();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
    Parent root = loader.load();
    idret.getScene().setRoot(root);
    }
@FXML
    public void tomessage(ActionEvent actionEvent) throws IOException {
    FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/AfficherMessage.fxml"));
    Parent root=loader.load();
    idret.getScene().setRoot(root);


}
}