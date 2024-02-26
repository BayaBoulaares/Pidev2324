package edu.esprit.services;

import edu.esprit.entities.Reclamation;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class ServiceReclamation implements IServices<Reclamation> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Reclamation r) throws SQLException {
        String req = "INSERT INTO `reclamation`(`nom`, `reclamation`, `date`, `rating`) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, r.getNom());
            ps.setString(2, r.getReclamation());

            // Use the current date and time for the 'date' field
            Timestamp sqlTimestamp = Timestamp.valueOf(LocalDateTime.now());
            ps.setTimestamp(3, sqlTimestamp);

            // Assuming 'rating' is an integer property in your Reclamation class
            ps.setString(4, r.getRating());

            ps.executeUpdate();
            System.out.println("Reclamation added!");
        }
    }

    @Override
    public void modifier(Reclamation r) throws SQLException {
        String req = "UPDATE reclamation SET nom=?, reclamation=?, date=?, rating=? WHERE id=?";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, r.getNom());
            ps.setString(2, r.getReclamation());

            // Use the current date and time for the 'date' field
            Timestamp sqlTimestamp = Timestamp.valueOf(LocalDateTime.now());
            ps.setTimestamp(3, sqlTimestamp);

            // Assuming 'rating' is an integer property in your Reclamation class
            ps.setString(4, r.getRating());

            ps.setInt(5, r.getId());
            ps.executeUpdate();
            System.out.println("Reclamation updated!");
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
                String rating = res.getString("rating");
                r = new Reclamation( id, nom,reclamation, date.toLocalDate(), rating);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return r ;
    }

    @Override
    public HashSet<Reclamation> getAll() throws SQLException {
        Connection cnx =DataSource.getInstance().getCnx();
        HashSet<Reclamation> reclamations = new HashSet<>();



            String req = "SELECT * FROM reclamation";
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                int id = res.getInt("id");
                String reclamation = res.getString("reclamation");

                String nom = res.getString("nom");

                Date date = res.getDate("date");
                String rating =res.getString("rating");
                Reclamation r = new Reclamation(id, nom, reclamation, date.toLocalDate(), rating);
                reclamations.add(r);
            }

        return reclamations;
    }

/*
    @Override
    public Set<Reclamation> getAll() {
        Set<Reclamation> reclamationSet = new HashSet<Reclamation>();
        try
        {
            Reclamation rec = new Reclamation();
            String sql = "SELECT * FROM reclamation";
            PreparedStatement statement = DataSource.getInstance().getCnx().prepareStatement(sql);
            try (ResultSet resultSet = statement.executeQuery())
            {
                while (resultSet.next())
                {

                    rec.setId(resultSet.getInt("id"));
                    reclamationSet.add(rec);
                }
            }
        }
        catch (SQLException e)
        {
            System.out.println("Failed to execute SQL Select statement for all edu.esprit.pack.entities.Users:");
            System.out.println(e.getMessage());
        }
        return reclamationSet;
    }

 */
}
