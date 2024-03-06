package edu.esprit.services;

import java.io.*;

public class CredentialsManager {
    private static final String FILENAME = "C:\\Users\\benmr\\IdeaProjects\\Gestion_integration3\\Gestion_Evenement2\\Gestion_Evenement2\\credentials.txt";


    public static void saveCredentials(String id, String username, String password, String state) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILENAME))) {
            writer.println(username);
            System.out.println(username);
            writer.println(id);
            writer.println(password);
            writer.println(state);

            System.out.println(password);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String[] loadCredentials() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String username = reader.readLine();
            String id = reader.readLine();
            String password = reader.readLine();
            String state=reader.readLine();
            return new String[]{username, id,password, state};
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void clearCredentials() {
        File file = new File(FILENAME);
        if (file.exists()) {
            file.delete();
        }
    }

}
