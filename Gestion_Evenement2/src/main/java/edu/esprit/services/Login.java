package edu.esprit.services;


import edu.esprit.entities.Administrateur;
import edu.esprit.entities.ParentE;
import edu.esprit.entities.Professeur;
import edu.esprit.entities.Utilisateur;
import edu.esprit.utils.DataSource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Login {
    private final ServiceUtilisateur PS = new ServiceUtilisateur();
    Connection cnx = DataSource.getInstance().getCnx();
    private final ServiceParent sp =new ServiceParent();


    @FXML
    private TextField loginuser;

    @FXML
    private PasswordField mdpuser;

    @FXML
    private RadioButton rememberCheckBox;
    @FXML
    private Button cnxgoogle;



    @FXML
    private Button connexion;

    @FXML
    private Button creercompte;
    // Définition des placeholders

    //loginuser.setPromptText("Entrez votre nom d'utilisateur");
    @FXML
    private Button mdpoublier;

    private static final String CLIENT_ID = "YOUR_CLIENT_ID";
    private static final String CLIENT_SECRET = "YOUR_CLIENT_SECRET";
    private static final String REDIRECT_URI = "YOUR_REDIRECT_URI";




    @FXML
    void addaccount(ActionEvent event) {
        try {
            // Charger le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SignUpParent.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Obtenir la référence à la scène actuelle à partir de l'événement
            Scene currentScene = mdpuser.getScene();

            // Obtenir la référence à la fenêtre actuelle à partir de la scène
            Stage stage = (Stage) currentScene.getWindow();

            // Définir la nouvelle scène sur la fenêtre
            stage.setScene(scene);

            // Afficher la nouvelle scène
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer l'exception, par exemple afficher une boîte de dialogue d'erreur
        }

    }


    /*void seconnecter(ActionEvent event) throws IOException {
        String login = loginuser.getText();
        String motDePasse = mdpuser.getText();

        Utilisateur utilisateur = PS.getByLogin(login);

        if (utilisateur != null && utilisateur.getMdp().equals(motDePasse)) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Validation");
            alert.setContentText("Connexion réussie");
            alert.showAndWait();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/UserInterface.fxml"));
            Parent root = loader.load();
            loginuser.getScene().setRoot(root);

        } else {
            // Login ou mot de passe incorrect
            // Affichez un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de connexion");
            alert.setHeaderText(null);
            alert.setContentText("Login ou mot de passe incorrect !");
            alert.showAndWait();
        }
    }*/
    //@FXML
    /*void seconnecter(ActionEvent event) throws IOException {
        String login = loginuser.getText();
        String motDePasse = mdpuser.getText();

        Utilisateur utilisateur = PS.getByLogin(login);
        System.out.println(utilisateur);

        if (utilisateur != null) {
            // Vérification du mot de passe uniquement si l'utilisateur est trouvé dans la base de données
            if (utilisateur.getMdp().equals(motDePasse)) {
                // Vérification du rôle de l'utilisateur
                if (utilisateur instanceof Administrateur) {
                    showAlertAndWait("Connexion réussie", "Validation");
                    //redirectToFXML("/fxml/DashboardAdmin.fxml");
                    FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/DashobardAdmin.fxml"));
                    Parent root=loader.load();
                    // loginuser.getScene().setRoot(root);
                } else if (utilisateur instanceof ParentE) {
                    // Redirection vers l'interface utilisateur normale
                    showAlertAndWait("Connexion réussie", "Validation");
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Profile1.fxml"));
                    Parent root = loader.load();

                    // Accéder au contrôleur de l'interface Profile
                    Profile1 profileController = loader.getController();
                    // Récupérer les données de l'utilisateur et les afficher dans l'interface Profile
                    profileController.setUserData((ParentE) utilisateur);
                    loginuser.getScene().setRoot(root);
                }
            } else {
                // Mot de passe incorrect
                showAlertAndWait("Mot de passe incorrect !", "Erreur de connexion");
            }
        } else {
            // Utilisateur non reconnu
            showAlertAndWait("Login incorrect !", "Erreur de connexion");
        }
    }*/
    @FXML
    void seconnecter(ActionEvent event) throws IOException {
        String login = loginuser.getText();
        String motDePasse = mdpuser.getText();
        Utilisateur utilisateur = PS.getByLogin(login);


        if (utilisateur != null) {
            // Vérification du mot de passe uniquement si l'utilisateur est trouvé dans la base de données
            if (utilisateur.getMdp().equals(motDePasse))  {
                // Vérification du rôle de l'utilisateur
                if (utilisateur instanceof Administrateur) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Succès");
                    alert.setHeaderText(null);
                    alert.setContentText("Connexion réussie !");
                    alert.showAndWait();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DashobardAdmin.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                    CredentialsManager.clearCredentials();
                    CredentialsManager.saveCredentials(String.valueOf(utilisateur.getId()),loginuser.getText(), mdpuser.getText(),String.valueOf(rememberCheckBox.isSelected()));

                } else if (utilisateur instanceof ParentE) {
                    ParentE pp = (ParentE) utilisateur;
                    // Redirection vers l'interface utilisateur normale
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Succès");
                    alert.setHeaderText(null);
                    alert.setContentText("Connexion réussie !");
                    alert.showAndWait();
                    FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/fxml/DasboardUser.fxml"));
                    Parent root2 = loader2.load();
                    DashboardUser udm=loader2.getController();
                    udm.getPe(pp);
                    udm.setUserId(pp.getId());
                    loginuser.getScene().setRoot(root2);
                    CredentialsManager.clearCredentials();
                    CredentialsManager.saveCredentials(String.valueOf(utilisateur.getId()),loginuser.getText(), mdpuser.getText(),String.valueOf(rememberCheckBox.isSelected()));

                }
                else  {
                    Professeur pp = (Professeur) utilisateur;
                    // Redirection vers l'interface utilisateur normale
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Succès");
                    alert.setHeaderText(null);
                    alert.setContentText("Connexion réussie !");
                    alert.showAndWait();
                    FXMLLoader loader3 = new FXMLLoader(getClass().getResource("/fxml/AfficherMatiere.fxml"));
                    Parent root3 = loader3.load();
                    mdpuser.getScene().setRoot(root3);
                    CredentialsManager.clearCredentials();
                    CredentialsManager.saveCredentials(String.valueOf(utilisateur.getId()),loginuser.getText(), mdpuser.getText(),String.valueOf(rememberCheckBox.isSelected()));

                }
            } else {
                // Mot de passe incorrect
                showAlertAndWait("Mot de passe incorrect !", "Erreur de connexion");
            }
        } else {
            // Utilisateur non reconnu
            showAlertAndWait("Login incorrect !", "Erreur de connexion");
        }
    }


    private void showAlertAndWait(String contentText, String title) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public void rememberCheckBox(ActionEvent actionEvent) {
    }

    /*public void redirectToFXML(String fxmlPath) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        loginuser.getScene().setRoot(root);
    }*/
    @FXML
    void tochangemdp(ActionEvent event) throws IOException{
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/fxml/SendSms.fxml"));
        Parent root2 = loader2.load();
        loginuser.getScene().setRoot(root2);
    }

    @FXML
    void seconnecteravecgoogle(ActionEvent event) throws GeneralSecurityException, IOException {

        String gClientId = "452055391469-0iqs7dmg959d7ro34t967b799b335406.apps.googleusercontent.com";
        String gRedir = "http://localhost:8080";
        String gScope = "https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email";
        String gSecret = "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";
        OAuthAuthenticator auth = new OAuthGoogleAuthenticator(gClientId, gRedir, gSecret, gScope);
        auth.startLogin(new AuthCallback() {
            @Override
            public void onLoginSuccess(JSONObject js) {
                try {

                    // Handle successful login
                    System.out.println("Login successful. JSON data: " + js.toString());
                    String userName = js.getString("given_name");
                    String userFamilyName = js.getString("family_name");
                    String userEmail = js.getString("email");
                    System.out.println("User Name: " + userName);
                    System.out.println("User email: " + userEmail);
                    ParentE parentE = sp.getByLogin(userEmail);
                    if (parentE != null) {

                        CredentialsManager.clearCredentials();
                        CredentialsManager.saveCredentials(String.valueOf(parentE.getId()), userEmail, "", "true");
                        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/fxml/DasboardUser.fxml"));
                        Parent root2 = loader2.load();
                        loginuser.getScene().setRoot(root2);

                    } else {
                        // Construction de la requête d'insertion
                        String sql = "INSERT INTO utilisateurs (nom,prenom,login,role) VALUES (?,?,?,?)";

                        // Création de la déclaration PreparedStatement avec la requête SQL
                        PreparedStatement statement = null;

                        statement = cnx.prepareStatement(sql);
                        statement.setString(1, userName);
                        statement.setString(2, userFamilyName);
                        statement.setString(3, userEmail);
                        statement.setString(4, "Parent");
                        System.out.println("linaaaaaaaaa");
                        int rowsInserted = statement.executeUpdate();

                        //sp.ajouter(pp);


                        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/fxml/DasboardUser.fxml"));
                        Parent root2 = loader2.load();
                        loginuser.getScene().setRoot(root2);
                        CredentialsManager.clearCredentials();

                        CredentialsManager.saveCredentials(String.valueOf(5), userEmail, "", "true");




                    }
                }catch (SQLException | IOException e) {
                    throw new RuntimeException(e);
                }


            }

            @Override
            public void onLoginFailure(Exception e) {
                // Handle login failure
                e.printStackTrace();
            }
        });



    }
}








