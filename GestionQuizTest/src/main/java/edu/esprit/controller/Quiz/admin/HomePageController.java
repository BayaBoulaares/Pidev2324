package edu.esprit.controller.Quiz.admin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class HomePageController {
    @FXML
    private AnchorPane mainContentContainer;
    @FXML
    public void quizTestBtnClicked(MouseEvent event){
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
