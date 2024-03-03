package edu.esprit.tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFx extends Application {

    public MainFx() {
    }

    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/LetsGetStarted.fxml"));
        Parent root = (Parent)loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMinWidth(1200);
        stage.setMinHeight(800);
        stage.setTitle("ajout");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
