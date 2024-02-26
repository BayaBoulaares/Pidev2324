package edu.esprit.controller;

import edu.esprit.entities.Matiere;
import edu.esprit.services.SeviceMatiere;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MatiersAffichage {

    @FXML
    private Button idaj;

    @FXML
    private Button idret;
    @FXML
    private FlowPane matiereFlowPane;
    @FXML
    private VBox alphabetButtons;

private String annee;
    private SeviceMatiere serviceMatiere = new SeviceMatiere();

    @FXML
    public void initialize() {

        // Load matières at startup
        loadMatieres();
    }
    public void  afficherSeulentAnnee(String annee)
    {
        this.annee=annee;
        System.out.println(this.annee);
        loadMatieres();
    }

    public void loadMatieres() {
      //  if(annee!=null) {
        try {
            // Fetch matières from the database or any other source
            List<Matiere> matieres = serviceMatiere.getOneByAnnee(annee);
            // Clear existing content in matiereFlowPane
            matiereFlowPane.getChildren().clear();

            matiereFlowPane.setHgap(10); // Ajout de l'espace horizontal entre les VBox
            matiereFlowPane.setVgap(10); // Ajout de l'espace vertical entre les VBox

            // Dynamically create labels, delete buttons, and edit buttons for each matière's name
            int itemsPerRow = 3;
            for (Matiere matiere : matieres) {
                VBox matiereHBox = createMatiereVBox(matiere);

                matiereFlowPane.getChildren().add(matiereHBox);
                VBox.setMargin(matiereHBox, new Insets(20, 20, 70, 20));
                // Add a new row after every 'itemsPerRow' items
                /*if (matiereFlowPane.getChildren().size() % itemsPerRow == 0) {
                    matiereFlowPane.getChildren().add(new HBox()); // New row
                }*/
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Exception");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } //} else {  throw new IllegalArgumentException("annee cannot be null");}
    }
    public VBox createMatiereVBox(Matiere matiere) {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER); // Align VBox content to the center
        vBox.setSpacing(8); // Set spacing between label and buttons

        vBox.setStyle("-fx-background-color: #FAFEFC; " +
                "-fx-border-width: 2px;" +
                "-fx-border-color: #2b3674;" +
                "-fx-background-radius: 5px;" +
                "-fx-border-radius: 5px; ");
        // Ajoutez ce pane pour pousser le contenu vers le centre
        Pane topPane = new Pane();
        topPane.setMinHeight(20); // Ajustez la hauteur comme nécessaire

        Label label = new Label(matiere.getAnnee().substring(0, 1) + matiere.getNommatiere().substring(0, 1).toUpperCase());
        label.setAlignment(Pos.CENTER); // Centrer le texte
        label.setStyle("-fx-background-color:   #27DEC1;" +
                "-fx-border-width: 2px; " +
                "-fx-padding: 3px; " +
                "-fx-border-radius: 5px; " +
                "-fx-text-fill: #2b3674;" +
                "-fx-border-color: #2b3674;"+
                "-fx-background-radius: 5px;");
        label.setFont(new Font("System Bold", 30));
        label.setMinHeight(80);
        label.setMinWidth(100);

        // Bouton Supprimer
        Button docButton = new Button(matiere.getNommatiere());
        docButton.setStyle("-fx-background-color: transparent; -fx-text-fill:#2b3674;");
        docButton.setFont(new Font("System Bold", 15));

        // Set the same minHeight and prefHeight for both label and button
        label.setMinHeight(50); // Set your desired height
        label.setPrefHeight(50);
        docButton.setMinHeight(50); // Set your desired height
        docButton.setPrefHeight(50);
        vBox.setMinWidth(150);//Définissez votre largeur souhaitée

        docButton.setOnAction(event -> handleAfficheButtonClick(matiere));
        // Ajoutez un autre pane en bas si nécessaire
        Pane bottomPane = new Pane();
        bottomPane.setMinHeight(20); // Ajustez la hauteur comme nécessaire

        vBox.getChildren().addAll(topPane,label, docButton,bottomPane);

        return vBox;
    }



    public void handleAfficheButtonClick(Matiere matiere) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherDocumentM.fxml"));
            Parent root = loader.load();

            // Accéder au contrôleur du formulaire de modification
          AfficherDocumentM ajouterdoc= loader.getController();
            Matiere me=serviceMatiere.test(matiere);
            if( me!=null)
            // Appeler la méthode pour passer la matière à modifier
            { ajouterdoc.setMatToCh(me);}

            // Changer la scène pour afficher le formulaire de modification
            idret.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleAlphabetButtonClick(ActionEvent actionEvent) {
        Button clickedButton = (Button) actionEvent.getSource();
        String selectedAlphabet = clickedButton.getText();
        filterMatieresByAlphabet(selectedAlphabet);
    }
    public void filterMatieresByAlphabet(String selectedAlphabet) {
        try {
            // Fetch matières from the database or any other source
            List<Matiere> matieres = serviceMatiere.getMatiereByAlphabet(selectedAlphabet);

            // Afficher les matières filtrées
            displayFilteredMatieres(matieres);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void displayFilteredMatieres(List<Matiere> matieres) {
        // Clear existing content in matiereFlowPane
        matiereFlowPane.getChildren().clear();

        // Dynamically create labels, delete buttons, and edit buttons for each matière's name
        int itemsPerRow = 3;
        for (Matiere matiere : matieres) {
            VBox  matiereHBox = createMatiereVBox(matiere);
            matiereFlowPane.getChildren().add(matiereHBox);

            // Add a new row after every 'itemsPerRow' items
            if (matiereFlowPane.getChildren().size() % itemsPerRow == 0) {
                matiereFlowPane.getChildren().add(new HBox()); // New row
            }
        }

    }

    public void affichertout(ActionEvent actionEvent) {
        loadMatieres();
    }
 @FXML
    public void retourMAtiere(ActionEvent actionEvent) {
     try {
         FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/acceuilMatiere.fxml"));
         Parent root = (Parent) loader.load();
         this.idret.getScene().setRoot(root);
     } catch (IOException e) {
         e.printStackTrace();
         showAlert(Alert.AlertType.ERROR, "Erreur de chargement", "Impossible de charger la page d'affichage");
     }
 }
    @FXML
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
