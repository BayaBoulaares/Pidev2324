package edu.esprit.controller;

import edu.esprit.entities.Document;
import edu.esprit.entities.Matiere;
import edu.esprit.services.ServiceDocument;
import javafx.fxml.FXML;
import javafx.scene.control.Button; // Utilisez javafx.scene.control.Button
import javafx.scene.control.Label; // Utilisez javafx.scene.control.Label
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image; // Utilisez javafx.scene.image.Image
import javafx.scene.image.ImageView; // Utilisez javafx.scene.image.ImageView

import java.sql.SQLException;
import java.util.ArrayList;

public class AfficherDocumentM {

    @FXML
    private VBox documentListVBox; // Assurez-vous que c'est le VBox où vous voulez ajouter les documents
    private final ServiceDocument DS=new ServiceDocument();

    private  Matiere matiere = new Matiere();
    public void initialize() {

            loadDocumentData();


    }
    public void setMatToCh(Matiere me) {
        System.out.println(me);
        matiere = me;
        System.out.println("********************");
        System.out.println(matiere);
        loadDocumentData();

    }
    private void loadDocumentData()  {
        try {
        ArrayList<Document> documents = DS.getAllByMatiere(matiere); // Utilisez votre méthode pour obtenir tous les documents
        for (Document doc : documents) {
            System.out.println("/////////////////////");
            System.out.println(doc);
            VBox docVBox = createDocumentVBox(doc);
            documentListVBox.getChildren().add(docVBox);
        }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private VBox createDocumentVBox(Document doc) {
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 10; -fx-border-radius: 5; -fx-background-radius: 5;"); // Ajout d'une couleur de fond et d'un padding

        Label titleLabel = new Label(doc.getTitre());
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;"); // Ajout de la taille de la police et du poids

        Label dateLabel = new Label(doc.getDate().toString());
        dateLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: grey;"); // Ajout de la taille de la police et de la couleur du texte

        Button openButton = new Button("Ouvrir");
        openButton.setOnAction(event -> openDocument(doc));
        openButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 10px 20px;"); // Ajout d'une couleur de fond, d'une couleur de texte et d'un padding


        HBox hBox = new HBox(titleLabel); // Combinaison de l'icône avec le titre
        hBox.setSpacing(10);

        vBox.getChildren().addAll( hBox,dateLabel, openButton);

        return vBox;
    }



    private void openDocument(Document doc) {
        // Implémentez cette méthode pour ouvrir le document
    }
}

