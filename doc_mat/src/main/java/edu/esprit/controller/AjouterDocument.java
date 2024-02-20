package edu.esprit.controller;

import edu.esprit.entities.*;
import edu.esprit.services.ServiceDocument;
import edu.esprit.services.SeviceMatiere;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;

public class AjouterDocument  implements Initializable {
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
    private final ServiceDocument DS = new ServiceDocument();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Charger les options pour le ComboBox Niveau
        idniveau.getItems().addAll(Niveau.values());

        // Charger les options pour le ComboBox Type
        idtype.getItems().addAll(Type.values());

        // Ajouter un ChangeListener au ComboBox idtype
        idtype.valueProperty().addListener(new ChangeListener<Type>() {
            @Override
            public void changed(ObservableValue<? extends Type> observable, Type oldValue, Type newValue) {
                // Mettre à jour la propriété éditable en fonction du type sélectionné
                updateEditableProperty(newValue);
            }
        });
    }
    public void setMatToAdd( Matiere me)
    {
        mt= me;
    }

    @FXML
    void RetourMatiere(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/AfficherMatiere.fxml"));
            Parent root = (Parent) loader.load();
            this.idtt.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur de chargement", "Impossible de charger la page d'affichage");
        }

    }

    @FXML
    void ajouterDocument(javafx.event.ActionEvent event) {
        if (validateInput()) {
            try {
                // Ajouter la date du jour
                LocalDate currentDate = LocalDate.now();

                this.DS.ajouter(new Document(this.idtt.getText(),this.idtype.getValue(),this.idurt.getText(),this.idniveau.getValue(), java.sql.Date.valueOf(currentDate),mt));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Validation");
                alert.setContentText("document added successfully");
                alert.showAndWait();
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/AfficherDocument.fxml"));
                Parent root = (Parent) loader.load();
                SeviceMatiere sm=new SeviceMatiere();
                // Accéder au contrôleur du formulaire de modification
                AfficherDocument affichedoc= loader.getController();
                Matiere me= sm.test(mt);
                if( me!=null)
                // Appeler la méthode pour passer la matière à modifier
                { affichedoc.setMatToShow(me);}
                this.idtt.getScene().setRoot(root);

            } catch (SQLException | IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erreur", e.getMessage());
            } catch (ExistanteException e) { showAlert(Alert.AlertType.ERROR, "Erreur", e.getMessage());}
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

            // Extraire l'extension du fichier
            String extension = FilenameUtils.getExtension(selectedFile.getName());

            // Définir la valeur du ComboBox idtype en fonction de l'extension
            if (extension.equalsIgnoreCase("pdf")) {
                idtype.setValue(Type.PDF);
                idurt.setText(selectedFile.getAbsolutePath());
            } else if (extension.equalsIgnoreCase("mp4") || extension.equalsIgnoreCase("avi")) {
                idtype.setValue(Type.VIDEO);
                idurt.setText(selectedFile.getAbsolutePath());
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur d'extension", "Ce type d'extension n'est pas pris en charge");
                idtype.setValue(null);
                updateEditableProperty(idtype.getValue());
            }



        }
    }
    @FXML
    private boolean validateInput() {
        String nom = idtt.getText();
        Type type=idtype.getValue();
        String url=idurt.getText();
        Niveau niveau=idniveau.getValue();

        return nom.length() >= 3 && !nom.isEmpty() && type != null && niveau != null && !url.isEmpty();

    }

    @FXML
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void updateEditableProperty(Type fileType) {
        // Si le type de fichier n'est pas PDF, VIDEO ou AVI, le TextField ne sera pas éditable
        boolean isEditable = fileType == Type.PDF || fileType == Type.VIDEO ;
        idurt.setEditable(isEditable);
    }

}
