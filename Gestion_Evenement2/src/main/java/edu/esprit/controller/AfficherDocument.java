package edu.esprit.controller;
import edu.esprit.APIapploadfichier.*;
import edu.esprit.services.CredentialsManager;
import edu.esprit.services.ProfileUser;
import edu.esprit.services.SeviceMatiere;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.google.api.services.drive.Drive;
import edu.esprit.entities.Document;
import edu.esprit.entities.Matiere;
import edu.esprit.entities.Niveau;
import edu.esprit.entities.Type;
import edu.esprit.services.ServiceDocument;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;

import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javafx.scene.control.ListView;
import org.apache.pdfbox.text.PDFTextStripper;

public class AfficherDocument {
    @FXML
    private Button idaj;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<Niveau> idl;
    @FXML
    private ListView<Document> idListView;



    @FXML
    private Button idretour;
    private int currentStep = 0;

    private ServiceDocument serviceDocument = new ServiceDocument();
     private Matiere matiere = new Matiere();

    @FXML
    private void initialize() {
        // Configure the ComboBox
        idl.getItems().setAll(Niveau.values());
        // Add "Tous les niveaux" as a separate item
        idl.getItems().add(null);

        idl.setOnAction(event -> handleLevelSelection());

        // Configure the ListView
        idListView.setCellFactory(param -> new ListCell<>() {
            private Button editButton = new Button("Modifier");
            private Button deleteButton = new Button("Supprimer");
            private Button afficheButton = new Button("Afficher");


            {
                editButton.setOnAction(event -> onEditButtonClicked(getItem()));
                deleteButton.setOnAction(event -> onDeleteButtonClicked(getItem()));
                afficheButton.setOnAction(event -> onAfficheClicked(getItem()));


                editButton.setStyle("-fx-background-color: transparent; -fx-text-fill:#a3aed0;-fx-font-weight: bold ");
                deleteButton.setStyle("-fx-background-color:transparent; -fx-text-fill: #a3aed0;-fx-font-weight: bold ");
                afficheButton.setStyle("-fx-background-color:transparent; -fx-text-fill: #a3aed0; -fx-font-weight: bold");

                editButton.setMaxWidth(200);
                deleteButton.setMaxWidth(200);
                afficheButton.setMaxWidth(200);

            }

            @Override
            protected void updateItem(Document item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Display more details of the document
                    Label label = new Label("Titre: " + item.getTitre() + "\n" +
                            "Type: " + item.getType() + "\n" +
                            "URL: " + item.getUrl() + "\n" +
                            "Niveau: " + item.getNiveau() + "\n" +
                            "Date: " + item.getDate());
                    label.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;-fx-text-fill: #2b3674;"); // Adjust the font size and weight

                    // Load the appropriate image based on the document type
                    ImageView imageView;
                    if (item.getType() == Type.PDF) {
                        imageView = new ImageView(new Image("file:C:/Users/benmr/IdeaProjects/test3/src/main/resources/images/pdf.png"));
                    } else {
                        imageView = new ImageView(new Image("file:C:/Users/benmr/IdeaProjects/test3/src/main/resources/images/video.png"));
                    }

                    imageView.setFitHeight(20); // Adjust the size of the image
                    imageView.setFitWidth(20);

                    setGraphic(new VBox(label, new HBox(imageView, editButton, deleteButton, afficheButton)));
                }
            }
        });

