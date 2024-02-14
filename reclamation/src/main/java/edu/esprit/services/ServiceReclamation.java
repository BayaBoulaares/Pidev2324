package edu.esprit.services;

import edu.esprit.entities.Reclamation;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;

public class ServiceReclamation implements IServices<Reclamation> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Reclamation r) {
        String req = "INSERT INTO `reclamation`(`nom`,`reclamation`, `date`) VALUES (?, ?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, r.getNom());
            ps.setString(2, r.getReclamation());

            // Use the current date for the 'date' field
            Date currentDate = new Date(System.currentTimeMillis());
            ps.setDate(3, currentDate);

            ps.executeUpdate();
            System.out.println("reclamation added!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void modifier(Reclamation r) {
        String req = "UPDATE reclamation SET nom=?,reclamation=?, date=? WHERE id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, r.getNom());
            ps.setString(2, r.getReclamation());

            ps.setDate(3, r.getDate());
            ps.setInt(4,r.getId());
            ps.executeUpdate();
            System.out.println("reclamation updated!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }






    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM reclamation WHERE id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id); // Utilisez le nom passé en paramètre
            ps.executeUpdate();
            System.out.println("reclamation deleted!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public Reclamation getOneById(int id) {
        String req = "SELECT * FROM reclamation WHERE id=?";
        Reclamation r = null;
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1,id);
            ResultSet res = ps.executeQuery();
            if (res.next()) {

                String nom = res.getString("nom");
                String reclamation = res.getString("reclamation");

                Date date = res.getDate("date");
                r = new Reclamation( id, nom,reclamation, date);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return r ;
    }


    @Override
    public HashSet<Reclamation> getAll() {
        Connection cnx =DataSource.getInstance().getCnx();
        HashSet<Reclamation> reclamations = new HashSet<>();


        try {
            String req = "SELECT * FROM reclamation";
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                int id = res.getInt("id");
                String nom = res.getString("nom");
                String reclamation = res.getString("reclamation");

                Date date = res.getDate(4);
                Reclamation r = new Reclamation(id, nom,reclamation, date);
                reclamations.add(r);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return reclamations;
    }
}
