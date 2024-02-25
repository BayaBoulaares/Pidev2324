package edu.esprit.crudoff.services;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import java.io.IOException;

public class DashboardAdmin {



    @FXML
    private Button toprofesseur;


    @FXML
    void toProfesseurs(ActionEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/CrudAdmins.fxml"));
        Parent root=loader.load();
        toprofesseur.getScene().setRoot(root);

    }

    public void deconnexion(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Parent root=loader.load();
        toprofesseur.getScene().setRoot(root);
    }
}
