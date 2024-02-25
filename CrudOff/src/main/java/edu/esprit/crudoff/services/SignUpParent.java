package edu.esprit.crudoff.services;

import edu.esprit.crudoff.entities.ParentE;
import edu.esprit.crudoff.utilis.DataSource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

public class SignUpParent {
    private final ServiceUtilisateur SU = new ServiceUtilisateur();


    @FXML
    private TextField adresseparent;
    @FXML
    private TextField confimmdp;


    @FXML
    private Button bconfirm;

    @FXML
    private Button breturn;

    @FXML
    private DatePicker dobenfant;

    @FXML
    private DatePicker dobparent;

    @FXML
    private TextField emailparent;

    @FXML
    private TextField mdpparent;

    @FXML
    private TextField nomenfant;

    @FXML
    private TextField nomparent;

    @FXML
    private TextField prenomenfant;

    @FXML
    private TextField prenomparent;

    @FXML
    private TextField telparent;
    private ServiceParent serviceParent = new ServiceParent();

    @FXML
    void addenfant(ActionEvent event) throws IOException {
        try {

            System.out.println("bb");
            String cmdp = confimmdp.getText();
            // Récupérer les valeurs des champs de saisie
            String nomParent = nomparent.getText();
            String prenomParent = prenomparent.getText();
            String adresseParent = adresseparent.getText();
            LocalDate dobParentValue = dobparent.getValue();
            Date dobParent = java.sql.Date.valueOf(dobParentValue);
            String telephoneText = telparent.getText();
            String emailParent = emailparent.getText();
            String mdpParent = mdpparent.getText();
            String nomEnfant = nomenfant.getText();
            String prenomEnfant = prenomenfant.getText();
            LocalDate dobEnfantValue = dobenfant.getValue();
            Date dobEnfant = java.sql.Date.valueOf(dobEnfantValue);
            //Controle de saisie

             if (prenomParent.matches(".*\\d.*"))
                {
                showAlert("Le nom et le prénom doivent avoir au moins 3 caractères et être alphanumériques !");
                return;
                }

            if (adresseParent.length() < 5) {
                showAlert("L'adresse doit avoir au moins 5 caractères.");
                return;
            }
            // Vérification du numéro de téléphone
            if (!telephoneText.matches("\\d{8}")) {
                showAlert("Le numéro de téléphone doit contenir exactement 8 chiffres.");
                return;
            }
            String regexpsd = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
            int telephone = Integer.parseInt(telephoneText);
            if (mdpParent.matches(regexpsd) && mdpParent.equals(cmdp)) {
                showAlert("Le mot de passe doit contenir au moins 6 caractères.");
                return;
            }
            // Vérification de l'email
            if (!(emailParent.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$"))) {
                showAlert("Email invalide.");
                return;
            }

            // Vérification si l'email existe déjà dans la base de données
            if (isEmailAlreadyExists(emailParent)) {
                showAlert("Cet email est déjà utilisé. Veuillez en choisir un autre.");
                return;
            }

            // Vérification de la date de naissance
            /*LocalDate dobDate = LocalDate.parse(dobenfant, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate currentDate = LocalDate.now();*/
            /*if (dobParent.plusYears(20).isAfter(currentDate)) {
                showAlert("La date de naissance doit remonter à au moins 25 ans.");
                return;
            }*/


            // Créer un nouvel objet Parent avec les données récupérées
            ParentE parent = new ParentE(nomParent, prenomParent, adresseParent, dobParent, telephone, emailParent, mdpParent, nomEnfant, prenomEnfant, dobEnfant);

            // Ajouter le parent à la base de données en utilisant le service Parent
            serviceParent.ajouter(parent);

            // Afficher un message de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Vous êtes ajouté avec succès !");
            alert.showAndWait();
            System.out.println(parent);
            /*FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/Profile.fxml"));
            Parent root=loader.load();
            nomenfant.getScene().setRoot(root);*/
            // Afficher les valeurs saisies dans les TextField
            afficherChampsDernierementAjoutes(nomParent, nomEnfant); // Appel de la méthode pour afficher les champs
            // Effacer les champs de saisie après l'ajout
            //effacerChamps();

        } catch (SQLException e) {
            // En cas d'erreur SQL, afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur SQL");
            alert.setHeaderText(null);
            alert.setContentText("Erreur lors de l'ajout de l'enfant : " + e.getMessage());
            alert.showAndWait();
        } catch (NumberFormatException e) {
            // En cas d'erreur de format de numéro de téléphone, afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de format");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez entrer un numéro de téléphone valide !");
            alert.showAndWait();
        }
    }




    // Méthode utilitaire pour afficher une alerte
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private boolean isEmailAlreadyExists(String email) throws SQLException {
        // Créer la requête SQL pour rechercher l'email dans la table des utilisateurs
        Connection cnx = DataSource.getInsatnce().getConnection();
        String sql = "SELECT COUNT(*) FROM utilisateurs WHERE login = ?";

        try (PreparedStatement statement = cnx.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                // S'il y a des résultats, cela signifie que l'email existe déjà
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        }

        return false; // Aucun email trouvé, donc il n'existe pas encore
    }

    public  void afficherChampsDernierementAjoutes(String nom, String prenom) {
        // Afficher les valeurs dans les TextField appropriés
        nomparent.setText(nom);
        nomenfant.setText(prenom);
        // Et ainsi de suite pour les autres champs
    }

    @FXML
    void returntologin(ActionEvent event) throws IOException{
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Parent root=loader.load();
        nomenfant.getScene().setRoot(root);
    }


    private boolean verifierNom(String nom) {
        // Vérifier s'il y a des chiffres dans le nom
        for (char c : nom.toCharArray()) {
            if (Character.isDigit(c)) {
                return false;
            }
        }
        // Vérifier s'il y a des espaces dans le nom
        if (nom.contains(" ")) {
            return false;
        }
        return true;
    }



}