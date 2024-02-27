package edu.esprit.services;

import edu.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceParticipation {

    private Connection cnx;

    public ServiceParticipation() {
        cnx = DataSource.getInstance().getCnx();
    }

    // Méthode pour insérer une participation dans la base de données
    public void insertParticipation(int idEvenement, int idUtilisateur) throws SQLException {
        String query = "INSERT INTO participation (Id_Event, Id_User) VALUES (?, ?)";
        try (PreparedStatement pstmt = cnx.prepareStatement(query)) {
            pstmt.setInt(1, idEvenement);
            pstmt.setInt(2, idUtilisateur);
            pstmt.executeUpdate();
            System.out.println("Participation added successfully!");
        } catch (SQLException ex) {
            System.out.println("Error while inserting participation: " + ex.getMessage());
            throw ex;
        }
    }

  /*  // Méthode pour obtenir l'identifiant de l'utilisateur à partir de son nom
    public int getUserIdFromUserName(String userName) throws SQLException {
        int userId = -1;
        String query = "SELECT idu FROM utilisateurs WHERE nom = ?";
        try (PreparedStatement pstmt = cnx.prepareStatement(query)) {
            pstmt.setString(1, userName);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    userId = resultSet.getInt("idu");
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error while getting user ID: " + ex.getMessage());
            throw ex;
        }
        return userId;
    }

    // Méthode pour obtenir le nom de l'utilisateur à partir de son identifiant
    public String getUserNameFromId(int userId) throws SQLException {
        String userName = null;
        String query = "SELECT nom FROM utilisateurs WHERE idu = ?";
        try (PreparedStatement pstmt = cnx.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    userName = resultSet.getString("nom");
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error while getting user name: " + ex.getMessage());
            throw ex;
        }
        return userName;
    }*/
}
