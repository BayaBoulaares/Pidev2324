package edu.esprit.crudoff.tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class  MainFx extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LetsGetStarted.fxml"));
        Parent root = loader.load();
        // Créer une nouvelle scène
        Scene scene = new Scene(root);
        // Obtenir les dimensions de l'écran
        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getBounds().getWidth();
        double screenHeight = screen.getBounds().getHeight();
        primaryStage.setScene(scene);
        primaryStage.setWidth(screenWidth * 0.9);
        primaryStage.setHeight(screenHeight * 0.9);
        primaryStage.setTitle("Gestion utilisateurs");
        primaryStage.show();

        }

        public static void main(String[] args) {
            launch(args);
    }

}
