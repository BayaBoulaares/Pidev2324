package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.esprit.entities.ParentE;
import edu.esprit.entities.User;
import edu.esprit.services.CredentialsManager;
import edu.esprit.services.ServiceParent;
import edu.esprit.services.ServiceUser;
import edu.esprit.utils.DataSource;
import emoji.EmojiPicker;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.sql.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static controller.ServerFormController.receiveMessage;

public class ClientFormController {
    public AnchorPane pane;
    public ScrollPane scrollPain;
    public VBox vBox;
    public JFXTextField txtMsg;
    public Text txtLabel;
    public JFXButton emojiButton;

    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private String clientName = "Client";
    private boolean isAdminClient = false;
    private static boolean adminConnected = false;
    private DataSource dataSource;

    String idP= CredentialsManager.loadCredentials()[0];
    ServiceParent serviceProfesseur = new ServiceParent();


    private ParentE prod= serviceProfesseur.getByLogin(idP);



    public void initialize() {
        // Initialize your data source (database connection)
        dataSource = DataSource.getInstance();

        new Thread(() -> {
            try {
                socket = new Socket("localhost", 3001);
                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                System.out.println("Client connected");

                // Fetch user data from the database and set clientName
                int userId = prod.getId(); // You may need to adjust this based on your user identification mechanism
                ServiceUser serviceUser = new ServiceUser();
                User user = serviceUser.getUserById(userId);
                setClientName(user.getNom());

                // Determine if this client is the admin or user
                isAdminClient = !adminConnected; // If admin is not connected, this client becomes admin
                adminConnected = true;

                ServerFormController.receiveMessage(isAdminClient ? "professeur joined." : clientName + " joined.");

                while (socket.isConnected()) {
                    String receivingMsg = dataInputStream.readUTF();
                    receiveMessage(receivingMsg, ClientFormController.this.vBox);
                }
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }).start();

        this.vBox.heightProperty().addListener((observableValue, oldValue, newValue) -> scrollPain.setVvalue((Double) newValue));

        emoji();
        // Load conversation history when initializing the chat window
        loadConversationHistory();
    }

    private void setAdminClient(boolean isAdmin) {
        isAdminClient = isAdmin;
    }

    private boolean isAdminClient() {
        return isAdminClient;
    }

    public void shutdown() {
        ServerFormController.receiveMessage(clientName + (isAdminClient ? " (User)" : "") + " left.");
    }

    private void emoji() {
        EmojiPicker emojiPicker = new EmojiPicker();

        VBox vBox = new VBox(emojiPicker);
        vBox.setPrefSize(150, 300);
        vBox.setLayoutX(400);
        vBox.setLayoutY(175);
        vBox.setStyle("-fx-font-size: 30");

        pane.getChildren().add(vBox);

        emojiPicker.setVisible(false);

        emojiButton.setOnAction(event -> {
            if (emojiPicker.isVisible()) {
                emojiPicker.setVisible(false);
            } else {
                emojiPicker.setVisible(true);
            }
        });

        emojiPicker.getEmojiListView().setOnMouseClicked(event -> {
            String selectedEmoji = emojiPicker.getEmojiListView().getSelectionModel().getSelectedItem();
            if (selectedEmoji != null) {
                txtMsg.setText(txtMsg.getText() + selectedEmoji);
            }
            emojiPicker.setVisible(false);
        });
    }

    public void txtMsgOnAction(ActionEvent actionEvent) {
        sendButtonOnAction(actionEvent);
    }

    public void sendButtonOnAction(ActionEvent actionEvent) {
        sendMsg(txtMsg.getText());
    }

    private void sendMsg(String msgToSend) {
        if (!msgToSend.isEmpty()) {
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.setPadding(new Insets(5, 5, 0, 10));

            Text text = new Text(msgToSend);
            text.setStyle("-fx-font-size: 14");
            TextFlow textFlow = new TextFlow(text);

            textFlow.setStyle("-fx-background-color: " +
                    (isAdminClient() ? "#FFD700" : "#0693e3") +
                    "; -fx-font-weight: bold; -fx-color: white; -fx-background-radius: 20px");
            textFlow.setPadding(new Insets(5, 10, 5, 10));
            text.setFill(Color.color(1, 1, 1));

            hBox.getChildren().add(textFlow);

            HBox hBoxTime = new HBox();
            hBoxTime.setAlignment(Pos.CENTER_RIGHT);
            hBoxTime.setPadding(new Insets(0, 5, 5, 10));
            String stringTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
            Text time = new Text(stringTime);
            time.setStyle("-fx-font-size: 8");

            hBoxTime.getChildren().add(time);

            vBox.getChildren().add(hBox);
            vBox.getChildren().add(hBoxTime);

            try {
                // Insert the message into the database
                try (Statement statement = dataSource.getCnx().createStatement()) {
                    String insertQuery = "INSERT INTO messagerie (nom, date, message, idu) VALUES (?, ?, ?, ?)";

                    try (PreparedStatement preparedStatement = dataSource.getCnx().prepareStatement(insertQuery)) {
                        preparedStatement.setString(1, isAdminClient() ? "User" : clientName);
                        preparedStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                        preparedStatement.setString(3, msgToSend);
                        preparedStatement.setInt(4,prod.getId() );  // Replace with the actual value for 'idu'
                        preparedStatement.executeUpdate();
                    }



                }


                dataOutputStream.writeUTF((isAdminClient() ? "User" : clientName) + "-" + msgToSend);
                dataOutputStream.flush();
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }

            txtMsg.clear();
        }
    }


    private void loadConversationHistory() {
        try {
            ServiceUser serviceUser = new ServiceUser();
            int userId = prod.getId(); // Adjust this based on your user identification mechanism
            User user = serviceUser.getUserById(userId);
            System.out.println(user);


            String loadHistoryQuery = "SELECT * FROM messagerie WHERE idu = ?";
            try (PreparedStatement preparedStatement = dataSource.getCnx().prepareStatement(loadHistoryQuery)) {
                preparedStatement.setInt(1, userId);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    String senderName = resultSet.getString("nom");
                    String message = resultSet.getString("message");
                    String fullMessage = senderName + "-" + message;

                    // Display the historical message in the chat window
                    receiveMessage(fullMessage, vBox);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void sendImage(String imagePath) {
        try {
            // Load image from file path
            Image image = new Image(new File(imagePath).toURI().toString());
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(200);
            imageView.setFitWidth(200);
            HBox hBox = new HBox();
            hBox.setPadding(new Insets(5, 5, 5, 10));
            hBox.getChildren().add(imageView);
            hBox.setAlignment(Pos.CENTER_RIGHT);

            vBox.getChildren().add(hBox);

            // Insert the image path into the database
            try (Statement statement = dataSource.getCnx().createStatement()) {
                String insertQuery = "INSERT INTO messagerie (nom, message) VALUES ('" +
                        (isAdminClient() ? "User" : clientName) +
                        "', '" + imagePath + "')";
                statement.executeUpdate(insertQuery);
            }

            dataOutputStream.writeUTF(clientName + "-" + imagePath);
            dataOutputStream.flush();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }


    public static void receiveMessage(String msg, VBox vBox) {
        if (msg.matches(".*\\.(png|jpe?g|gif)$")) {
            String[] parts = msg.split("-");
            if (parts.length == 2) {
                String senderName = parts[0].trim();
                String imageUrl = parts[1].trim();
                Platform.runLater(() -> {
                    try {
                        displayImage(senderName, imageUrl, vBox);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        } else {
            Platform.runLater(() -> displayTextMessage(msg, vBox));
        }
    }

    private static void displayImage(String senderName, String imageUrl, VBox vBox) throws IOException {
        Image image = new Image(new File(imageUrl).toURI().toString());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(200);
        imageView.setFitWidth(200);

        HBox hBoxName = new HBox();
        hBoxName.setAlignment(Pos.CENTER_LEFT);
        Text textName = new Text(senderName);
        TextFlow textFlowName = new TextFlow(textName);
        hBoxName.getChildren().add(textFlowName);

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5, 5, 5, 10));
        hBox.getChildren().add(imageView);

        vBox.getChildren().addAll(hBoxName, hBox);
    }

    private static void displayTextMessage(String msg, VBox vBox) {
        String[] parts = msg.split("-");
        if (parts.length == 2) {
            String senderName = parts[0].trim();
            String messageContent = parts[1].trim();

            HBox hBoxName = new HBox();
            hBoxName.setAlignment(Pos.CENTER_LEFT);
            Text textName = new Text(senderName);
            TextFlow textFlowName = new TextFlow(textName);
            hBoxName.getChildren().add(textFlowName);

            Text text = new Text(messageContent);
            TextFlow textFlow = new TextFlow(text);
            textFlow.setStyle("-fx-background-color: #abb8c3; -fx-font-weight: bold; -fx-background-radius: 20px");
            textFlow.setPadding(new Insets(5, 10, 5, 10));

            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.setPadding(new Insets(5, 5, 5, 10));
            hBox.getChildren().add(textFlow);

            vBox.getChildren().addAll(hBoxName, hBox);
        }
    }

    public void attachedButtonOnAction(ActionEvent actionEvent) {
        FileDialog dialog = new FileDialog((Frame) null, "Select File to Open");
        dialog.setMode(FileDialog.LOAD);
        dialog.setVisible(true);
        String file = dialog.getDirectory() + dialog.getFile();
        dialog.dispose();
        sendImage(file);
        System.out.println(file + " chosen.");
    }

    public void setClientName(String name) {
        clientName = name;
    }
}