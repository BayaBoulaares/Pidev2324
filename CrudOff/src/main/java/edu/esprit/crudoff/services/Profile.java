package edu.esprit.crudoff.services;

import edu.esprit.crudoff.entities.ParentE;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static java.lang.Integer.parseInt;

public class Profile {
    //private final ServiceUtilisateur PS = new ServiceUtilisateur();

    public ServiceParent PS = new ServiceParent();
    public ServiceUtilisateur ps = new ServiceUtilisateur();

    @FXML
    private Button edit;

    @FXML
    private TextArea editadresse;

    @FXML
    private DatePicker editdob;

    @FXML
    private DatePicker editdobenfant;


    @FXML
    private TextArea editlogin;

    @FXML
    private TextArea editmdp;

    @FXML
    private TextArea editnom;

    @FXML
    private TextArea editnomenfant;

    @FXML
    private TextArea editprenom;

    @FXML
    private TextArea editprenomenfant;

    @FXML
    private TextArea edittel;

    @FXML
    private Button evaluation;

    @FXML
    private Button evenements;

    @FXML
    private Label labelnom;

    @FXML
    private Button modifierprofile;

    @FXML
    private Button profile;

    @FXML
    private Button reclamation;

    @FXML
    private Button supprimer;
    private ParentE user ;
    public void modifiercompte11(int idUtilisateur) {
        /*try {
            // Appeler la fonction supprimer avec l'ID de l'utilisateur
            spp.modifier(idUtilisateur);

            // Afficher un message de confirmation
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Le profil de l'utilisateur a été supprimé avec succès.");
            alert.showAndWait();
            System.out.println("spprimer1");
        } catch (SQLException e) {
            // Gérer les erreurs de suppression du profil
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Une erreur est survenue lors de la suppression du profil : " + e.getMessage());
            alert.showAndWait();
        }*/
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



    }

    @FXML
    void supprimercompte(ActionEvent event) {

    }
    @FXML
    void modifiercompte(ActionEvent event) throws SQLException {

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
              String mdp = editmdp.getText();
             String nomEnfant = editnomenfant.getText();
             String prenomEnfant = editprenomenfant.getText();
             LocalDate localDate2 = editdobenfant.getValue();
            String dateString2 = localDate2.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            java.sql.Date dateNaissance2 = java.sql.Date.valueOf(dateString2);
              CredentialsManager crd = new CredentialsManager();
              String[] crds = crd.loadCredentials();

              ParentE parent1 = new ParentE(parseInt(crds[1]),nom, prenom, adresse, dateNaissance,tel, nomEnfant, prenomEnfant,dateNaissance2);
              System.out.println("this is parent"+parent1);
              PS.modifier(parent1);
              System.out.println(parent1);

        } catch (SQLException e) {

        }








        // Créer un nouvel objet Professeur avec les données récupérées
        /*Professeur ppp = new Professeur(idy,nom, prenom, adresse, tel, email,mdp, discipline);
        System.out.println(ppp);
        // Appeler la méthode de modification dans votre service
        sl.modifier(ppp);*/


    }

        // Autres attributs et méthodes
    public void setUserData(ParentE parentE) {


        editnom.setText(parentE.getNom());
        editprenom.setText(parentE.getPrenom());
        editadresse.setText(parentE.getNomE());
        editnomenfant.setText(parentE.getPrenomE());
        editprenomenfant.setText(parentE.getPrenomE());
        // Convertir le long en objet java.util.Date
        Date dateNaissance = new Date(parentE.getDateNaissance().getTime());

        // Formater la date pour l'afficher dans le composant TextArea
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dateString = sdf.format(dateNaissance);

        // Définir la date formatée dans le composant TextArea
        //editdobenfant.setText(dateString);
        //Date dateNaissanceEn = new Date(parentE.getDateNaissanceE().getTime());

        // Formater la date pour l'afficher dans le composant TextArea
        //SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
        //String dateString2 = sdf.format(dateNaissanceEn);

        // Définir la date formatée dans le composant TextArea
        //editdob.setText(dateString2);

        edittel.setText(String.valueOf(parentE.getTel()));
        editmdp.setText(parentE.getAdresse());
        editlogin.setText(parentE.getLogin());

        // Assurez-vous d'adapter cette méthode en fonction de votre modèle d'utilisateur

    }

}
