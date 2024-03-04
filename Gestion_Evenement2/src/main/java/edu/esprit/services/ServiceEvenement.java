package edu.esprit.services;

import edu.esprit.entities.Evenement;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ServiceEvenement implements IService<Evenement> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Evenement e) throws SQLException {
        String req = "INSERT INTO `evenement`(`Nom_Event`, `Description`, `Lieu_Event`, `Date_Debut`, `Date_Fin`, `Nb_Max`, `image`) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, e.getNom_Event());
            ps.setString(2, e.getDescription());
            ps.setString(3, e.getLieu_Event());
            ps.setDate(4, new java.sql.Date(e.getDate_Debut().getTime()));
            ps.setDate(5, new java.sql.Date(e.getDate_Fin().getTime()));
            ps.setInt(6, e.getNb_Max());
            ps.setString(7, e.getImage());
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
            String req = "UPDATE evenement SET Nom_Event = ?, Description = ?, Lieu_Event = ?, Date_Debut = ?, Date_Fin = ?, Nb_Max = ?, image = ? WHERE ID_Event = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, e.getNom_Event());
            ps.setString(2, e.getDescription());
            ps.setString(3, e.getLieu_Event());
            ps.setDate(4, new java.sql.Date(e.getDate_Debut().getTime()));
            ps.setDate(5, new java.sql.Date(e.getDate_Fin().getTime()));
            ps.setInt(6, e.getNb_Max());
            ps.setString(7, e.getImage());
            ps.setInt(8, e.getId_Event());
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
    public Collection<Evenement> getAll() throws SQLException {
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
                String image = res.getString("image"); // Retrieving image as String
                Evenement e = new Evenement(id, nomEvent, description, lieuEvent, dateDebut, dateFin, nbMax, image);
                evenements.add(e);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération des événements : " + ex.getMessage());
            throw ex;
        }
        return evenements;
    }

    @Override
    public Evenement getOne(int id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
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
                String image = res.getString("image"); // Retrieving image as String
                evenement = new Evenement(eventId, nomEvent, description, lieuEvent, dateDebut, dateFin, nbMax, image);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération de l'événement : " + ex.getMessage());
            throw ex;
        }
        return evenement;
    }
    public boolean eventExists(String nomEvenement) throws SQLException {
        String req = "SELECT COUNT(*) FROM evenement WHERE Nom_Event = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, nomEvenement);
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                int count = res.getInt(1);
                return count > 0;
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la vérification de l'existence de l'événement : " + ex.getMessage());
            throw ex;
        }
        return false;
    }
}
