package edu.esprit.services;

import edu.esprit.entities.Evenement;
import edu.esprit.entities.Sponsor;
import edu.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.HashSet;

public class ServiceSponsor implements IService<Sponsor> {

    @Override
    public void ajouter(Sponsor s) {
        Connection cnx = DataSource.getInstance().getCnx();

        try {
            String req = "INSERT INTO sponsor(Nom, Description, Fond, Id_Event) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setString(1, s.getNomSponsor());
            pstmt.setString(2, s.getDescription_s());
            pstmt.setString(3, s.getFond().toString()); // Assuming Fond is an enum inside Sponsor class
            pstmt.setInt(4, s.getEvenement().getId_Event());
            pstmt.executeUpdate();
            System.out.println("Sponsor ajouté avec succès");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Sponsor s) {
        Connection cnx = DataSource.getInstance().getCnx();

        try {
            String req = "UPDATE sponsor SET Nom=?, Description=?, Fond=?, Id_Event=? WHERE Id_Sopnsor=?"; // Correction: Utilisation du nom correct de la colonne dans la clause WHERE
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setString(1, s.getNomSponsor());
            pstmt.setString(2, s.getDescription_s());
            pstmt.setString(3, s.getFond().toString()); // Assuming Fond is an enum inside Sponsor class
            pstmt.setInt(4, s.getEvenement().getId_Event());
            pstmt.setInt(5, s.getId_Sponsor());

            pstmt.executeUpdate();
            System.out.println("Sponsor modifié avec succès");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
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
    public Set<Sponsor> getAll() {
        Connection cnx = DataSource.getInstance().getCnx();
        Set<Sponsor> sponsors = new HashSet<>();
        ServiceEvenement serviceEvenement = new ServiceEvenement();

        try {
            String req = "SELECT * FROM sponsor";
            PreparedStatement pstmt = cnx.prepareStatement(req);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int idSponsor = rs.getInt("Id_Sopnsor"); // Correction: utilisation du nom correct de la colonne
                String nomSponsor = rs.getString("Nom");
                String description = rs.getString("Description");
                String fondStr = rs.getString("Fond");
                int idEvent = rs.getInt("Id_Event");

                // Convertir les types String en Enum
                edu.esprit.entities.Sponsor.Fond fond = edu.esprit.entities.Sponsor.Fond.valueOf(fondStr);

                // Récupérer l'objet Evenement complet de la base de données
                Evenement evenement = serviceEvenement.getOneById(idEvent);

                // Créer un objet Sponsor
                Sponsor sponsor = new Sponsor(idSponsor, nomSponsor, description, fond, evenement);
                sponsors.add(sponsor);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération des sponsors : " + ex.getMessage());
        }

        return sponsors;
    }






    @Override
    public Sponsor getOneById(int id) {
        Connection cnx = DataSource.getInstance().getCnx();
        Sponsor sponsor = null;

        try {
            String req = "SELECT * FROM sponsor WHERE Id_Sponsor=?";
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int idSponsor = rs.getInt("Id_Sponsor");
                String nomSponsor = rs.getString("Nom");
                String description = rs.getString("Description");
                String fondStr = rs.getString("Fond");
                int idEvent = rs.getInt("Id_Event");

                // Convertir les types String en Enum
                edu.esprit.entities.Sponsor.Fond fond = edu.esprit.entities.Sponsor.Fond.valueOf(fondStr);

                // Créer un objet Sponsor
                Evenement evenement = new Evenement(idEvent); // Vous devez probablement récupérer l'Evenement à partir de la base de données
                sponsor = new Sponsor(idSponsor, nomSponsor, description, fond, evenement);
            }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la récupération du sponsor : " + ex.getMessage());
        }

        return sponsor;
    }



}
