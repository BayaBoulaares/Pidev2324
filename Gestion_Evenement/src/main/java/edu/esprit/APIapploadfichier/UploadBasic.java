package edu.esprit.APIapploadfichier;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class UploadBasic {

    public static String uploadPDF(String filePathString) throws IOException {
        // Load pre-authorized user credentials from the project resources.
        InputStream credentialsStream = UploadBasic.class.getResourceAsStream("/credentials.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream)
                .createScoped(Arrays.asList(DriveScopes.DRIVE_FILE));
        HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credentials);

        // Build a new authorized API client service.
        Drive service = new Drive.Builder(new NetHttpTransport(),
                GsonFactory.getDefaultInstance(),
                requestInitializer)
                .setApplicationName("Drive samples")
                .build();

        // Upload file document.pdf on drive.
        File fileMetadata = new File();
        fileMetadata.setName("document.pdf");

        // File's content.
        java.io.File filePath = new java.io.File(filePathString);

        // Specify media type and file-path for file.
        FileContent mediaContent = new FileContent("application/pdf", filePath);
        try {
            File file = service.files().create(fileMetadata, mediaContent)
                    .setFields("id")
                    .execute();
            System.out.println("File ID: " + file.getId());
            return file.getId();
        } catch (GoogleJsonResponseException e) {
            // TODO(developer) - handle error appropriately
            System.err.println("Unable to upload file: " + e.getDetails());
            throw e;
        }
    }
    public static String uploadVideo(String filePathString, String videoType) throws IOException {
        // Set the path to the service account key file.
        System.setProperty("GOOGLE_APPLICATION_CREDENTIALS", "C:\\Users\\benmr\\IdeaProjects\\test3\\src\\main\\resources\\credentials.json");

        // Load pre-authorized user credentials from the environment.
        GoogleCredentials credentials = GoogleCredentials.getApplicationDefault()
                .createScoped(Arrays.asList(DriveScopes.DRIVE_FILE));
        HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credentials);

        // Build a new authorized API client service.
        Drive service = new Drive.Builder(new NetHttpTransport(),
                GsonFactory.getDefaultInstance(),
                requestInitializer)
                .setApplicationName("Drive samples")
                .build();

        // Upload file video on drive.
        File fileMetadata = new File();
        fileMetadata.setName("video." + videoType);

        // File's content.
        java.io.File filePath = new java.io.File(filePathString);

        // Specify media type and file-path for file.
        FileContent mediaContent = new FileContent("video/" + videoType, filePath);
        try {
            File file = service.files().create(fileMetadata, mediaContent)
                    .setFields("id")
                    .execute();
            System.out.println("File ID: " + file.getId());
            return file.getId();
        } catch (GoogleJsonResponseException e) {
            // TODO(developer) - handle error appropriately
            System.err.println("Unable to upload file: " + e.getDetails());
            throw e;
        }
    }
    public static Drive getDriveService() throws IOException {
        // Set the path to the service account key file.
        System.setProperty("GOOGLE_APPLICATION_CREDENTIALS", "C:\\Users\\benmr\\IdeaProjects\\test3\\src\\main\\resources\\credentials.json");

        // Load pre-authorized user credentials from the environment.
        GoogleCredentials credentials = GoogleCredentials.getApplicationDefault()
                .createScoped(Arrays.asList(DriveScopes.DRIVE_FILE));
        HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credentials);

        // Build a new authorized API client service.
        return new Drive.Builder(new NetHttpTransport(),
                GsonFactory.getDefaultInstance(),
                requestInitializer)
                .setApplicationName("Drive samples")
                .build();
    }



}
