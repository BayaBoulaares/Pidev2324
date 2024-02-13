package edu.esprit.services;

import edu.esprit.entities.Matiere;
import edu.esprit.utils.DataSource;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SeviceMatiere implements IService<Matiere> {
    @Override
    public void ajouter(Matiere v) {
        Connection cnx = DataSource.getInstance().getCnx();

        try {
            String req = "INSERT INTO matiere(nom_matiere, description) VALUES (?, ?)";
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setString(1, v.getNommatiere());
            pstmt.setString(2, v.getDescription());

            pstmt.executeUpdate();
            System.out.println("Matiere ajoutee avec succes");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Matiere v) {
        Connection cnx = DataSource.getInstance().getCnx();

        try {
            String req = "UPDATE matiere SET nom_matiere=?, description=? WHERE id=?";
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setString(1, v.getNommatiere());
            pstmt.setString(2, v.getDescription());
            pstmt.setInt(3, v.getId());

            pstmt.executeUpdate();
            System.out.println("Matiere modifiee avec succes");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void supprimer(int id) {
        Connection cnx = DataSource.getInstance().getCnx();

        try {
            String req = "DELETE FROM matiere WHERE id=?";
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setInt(1, id); // Utilisez l'identifiant passé en paramètre

            pstmt.executeUpdate();
            System.out.println("Matiere supprimee avec succes");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public ArrayList<Matiere> getAll() {
        Connection cnx = DataSource.getInstance().getCnx();
        ArrayList<Matiere> matieres = new ArrayList<>();

        try {
            String req = "SELECT * FROM matiere";
            PreparedStatement pstmt = cnx.prepareStatement(req);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nomMatiere = rs.getString("nom_matiere");
                String description = rs.getString("description");

                Matiere matiere = new Matiere(id, nomMatiere, description);
                matieres.add(matiere);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return matieres;
    }

    @Override
    public Matiere getOne(int id) {
        Connection cnx = DataSource.getInstance().getCnx();
        Matiere matiere = null;

        try {
            String req = "SELECT * FROM matiere WHERE id=?";
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String nomMatiere = rs.getString("nom_matiere");
                String description = rs.getString("description");

                matiere = new Matiere(id, nomMatiere, description);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return matiere;
    }
}
