package edu.esprit.controller;

import edu.esprit.entities.*;
import edu.esprit.services.CredentialsManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import edu.esprit.services.SeviceMatiere;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.PrimitiveIterator;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;

public class ModifierMatiereController implements Initializable {

    @FXML
    private ComboBox<CAT> idcat;
    @FXML
    private ComboBox<String> idannee;
    @FXML
    private Button idcon;

    @FXML
    private TextArea iddesc;

    @FXML
    private TextField idnom;

    @FXML
    private Button idretour;
    private  int id;
    private final SeviceMatiere MS = new SeviceMatiere();
    private Professeur pp;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idannee.getItems().addAll("1 ere annee", "2 eme annee", "3 éme annee", "4 eme annee", "5 eme annee", "6 eme anne");
      idcat.getItems().addAll(CAT.values());

    }

    public void setMatiereToModify(Matiere matiere) {
        idnom.setText(matiere.getNommatiere());
        iddesc.setText(matiere.getDescription());
        id=matiere.getId();
        pp=matiere.getProf();
        idannee.getItems().add(matiere.getAnnee());
        idcat.getItems().add(matiere.getCategorie());
        idcat.setValue(matiere.getCategorie());
        idannee.setValue(matiere.getAnnee());
        // Vous pouvez également stocker la matière dans une variable de classe pour une utilisation ultérieure lors de la modification
    }

    public void ModifierMatiere(ActionEvent actionEvent) {
        String validationError = validateInput();

        if (validationError.isEmpty()) {
            try {


                    this.MS.modifier(new Matiere(id,this.idnom.getText(), this.iddesc.getText(),idannee.getValue(),idcat.getValue(),pp));
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Validation");
                    alert.setContentText("Matiere updated successfully");
                    alert.showAndWait();
                    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/AfficherMatiere.fxml"));
                    Parent root = (Parent) loader.load();
                    this.idnom.getScene().setRoot(root);

            } catch (SQLException | IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erreur", e.getMessage());
            } catch (ExistanteException e) {
                 //e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erreur", e.getMessage());
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

    @FXML
    public void RetourAffichage(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/AfficherMatiere.fxml"));
            Parent root = (Parent) loader.load();
            AffichageMatiereController acm=loader.getController();
            acm.setProftoGet(pp);
            this.idnom.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur de chargement", "Impossible de charger la page d'affichage");
        }
    }
@FXML
    public void tohisprofile(ActionEvent actionEvent) {
    }
@FXML
    public void toMatiere(ActionEvent actionEvent) throws IOException {
    FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/AfficherMatiere.fxml"));
    Parent root=loader.load();
    AffichageMatiereController acm=loader.getController();
    acm.setProftoGet(pp);
    idcon.getScene().setRoot(root);
    }
@FXML
    public void deconnexion(ActionEvent actionEvent) throws IOException {
    CredentialsManager.clearCredentials();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
    Parent root = loader.load();
    idretour.getScene().setRoot(root);
    }
}