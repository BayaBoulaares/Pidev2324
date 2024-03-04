package edu.esprit.services;

import edu.esprit.controller.AcceuilMatiereC;
import edu.esprit.controller.AfficherEvents;
import edu.esprit.controller.AfficherReclamation;
import edu.esprit.controller.AjouterReclamation;
import edu.esprit.entities.ParentE;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;
import java.sql.SQLException;

public class DashboardUser {
    @FXML
    private Button dcxn;

    @FXML
    private Button evaluation1;

    @FXML
    private Button evenements1;

    @FXML
    private Button reclamation1;

    @FXML
    private Button toprofile;
    private ParentE ep;
    public static int userId;
    public void getPe(ParentE ep)
    {
        this.ep=ep;
    }
    public void deconnexion(ActionEvent actionEvent) throws IOException  {
        CredentialsManager.clearCredentials();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Parent root = loader.load();
        reclamation1.getScene().setRoot(root);

    }

    public void tohisprofile(ActionEvent actionEvent) throws IOException {

            FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/ProfileUser.fxml"));
            Parent root=loader.load();
            ProfileUser pf=loader.getController();
            pf.getPe(ep);
            reclamation1.getScene().setRoot(root);


    }

    public void toacceuiel(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/DasboardUser.fxml"));
        Parent root=loader.load();
        DashboardUser ash=loader.getController();
        ash.getPe(ep);
        reclamation1.getScene().setRoot(root);
    }
 @FXML
    public void toMatiere(ActionEvent actionEvent) throws IOException {
     FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/acceuilMatiere.fxml"));
     Parent root=loader.load();
     AcceuilMatiereC acm=loader.getController();
     acm.getPe(ep);
     reclamation1.getScene().setRoot(root);
    }
    @FXML
    public void ListEvents(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Liste_Evenement.fxml"));
        Parent root = loader.load();
        AfficherEvents controller = loader.getController();

        controller.initialize();
        reclamation1.getScene().setRoot(root);
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    @FXML
    void AfficherReclamation(ActionEvent event) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AjouterReclamation.fxml"));
        Parent root = loader.load();
        AjouterReclamation controller = loader.getController();

        controller.setProftoGet(ep);
        reclamation1.getScene().setRoot(root);

    }

}
