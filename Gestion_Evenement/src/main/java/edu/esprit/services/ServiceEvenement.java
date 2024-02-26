package edu.esprit.services;

import edu.esprit.entities.Evenement;
import edu.esprit.entities.Status;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class ServiceEvenement implements IService<Evenement> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Evenement e) throws SQLException {
        String req = "INSERT INTO `evenement`(`Nom_Event`, `Description`, `Lieu_Event`, `Date_Debut`, `Date_Fin`, `Nb_Max`, `Status`, `image`) VALUES (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, e.getNom_Event());
            ps.setString(2, e.getDescription());
            ps.setString(3, e.getLieu_Event());
            ps.setDate(4, new java.sql.Date(e.getDate_Debut().getTime()));
            ps.setDate(5, new java.sql.Date(e.getDate_Fin().getTime()));
            ps.setInt(6, e.getNb_Max());
            ps.setString(7, e.getStatus().name());
            ps.setString(8, e.getImage()); // Assuming getImage() returns String for the image path
            ps.executeUpdate();
            System.out.println("Evenement ajouté avec succès !");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de l'ajout de l'événement : " + ex.getMessage());
            throw ex;
        }
    }

    @Override
    public void modifier(Evenement e) throws SQLException {
        try {
            String req = "UPDATE evenement SET Nom_Event = ?, Description = ?, Lieu_Event = ?, Date_Debut = ?, Date_Fin = ?, Nb_Max = ?, Status = ?, image = ? WHERE ID_Event = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, e.getNom_Event());
            ps.setString(2, e.getDescription());
            ps.setString(3, e.getLieu_Event());
            ps.setDate(4, new java.sql.Date(e.getDate_Debut().getTime()));
            ps.setDate(5, new java.sql.Date(e.getDate_Fin().getTime()));
            ps.setInt(6, e.getNb_Max());
            ps.setString(7, e.getStatus().name());
            ps.setString(8, e.getImage());
            ps.setInt(9, e.getId_Event());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Evenement modifié avec succès !");
            } else {
                System.out.println("Aucun événement trouvé avec cet ID pour être modifié.");
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la modification de l'événement : " + ex.getMessage());
            throw ex;
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        try {
            String req = "DELETE FROM evenement WHERE ID_Event=?";
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Evenement supprimé avec succès");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la suppression de l'événement : " + ex.getMessage());
            throw ex;
        }
    }

    @Override
    public Evenement getOneById(int id) throws SQLException {
        Evenement evenement = null;
        try {
            String req = "SELECT * FROM evenement WHERE ID_Event = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                int eventId = res.getInt("Id_Event");
                String nomEvent = res.getString("Nom_Event");
                String description = res.getString("Description");
                String lieuEvent = res.getString("Lieu_Event");
                Date dateDebut = res.getDate("Date_Debut");
                Date dateFin = res.getDate("Date_Fin");
                int nbMax = res.getInt("Nb_Max");
                Status status = Status.valueOf(res.getString("Status"));
                String image = res.getString("image"); // Retrieving image as String
                evenement = new Evenement(eventId, nomEvent, description, lieuEvent, dateDebut, dateFin, nbMax, status, image);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération de l'événement : " + ex.getMessage());
            throw ex;
        }
        return evenement;
    }

    @Override
    public List<Evenement> getAll() throws SQLException {
        List<Evenement> evenements = new ArrayList<>();
        try {
            String req = "SELECT * FROM evenement";
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                int id = res.getInt("Id_Event");
                String nomEvent = res.getString("Nom_Event");
                String description = res.getString("Description");
                String lieuEvent = res.getString("Lieu_Event");
                Date dateDebut = res.getDate("Date_Debut");
                Date dateFin = res.getDate("Date_Fin");
                int nbMax = res.getInt("Nb_Max");
                Status status = Status.valueOf(res.getString("Status"));
                String image = res.getString("image"); // Retrieving image as String
                Evenement e = new Evenement(id, nomEvent, description, lieuEvent, dateDebut, dateFin, nbMax, status, image);
                evenements.add(e);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération des événements : " + ex.getMessage());
            throw ex;
        }
        return evenements;
    }
    public List<Evenement> getEventsForCurrentWeek() throws SQLException {
        List<Evenement> evenementsSemaine = new ArrayList<>();
        try {
            // Obtenez la date de début et de fin de la semaine actuelle
            LocalDate debutSemaine = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            LocalDate finSemaine = debutSemaine.plusDays(6); // Dimanche est le dernier jour de la semaine

            String req = "SELECT * FROM evenement WHERE Date_Fin >= ? AND Date_Debut <= ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setDate(1, java.sql.Date.valueOf(debutSemaine));
            ps.setDate(2, java.sql.Date.valueOf(finSemaine));
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                int id = res.getInt("Id_Event");
                String nomEvent = res.getString("Nom_Event");
                String description = res.getString("Description");
                String lieuEvent = res.getString("Lieu_Event");
                Date dateDebut = res.getDate("Date_Debut");
                Date dateFin = res.getDate("Date_Fin");
                int nbMax = res.getInt("Nb_Max");
                Status status = Status.valueOf(res.getString("Status"));
                String image = res.getString("image");
                Evenement e = new Evenement(id, nomEvent, description, lieuEvent, dateDebut, dateFin, nbMax, status, image);
                evenementsSemaine.add(e);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération des événements de la semaine : " + ex.getMessage());
            throw ex;
        }
        return evenementsSemaine;
    }


}
