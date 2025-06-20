package edu.esprit.services;


import edu.esprit.controller.AffichageMatiereController;
import edu.esprit.entities.Administrateur;
import edu.esprit.entities.ParentE;
import edu.esprit.entities.Professeur;
import edu.esprit.entities.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class LetsGetStarted {

    @FXML
    private Button rejoindre;
    private final ServiceUtilisateur PS = new ServiceUtilisateur();
    private Utilisateur utilisateur ;

    /*@FXML
    void tologin(ActionEvent event) throws IOException {
        CredentialsManager crd = new CredentialsManager();
        String[] mm = crd.loadCredentials();
        if(mm == null && !parseBoolean(mm[3])    ){
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
            Parent root=loader.load();
            rejoindre.getScene().setRoot(root);
            return;
        }
        Utilisateur utilisateur = PS.getByLogin(mm[0]);

        System.out.println(utilisateur);
        if (utilisateur != null) {
                // Vérification du rôle de l'utilisateur
                if (utilisateur instanceof Administrateur) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DashobardAdmin.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } else if (utilisateur instanceof ParentE) {
                    // Redirection vers l'interface utilisateur normale


                    FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/fxml/DasboardUser.fxml"));
                    Parent root2 = loader2.load();
                    // Récupérer les données de l'utilisateur et les afficher dans l'interface Profile
                    //profileController.setUserData((ParentE) utilisateur);
                    rejoindre.getScene().setRoot(root2);





                }

        } else {
            // Utilisateur non reconnu
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
            Parent root=loader.load();
            rejoindre.getScene().setRoot(root);
        }





    }*/
    @FXML
    void tologin(ActionEvent event) throws IOException {
        CredentialsManager crd = new CredentialsManager();
        String[] mm = crd.loadCredentials();

        if (mm == null || !Boolean.parseBoolean(mm[3])) {
            loadLoginScene();
            return;
        }

         utilisateur = PS.getByLogin(mm[0]);
        if (utilisateur != null) {
            redirectToDashboard(utilisateur, event);
        } else {
            loadLoginScene();
        }
    }

    private void loadLoginScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Parent root = loader.load();
        rejoindre.getScene().setRoot(root);
    }

    private void redirectToDashboard(Utilisateur utilisateur, ActionEvent event) throws IOException {
        String resource;
        int a = 0;
        if (utilisateur instanceof Administrateur) {
            resource = "/fxml/DashobardAdmin.fxml";
        } else if (utilisateur instanceof ParentE) {
            resource = "/fxml/DasboardUser.fxml";
            a=2;
        } else {
            resource = "/fxml/AfficherMatiere.fxml";
            a=3;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
        Parent root = loader.load();
        if(a==2) {
            DashboardUser acm=loader.getController();
            ParentE pt= (ParentE) utilisateur;
            acm.getPe(pt);
            acm.setUserId(pt.getId());
        } else if (a==3) {
            AffichageMatiereController acm=loader.getController();
            Professeur pt= (Professeur) utilisateur;
            acm.setProftoGet(pt);
        }
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}