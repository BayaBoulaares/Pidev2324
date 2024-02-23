package edu.esprit.controller;

import com.google.api.services.drive.Drive;
import edu.esprit.APIapploadfichier.PDFViewer;
import edu.esprit.APIapploadfichier.UploadBasic;
import edu.esprit.entities.Document;
import edu.esprit.entities.Matiere;
import edu.esprit.entities.Niveau;
import edu.esprit.services.ServiceDocument;
import edu.esprit.services.SeviceMatiere;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.TableHeaderRow;
import javafx.scene.control.skin.TableViewSkin;
import javafx.scene.input.MouseEvent;
import org.apache.pdfbox.pdmodel.PDDocument;

import javax.swing.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class AfficherDocument {
    @FXML
    private Button idaj;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<Niveau> idl;

    @FXML
    private TableView<Document> idtab;

    @FXML
    private TableColumn<Document, Integer> idDate;

    @FXML
    private TableColumn<Document, String> idType;

    @FXML
    private TableColumn<Document, String> idniveau;

    @FXML
    private TableColumn<Document, String> idtitre;

    @FXML
    private TableColumn<Document, String> idurl;

    @FXML
    private TableColumn<Document, Document> idactions;

    @FXML
    private Button idretour;
    private int currentStep = 0;

    private ServiceDocument serviceDocument = new ServiceDocument();
     private Matiere matiere = new Matiere();

    @FXML
    private void initialize() {
        // Configure les colonnes du TableView
        idDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        idType.setCellValueFactory(new PropertyValueFactory<>("type"));
        idniveau.setCellValueFactory(new PropertyValueFactory<>("niveau"));
        idtitre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        idurl.setCellValueFactory(new PropertyValueFactory<>("url"));

        // Configure la colonne "Actions" avec des boutons "Modifier" et "Supprimer"
        configureActionsColumn();



        // Configure the ComboBox
        idl.getItems().setAll(Niveau.values());
        // Add "Tous les niveaux" as a separate item
       idl.getItems().add(null);

        idl.setOnAction(event -> handleLevelSelection());
        // Charge les données dans le TableView
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

                idtab.setItems(documentList);
            } else {
                idtab.setItems(FXCollections.emptyObservableList());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void configureActionsColumn() {
        idactions.setCellValueFactory(param -> {
            ObjectProperty<Document> property = new SimpleObjectProperty<>(param.getValue());
            return property;
        });
        idactions.setCellFactory(column -> new TableCell<>() {
            private Button editButton = new Button("Modifier");
            private Button deleteButton = new Button("Supprimer");
            private Button afficheButton = new Button("Afficher");


            {
               editButton.setOnAction(event -> onEditButtonClicked(getItem()));
                deleteButton.setOnAction(event -> onDeleteButtonClicked(getItem()));
                afficheButton.setOnAction(event -> onAfficheClicked(getItem()) );

                editButton.setStyle("-fx-background-color: transparent; -fx-text-fill:#a3aed0; ");
                deleteButton.setStyle("-fx-background-color:transparent; -fx-text-fill: #a3aed0; ");
                afficheButton.setStyle("-fx-background-color:transparent; -fx-text-fill: #a3aed0; ");

                editButton.setMaxWidth(200);
                deleteButton.setMaxWidth(200);
                afficheButton.setMaxWidth(200);
            }

            @Override
            protected void updateItem(Document item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setGraphic(null);
                } else {
                    setGraphic(new javafx.scene.layout.HBox(editButton, deleteButton , afficheButton ));
                }
            }
        });
    }

    private void onEditButtonClicked(Document document) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierDocument.fxml"));
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
        PDDocument pdfDocument = null;
        try {
            // Extraire l'ID du fichier à partir de l'URL
            String url = document.getUrl();
            String fileId;
            if (url.contains("/view")) {
                fileId = url.substring(url.indexOf("/d/") + 3, url.indexOf("/view"));
            } else {
                fileId = url.substring(url.indexOf("/d/") + 3);
            }

            // Télécharger le fichier PDF à partir de Google Drive
            Drive service = UploadBasic.getDriveService();
            OutputStream outputStream = new ByteArrayOutputStream();
            service.files().get(fileId).executeMediaAndDownloadTo(outputStream);

            // Convertir le OutputStream en ByteArrayInputStream
            ByteArrayInputStream inputStream = new ByteArrayInputStream(((ByteArrayOutputStream) outputStream).toByteArray());

            // Charger le document PDF à partir du ByteArrayInputStream
            pdfDocument = PDDocument.load(inputStream);

            // Afficher le document PDF dans une nouvelle fenêtre
            PDFViewer viewer = new PDFViewer("PDF Viewer", pdfDocument);
            viewer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            viewer.setSize(800, 600);
            viewer.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (pdfDocument != null) {
                try {
                    pdfDocument.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }





    public void retourMatiere(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherMatiere.fxml"));
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

                idtab.setItems(documentList);
            } else {
                idtab.setItems(FXCollections.emptyObservableList());
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
                ObservableList<Document> documentList = FXCollections.observableArrayList(serviceDocument.getByDate(sqlDate));
                idtab.setItems(documentList);
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

    public void retourAjouterDoc(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterDocument.fxml"));
            Parent root = loader.load();

            // Accéder au contrôleur du formulaire de modification
            AjouterDocument ajouterdoc= loader.getController();

            if( matiere!=null)
            // Appeler la méthode pour passer la matière à modifier
            { ajouterdoc.setMatToAdd(matiere);}

            // Changer la scène pour afficher le formulaire de modification
            idaj.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}