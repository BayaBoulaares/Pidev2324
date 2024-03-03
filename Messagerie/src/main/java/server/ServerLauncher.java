package server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ServerLauncher extends Application {

    private static boolean isLaunched = false;

    public static void main(String[] args) {
        // Check if the application is not already launched
        if (!isLaunched) {
            isLaunched = true;
            launch(args);
        } else {
            // Handle the case where the application is already launched
            System.out.println("Application is already launched.");
            // You might want to bring the existing instance to the front or handle it as needed
        }
    }

    public void initializeJavaFX() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Your JavaFX application initialization code here
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ServerForm.fxml"))));
        primaryStage.setTitle("Server");
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        primaryStage.show();

        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage.getScene().getWindow()); // Fixed: Declare primaryStage
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/LoginForm.fxml"))));
        stage.setTitle("EChat");
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }
}
