package edu.esprit.controller.Quiz.admin;

import edu.esprit.controller.AffichageMatiereController;
import edu.esprit.entities.Professeur;
import edu.esprit.services.CredentialsManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {

    public static Professeur prod;
    public void setProftoGet(Professeur prof)
    {
        System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbb");
        prod=prof;
        System.out.println(prod);

    }
    @FXML
    private Button dcxn;
    @FXML
    private AnchorPane mainContentContainer;
    @FXML
    public void toMatiere(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/AfficherMatiere.fxml"));
        Parent root=loader.load();
        AffichageMatiereController acm=loader.getController();
        acm.setProftoGet(prod);
        dcxn.getScene().setRoot(root);
    }
    @FXML
    public void deconnexion(ActionEvent actionEvent) throws IOException {
        CredentialsManager.clearCredentials();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Parent root = loader.load();
        dcxn.getScene().setRoot(root);
    }
    @FXML
    public void tomessage(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/AfficherMessage.fxml"));
        Parent root=loader.load();
        dcxn.getScene().setRoot(root);
    }
    @FXML
    public void toEvaluation(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/gui/Quiz/admin/homePage.fxml"));
        Parent root=loader.load();
        dcxn.getScene().setRoot(root);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/gui/Quiz/admin/quizTestPage.fxml"));
            Parent root = fxmlLoader.load();
            mainContentContainer.getChildren().clear();
            mainContentContainer.getChildren().add(root);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
