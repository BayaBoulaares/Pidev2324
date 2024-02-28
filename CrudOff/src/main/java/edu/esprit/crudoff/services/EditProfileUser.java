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
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class EditProfileUser {
    Connection cnx = DataSource.getInsatnce().getConnection();


    @FXML
    private TextArea editadresse;
    @FXML
    private Button selectimage;

    private String imagePath;

    @FXML
    private DatePicker editdob;

    @FXML
    private DatePicker editdobenfant;

    @FXML
    private TextArea editnom;

    @FXML
    private TextArea editnomenfant;

    @FXML
    private TextArea editprenom;

    @FXML
    private TextArea editprenomenfant;
    @FXML
    private ImageView parentimage;


    @FXML
    private TextArea edittel;

    @FXML
    private Button evaluation;

    @FXML
    private Button evenements;

    @FXML
    private Button modifierpr;

    @FXML
    private Button modifierprofile;

    @FXML
    private Label padresse;

    @FXML
    private Button profile;

    @FXML
    private Label ptel;

    @FXML
    private Button reclamation;

    @FXML
    private Button supprimer;
    public ServiceParent PS = new ServiceParent();
    public ServiceUtilisateur ps = new ServiceUtilisateur();
    private ProfileUser pr = new ProfileUser();
    private ParentE user ;
    /*@FXML
    void modifiercompte(ActionEvent event) {
        try{

            String nom = editnom.getText();
            System.out.println(nom);
            String prenom = editprenom.getText();
            System.out.println(prenom);
            String adresse = editadresse.getText();
            LocalDate localDate = editdob.getValue();
            String dateString = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            java.sql.Date dateNaissance = java.sql.Date.valueOf(dateString);
            int tel = parseInt(edittel.getText());
            String nomEnfant = editnomenfant.getText();
            String prenomEnfant = editprenomenfant.getText();
            LocalDate localDate2 = editdobenfant.getValue();
            String dateString2 = localDate2.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            java.sql.Date dateNaissance2 = java.sql.Date.valueOf(dateString2);
            String image = String.valueOf(parentimage.getImage());
            CredentialsManager crd = new CredentialsManager();
            String[] crds = crd.loadCredentials();
            ParentE parent1 = new ParentE(parseInt(crds[1]),nom, prenom, adresse, dateNaissance,tel, nomEnfant, prenomEnfant,dateNaissance2,image);
            System.out.println("this is parent"+parent1);
            PS.modifier(parent1);
            System.out.println(parent1);

        } catch (SQLException e) {

        }


    }*/

    @FXML
    void supprimercompte(ActionEvent event) throws IOException {
        //CredentialsManager crd = new CredentialsManager();
        String[] crds = CredentialsManager.loadCredentials();
        //user = PS.getByLogin(crds[0]);

            PS.supprimer(parseInt(crds[1]));
        viderLabels();
        System.out.println("spprimer3");
    }



    @FXML
    void initialize() {
        CredentialsManager crd = new CredentialsManager();
        String[] crds = crd.loadCredentials();
        user = PS.getByLogin(crds[0]);
        System.out.println(user);
        editnom.setText(user.getNom());
        editprenom.setText(user.getPrenom());
        editadresse.setText(user.getPrenom());

        // Supposons que user.getDateNaissance() retourne un objet de type java.util.Date
        java.util.Date dateNaissance = user.getDateNaissance();
        // Conversion de java.util.Date en java.sql.Date
        java.sql.Date sqlDateNaissance = new java.sql.Date(dateNaissance.getTime());

        // Conversion de java.sql.Date en LocalDate (Java 8+)
        LocalDate localDateNaissance = sqlDateNaissance.toLocalDate();

        // Définition de la valeur du DatePicker
        editdob.setValue(localDateNaissance);

        // Supposons que user.getDateNaissance() retourne un objet de type java.util.Date
        // java.util.Date dateNaissancekid =  user.getDateNaissanceE();
        // Conversion de java.util.Date en java.sql.Date
        java.sql.Date sqlDateNaissancekid = new java.sql.Date(dateNaissance.getTime());

        // Conversion de java.sql.Date en LocalDate (Java 8+)
        LocalDate localDateNaissancekid = sqlDateNaissance.toLocalDate();

        // Définition de la valeur du DatePicker
        editdobenfant.setValue(localDateNaissancekid);
        //int tel = Integer.parseInt(edittel.getText());
        edittel.setText(String.valueOf(user.getTel()));
        editnomenfant.setText(user.getNomE());
        editprenomenfant.setText(user.getPrenomE());
        String imagePath = String.valueOf(user.getImage());
        System.out.println(imagePath);
        String image = user.getImage();
        System.out.println(image);
        Image image2 = new Image(new File(imagePath).toURI().toString());
        parentimage.setImage(image2);




    }



    public void déconnxion(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/LetsGetStarted.fxml"));
        Parent root=loader.load();
        editnom.getScene().setRoot(root);
    }

    public void modifiercompte(ActionEvent actionEvent) {
        ParentE parent2 = null;
        try{
            String nom = editnom.getText();
            System.out.println(nom);
            String prenom = editprenom.getText();
            System.out.println(prenom);
            String adresse = editadresse.getText();
            LocalDate localDate = editdob.getValue();
            String dateString = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            java.sql.Date dateNaissance = java.sql.Date.valueOf(dateString);
            String teltext = edittel.getText();
            int tel = parseInt(teltext);
            String nomEnfant = editnomenfant.getText();
            String prenomEnfant = editprenomenfant.getText();
            LocalDate localDate2 = editdobenfant.getValue();
            String dateString2 = localDate2.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            java.sql.Date dateNaissance2 = java.sql.Date.valueOf(dateString2);
            String image = String.valueOf(parentimage.getImage());
            CredentialsManager crd = new CredentialsManager();
            String[] crds = crd.loadCredentials();
            parent2 = PS.getByLogin(crds[0]);
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
            if (prenom.length() < 3  ) {
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
            if (adresse.length() < 10  ) {
                showAlert("Erreur de saisie", "Adresse invalide", "L'adresse doit au moins 10 caractéres !");
                return;

            }


            if (!isValidPhoneNumber(teltext)) {

                showAlert("Erreur de saisie", "numéro de téléphone invalide", "Veuillez entrer un numéro de téléphone valide.");
                return;

            }


            // Calculer la date limite (il y a 23 ans à partir de la date actuelle)
            //LocalDate dateLimite = LocalDate.now().minusYears(23);

            // Vérifier si la date de naissance est supérieure ou égale à la date limite
            if (localDate2 != null && localDate != null && localDate2.isBefore(localDate)) {
                showAlert("Erreur", "Date de naissance incorrecte", "La date de naissance du parent doit être supérieure à la date de naissance de l'enfant.");

            }


            ParentE parent1 = new ParentE(parent2.getId(),nom, prenom, adresse, dateNaissance,tel, nomEnfant, prenomEnfant,dateNaissance2,image);
            System.out.println("this is parent"+parent1);
            PS.modifier(parent1);
            System.out.println(parent1);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Validation");
            alert.setContentText("Person mis à jours avec succes");
            alert.showAndWait();
            System.out.println("this is parent"+parent1);
            /*FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/ProfileUser.fxml"));
            Parent root=loader.load();
            editadresse.getScene().setRoot(root);*/

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Exeption");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }



    @FXML

    void reselectimage(ActionEvent event) {
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
    public void viderLabels() {
        editnom.setText("");
        editprenom.setText("");
        editdob.setValue(null);
        editadresse.setText("");
        edittel.setText("");
        editnomenfant.setText("");
        editprenomenfant.setText("");
        editdobenfant.setValue(null);


        // Ajoutez d'autres labels à vider si nécessaire
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



