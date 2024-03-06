package edu.esprit.controller;

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
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class DetailMatierAdmin {

    @FXML
    private Button dcxn;

    @FXML
    private Button evaluation1;

    @FXML
    private Button evenements1;

    @FXML
    private TextField idanne;

    @FXML
    private TextField idcat;

    @FXML
    private Button idcm;

    @FXML
    private TextArea iddesc;

    @FXML
    private AnchorPane idn;

    @FXML
    private TextField idnembre;

    @FXML
    private TextField idnom;

    @FXML
    private Button idret;

    @FXML
    private Button parent;

    @FXML
    private Button reclamation1;

    @FXML
    private Button toaccueil;

    @FXML
    private Button toprofesseur;

   private Matiere me;


    public void setMatiereToShow(Matiere ma)
    {     me=ma;
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
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/AfficherMatierAdmin.fxml"));
            Parent root = (Parent) loader.load();
            this.idnom.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur de chargement", "Impossible de charger la page d'affichage");
        }

    }

    @FXML
    public void deconnexion(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Parent root=loader.load();
        toprofesseur.getScene().setRoot(root);
    }
@FXML
    public void toaccueil(ActionEvent actionEvent) throws IOException {
    FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/DashobardAdmin.fxml"));
    Parent root=loader.load();
    toprofesseur.getScene().setRoot(root);
    }
@FXML
    public void toparent(ActionEvent actionEvent) throws IOException {
    FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/AffichageParent.fxml"));
    Parent root=loader.load();
    toprofesseur.getScene().setRoot(root);
    }
@FXML
    public void ConsulterMatiere(ActionEvent actionEvent) throws IOException {
    FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/AfficherMatierAdmin.fxml"));
    Parent root=loader.load();
    toprofesseur.getScene().setRoot(root);
    }
@FXML
    public void toProfesseurs(ActionEvent actionEvent) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CrudAdmins.fxml"));
    Parent root = loader.load();
    toprofesseur.getScene().setRoot(root);
    }
@FXML
    public void toEvent(ActionEvent actionEvent) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Ajout_Evenement.fxml"));
    Parent root = loader.load();
    toprofesseur.getScene().setRoot(root);
    }
    @FXML
    public void toReclamation(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AfficherReclamation.fxml"));
        Parent root = loader.load();

        reclamation1.getScene().setRoot(root);
    }
}
