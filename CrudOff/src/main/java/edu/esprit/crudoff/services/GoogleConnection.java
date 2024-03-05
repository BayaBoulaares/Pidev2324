package edu.esprit.crudoff.services;


import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.Collections;

/*import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.InputStreamReader;
import java.util.Arrays;*/
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class GoogleConnection {

    private static final String CLIENT_SECRETS_FILE_PATH = "C://Users//USER//OneDrive//Desktop//Pidev2324//CrudOff//src//main//resources//client_secret_452055391469-0iqs7dmg959d7ro34t967b799b335406.apps.googleusercontent.com (1).json";
    private static final String REDIRECT_URI = "http://localhost";
    private static final String SCOPE = "https://www.googleapis.com/auth/userinfo.profile";
    private static final String PROFILE_API_URL = "https://www.googleapis.com/oauth2/v1/userinfo";


    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static NetHttpTransport httpTransport;

    public static GoogleAuthorizationCodeFlow getFlow() throws IOException, GeneralSecurityException {
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
                new InputStreamReader(new FileInputStream(CLIENT_SECRETS_FILE_PATH)));
        httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        return new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, JSON_FACTORY, clientSecrets,
                Collections.singleton(SCOPE))
                .build();
    }

    public static String buildAuthorizationUrl() throws IOException, GeneralSecurityException {
        GoogleAuthorizationCodeRequestUrl url = getFlow().newAuthorizationUrl();
        return url.setRedirectUri(REDIRECT_URI).build();
    }
    public static JsonObject getUserInfo(String accessToken) throws IOException {
        String url = "https://www.googleapis.com/oauth2/v3/userinfo";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // Définir la méthode de requête et les en-têtes
        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", "Bearer " + accessToken);

        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) { // Si la requête a réussi
            // Lecture de la réponse
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Convertir la réponse JSON en objet JsonObject
            Gson gson = new Gson();
            return gson.fromJson(response.toString(), JsonObject.class);
        } else {
            System.out.println("La requête a échoué avec le code de réponse : " + responseCode);
            return null;
        }
    }

  /* private static final String CLIENT_SECRET_FILE = "/path/to/client_secret.json"; // Remplacez cela par votre chemin d'accès au fichier JSON contenant vos identifiants client

    private static final String APPLICATION_NAME = "Votre application";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private static GoogleAuthorizationCodeFlow flow;

    public static void init() throws IOException, GeneralSecurityException {
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(GoogleConnection.class.getResourceAsStream(CLIENT_SECRET_FILE)));

        flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, clientSecrets,
                Collections.singletonList("https://www.googleapis.com/auth/userinfo.email"))
                .setAccessType("offline")
                .build();
    }

    public static String signIn() throws IOException {
        String url = flow.newAuthorizationUrl().setRedirectUri("urn:ietf:wg:oauth:2.0:oob").build();
        // Rediriger l'utilisateur vers cette URL pour se connecter avec Google
        return url;
    }

    public static String getUserEmail(String authCode) throws IOException, GeneralSecurityException {
        String accessToken = flow.newTokenRequest(authCode).setRedirectUri("urn:ietf:wg:oauth:2.0:oob").execute().getAccessToken();

        Oauth2 oauth2 = new Oauth2.Builder(
                GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, null)
                .setApplicationName(APPLICATION_NAME)
                .build();

        Userinfo userinfo = oauth2.userinfo().get().setAccessToken(accessToken).execute();
        return userinfo.getEmail();
    }
*/
   /*private static final String CLIENT_SECRET_FILE_PATH = "C://Users//USER//OneDrive//Desktop//Pidev2324//CrudOff//src//main//resources//client_secret_452055391469-0iqs7dmg959d7ro34t967b799b335406.apps.googleusercontent.com (1).json"; // Mettez votre chemin vers le fichier client_secret.json
    private static final String REDIRECT_URI = "http://localhost";


    private static final String SCOPE = "openid email"; // Les autorisations que vous souhaitez demander
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();


    public static void main(String[] args) throws Exception {
        // Chargez les secrets client
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                JacksonFactory.getDefaultInstance(),
                new InputStreamReader(GoogleConnection.class.getResourceAsStream(CLIENT_SECRET_FILE_PATH))
        );

        // Créez le flux de code d'autorisation
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        AuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                jsonFactory,
                clientSecrets,
                Arrays.asList(SCOPE))
                .build();

        // Générez l'URL de connexion
        AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI);
        String url = authorizationUrl.build();
        String authCode = "";

        // Ouvrez l'URL dans le navigateur ou effectuez une redirection
        // vers cette URL pour que l'utilisateur puisse se connecter

        // Attendre l'authentification de l'utilisateur et récupérer le code d'autorisation

        // Échangez le code d'autorisation contre des informations d'identification
        TokenResponse response = flow.newTokenRequest(authCode).setRedirectUri(REDIRECT_URI).execute();
        Credential credential = flow.createAndStoreCredential(response, null);

        // Utilisez credential pour accéder aux API Google
    }
    public static String getAuthorizationUrl() {
        try {
            // Chargez les secrets client
            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                    JSON_FACTORY,
                    new InputStreamReader(GoogleConnection.class.getResourceAsStream(CLIENT_SECRET_FILE_PATH))
            );

            // Créez le flux de code d'autorisation
            AuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JSON_FACTORY,
                    clientSecrets,
                    Arrays.asList(SCOPE))
                    .build();

            // Générez l'URL de connexion
            AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI);
            return authorizationUrl.build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }*/

}

