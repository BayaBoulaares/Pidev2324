package edu.esprit.controller;

import edu.esprit.entities.*;
import edu.esprit.services.ServiceDocument;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ModiffierDocument  implements Initializable {
    @FXML
    private ComboBox<Niveau> idniveau;

    @FXML
    private TextField idtt;

    @FXML
    private ComboBox<Type> idtype;

    @FXML
    private Button idurl;
    @FXML
    private TextField idurt;
    private Matiere mt=new Matiere();
    int id;
    private final ServiceDocument DS = new ServiceDocument();
    public void setDocumentToModify( Document doc) {
        idtt.setText(doc.getTitre());
        idtype.setValue(doc.getType());
        idurt.setText(doc.getUrl());
        idniveau.setValue(doc.getNiveau());
        id=doc.getId();
        // Vous pouvez également stocker la matière dans une variable de classe pour une utilisation ultérieure lors de la modification
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Charger les options pour le ComboBox Niveau
        idniveau.getItems().addAll(Niveau.values());

        // Charger les options pour le ComboBox Type
        idtype.getItems().addAll(Type.values());
    }

    @FXML
    void RetourMatiere(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/AfficherDocument.fxml"));
            Parent root = (Parent) loader.load();
            this.idtt.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur de chargement", "Impossible de charger la page d'affichage");
        }

    }

    @FXML
    void modifierDocument(javafx.event.ActionEvent event) {
        if (validateInput()) {
            try {
                // Ajouter la date du jour
                LocalDate currentDate = LocalDate.now();

                this.DS.modifier(new Document(id,this.idtt.getText(),this.idtype.getValue(),this.idurt.getText(),this.idniveau.getValue(), java.sql.Date.valueOf(currentDate),null));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Validation");
                alert.setContentText("Document updated successfully");
                alert.showAndWait();
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/AfficherDocument.fxml"));
                Parent root = (Parent) loader.load();
                this.idtt.getScene().setRoot(root);

            } catch (SQLException | IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erreur", e.getMessage());
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Veuillez remplir tous les champs!");
        }


    }

    @FXML
    void choisirFichier(javafx.event.ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir un fichier");

        // Filtre pour tous les fichiers
        FileChooser.ExtensionFilter extFilterAll = new FileChooser.ExtensionFilter("Tous les fichiers", "*.*");
        fileChooser.getExtensionFilters().add(extFilterAll);

        // Afficher la boîte de dialogue et récupérer le fichier sélectionné
        java.io.File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            // Afficher le chemin du fichier dans le TextField
            idurt.setText(selectedFile.getAbsolutePath());

            // Extraire l'extension du fichier
            String extension = FilenameUtils.getExtension(selectedFile.getName());

            // Définir la valeur du ComboBox idtype en fonction de l'extension
            if (extension.equalsIgnoreCase("pdf")) {
                idtype.setValue(Type.PDF);
            } else if (extension.equalsIgnoreCase("mp4") || extension.equalsIgnoreCase("avi")) {
                idtype.setValue(Type.VIDEO);
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur d'extension", "Ce type d'extension n'est pas pris en charge");
            }
        }
    }
    @FXML
    private boolean validateInput() {
        String nom = idtt.getText();


        return nom.length() >= 3 && !nom.isEmpty() ;

    }

    @FXML
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
