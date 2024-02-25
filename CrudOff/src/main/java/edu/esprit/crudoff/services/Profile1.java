package edu.esprit.crudoff.services;
import edu.esprit.crudoff.entities.ParentE;
import edu.esprit.crudoff.entities.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Profile1 {
    private final ServiceUtilisateur PS = new ServiceUtilisateur();
    public ServiceParent spp = new ServiceParent();
    @FXML
    private Label labelnom;

    @FXML
    private Button modifierprofile1;

    @FXML
    private Label padresse;

    @FXML
    private Label pdob;

    @FXML
    private Label pdobenfant;

    @FXML
    private Label plogin;

    @FXML
    private Label pmdp;

    @FXML
    private Label pnom;

    @FXML
    private Label pnomenfant;

    @FXML
    private Label ppprenom;

    @FXML
    private Label pprenomenfant;

    @FXML
    private Button profile;

    @FXML
    private Label ptel;

    @FXML
    private Button evaluation;

    @FXML
    private Button evenements;

    @FXML
    private Button reclamation;

    @FXML
    private Button supprimer1;


    @FXML
    void initialize() throws IOException{

        CredentialsManager crd = new CredentialsManager();
        String[] crds = crd.loadCredentials();
        Utilisateur utilisateur = PS.getByLogin(crds[0]);
        System.out.println((ParentE) utilisateur);
        setUserData((ParentE) utilisateur);


    }
    public void supprimercompte11(int idUtilisateur) {
        try {
            // Appeler la fonction supprimer avec l'ID de l'utilisateur
            spp.supprimer(idUtilisateur);

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
        }
    }


    @FXML
    void tomodifier(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Profile.fxml"));
        Parent root = loader.load();
        ppprenom.getScene().setRoot(root);

    }
    public void setUserData(ParentE parentE) {

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
        pmdp.setText(parentE.getMdp());
        // Assurez-vous d'adapter cette méthode en fonction de votre modèle d'utilisateur

    }

    @FXML
    public void supprimercompte1(ActionEvent actionEvent) {
            String ll = plogin.getText();
            int idUtilisateur = spp.getIdUtilisateurParLogin(ll);
            supprimercompte11(idUtilisateur);
            viderLabels();
            System.out.println("spprimer3");
        }

    public void viderLabels() {
        pnom.setText("");
        ppprenom.setText("");
        pnomenfant.setText("");
        pprenomenfant.setText("");
        pdob.setText("");
        pdobenfant.setText("");
        ptel.setText("");
        plogin.setText("");
        pmdp.setText("");
        padresse.setText("");
        // Ajoutez d'autres labels à vider si nécessaire
    }



}

