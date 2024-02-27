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


}
