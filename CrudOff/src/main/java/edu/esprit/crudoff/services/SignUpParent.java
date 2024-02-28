package edu.esprit.crudoff.services;

import edu.esprit.crudoff.entities.ParentE;
import edu.esprit.crudoff.utilis.DataSource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.regex.Pattern;

public class SignUpParent {
    private final ServiceUtilisateur SU = new ServiceUtilisateur();
    Connection cnx = DataSource.getInsatnce().getConnection();
    @FXML
    private ImageView parentimage;

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
    private PasswordField mdpparent;

    @FXML
    private TextField nomenfant;

    @FXML
    private TextField nomparent;

    @FXML
    private TextField prenomenfant;

    @FXML
    private PasswordField confirmmdpparent;
    private String imagePath;

    @FXML
    private TextField prenomparent;

    @FXML
    private TextField telparent;
    private ServiceParent serviceParent = new ServiceParent();

    @FXML
    void addenfant(ActionEvent event) throws IOException {
        try {

            System.out.println("bb");
            String cmdp = confirmmdpparent.getText();
            // Récupérer les valeurs des champs de saisie
            String nomParent = nomparent.getText();
            String prenomParent = prenomparent.getText();
            String adresseParent = adresseparent.getText();
            LocalDate dobParentValue = dobparent.getValue();
            //Date dobParent = java.sql.Date.valueOf(dobParentValue);
            String telephoneText = telparent.getText();
            String emailParent = emailparent.getText();
            String mdpParent = mdpparent.getText();
            String nomEnfant = nomenfant.getText();
            String prenomEnfant = prenomenfant.getText();
            LocalDate dobEnfantValue = dobenfant.getValue();
            //Date dobEnfant = java.sql.Date.valueOf(dobEnfantValue);

            //Controle de saisie
            if (nomParent.isEmpty() || prenomParent.isEmpty() || adresseParent.isEmpty() ||
                    dobParentValue == null || telephoneText.isEmpty() || emailParent.isEmpty() ||
                    mdpParent.isEmpty() || nomEnfant.isEmpty() || prenomEnfant.isEmpty() ||
                    dobEnfantValue == null || cmdp.isEmpty()
            ) {
                showAlert("Erreur de saisie", "Tous les champs sont obligatoires ", "Veuillez remplir tous les champs.");
                return;
            }
            java.sql.Date sqlDate = java.sql.Date.valueOf(dobParentValue);
            java.sql.Date sqlDate2 = java.sql.Date.valueOf(dobEnfantValue);
            int telephone = Integer.parseInt(telephoneText);

            if (!isValidName(nomParent)  ) {
                showAlert("Erreur de saisie", "nom invalide", "Le nom doit contenir que des lettres !");
                return;

            }
            if (nomParent.length() < 3  ) {
                showAlert("Erreur de saisie", "nom invalide", "Le nom doit au moins 3 caractéres !");
                return;

            }

            if (!isValidName(prenomParent)) {
                showAlert("Erreur de saisie", "prénom invalide", "Le prénom doit avoir minimum 3 caractères.");
                return;

            }
            if (prenomParent.length() < 3  ) {
                showAlert("Erreur de saisie", "nom invalide", "Le nom doit au moins 3 caractéres !");
                return;

            }
            if (!isValidName(nomEnfant)  ) {
                showAlert("Erreur de saisie", "nom invalide", "Le nom doit contenir que des lettres !");
                return;

            }
            if (nomEnfant.length() < 3  ) {
                showAlert("Erreur de saisie", "nom invalide", "Le nom doit au moins 3 caractéres !");
                return;

            }

            if (!isValidName(prenomEnfant)) {
                showAlert("Erreur de saisie", "prénom invalide", "Le prénom doit avoir minimum 3 caractères.");
                return;

            }
            if (prenomEnfant.length() < 3  ) {
                showAlert("Erreur de saisie", "nom invalide", "Le nom doit au moins 3 caractéres !");
                return;

            }
            if (adresseParent.length() < 10  ) {
                showAlert("Erreur de saisie", "Adresse invalide", "L'adresse doit au moins 10 caractéres !");
                return;

            }


            if (!isValidPhoneNumber(telephoneText)) {

                showAlert("Erreur de saisie", "numéro de téléphone invalide", "Veuillez entrer un numéro de téléphone valide.");
                return;

            }

            if (!isValidEmail(emailParent)) {
                showAlert("Erreur de saisie", "Adresse e-mail invalide", "Veuillez entrer une adresse e-mail valide.");
                return;
            }
            // Vérifier si l'email est unique
            if (!isEmailUnique(emailParent)) {

                showAlert("Erreur de saisie", "Adresse e-mail existante !", "Cet email existe déjà. Veuillez en choisir un autre.");

                return;
            }
            // Calculer la date limite (il y a 23 ans à partir de la date actuelle)
            //LocalDate dateLimite = LocalDate.now().minusYears(23);

            // Vérifier si la date de naissance est supérieure ou égale à la date limite
            if (dobEnfantValue != null && dobParentValue != null && dobEnfantValue.isBefore(dobParentValue)) {
                showAlert("Erreur", "Date de naissance incorrecte", "La date de naissance du parent doit être supérieure à la date de naissance de l'enfant.");
                return;

            }
            if (!isValidPassword(mdpParent)) {

                showAlert("Erreur de saisie", "Mot de passe invalide", "Le mot de passe doit contenir au moins 8 caractères, une lettre majuscule, une lettre minuscule, un chiffre et un caractère spécial.");

                return;
            }
            if (!mdpParent.equals(cmdp)) {
                showAlert("Erreur", "Confirmation de mot de passe incorrecte", "Les mots de passe ne correspondent pas.");
                return;
            } else {
                // Mot de passe confirmé, vous pouvez effectuer d'autres actions ici
                System.out.println("Mot de passe confirmé" );
            }
            /*if (imagePath.isEmpty() || imagePath == null) {
                showAlert("Veuillez choisir une image !");
                return;
            }*/


            // Créer un nouvel objet Parent avec les données récupérées
            ParentE parent = new ParentE(nomParent, prenomParent, adresseParent, sqlDate, telephone, emailParent, mdpParent, nomEnfant, prenomEnfant, sqlDate2,imagePath);

            // Ajouter le parent à la base de données en utilisant le service Parent

            serviceParent.ajouter(parent);

            // Afficher un message de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Vous êtes ajouté avec succès !");
            alert.showAndWait();
            System.out.println(parent);
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
            Parent root=loader.load();
            nomenfant.getScene().setRoot(root);

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
    @FXML
    void selectimage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(((Button) event.getSource()).getScene().getWindow());
        if (selectedFile != null) {
            try {
                Image image = new Image(selectedFile.toURI().toString()); // Corrected line
                parentimage.setImage(image);
                imagePath = selectedFile.getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

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


}