        // Load the data into the ListView
        loadDocumentData();
    }

    private void handleLevelSelection() {
        loadDocumentData();
    }

    public void setMatToShow(Matiere me) {
        System.out.println(me);
        matiere = me;
        System.out.println("********************");
        System.out.println(matiere);
        loadDocumentData();
    }



    private void loadDocumentData() {
        try {


            if (matiere != null) {
                Niveau selectedLevel = idl.getValue();
                ObservableList<Document> documentList;

                if (selectedLevel != null && selectedLevel.equals(Niveau.LEVEL_1)) {
                    documentList = FXCollections.observableArrayList(serviceDocument.getByLevel(selectedLevel, matiere));
                } else if (selectedLevel != null && selectedLevel.equals(Niveau.LEVEL_2)) {
                    documentList = FXCollections.observableArrayList(serviceDocument.getByLevel(selectedLevel, matiere));
                } else if (selectedLevel != null && selectedLevel.equals(Niveau.LEVEL_3)) {
                    documentList = FXCollections.observableArrayList(serviceDocument.getByLevel(selectedLevel, matiere));
                } else {
                    documentList = FXCollections.observableArrayList(serviceDocument.getAllByMatiere(matiere));
                }

                idListView.setItems(documentList);
            } else {
                idListView.setItems(FXCollections.emptyObservableList());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void onEditButtonClicked(Document document) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ModifierDocument.fxml"));
            Parent root = loader.load();

            ModiffierDocument modifierdocument = loader.getController();
            modifierdocument.setDocumentToModify(document);

            idretour.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onDeleteButtonClicked(Document document) {
        try {
            serviceDocument.supprimer(document.getId());
            loadDocumentData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void onAfficheClicked(Document document) {
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

                // Créer un PDFTextStripper pour lire le texte du PDF
                PDFTextStripper pdfStripper = new PDFTextStripper();

                // Lire le texte du PDF
                String text = pdfStripper.getText(pdfDocument);

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


            if (e.getMessage().contains("Page tree root must be a dictionary")) {
                System.err.println("Failed to load PDF file because it does not conform to the PDF specification.");
            } else {
                e.printStackTrace();}
        }
    }

    private void showStatistics(List<Statistique> statistiques) {
        Stage stage = new Stage();
        stage.setTitle("Statistiques");

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();


        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Statistiques des documents par annee");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Statistique statistique : statistiques) {
            String matiere = statistique.getTitre();
            int  date = statistique.getNembre();
       // Convert date to milliseconds
            series.getData().add(new XYChart.Data<>(matiere, date));
        }

        barChart.getData().add(series);

        Scene scene = new Scene(barChart, 800, 600);
        stage.setScene(scene);
        stage.show();
    }
    public void retourMatiere(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AfficherMatiere.fxml"));
            Parent root = (Parent) loader.load();
            this.idretour.getScene().setRoot(root);
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

    public void trier(ActionEvent actionEvent) {
        try {

            if (matiere != null) {
                Niveau selectedLevel = idl.getValue();
                ObservableList<Document> documentList;

                if (selectedLevel != null &&  selectedLevel.equals(Niveau.LEVEL_1)) {
                    documentList = FXCollections.observableArrayList(serviceDocument.getByLevel(selectedLevel,matiere));
                } else  if (selectedLevel != null && selectedLevel.equals(Niveau.LEVEL_2))  {
                    documentList = FXCollections.observableArrayList(serviceDocument.getByLevel(selectedLevel,matiere));
                } else   if (selectedLevel != null && selectedLevel.equals(Niveau.LEVEL_3))
                {documentList = FXCollections.observableArrayList(serviceDocument.getByLevel(selectedLevel,matiere));}
                else { documentList = FXCollections.observableArrayList(serviceDocument. getAllTitle());}

                idListView.setItems(documentList);
            } else {
                idListView.setItems(FXCollections.emptyObservableList());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void rechercheDate(ActionEvent actionEvent) {
        try {
            // Récupérer la date sélectionnée dans le DatePicker
            LocalDate selectedDate = datePicker.getValue();

            // Vérifier si une date est sélectionnée
            if (selectedDate != null) {
                // Convertir LocalDate en java.sql.Date
                java.sql.Date sqlDate = java.sql.Date.valueOf(selectedDate);

                // Appeler la méthode de service pour obtenir les documents pour une date spécifique
                ObservableList<Document> documentList = FXCollections.observableArrayList(serviceDocument.getByDate(sqlDate,matiere));
                idListView.setItems(documentList);
            } else {
                // Si aucune date n'est sélectionnée, afficher tous les documents
                loadDocumentData();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void retourAjouterDoc(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AjouterDocument.fxml"));
            Parent root = loader.load();
            AjouterDocument ajouterdoc= loader.getController();
            SeviceMatiere serviceMatiere=new SeviceMatiere();
            Matiere me=serviceMatiere.getOne(matiere.getId());
            if( me!=null)

            { ajouterdoc.setMatToAdd(me);}
                    idaj.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
@FXML
    public void onShowStatitique(ActionEvent actionEvent) {
    StatistiquesAPI api = new StatistiquesAPI();
    System.out.println("**********************///");
    System.out.println(matiere.getId());
    List<Statistique> statistiques = api.getStatistiquesAnnee(matiere);
    showStatistics(statistiques);
    }


@FXML
    public void toMatiere(ActionEvent actionEvent) throws IOException {
    FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/AfficherMatiere.fxml"));
    Parent root=loader.load();
    AffichageMatiereController acm=loader.getController();
    acm.setProftoGet(matiere.getProf());
    idaj.getScene().setRoot(root);

    }
@FXML
    public void deconnexion(ActionEvent actionEvent) throws IOException {
    CredentialsManager.clearCredentials();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
    Parent root = loader.load();
    idaj.getScene().setRoot(root);
    }
@FXML
    public void tomessage(ActionEvent actionEvent) throws IOException {
    FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/AfficherMessage.fxml"));
    Parent root=loader.load();
    idaj.getScene().setRoot(root);

    }
}