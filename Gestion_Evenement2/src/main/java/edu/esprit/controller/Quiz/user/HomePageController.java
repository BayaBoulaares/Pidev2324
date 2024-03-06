package edu.esprit.controller.Quiz.user;

import edu.esprit.controller.AjouterReclamation;
import edu.esprit.services.CredentialsManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {

    @FXML
    private AnchorPane mainContentContainer;

    @FXML
    public void quizTestBtnClicked(MouseEvent event){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/gui/Quiz/user/quizTestPage.fxml"));
            Parent root = fxmlLoader.load();
            mainContentContainer.getChildren().clear();
            mainContentContainer.getChildren().add(root);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void deconnexion(ActionEvent actionEvent) throws IOException {
        CredentialsManager.clearCredentials();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Parent root = loader.load();
        // Utilisez getRoot() au lieu de getScene()
        ((Node) actionEvent.getSource()).getScene().setRoot(root);
    }

    @FXML
    public void toMatiere(ActionEvent actionEvent) throws IOException {
        CredentialsManager.clearCredentials();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/acceuilMatiere.fxml"));
        Parent root = loader.load();
        // Utilisez getRoot() au lieu de getScene()
        ((Node) actionEvent.getSource()).getScene().setRoot(root);
    }
    @FXML
    public void tohisprofile(ActionEvent actionEvent) throws IOException {
        CredentialsManager.clearCredentials();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ProfileUser.fxml"));
        Parent root = loader.load();
        // Utilisez getRoot() au lieu de getScene()
        ((Node) actionEvent.getSource()).getScene().setRoot(root);
    }
    @FXML
    public void toacceuiel(ActionEvent actionEvent) throws IOException {
        CredentialsManager.clearCredentials();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DasboardUser.fxml"));
        Parent root = loader.load();
        // Utilisez getRoot() au lieu de getScene()
        ((Node) actionEvent.getSource()).getScene().setRoot(root);
    }
    @FXML
    public void toReclamation(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AjouterReclamation.fxml"));
        Parent root = loader.load();
   /* AjouterReclamation controller = loader.getController();

    controller.setProftoGet(ep);*/
        ((Node) actionEvent.getSource()).getScene().setRoot(root);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/gui/Quiz/user/quizTestPage.fxml"));
            Parent root = fxmlLoader.load();
            mainContentContainer.getChildren().clear();
            mainContentContainer.getChildren().add(root);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    @FXML
    public void toEvent(ActionEvent actionEvent) throws IOException {
        CredentialsManager.clearCredentials();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Liste_Evenement.fxml"));
        Parent root = loader.load();
        mainContentContainer.getScene().setRoot(root);
    }
    @FXML
    public void toReclametion(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AjouterReclamation.fxml"));
        Parent root = loader.load();
        AjouterReclamation controller = loader.getController();
        // controller.setProftoGet(ep);
        mainContentContainer.getScene().setRoot(root);
    }
    @FXML
    public void toEvaluation(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/gui/Quiz/user/homePage.fxml"));
        Parent root=loader.load();
        mainContentContainer.getScene().setRoot(root);
    }


}
