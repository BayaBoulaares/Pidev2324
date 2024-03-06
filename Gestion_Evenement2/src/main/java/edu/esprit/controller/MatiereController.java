package edu.esprit.controller;

import edu.esprit.entities.CAT;
import edu.esprit.entities.ExistanteException;
import edu.esprit.entities.Matiere;
import edu.esprit.entities.Professeur;
import edu.esprit.services.CredentialsManager;
import edu.esprit.services.ServiceProfesseur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import edu.esprit.services.SeviceMatiere;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.SQLException;

public class MatiereController {
    @FXML
    private ComboBox<CAT> idcat;
    @FXML
    private ComboBox<String> idannee;

    @FXML
    private Button idc;
    @FXML
    private Button idcon;

    @FXML
    private TextArea iddesc;

    @FXML
    private TextField idnom;
    private final SeviceMatiere MS = new SeviceMatiere();
    String idP=CredentialsManager.loadCredentials()[0];
    ServiceProfesseur serviceProfesseur = new ServiceProfesseur();
    private Professeur prof= serviceProfesseur.getByLogin(idP);
    private int currentStep = 0;
    @FXML
    public void initialize() {
        // Initialize the ComboBox with the desired items
        idannee.getItems().addAll("1 ere annee", "2 eme annee", "3 éme annee", "4 eme annee", "5 eme annee", "6 eme anne");
        idcat.getItems().addAll(CAT.SCIENTIFIQUE, CAT.LANGUE, CAT.HISTOIRE, CAT.GEOGRAPHIE);
        afficherGuideUtilisation();
    }

/*public void professGet(Professeur prof)
{
    System.out.println("//////////////////");
    System.out.println(prof);
    this.prof=prof;
}*/
    public  MatiereController(){

    }
    @FXML
    public void RetourAffichage(javafx.event.ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/AfficherMatiere.fxml"));
            Parent root = (Parent) loader.load();
            AffichageMatiereController acm=loader.getController();
            acm.setProftoGet(prof);
            this.idnom.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur de chargement", "Impossible de charger la page d'affichage");
        }
    }


    @FXML
    public void ajouterMatiere(javafx.event.ActionEvent actionEvent) {
        String validationError = validateInput();
        System.out.println(prof);
        if (validationError.isEmpty()) {
            try {
                this.MS.ajouter(new Matiere(this.idnom.getText(), this.iddesc.getText(),this.idannee.getValue(),this.idcat.getValue(),prof));

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Validation");
                alert.setContentText("Person added succesfully");
                alert.showAndWait();
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/AfficherMatiere.fxml"));
                Parent root = (Parent)loader.load();
                this.idnom.getScene().setRoot(root);
            } catch (ExistanteException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", e.getMessage());
            }catch (SQLException var5) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("SQL Exeption");
                alert.setContentText(var5.getMessage());
                alert.showAndWait();
            }  catch (IOException var6) {
                throw new RuntimeException(var6);
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur de validation", validationError);
        }

    }
    @FXML
    private String validateInput() {
        String nom = idnom.getText();
        CAT categorie = idcat.getValue();
        String desc = iddesc.getText();
        String anne = idannee.getValue();

        StringBuilder validationError = new StringBuilder();

        if (nom.isEmpty() || nom.length() < 3 || !nom.matches("[a-zA-Z]+")) {
            validationError.append("Le nom doit avoir au moins 3 caractères et ne doit pas contenir de chiffres.\n");
        }

        if (categorie == null) {
            validationError.append("Veuillez sélectionner une catégorie.\n");
        }

        if (desc.isEmpty() || desc.length() < 5) {
            validationError.append("La description doit contenir aux moins 5 caractères.\n");
        }

        if (anne == null || anne.isEmpty()) {
            validationError.append("Veuillez spécifier une année.\n");
        }
        // Additional validation for category based on the name prefix
        if (nom.equals("mathematique") || nom.equals("physique") || nom.equals("science")) {
            if (categorie != CAT.SCIENTIFIQUE) {
                validationError.append("Le nom de la matière indique une catégorie scientifique, veuillez sélectionner 'Scientifique' comme catégorie.\n");
            }
        } else if (nom.equals("arabe") || nom.equals("francais") || nom.equals("anglais")) {
            if (categorie != CAT.LANGUE) {
                validationError.append("Le nom de la matière indique une catégorie langue, veuillez sélectionner 'Langue' comme catégorie.\n");
            }
        }

        return validationError.toString().trim(); // No leading/trailing whitespaces
    }

    @FXML
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }


    public void consulterMatiere(ActionEvent actionEvent) {
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AfficherMatiere.fxml"));
        Parent root = loader.load();
        idc.getScene().setRoot(root);
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
@FXML
    public void tohisprofile(ActionEvent actionEvent) {
    }
@FXML
    public void toMatiere(ActionEvent actionEvent) throws IOException {
    FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/acceuilMatiere.fxml"));
    Parent root=loader.load();
    AffichageMatiereController acm=loader.getController();
    acm.setProftoGet(prof);
    idc.getScene().setRoot(root);
    }
@FXML
    public void deconnexion(ActionEvent actionEvent) throws IOException {
    CredentialsManager.clearCredentials();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
    Parent root = loader.load();
    idc.getScene().setRoot(root);
    }
    public void afficherGuideUtilisation() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Guide d'utilisation");
        alert.setHeaderText(null);

        StringBuilder guideText = new StringBuilder("Bienvenue dans l'interface d'ajout de Matiers. Veuillez suivre les instructions ci-dessous :\n\n");

        alert.setContentText(guideText.toString());
        alert.showAndWait();
    }
    private void showAlertAtPosition(Alert alert, Node node) {
        // Convert local coordinates to scene coordinates
        Bounds boundsInScene = node.localToScene(node.getBoundsInLocal());

        // Définissez la position de l'alerte à côté du champ de texte ou du ComboBox
        alert.setX(node.getScene().getWindow().getX() + node.getScene().getX() + boundsInScene.getMinX());
        alert.setY(node.getScene().getWindow().getY() + node.getScene().getY() + boundsInScene.getMinY() + boundsInScene.getHeight());

        alert.showAndWait();
    }
    @FXML
    public void showexplication(MouseEvent mouseEvent) {
        // Display the alert corresponding to the current step
        if (currentStep < 5) {
            // Display the alert corresponding to the current step
            switch (currentStep) {
                case 0:
                    showAlertAtPosition(createAlert("1.  Remplissez le champ 'Titre' avec le nom de matiers. Assurez-vous qu'il contient au moins 3 caractères. et pas de nombre"), idnom);
                    break;
                case 1:
                    showAlertAtPosition(createAlert("2.Ajouter une description pour la matiere."), iddesc);
                    break;
                case 2:
                    showAlertAtPosition(createAlert("3.Ajouter pour qu'elle annee cette matiere."), idannee);
                    break;
                case 3:
                    showAlertAtPosition(createAlert("4. Sélectionnez la categorie du Matiers à partir du menu déroulant 'Niveau'."), idcat);
                    break;
                case 4:
                    showAlertAtPosition(createAlert("5. Cliquez sur le bouton 'confirmer' pour ajouter la Matiere."), idc);
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
@FXML
    public void tomessage(ActionEvent actionEvent) throws IOException {
    FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/AfficherMessage.fxml"));
    Parent root=loader.load();
    idc.getScene().setRoot(root);
    }
}
