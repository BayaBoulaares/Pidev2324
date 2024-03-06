package edu.esprit.services;

import edu.esprit.entities.Reclamation;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashSet;

public class ServiceReclamation implements IService<Reclamation> {

    private static final ServiceReclamation instance = new ServiceReclamation();

    // Private constructor to prevent instantiation
    public ServiceReclamation() {
        // Initialization code, if any
    }

    // Method to get the singleton instance
    public static ServiceReclamation getInstance() {
        return instance;
    }

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void ajouter(Reclamation r) throws SQLException {
        String req = "INSERT INTO `reclamation`(`nom`, `reclamation`, `date`, `rating`, `idu`) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, r.getNom());
            ps.setString(2, r.getReclamation());
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(4, r.getRating());
            ps.setInt(5, r.getIdu());

            ps.executeUpdate();

            // Retrieve the generated ID
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    r.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Failed to retrieve generated ID.");
                }
            }

            System.out.println("Reclamation added with ID: " + r.getId());
        }
    }

    @Override
    public void modifier(Reclamation r) throws SQLException {
        String req = "UPDATE reclamation SET nom=?, reclamation=?, date=?, rating=? WHERE id=?";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, r.getNom());
            ps.setString(2, r.getReclamation());
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(4, r.getRating());
            ps.setInt(5, r.getId());

            ps.executeUpdate();
            System.out.println("Reclamation updated!");
        }
    }

    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM reclamation WHERE id=?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Reclamation deleted!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Reclamation getOneById(int id) {
        String req = "SELECT * FROM reclamation WHERE id=?";
        Reclamation r = null;
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id);
            try (ResultSet res = ps.executeQuery()) {
                if (res.next()) {
                    String nom = res.getString("nom");
                    String reclamation = res.getString("reclamation");
                    Date date = res.getDate("date");
                    String rating = res.getString("rating");
                    int idu = res.getInt("idu");

                    r = new Reclamation(id, nom, reclamation, date.toLocalDate(), rating);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return r;
    }

    @Override
    public HashSet<Reclamation> getAll() throws SQLException {
        String req = "SELECT * FROM reclamation";
        try (Statement st = cnx.createStatement();
             ResultSet res = st.executeQuery(req)) {

            HashSet<Reclamation> reclamations = new HashSet<>();

            while (res.next()) {
                int id = res.getInt("id");
                String reclamation = res.getString("reclamation");
                String nom = res.getString("nom");
                Date date = res.getDate("date");
                String rating = res.getString("rating");
                int idu = res.getInt("idu");

                Reclamation r = new Reclamation(id, nom, reclamation, date.toLocalDate(), rating);
                reclamations.add(r);
            }

            return reclamations;
        }
    }

    @Override
    public Reclamation getOne(int id) throws SQLException {
        return null;
    }
}
