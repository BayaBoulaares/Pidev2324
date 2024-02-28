package edu.esprit.crudoff.services;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import java.io.IOException;

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
    public void deconnexion(ActionEvent actionEvent) throws IOException  {
        CredentialsManager.clearCredentials();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Parent root = loader.load();
        reclamation1.getScene().setRoot(root);

    }

    public void tohisprofile(ActionEvent actionEvent) throws IOException {

            FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/ProfileUser.fxml"));
            Parent root=loader.load();
            reclamation1.getScene().setRoot(root);


    }
}
