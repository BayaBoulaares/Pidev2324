package edu.esprit.crudoff.services;

import edu.esprit.crudoff.entities.Professeur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class AddProfs {
    private final ServiceProfesseur SP = new ServiceProfesseur();
    @FXML
    private Label nameerror;
    @FXML
    private DatePicker adddobfield;
    @FXML
    private TextField addpadressefield;

    @FXML
    private TextField addpdiscfield;

    @FXML
    private TextField addpemailfield;

    @FXML
    private TextField addpnomfield;

    @FXML
    private TextField addpprenomfield;

    @FXML
    private TextField addptelfield;
    @FXML
    private PasswordField pfmdp;

    @FXML
    private Button bconfirm;

    @FXML
    private Button breturn;

    @FXML
    private Button dcxn;

    @FXML
    private Button evaluation1;

    @FXML
    private Button evenements1;

    @FXML
    private Button profile2;

    @FXML
    private Button proform1;

    @FXML
    private Button reclamation1;
    @FXML
    private Button totableprof;

    @FXML
    private Button tousers;



    @FXML
    void confirmer(ActionEvent event)  {
        try {
            LocalDate selectedDate = adddobfield.getValue();
            java.sql.Date sqlDate = java.sql.Date.valueOf(selectedDate);
            System.out.println(sqlDate);
            String telephoneText = addptelfield.getText();
            int telephone = Integer.parseInt(telephoneText);
            String nom = addpnomfield.getText();
            String prenom = addpprenomfield.getText();
            String adresse = addpadressefield.getText();
            String email =addpemailfield.getText();
            String mdp = pfmdp.getText();
            String disc = addpdiscfield.getText();




            Professeur pp = new Professeur(nom, prenom,adresse,sqlDate,telephone,email,mdp,disc);
            SP.ajouter(pp);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Validation");
            alert.setContentText("Person added succesfully");
            alert.showAndWait();
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/CrudAdmins.fxml"));
            Parent root=loader.load();
            addpadressefield.getScene().setRoot(root);

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Exeption");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void deconnexion(ActionEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Parent root=loader.load();
        addpdiscfield.getScene().setRoot(root);

    }
    @FXML
    void toUsers(ActionEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Parent root=loader.load();
        addpdiscfield.getScene().setRoot(root);

    }

    @FXML
    void totableprof(ActionEvent event) throws IOException{
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/CrudAdmins.fxml"));
        Parent root=loader.load();
        addpdiscfield.getScene().setRoot(root);

    }
}
