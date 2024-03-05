package edu.esprit.services;

import edu.esprit.controller.AcceuilMatiereC;
import edu.esprit.controller.AfficherEvents;
import edu.esprit.controller.AjouterReclamation;
import edu.esprit.controller.MatiersAffichage;
import edu.esprit.entities.ParentE;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Integer.parseInt;

public class ProfileUser {

    @FXML
    private AnchorPane parent;

    @FXML
    private HBox hbox2;



    @FXML
    private Button evaluation;

    @FXML
    private Button evenements;

    @FXML
    private Button modifierprofile;

    @FXML
    private Label padresse;

    @FXML
    private Label pdob;

    @FXML
    private Label pdobenfant;

    @FXML
    private Label plogin;

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
    private Button reclamation;

    @FXML
    private Button supprimer;
    @FXML
    private ImageView parentimage;

    private final ServiceParent ps = new ServiceParent();
    private ParentE ep;
    public void getPe(ParentE ep)
    {
        this.ep=ep;
    }

    @FXML
    void supprimercompte(ActionEvent event) throws IOException {

        String[] crds = CredentialsManager.loadCredentials();
        //user = PS.getByLogin(crds[0]);

        ps.supprimer(parseInt(crds[1]));
        viderLabels();
        System.out.println("spprimer3");
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/LetsGetStarted.fxml"));
        Parent root=loader.load();
        ptel.getScene().setRoot(root);

    }
    @FXML
    void initialize() throws IOException{

        CredentialsManager crd = new CredentialsManager();
        String[] crds = crd.loadCredentials();

        ParentE utilisateur = ps.getByLogin(crds[0]);
        System.out.println(utilisateur);
        setUserData( utilisateur);


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
        String imagePath = String.valueOf(parentE.getImage());
        System.out.println(imagePath);
        String image = parentE.getImage();
        System.out.println(image);
        Image image2 = new Image(new File(imagePath).toURI().toString());
        parentimage.setImage(image2);

    }

    @FXML
    void tomodifier(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EditProfileUser.fxml"));
        Parent root = loader.load();
        ppprenom.getScene().setRoot(root);


    }

    public void deconnexion(ActionEvent actionEvent)throws IOException  {
        CredentialsManager.clearCredentials();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Parent root = loader.load();
        ppprenom.getScene().setRoot(root);

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
        padresse.setText("");
        // Ajoutez d'autres labels à vider si nécessaire
    }
    @FXML
    public void toaccueil(ActionEvent actionEvent) throws IOException{
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/DasboardUser.fxml"));
        Parent root=loader.load();
        pdob.getScene().setRoot(root);
    }

    public void toMatiere(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/acceuilMatiere.fxml"));
        Parent root=loader.load();
        AcceuilMatiereC am=loader.getController();
        am.getPe(ep);
        pdob.getScene().setRoot(root);

    }

    public void toRecl(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AjouterReclamation.fxml"));
        Parent root = loader.load();
        AjouterReclamation controller = loader.getController();

      //  controller.setProftoGet(ep);
        pdob.getScene().setRoot(root);
    }

    public void toEvent(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Liste_Evenement.fxml"));
        Parent root = loader.load();
        AfficherEvents controller = loader.getController();

        controller.initialize();
        pdob.getScene().setRoot(root);
    }
}
