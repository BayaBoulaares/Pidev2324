package edu.esprit.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import com.google.api.services.drive.Drive;
import edu.esprit.APIapploadfichier.PDFViewer;
import edu.esprit.APIapploadfichier.UploadBasic;
import edu.esprit.APIapploadfichier.VideoPlayer;
import edu.esprit.entities.Document;
import edu.esprit.entities.Matiere;
import edu.esprit.entities.Type;
import edu.esprit.services.ServiceDocument;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button; // Utilisez javafx.scene.control.Button
import javafx.scene.control.Label; // Utilisez javafx.scene.control.Label
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image; // Utilisez javafx.scene.image.Image
import javafx.scene.image.ImageView; // Utilisez javafx.scene.image.ImageView
import org.apache.pdfbox.pdmodel.PDDocument;

import javax.swing.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;

public class AfficherDocumentM {

    @FXML
    private Button idret;

    @FXML
    private VBox documentListVBox; // Assurez-vous que c'est le VBox où vous voulez ajouter les documents
    private final ServiceDocument DS=new ServiceDocument();

    private  Matiere matiere = new Matiere();
    public void initialize() {

            loadDocumentData();


    }
    public void setMatToCh(Matiere me) {
        matiere = me;
        loadDocumentData();

    }
    private void loadDocumentData()  {
        try {
        ArrayList<Document> documents = DS.getAllByMatiere(matiere); // Utilisez votre méthode pour obtenir tous les documents
        for (Document doc : documents) {
           /* System.out.println("/////////////////////");
            System.out.println(doc);*/
            VBox docVBox = createDocumentVBox(doc);
            VBox.setMargin(docVBox, new Insets(10, 10, 10, 70)); // Ajoutez cette ligne pour définir une marge supérieure de 10 pixels
            documentListVBox.getChildren().add(docVBox);
        }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private VBox createDocumentVBox(Document doc) {
        VBox vBox = new VBox();
        vBox.setSpacing(8);
        vBox.setMaxHeight(5); // Définir une hauteur maximale pour la VBox

        vBox.setStyle("-fx-background-color: transparent; -fx-border-color: #2b3674;  -fx-padding:5; -fx-border-radius: 10; -fx-background-radius: 10;"); // Ajout d'une couleur de fond et d'un padding

        Label titleLabel = new Label(doc.getTitre());
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-border-radius: 10; -fx-background-radius: 10;"); // Ajout de la taille de la police et du poids

        Label dateLabel = new Label(doc.getDate().toString());
        dateLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #2b3674;"); // Ajout de la taille de la police et de la couleur du texte

        Button openButton = new Button("Ouvrir");
        openButton.setOnAction(event -> openDocument(doc));
        openButton.setStyle("-fx-background-color:  #2b3674; -fx-text-fill:  #FAFEFC; -fx-padding: 10px 20px;"); // Ajout d'une couleur de fond, d'une couleur de texte et d'un padding

// Load the appropriate image based on the document type
        ImageView imageView;
        if (doc.getType() == Type.PDF) {
            imageView = new ImageView(new Image("file:C:\\Users\\benmr\\IdeaProjects\\test3\\src\\main\\resources\\image\\document.png"));
        } else {
            imageView = new ImageView(new Image("file:C:\\Users\\benmr\\IdeaProjects\\test3\\src\\main\\resources\\image\\video.png"));
        }
        imageView.setFitHeight(20); // Adjust the size of the image
        imageView.setFitWidth(20);
        HBox hBox = new HBox(imageView,titleLabel); // Combinaison de l'icône avec le titre
        hBox.setSpacing(10);

        vBox.getChildren().addAll( hBox,dateLabel, openButton);

        return vBox;
    }



    private void openDocument(Document document) {
        try {

            // Extraire l'ID du fichier à partir de l'URL
            String url = document.getUrl();
            String fileId;
            if (url.contains("/view")) {
                fileId = url.substring(url.indexOf("/d/") + 3, url.indexOf("/view"));
            } else {
                fileId = url.substring(url.indexOf("/d/") + 3);
            }
            System.out.println(url);
            // Extraire l'extension du fichier à partir de l'URL
            String extension = url.substring(url.lastIndexOf(".") + 1);

            // Télécharger le fichier à partir de Google Drive
            Drive service = UploadBasic.getDriveService();
            OutputStream outputStream = new ByteArrayOutputStream();
            service.files().get(fileId).executeMediaAndDownloadTo(outputStream);

            // Convertir le OutputStream en ByteArrayInputStream
            ByteArrayInputStream inputStream = new ByteArrayInputStream(((ByteArrayOutputStream) outputStream).toByteArray());

            if (document.getType()== Type.PDF) {
                // Charger le document PDF à partir du ByteArrayInputStream
                PDDocument pdfDocument = PDDocument.load(inputStream);

                // Afficher le document PDF dans une nouvelle fenêtre
                PDFViewer viewer = new PDFViewer("PDF Viewer", pdfDocument);
                viewer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                viewer.setSize(800, 600);
                viewer.setVisible(true);

                // Fermer le document PDF
                pdfDocument.close();
            } else {
                // Enregistrer le fichier vidéo en mémoire
                byte[] videoBytes = ((ByteArrayOutputStream) outputStream).toByteArray();

                // Définir l'extension
                extension = ".mp4"; // Remplacez ".mp4" par l'extension de fichier appropriée

                // Créer un fichier temporaire avec l'extension appropriée
                Path tempFile = Files.createTempFile("video", extension);

                // Lire la vidéo dans une nouvelle fenêtre
                VideoPlayer player = new VideoPlayer("Video Player", videoBytes, extension);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void retourMatiere(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherMatiers.fxml"));
            Parent root = loader.load();
            MatiersAffichage affichermatier=loader.getController();
            System.out.println("fffrtt");
            System.out.println(matiere.getAnnee());
             affichermatier.afficherSeulentAnnee(matiere.getAnnee());
            idret.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

