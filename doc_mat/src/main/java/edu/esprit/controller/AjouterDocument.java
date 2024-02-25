package edu.esprit.controller;

import edu.esprit.APIapploadfichier.UploadBasic;
import edu.esprit.APIapploadfichier.VideoCompressor;
import edu.esprit.entities.*;
import edu.esprit.services.ServiceDocument;
import edu.esprit.services.SeviceMatiere;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;

public class AjouterDocument  implements Initializable {
    @FXML
    private Button idc;
    @FXML
    private Button idconf;
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
    private static boolean guideUtilisationAffiche = false;
    private int currentStep = 0;
     private String fileUrl;
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
        // Afficher le guide d'utilisation lors de la première utilisation
        if (!guideUtilisationAffiche) {
            afficherGuideUtilisation();
            guideUtilisationAffiche = true;
        }

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

                this.DS.ajouter(new Document(this.idtt.getText(),this.idtype.getValue(),fileUrl,this.idniveau.getValue(), java.sql.Date.valueOf(currentDate),mt));
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
        idtype.setValue(null);
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
            } else if (extension.equalsIgnoreCase("mp4")) {
                idtype.setValue(Type.MP4);
            } else if (extension.equalsIgnoreCase("avi")) {
                idtype.setValue(Type.AVI);
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur d'extension", "Ce type d'extension n'est pas pris en charge");
                idtype.setValue(null);
                updateEditableProperty(idtype.getValue());
                return;
            }

            new Thread(() -> {
                try {
                    // Ajouter le fichier à Google Drive et récupérer son URL
                    String fileId;
                    if (extension.equalsIgnoreCase("pdf")) {
                        fileId = UploadBasic.uploadPDF(selectedFile.getAbsolutePath());
                    } else { // mp4 or avi
                        String compressedFilePath = VideoCompressor. compressVideo(selectedFile.getAbsolutePath(), extension);
                        fileId = UploadBasic.uploadVideo(compressedFilePath, extension);
                    }


                    fileUrl = "https://drive.google.com/file/d/" + fileId;

                    // Afficher l'URL du fichier dans l'interface utilisateur
                    Platform.runLater(() -> {
                        System.out.println("URL du fichier : " + fileUrl);
                        idurt.setText(selectedFile.getAbsolutePath()); // Mettre à jour le champ de texte avec le chemin du fichier
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

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
        boolean isEditable = fileType == Type.PDF || fileType == Type.AVI|| fileType==Type.MP4;
        idurt.setEditable(isEditable);
    }
    public void afficherGuideUtilisation() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Guide d'utilisation");
        alert.setHeaderText(null);

        StringBuilder guideText = new StringBuilder("Bienvenue dans l'interface d'ajout de document. Veuillez suivre les instructions ci-dessous :\n\n");

        alert.setContentText(guideText.toString());
        alert.showAndWait();
    }

    public void showexplication(MouseEvent mouseEvent) {
        // Display the alert corresponding to the current step
        if (currentStep < 5) {
            // Display the alert corresponding to the current step
            switch (currentStep) {
                case 0:
                    showAlertAtPosition(createAlert("1.  Remplissez le champ 'Titre' avec le titre du document. Assurez-vous qu'il contient au moins 3 caractères."), idtt);
                    break;
                case 1:
                    showAlertAtPosition(createAlert("2. Choisissez le type de document à partir du menu déroulant 'Type'."), idtype);
                    break;
                case 2:
                    showAlertAtPosition(createAlert("3. Selon le type de document, le champ 'URL' peut être éditable ou non. Si le type est PDF ou Vidéo, vous pouvez choisir un fichier à partir du bouton 'Choisir Fichier'."), idurt);
                    break;
                case 3:
                    showAlertAtPosition(createAlert("4. Sélectionnez le niveau du document à partir du menu déroulant 'Niveau'."), idniveau);
                    break;
                case 4:
                    showAlertAtPosition(createAlert("5. Cliquez sur le bouton 'Ajouter' pour enregistrer le document."), idconf);
                    break;
                default:
                    break;
            }

            // Increment the step for the next time the method is called
            currentStep++;
        }
    }

    private Alert createAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Guide d'utilisation");
        alert.setHeaderText(null);
        alert.setContentText(content);
        return alert;
    }

    private void showAlertAtPosition(Alert alert, Node node) {
        // Convert local coordinates to scene coordinates
        Bounds boundsInScene = node.localToScene(node.getBoundsInLocal());

        // Définissez la position de l'alerte à côté du champ de texte ou du ComboBox
        alert.setX(node.getScene().getWindow().getX() + node.getScene().getX() + boundsInScene.getMinX());
        alert.setY(node.getScene().getWindow().getY() + node.getScene().getY() + boundsInScene.getMinY() + boundsInScene.getHeight());

        alert.showAndWait();
    }

    public void condulterDocument(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherDocument.fxml"));
            Parent root = loader.load();
            AfficherDocument affichedoc= loader.getController();
            if( mt!=null)
            { affichedoc.setMatToShow(mt);}
            idc.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
