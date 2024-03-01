package edu.esprit.tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import javax.imageio.IIOException;
import java.io.IOException;

public class MainFx extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/AfficherReclamation.fxml"));
        Parent root=loader.load();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        //scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());

        stage.setTitle("AUTO_LEARN");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
