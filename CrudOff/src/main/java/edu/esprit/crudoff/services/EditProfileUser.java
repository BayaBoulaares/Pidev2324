package edu.esprit.crudoff.services;


import edu.esprit.crudoff.entities.ParentE;
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
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.lang.Integer.parseInt;

public class EditProfileUser {

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
        CredentialsManager crd = new CredentialsManager();
        String[] crds = crd.loadCredentials();
        //user = PS.getByLogin(crds[0]);
        try {
            PS.supprimer(parseInt(crds[1]));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Validation");
            alert.setContentText("Person mis à jours avec succes");
            alert.showAndWait();
            FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/ProfileUser.fxml"));
            Parent root=loader.load();
            editadresse.getScene().setRoot(root);

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Exeption");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
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
        //editdob.setNu("");
        editnomenfant.setText("");
        edittel.setText("");
        //editdobenfant.setText("");
        editadresse.setText("");

        // Ajoutez d'autres labels à vider si nécessaire
    }/*public void setUserData(ParentE parentE) {

        pnom.setText(parentE.getNom());
        ppprenom.setText(parentE.getPrenom());
        pnomenfant.setText(parentE.getNomE());
        pprenomenfant.setText(parentE.getPrenomE());
        // Convertir le long en objet java.util.Date
        Date dateNaissance = new Date(parentE.getDateNaissance().getTime());

        // Formater la date pour l'afficher dans le composant TextArea
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dateString = sdf.format(dateNaissance);

        // Définir la date formatée dans le composant TextArea
        pdob.setText(dateString);
        Date dateNaissanceEn = new Date(parentE.getDateNaissanceE().getTime());

        // Formater la date pour l'afficher dans le composant TextArea
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
        String dateString2 = sdf.format(dateNaissanceEn);

        // Définir la date formatée dans le composant TextArea
        pdobenfant.setText(dateString2);
        ptel.setText(String.valueOf(parentE.getTel()));
        padresse.setText(parentE.getAdresse());
        plogin.setText(parentE.getLogin());
        String imagePath = String.valueOf(parentE.getImage());
        System.out.println(imagePath);
        String image = parentE.getImage();
        System.out.println(image);
        Image image2 = new Image(new File(imagePath).toURI().toString());
        parentimage.setImage(image2);*/

    }



