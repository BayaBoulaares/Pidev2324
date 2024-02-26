package edu.esprit.controller;

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
    @FXML

    public void affiche1(ActionEvent actionEvent) {
        try {
            // Get the text of the button
            String btnText = id1.getText();
            System.out.println("*******");
            System.out.println(btnText);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherMatiers.fxml"));
            Parent root =  (Parent) loader.load();

            // Accéder au contrôleur du formulaire de matiers affichage
            MatiersAffichage affichematiere= loader.getController();

            affichematiere.afficherSeulentAnnee(btnText);
            affichematiere.initialize(); // New method to initialize data

            // Changer la scène pour afficher le formulaire de modification
            id1.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void afficher2(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherMatiers.fxml"));
            Parent root = loader.load();

            // Accéder au contrôleur du formulaire de matiers affichage
            MatiersAffichage affichematiere= loader.getController();
            affichematiere.afficherSeulentAnnee(id2.getText());
            // Changer la scène pour afficher le formulaire de modification
            id2.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void afficher3(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherMatiers.fxml"));
            Parent root = loader.load();

            // Accéder au contrôleur du formulaire de matiers affichage
            MatiersAffichage affichematiere= loader.getController();
            affichematiere.afficherSeulentAnnee(id3.getText());
            // Changer la scène pour afficher le formulaire de modification
            id3.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void afficher4(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherMatiers.fxml"));
            Parent root = loader.load();

            // Accéder au contrôleur du formulaire de matiers affichage
            MatiersAffichage affichematiere= loader.getController();
            affichematiere.afficherSeulentAnnee(id4.getText());
            // Changer la scène pour afficher le formulaire de modification
            id4.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void afficher5(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherMatiers.fxml"));
            Parent root = loader.load();

            // Accéder au contrôleur du formulaire de matiers affichage
            MatiersAffichage affichematiere= loader.getController();
            affichematiere.afficherSeulentAnnee(id5.getText());
            // Changer la scène pour afficher le formulaire de modification
            id5.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void afficher6(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherMatiers.fxml"));
            Parent root = loader.load();

            // Accéder au contrôleur du formulaire de matiers affichage
            MatiersAffichage affichematiere= loader.getController();
            affichematiere.afficherSeulentAnnee(id6.getText());
            // Changer la scène pour afficher le formulaire de modification
            id6.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
