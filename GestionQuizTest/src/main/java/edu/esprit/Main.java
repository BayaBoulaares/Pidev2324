package edu.esprit;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/gui/Quiz/user/homePage.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root,1280,720);
            stage.setScene(scene);
            stage.setTitle("E-Learn");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo.png")));
            stage.setResizable(false);
            stage.show();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    public static void main(String[] args) {
        launch();
    }
}