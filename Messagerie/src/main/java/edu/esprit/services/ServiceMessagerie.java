package edu.esprit.services;

import edu.esprit.entities.Messagerie;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;

public class ServiceMessagerie implements IServices<Messagerie> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Messagerie m) throws SQLException {
        if (m != null && m.getNom() != null && m.getMessage() != null) {
            String req = "INSERT INTO `messagerie`(`nom`, `date`,`message`) VALUES (?, ?, ?)";
            try (PreparedStatement ps = cnx.prepareStatement(req)) {
                ps.setString(1, m.getNom());
                ps.setDate(2, new Date(System.currentTimeMillis()));
                String validatedMessage = m.getMessage().substring(0, 1).toUpperCase() + m.getMessage().substring(1);
                ps.setString(3, validatedMessage);

                ps.executeUpdate();
                System.out.println("Messagerie added!");
            } catch (SQLException e) {
                System.out.println("Error adding Messagerie: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid Messagerie object provided.");
        }

    }


    @Override
    public void modifier(Messagerie m) {
        if (m != null && m.getNom() != null && m.getMessage() != null) {
            String req = "UPDATE messagerie SET nom=?,message=?, date=?  WHERE id=?";
            try (PreparedStatement ps = cnx.prepareStatement(req)) {
                ps.setString(1, m.getNom());
                ps.setString(2, m.getMessage());
                ps.setDate(3, new Date(System.currentTimeMillis()));
                ps.setInt(4, m.getId());
                ps.executeUpdate();
                System.out.println("Messagerie updated!");
            } catch (SQLException e) {
                System.out.println("Error updating Messagerie: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid Messagerie object provided.");
        }
    }






    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM messagerie WHERE id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id); // Utilisez le nom passé en paramètre
            ps.executeUpdate();
            System.out.println("Messagerie deleted!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public Messagerie getOneById(int id) {
        String req = "SELECT * FROM messagerie WHERE id=?";
        Messagerie m = null;
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1,id);
            ResultSet res = ps.executeQuery();
            if (res.next()) {

                String nom = res.getString("nom");
                Date date = res.getDate("date");
                String message = res.getString("message");

                m = new Messagerie( id, nom, date,message);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return m ;
    }


    @Override
    public HashSet<Messagerie> getAll() throws SQLException {
        Connection cnx =DataSource.getInstance().getCnx();
        HashSet<Messagerie> messageries = new HashSet<>();



            String req = "SELECT * FROM messagerie";
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                int id = res.getInt("id");
                String nom = res.getString("nom");
                Date date = res.getDate(3);
                String message = res.getString("message");
                Messagerie m = new Messagerie(id, nom, date, message);
                messageries.add(m);
            }



        return messageries;
    }
}
