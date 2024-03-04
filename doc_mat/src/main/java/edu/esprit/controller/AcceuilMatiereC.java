package edu.esprit.controller;

import edu.esprit.entities.ParentE;
import edu.esprit.services.CredentialsManager;
import edu.esprit.services.DashboardUser;
import edu.esprit.services.ProfileUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.awt.*;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

public class AcceuilMatiereC {



    @FXML
    private Button id1;

    @FXML
    private Button id2;

    @FXML
    private Button id3;

    @FXML
    private Button id4;

    @FXML
    private Button id5;

    @FXML
    private Button id6;

    @FXML
    private Button ide;
    private ParentE ep;
    public void getPe(ParentE ep)
    {
        this.ep=ep;
    }
    @FXML

    public void affiche1(ActionEvent actionEvent) {
        try {
            // Get the text of the button
            String btnText = id1.getText();
            System.out.println("*******");
            System.out.println(btnText);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AfficherMatiers.fxml"));
            Parent root =  (Parent) loader.load();

            // Accéder au contrôleur du formulaire de matiers affichage
            MatiersAffichage affichematiere= loader.getController();
           affichematiere.getPe(ep);
            affichematiere.afficherSeulentAnnee(btnText);
            affichematiere.initialize(); // New method to initialize data

            // Changer la scène pour afficher le formulaire de modification
            id1.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
@FXML
    public void afficher2(ActionEvent actionEvent) {
        try {
            // Get the text of the button
            String btnText = id2.getText();
            System.out.println("*******");
            System.out.println(btnText);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AfficherMatiers.fxml"));
            Parent root =  (Parent) loader.load();

            // Accéder au contrôleur du formulaire de matiers affichage
            MatiersAffichage affichematiere= loader.getController();
            affichematiere.getPe(ep);
            affichematiere.afficherSeulentAnnee(btnText);
            affichematiere.initialize(); // New method to initialize data

            // Changer la scène pour afficher le formulaire de modification
            id2.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  @FXML
    public void afficher3(ActionEvent actionEvent) {
        try {
            // Get the text of the button
            String btnText = id3.getText();
            System.out.println("*******");
            System.out.println(btnText);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AfficherMatiers.fxml"));
            Parent root =  (Parent) loader.load();

            // Accéder au contrôleur du formulaire de matiers affichage
            MatiersAffichage affichematiere= loader.getController();
            affichematiere.getPe(ep);
            affichematiere.afficherSeulentAnnee(btnText);
            affichematiere.initialize(); // New method to initialize data

            // Changer la scène pour afficher le formulaire de modification
            id3.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  @FXML
    public void afficher4(ActionEvent actionEvent) {
        try {
            // Get the text of the button
            String btnText = id4.getText();
            System.out.println("*******");
            System.out.println(btnText);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AfficherMatiers.fxml"));
            Parent root =  (Parent) loader.load();

            // Accéder au contrôleur du formulaire de matiers affichage
            MatiersAffichage affichematiere= loader.getController();
            affichematiere.getPe(ep);
            affichematiere.afficherSeulentAnnee(btnText);
            affichematiere.initialize(); // New method to initialize data

            // Changer la scène pour afficher le formulaire de modification
            id4.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
@FXML
    public void afficher5(ActionEvent actionEvent) {
        try {
            // Get the text of the button
            String btnText = id5.getText();
            System.out.println("*******");
            System.out.println(btnText);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AfficherMatiers.fxml"));
            Parent root =  (Parent) loader.load();

            // Accéder au contrôleur du formulaire de matiers affichage
            MatiersAffichage affichematiere= loader.getController();
            affichematiere.getPe(ep);
            affichematiere.afficherSeulentAnnee(btnText);
            affichematiere.initialize(); // New method to initialize data

            // Changer la scène pour afficher le formulaire de modification
            id5.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 @FXML
    public void afficher6(ActionEvent actionEvent) {
        try {
            // Get the text of the button
            String btnText = id6.getText();
            System.out.println("*******");
            System.out.println(btnText);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AfficherMatiers.fxml"));
            Parent root =  (Parent) loader.load();

            // Accéder au contrôleur du formulaire de matiers affichage
            MatiersAffichage affichematiere= loader.getController();
            affichematiere.getPe(ep);
            affichematiere.afficherSeulentAnnee(btnText);
            affichematiere.initialize(); // New method to initialize data

            // Changer la scène pour afficher le formulaire de modification
            id6.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
@FXML
    public void toDashBorad(ActionEvent actionEvent) throws IOException {
    FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/DasboardUser.fxml"));
    Parent root=loader.load();
    id1.getScene().setRoot(root);

    }
@FXML
    public void toacceuiel(ActionEvent actionEvent) throws IOException {
    FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/DasboardUser.fxml"));
    Parent root=loader.load();
    DashboardUser ash=loader.getController();
    ash.getPe(ep);
    id1.getScene().setRoot(root);
    }
@FXML
    public void tohisprofile(ActionEvent actionEvent) throws IOException {
    FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/ProfileUser.fxml"));
    Parent root=loader.load();
    ProfileUser pf=loader.getController();
    pf.getPe(ep);
    id1.getScene().setRoot(root);
    }
@FXML
    public void toMatiere(ActionEvent actionEvent) throws IOException {
    FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/acceuilMatiere.fxml"));
    Parent root=loader.load();
    AcceuilMatiereC acm=loader.getController();
    acm.getPe(ep);
    id1.getScene().setRoot(root);
    }
@FXML
    public void deconnexion(ActionEvent actionEvent) throws IOException {
    CredentialsManager.clearCredentials();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
    Parent root = loader.load();
    id1.getScene().setRoot(root);
    }
}
