package edu.esprit.services;
import edu.esprit.entities.Professeur;
import edu.esprit.utils.DataSource;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.regex.Pattern;

public class AddProfs {
    private final ServiceProfesseur SP = new ServiceProfesseur();
    Connection cnx = DataSource.getInstance().getCnx();

    @FXML
    private Label nameerror;
    @FXML
    private DatePicker adddobfield;
    @FXML
    private TextField addpadressefield;

    @FXML
    private TextField addpdiscfield;

    @FXML
    private TextField addpemailfield;

    @FXML
    private TextField addpnomfield;

    @FXML
    private TextField addpprenomfield;

    @FXML
    private TextField addptelfield;
    @FXML
    private PasswordField pfmdp;

    @FXML
    private Button bconfirm;

    @FXML
    private Button breturn;

    @FXML
    private Button dcxn;

    @FXML
    private Button evaluation1;

    @FXML
    private Button evenements1;

    @FXML
    private Button profile2;

    @FXML
    private Button proform1;

    @FXML
    private Button reclamation1;
    @FXML
    private Button totableprof;

    @FXML
    private Button tousers;



    @FXML
    void confirmer(ActionEvent event)  {
        try {
            LocalDate selectedDate = adddobfield.getValue();
            //Date dobEnfant = java.sql.Date.valueOf(selectedDate);
            //System.out.println(sqlDate);
            String telephoneText = addptelfield.getText();
            //int telephone = Integer.parseInt(telephoneText);
            String nom = addpnomfield.getText();
            String prenom = addpprenomfield.getText();
            String adresse = addpadressefield.getText();
            String email =addpemailfield.getText();
            String mdp = pfmdp.getText();
            String disc = addpdiscfield.getText();
            if (nom.isEmpty() || prenom.isEmpty() || adresse.isEmpty() ||
                    selectedDate == null || telephoneText.isEmpty() || email.isEmpty() ||
                    mdp.isEmpty() || disc.isEmpty()
                   ) {
                showAlert("Erreur de saisie", "Tous les champs sont obligatoires ", "Veuillez remplir tous les champs.");
                return;
            }
            java.sql.Date sqlDate = java.sql.Date.valueOf(selectedDate);
            int telephone = Integer.parseInt(telephoneText);

            if (!isValidName(nom)  ) {
                showAlert("Erreur de saisie", "nom invalide", "Le nom doit contenir que des lettres !");
                return;

            }
            if (nom.length() < 3  ) {
                showAlert("Erreur de saisie", "nom invalide", "Le nom doit au moins 3 caractéres !");
                return;

            }

            if (!isValidName(prenom)) {
                showAlert("Erreur de saisie", "prénom invalide", "Le prénom doit avoir minimum 3 caractères.");
                return;

            }

            if (!isValidPhoneNumber(telephoneText)) {

                showAlert("Erreur de saisie", "numéro de téléphone invalide", "Veuillez entrer un numéro de téléphone valide.");
                return;

            }

            if (!isValidEmail(email)) {
                showAlert("Erreur de saisie", "Adresse e-mail invalide", "Veuillez entrer une adresse e-mail valide.");
                return;
            }
            // Vérifier si l'email est unique
            if (!isEmailUnique(email)) {

                showAlert("Erreur de saisie", "Adresse e-mail existante !", "Cet email existe déjà. Veuillez en choisir un autre.");

                return;
            }
            // Calculer la date limite (il y a 23 ans à partir de la date actuelle)
            LocalDate dateLimite = LocalDate.now().minusYears(23);

            // Vérifier si la date de naissance est supérieure ou égale à la date limite
            if (selectedDate == null || selectedDate.isAfter(dateLimite)) {
                showAlert("Erreur de saisie", "Date de naissance invalide", "La date de naissance doit être supérieure ou égale à il y a 23 ans.");
                return;
            }
            if (!isValidPassword(mdp)) {

                showAlert("Erreur de saisie", "Mot de passe invalide", "Le mot de passe doit contenir au moins 8 caractères, une lettre majuscule, une lettre minuscule, un chiffre et un caractère spécial.");

                return;
            }
            Professeur pp = new Professeur(nom, prenom,adresse,sqlDate,telephone,email,mdp,disc);
            SP.ajouter(pp);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Validation");
            alert.setContentText("Person added succesfully");
            alert.showAndWait();
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/CrudAdmins.fxml"));
            Parent root=loader.load();
            addpadressefield.getScene().setRoot(root);

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Exeption");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void deconnexion(ActionEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Parent root=loader.load();
        addpdiscfield.getScene().setRoot(root);

    }
    @FXML
    void toUsers(ActionEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Parent root=loader.load();
        addpdiscfield.getScene().setRoot(root);

    }

    @FXML
    void totableprof(ActionEvent event) throws IOException{
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/CrudAdmins.fxml"));
        Parent root=loader.load();
        addpdiscfield.getScene().setRoot(root);

    }
    public static boolean isValidName(String name) {
        return name.matches("[a-zA-Z]+");
    }
    public static boolean isValidPhoneNumber(String phoneNumber) {
        // Un exemple simple de validation de numéro de téléphone
        // Vous pouvez ajouter des règles supplémentaires selon vos besoins
        return phoneNumber.matches("\\d{8}"); // Exemple : 10 chiffres
    }
    // Méthode pour valider une adresse e-mail
    public static boolean isValidEmail(String email) {
        // Utilisation d'une expression régulière simple pour valider l'adresse e-mail
        // Vous pouvez utiliser une expression régulière plus complexe pour une validation plus stricte si nécessaire
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
    // Méthode pour afficher une alerte
    public static void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public static boolean isValidPassword(String password) {
        // Au moins 8 caractères
        if (password.length() < 8) {
            return false;
        }

        // Au moins une lettre majuscule
        if (!password.matches(".*[A-Z].*")) {
            return false;
        }

        // Au moins une lettre minuscule
        if (!password.matches(".*[a-z].*")) {
            return false;
        }

        // Au moins un chiffre
        if (!password.matches(".*\\d.*")) {
            return false;
        }

        // Au moins un caractère spécial
        if (!password.matches(".*[!@#$%^&*()-_+=?/<>,.].*")) {
            return false;
        }

        return true;
    }
    public boolean isEmailUnique(String email) {
        // Charger le pilote JDBC et établir une connexion à la base de données
        try {
            // Préparer la requête SQL pour vérifier si l'email existe déjà
            String query = "SELECT COUNT(*) FROM utilisateurs WHERE login = ?";
            try (PreparedStatement statement = cnx.prepareStatement(query)) {
                statement.setString(1, email);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        // Si count est supérieur à zéro, cela signifie que l'email existe déjà
                        return count == 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer les exceptions appropriées selon votre application
        }
        return false; // En cas d'erreur ou de problème de connexion, retourner false par défaut
    }

@FXML
    public void toMatiere(MouseEvent mouseEvent) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AfficherMatierAdmin.fxml"));
    Parent root = loader.load();
    reclamation1.getScene().setRoot(root);

    }
@FXML
    public void toevent(ActionEvent actionEvent) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Ajout_Evenement.fxml"));
    Parent root = loader.load();
    reclamation1.getScene().setRoot(root);
    }
@FXML
    public void toReclamation(ActionEvent actionEvent) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AfficherReclamation.fxml"));
    Parent root = loader.load();

    reclamation1.getScene().setRoot(root);
    }
}

