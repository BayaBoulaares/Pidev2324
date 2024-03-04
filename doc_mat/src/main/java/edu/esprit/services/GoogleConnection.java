package edu.esprit.services;


import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;

public class GoogleConnection {

    private static final String CLIENT_SECRETS_FILE_PATH = "C://Users//USER//OneDrive//Desktop//Pidev2324//CrudOff//src//main//resources//client_secret_452055391469-0iqs7dmg959d7ro34t967b799b335406.apps.googleusercontent.com.json";
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
    public static String fetchUserProfile(String accessToken) throws IOException, GeneralSecurityException {
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken);
        HttpRequestFactory requestFactory = httpTransport.createRequestFactory(credential);
        HttpResponse response = requestFactory.buildGetRequest(new GenericUrl(PROFILE_API_URL)).execute();

        // Parse the response to get the user profile information
        String userProfile = response.parseAsString();
        return userProfile;

    }


}

