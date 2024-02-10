package edu.esprit.services;

import edu.esprit.entities.Evenement;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceEvenement implements IService<Evenement> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Evenement e) {
        String req = "INSERT INTO `evenement`(`Nom_Event`, `Description`, `Lieu_Event`, `Date_Debut`, `Date_Fin`, `Nb_Max`, `Status`) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, e.getNom_Event());
            ps.setString(2, e.getDescription());
            ps.setString(3, e.getLieu_Event());
            ps.setDate(4, new java.sql.Date(e.getDate_Debut().getTime()));
            ps.setDate(5, new java.sql.Date(e.getDate_Fin().getTime()));
            ps.setInt(6, e.getNb_Max());
            ps.setString(7, e.getStatus().toString());
            ps.executeUpdate();
            System.out.println("Evenement added !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Evenement e) {
        // Ajoutez le code pour modifier un événement
    }

    @Override
    public void supprimer(int id) {
        // Ajoutez le code pour supprimer un événement
    }

    @Override
    public Evenement getOneById(int id) {
        // Ajoutez le code pour obtenir un événement par son ID
        return null;
    }

    @Override
    public Set<Evenement> getAll() {
        Set<Evenement> evenements = new HashSet<>();

        String req = "SELECT * FROM evenement";
        try {
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
                Evenement.Status status = Evenement.Status.valueOf(res.getString("Status"));
                Evenement e = new Evenement(id, nomEvent, description, lieuEvent, dateDebut, dateFin, nbMax, status);
                evenements.add(e);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return evenements;
    }
}
