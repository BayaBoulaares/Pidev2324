package edu.esprit.services;

import edu.esprit.entities.Evenement;
import edu.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;

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

            // Check if the maximum number of participants is reached
            if (isMaxParticipantsReached(idEvenement)) {
                removeEvent(idEvenement);
            }
        } catch (SQLException ex) {
            System.out.println("Error while inserting participation: " + ex.getMessage());
            throw ex;
        }
    }


    public List<Evenement> getParticipatedEvents(int userId) throws SQLException {
        List<Evenement> events = new ArrayList<>();
        String query = "SELECT e.Id_Event, e.Nom_Event, e.Date_Debut, e.Date_Fin, e.Lieu_Event, e.Description, e.Nb_Max, e.image FROM evenement e JOIN participation p ON e.Id_Event = p.Id_Event WHERE p.Id_User = ?";
        try (PreparedStatement pstmt = cnx.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                while (resultSet.next()) {
                    Evenement event = new Evenement();
                    event.setId_Event(resultSet.getInt("Id_Event"));
                    event.setNom_Event(resultSet.getString("Nom_Event"));
                    event.setDate_Debut(resultSet.getDate("Date_Debut"));
                    event.setDate_Fin(resultSet.getDate("Date_Fin"));
                    event.setLieu_Event(resultSet.getString("Lieu_Event"));
                    event.setDescription(resultSet.getString("Description"));
                    event.setNb_Max(resultSet.getInt("Nb_Max"));
                    event.setImage(resultSet.getString("image"));
                    events.add(event);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error while getting participated events: " + ex.getMessage());
            throw ex;
        }
        return events;
    }
    public boolean hasParticipated(int eventId, int userId) throws SQLException {
        String query = "SELECT COUNT(*) FROM participation WHERE Id_Event = ? AND Id_User = ?";
        try (PreparedStatement pstmt = cnx.prepareStatement(query)) {
            pstmt.setInt(1, eventId);
            pstmt.setInt(2, userId);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error while checking participation: " + ex.getMessage());
            throw ex;
        }
        return false;
    }
    public int getNumberOfParticipants(int Id_Event) throws SQLException {
        // Execute a SQL query to count the number of participants for the event
        String query = "SELECT COUNT(*) FROM participation WHERE Id_Event = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, Id_Event);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1); // Return the count of participants
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error while getting number of participants: " + ex.getMessage());
            throw ex;
        }
        return 0; // Return 0 if no participants found or an error occurred
    }


    public boolean isMaxParticipantsReached(int idEvenement) throws SQLException {
        // Get the current number of participants for the event
        int currentParticipants = getNumberOfParticipants(idEvenement);
        // Get the maximum number of participants for the event
        int maxParticipants = getMaxParticipants(idEvenement);
        // Return true if the current number equals or exceeds the maximum
        return currentParticipants >= maxParticipants;
    }

    private int getMaxParticipants(int idEvenement) throws SQLException {
        String query = "SELECT Nb_Max FROM evenement WHERE Id_Event = ?";
        try (PreparedStatement pstmt = cnx.prepareStatement(query)) {
            pstmt.setInt(1, idEvenement);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("Nb_Max"); // Return the maximum number of participants
                }
            }
        }
        return -1; // Return -1 if no maximum participants found
    }

    public void removeEvent(int idEvenement) throws SQLException {
        String query = "DELETE FROM evenement WHERE Id_Event = ?";
        try (PreparedStatement pstmt = cnx.prepareStatement(query)) {
            pstmt.setInt(1, idEvenement);
            pstmt.executeUpdate();
            System.out.println("Event removed successfully!");
        } catch (SQLException ex) {
            System.out.println("Error while removing event: " + ex.getMessage());
            throw ex;
        }
    }
    public void removeEventIfEnded(int idEvenement) throws SQLException {
        String query = "DELETE FROM evenement WHERE Id_Event = ?";
        try (PreparedStatement pstmt = cnx.prepareStatement(query)) {
            pstmt.setInt(1, idEvenement);

            // Check if the event has ended before removing it
            if (isEventEnded(idEvenement)) {
                pstmt.executeUpdate();
                System.out.println("Event removed successfully because it has ended!");
            } else {
                System.out.println("Event has not ended yet, so it was not removed.");
            }
        } catch (SQLException ex) {
            System.out.println("Error while removing event: " + ex.getMessage());
            throw ex;
        }
    }

    private boolean isEventEnded(int idEvenement) throws SQLException {
        String query = "SELECT Date_Fin FROM evenement WHERE Id_Event = ?";
        try (PreparedStatement pstmt = cnx.prepareStatement(query)) {
            pstmt.setInt(1, idEvenement);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    java.util.Date endDate = resultSet.getDate("Date_Fin");
                    java.util.Date currentDate = new java.util.Date();
                    return endDate != null && endDate.before(currentDate);
                }
            }
        }
        return false;
    }


}
