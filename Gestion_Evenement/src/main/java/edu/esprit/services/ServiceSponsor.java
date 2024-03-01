package edu.esprit.services;

import edu.esprit.entities.Evenement;
import edu.esprit.entities.Fond;
import edu.esprit.entities.Sponsor;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ServiceSponsor implements IService<Sponsor> {

    @Override
    public void ajouter(Sponsor s) throws SQLException {
        Connection cnx = DataSource.getInstance().getCnx();

        try {
            String req = "INSERT INTO sponsor(Id_Sopnsor, Nom, Description, Fond, Id_Event, image) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setInt(1, s.getId_Sponsor());
            pstmt.setString(2, s.getNomSponsor());
            pstmt.setString(3, s.getDescription_s());
            pstmt.setString(4, s.getFond().toString());
            pstmt.setInt(5, s.getEvenement().getId_Event());
            pstmt.setString(6, s.getImage());
            pstmt.executeUpdate();
            System.out.println("Sponsor ajouté avec succès");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Sponsor s) throws SQLException {
        Connection cnx = DataSource.getInstance().getCnx();

        try {
            String req = "UPDATE sponsor SET Nom=?, Description=?, Fond=?, Id_Event=?, image=? WHERE Id_Sopnsor=?";
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setString(1, s.getNomSponsor());
            pstmt.setString(2, s.getDescription_s());
            pstmt.setString(3, s.getFond().toString());
            pstmt.setInt(4, s.getEvenement().getId_Event());
            pstmt.setString(5, s.getImage());
            pstmt.setInt(6, s.getId_Sponsor());

            pstmt.executeUpdate();
            System.out.println("Sponsor modifié avec succès");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        Connection cnx = DataSource.getInstance().getCnx();

        try {
            String req = "DELETE FROM sponsor WHERE Id_Sopnsor=?";
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setInt(1, id);

            pstmt.executeUpdate();
            System.out.println("Sponsor supprimé avec succès");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    @Override
    public List<Sponsor> getAll() throws SQLException {
        Connection cnx = DataSource.getInstance().getCnx();
        List<Sponsor> sponsors = new ArrayList<>();
        ServiceEvenement serviceEvenement = new ServiceEvenement();

        try {
            String req = "SELECT * FROM sponsor";
            PreparedStatement pstmt = cnx.prepareStatement(req);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int idSponsor = rs.getInt("Id_Sopnsor");
                String nomSponsor = rs.getString("Nom");
                String description = rs.getString("Description");
                String fondStr = rs.getString("Fond");
                int idEvent = rs.getInt("Id_Event");
                String image = rs.getString("image");

                Fond fond = Fond.valueOf(fondStr);
                Evenement evenement = serviceEvenement.getOneById(idEvent);

                Sponsor sponsor = new Sponsor(idSponsor, nomSponsor, description, fond, evenement,image);
                sponsors.add(sponsor);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération des sponsors : " + ex.getMessage());
        }

        return sponsors;
    }

    @Override
    public Sponsor getOneById(int id) throws SQLException {
        Connection cnx = DataSource.getInstance().getCnx();
        Sponsor sponsor = null;
        ServiceEvenement serviceEvenement = new ServiceEvenement(); // Création d'une instance de ServiceEvenement

        try {
            String req = "SELECT * FROM sponsor WHERE Id_Sopnsor=?";
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int idSponsor = rs.getInt("Id_Sopnsor");
                String nomSponsor = rs.getString("Nom");
                String description = rs.getString("Description");
                String fondStr = rs.getString("Fond");
                int idEvent = rs.getInt("Id_Event");
                String image = rs.getString("image");

                Fond fond = Fond.valueOf(fondStr);
                Evenement evenement = serviceEvenement.getOneById(idEvent);

                sponsor = new Sponsor(idSponsor, nomSponsor, description, fond, evenement,image);

            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération du sponsor : " + ex.getMessage());
        }

        return sponsor;
    }

    public List<Sponsor> getByEventName(String eventName) {
        Connection cnx = DataSource.getInstance().getCnx();
        List<Sponsor> sponsors = new ArrayList<>();
        ServiceEvenement serviceEvenement = new ServiceEvenement(); // Création d'une instance de ServiceEvenement

        try {
            String req = "SELECT s.* FROM sponsor s JOIN evenement e ON s.Id_Event = e.Id_Event WHERE e.Nom_Event = ?";
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setString(1, eventName);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int idSponsor = rs.getInt("Id_Sopnsor");
                String nomSponsor = rs.getString("Nom");
                String description = rs.getString("Description");
                String fondStr = rs.getString("Fond");
                int idEvent = rs.getInt("Id_Event");
                String image = rs.getString("image");

                Fond fond = Fond.valueOf(fondStr);
                Evenement evenement = serviceEvenement.getOneById(idEvent);

                Sponsor sponsor = new Sponsor(idSponsor, nomSponsor, description, fond, evenement,image);
                sponsors.add(sponsor);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération des sponsors par nom d'événement : " + ex.getMessage());
        }

        return sponsors;
    }
    public Map<String, Integer> getNombreEvenementsParSponsor() throws SQLException {
        Map<String, Integer> nombreEvenementsParSponsor = new HashMap<>();
        Connection cnx = null;

        try {
            // Get the connection
            cnx = DataSource.getInstance().getCnx();

            String query = "SELECT Nom, COUNT(Id_Event) AS nombre_evenements FROM sponsor GROUP BY Nom";
            try (PreparedStatement pstmt = cnx.prepareStatement(query);
                 ResultSet resultSet = pstmt.executeQuery()) {
                while (resultSet.next()) {
                    String nomSponsor = resultSet.getString("Nom");
                    int nombre = resultSet.getInt("nombre_evenements");
                    nombreEvenementsParSponsor.put(nomSponsor, nombre);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération du nombre d'événements par sponsor : " + ex.getMessage());
            throw ex;
        }

        return nombreEvenementsParSponsor;
    }



}

