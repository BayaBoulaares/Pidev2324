package edu.esprit.services;

import edu.esprit.controller.AffichageMatiereController;
import edu.esprit.controller.AfficherEvenement;
import edu.esprit.controller.AfficherEvents;
import edu.esprit.entities.Matiere;
import edu.esprit.entities.Professeur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardAdmin {


    @FXML
    private Button toprofesseur;

    @FXML
    private Button parent;

    private Professeur po;

    @FXML
    void toProfesseurs(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CrudAdmins.fxml"));
        Parent root = loader.load();
        toprofesseur.getScene().setRoot(root);

    }

    public void deconnexion(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Parent root = loader.load();
        toprofesseur.getScene().setRoot(root);
    }

    @FXML
    void toparent(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AffichageParent.fxml"));
        Parent root = loader.load();
        toprofesseur.getScene().setRoot(root);

    }

    public void toaccueil(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DashobardAdmin.fxml"));
        Parent root = loader.load();
        toprofesseur.getScene().setRoot(root);
    }

    @FXML
    public void ConsulterMatiere(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AfficherMatierAdmin.fxml"));
        Parent root = loader.load();
        toprofesseur.getScene().setRoot(root);
    }

    @FXML
    public void showEvent(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Ajout_Evenement.fxml"));
        Parent root = loader.load();
        toprofesseur.getScene().setRoot(root);

    }
}