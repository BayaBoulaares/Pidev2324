package edu.esprit.services;

import edu.esprit.utils.DataSource;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class OAuthAuthenticator {
    private JSONObject accessedJsonData;
    Connection cnx = DataSource.getInstance().getCnx();

    private boolean gotData = false;
    private boolean attemptRecieved = false;
    private boolean loginAttempted = false;

    private String accessToken;
    private String accessCode;

    private String clientID;
    private String redirectUri;
    private String clientSecret;

    private Stage stage;


    public OAuthAuthenticator (String clientID, String redirectUri, String clientSecret) {
        this.clientID = clientID;
        this.redirectUri = redirectUri;
        this.clientSecret = clientSecret;
    }

    public String getClientID() {
        return clientID;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getRedirectUri(){
        return redirectUri;
    }

    public void startLogin(AuthCallback authCallback) {
        if (loginAttempted) {
            System.out.println("Login attempted 55");
            authCallback.onLoginFailure(new RuntimeException("Login attempted already"));
            return;
        }

        loginAttempted = true;
        stage = new Stage();
        WebView root = new WebView();
        WebEngine engine = root.getEngine();

        engine.load(getWebUrl());
        System.out.println("Engine load 64");

        engine.setOnStatusChanged(event -> {
            System.out.println("Status changed event received.");

            if (gotData || attemptRecieved) {
                System.out.println("* Already got data or attempt received. Skipping.");
                return;
            }

            System.out.println("* Handling status change event...");

            if (event.getSource() instanceof WebEngine) {
                WebEngine we = (WebEngine) event.getSource();
                System.out.println("* WebEngine instance obtained.");
                String location = we.getLocation();
                System.out.println("* Location: " + location);

                if (location.contains("code") && location.startsWith(getRedirectUri())) {
                    System.out.println("* Attempt received. Processing...");

                    attemptRecieved = true;
                    System.out.println("* Attempt received flag set to true.");

                    closeStage();
                    System.out.println("* Stage closed.");

                    accessCode = location.substring(location.indexOf("code=") + 5);
                    System.out.println("* Access code extracted: " + accessCode);

                    try {
                        accessToken = doGetAccessTokenRequest(accessCode);
                        System.out.println("* Access token acquired.");

                        String returnedJson = doGetAccountInfo(accessToken);
                        System.out.println("* Account info retrieved: " + returnedJson);

                        accessedJsonData = new JSONObject(returnedJson);

                        System.out.println("* JSON data processed.");

                        gotData = true;
                        System.out.println("* Got data flag set to true.");

                        authCallback.onLoginSuccess(accessedJsonData);
                    } catch (Exception e) {
                        authCallback.onLoginFailure(e);
                    }
                }
            }
        });

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    abstract String getWebUrl();

    abstract String getApiTokenUrl();

    abstract String getApiAccessUrl();

    abstract String getApiAccessParams();

    public String getAccessToken() {
        return accessToken;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public boolean hasFinishedSuccessfully() {
        return gotData;
    }

    public JSONObject getJsonData() {
        if(gotData) {
            return accessedJsonData;
        } else {
            return null;

        }
    }

    private void closeStage() {
        stage.close();
    }

    private void notifyLoginViewCompleted() {
        if(gotData) {
            //LoginView.getInstance().completedOAuthLogin(this);
            System.out.println("Aziiiiiiiiiiiiiiiiiiiiiiz");
        }
    }

    private String doGetAccountInfo(String accessToken) {
        try {
            HttpURLConnection connection2 = null;
            URL url2 = new URL(getApiTokenUrl());
            connection2 = (HttpURLConnection) url2.openConnection();
            connection2.setRequestProperty("User-Agent", "Mozilla/5.0");

            connection2.setDoInput(true);
            connection2.setDoOutput(true);

            System.out.println("URL: " + getApiTokenUrl());

            int reponseCode2 = connection2.getResponseCode();

            if (reponseCode2 == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in2 = new BufferedReader(new InputStreamReader(
                        connection2.getInputStream()));
                String inputLine2;
                StringBuffer response2 = new StringBuffer();

                while ((inputLine2 = in2.readLine()) != null) {
                    response2.append(inputLine2);
                }
                in2.close();
                connection2.disconnect();
                return response2.toString();
            } else {
                System.out.println("Error retrieving api data!: " + reponseCode2);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("####### ERROR GETTING ACCOUNT INFO ##############");
        }
        return null;
    }

    private String doGetAccessTokenRequest(String code) {
        try {
            URL url = new URL(getApiAccessUrl());
            String urlParams = getApiAccessParams();

            System.out.println("URL: " + getApiAccessUrl());
            System.out.println("PARAMS: " + urlParams);

            byte[] postData = urlParams.getBytes(StandardCharsets.UTF_8);
            int postDataLength = postData.length;

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setRequestProperty("charset", "utf-8");
            connection.setRequestProperty("Content-Length", "" + postDataLength);
            connection.setRequestProperty("Connection", "keep-alive");
            connection.setRequestProperty("Referer", "https://accounts.google.com/o/oauth2/token");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setUseCaches(false);

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);

            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.write(postData);

            wr.flush();

            int responseCode = connection.getResponseCode();

            System.out.println(responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) { // success

            } else {
                System.err.println("Error getting access token for OAuth Login!");
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            connection.disconnect();
            String fullResponse = response.toString();

            JSONObject json = new JSONObject(fullResponse);

            String accessToken = json.getString("access_token");

            System.out.println(fullResponse);

            System.out.println("ACCESS TOKEN: " + accessToken);

            return accessToken;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
   /* private void insertDataIntoDatabase(String accessToken, JSONObject jsonData) {
        // Connexion à la base de données
        try  {
            // Construction de la requête d'insertion
            String sql = "INSERT INTO utilisateurs (nom,prenom,login) VALUES (?,?,?)";

            // Création de la déclaration PreparedStatement avec la requête SQL
            PreparedStatement statement = cnx.prepareStatement(sql);
            //statement.setString(1, accessToken);
            // Ajoutez d'autres paramètres selon votre structure de données
            statement.setString(1, jsonData.getString("nom"));
            statement.setString(2, jsonData.getString("prenom"));
            statement.setString(3, jsonData.getString("login"));
            // Ajoutez d'autres attributs si nécessaire

            // Exécution de la requête
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Les données ont été insérées avec succès dans la base de données !");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion des données dans la base de données : " + e.getMessage());
        }
    }
    private void handleAuthentication(String accessToken, JSONObject jsonData) {
        // Insérer les données dans la base de données
        insertDataIntoDatabase(accessToken, jsonData);
        // Autres actions à effectuer après l'insertion dans la base de données
    }*/
}