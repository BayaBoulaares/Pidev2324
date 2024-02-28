package edu.esprit.crudoff.services;

import edu.esprit.crudoff.entities.Professeur;
import edu.esprit.crudoff.utilis.DataSource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;


public class EditProfs {
    Connection cnx = DataSource.getInsatnce().getConnection();


    @FXML
    private PasswordField pmdp;

    @FXML
    private DatePicker editdob;
        @FXML
        private Button accueil;

        @FXML
        private Button adminform;

        @FXML
        private Button bconfirm;

        @FXML
        private Button breturn;

        @FXML
        private TextField editpdiscfield;

        @FXML
        private TextField editpadressefield;

        @FXML
        private DatePicker editdobfield;

        @FXML
        private TextField editpemailfield;

        @FXML
        private TextField editpnomfield;

        @FXML
        private TextField editpprenomfield;

        @FXML
        private TextField editptelfield;

        @FXML
        private Button proform;

        @FXML
        private Button users;

    // Dans le contrôleur du formulaire (FormController)
    public void fillFormData(Professeur p) {
        // Remplir les champs du formulaire avec les informations de l'utilisateur
        editpnomfield.setText(p.getNom());
        editpprenomfield.setText(p.getPrenom());
        editpadressefield.setText(p.getAdresse());
        editpdiscfield.setText(p.getDiscpline());//bel instanceOf
        editptelfield.setText(String.valueOf(p.getTel()));
        editpemailfield.setText(p.getLogin());
        //editpmdp.setText(p.getMdp());


        // Remplir d'autres champs selon les besoins
    }
    @FXML
    void confirmchanges(ActionEvent event) throws SQLException, IOException {
        try {
        ServiceProfesseur sl = new ServiceProfesseur();
        System.out.println("baya");
        String nom = editpnomfield.getText();
        String prenom = editpprenomfield.getText();
        String adresse = editpadressefield.getText();
        String discipline = editpdiscfield.getText();
        LocalDate localDate = editdob.getValue();
        String dateString = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        java.sql.Date dateNaissance = java.sql.Date.valueOf(dateString);
        System.out.println(discipline);
        String email = editpemailfield.getText();
        int idy = sl.recupereId(email);
        System.out.println(idy);
        String mdp = pmdp.getText();
        System.out.println(mdp);
        // Tentative de conversion du numéro de téléphone en entier
            String telephoneText = editptelfield.getText();
        int tel = Integer.parseInt(telephoneText);
        // Créer un nouvel objet Professeur avec les données récupérées

            java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);


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
            // Calculer la date limite (il y a 23 ans à partir de la date actuelle)
            LocalDate dateLimite = LocalDate.now().minusYears(23);

            // Vérifier si la date de naissance est supérieure ou égale à la date limite
            if (localDate == null || localDate.isAfter(dateLimite)) {
                showAlert("Erreur de saisie", "Date de naissance invalide", "La date de naissance doit être supérieure ou égale à il y a 23 ans.");
                return;
            }
            if (!isValidPassword(mdp)) {

                showAlert("Erreur de saisie", "Mot de passe invalide", "Le mot de passe doit contenir au moins 8 caractères, une lettre majuscule, une lettre minuscule, un chiffre et un caractère spécial.");

                return;
            }
        Professeur ppp = new Professeur(idy,nom, prenom, adresse,dateNaissance, tel, email,mdp, discipline);
        System.out.println(ppp);
        // Appeler la méthode de modification dans votre service
            sl.modifier(ppp);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Validation");
            alert.setContentText("Le professeur est mis à jour avec succès updated ");
            alert.showAndWait();

           FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/CrudAdmins.fxml"));
        Parent root=loader.load();
        editpdiscfield.getScene().setRoot(root);


        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Exeption");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }












    public void deconnexion(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Parent root=loader.load();
        editpdiscfield.getScene().setRoot(root);
    }



    @FXML
    void toprof(ActionEvent event)throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/CrudAdmins.fxml"));
        Parent root=loader.load();
        editpdiscfield.getScene().setRoot(root);

    }

    @FXML
    void tousers(ActionEvent event) throws IOException{
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/CrudAdmins.fxml"));
        Parent root=loader.load();
        editpdiscfield.getScene().setRoot(root);

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


