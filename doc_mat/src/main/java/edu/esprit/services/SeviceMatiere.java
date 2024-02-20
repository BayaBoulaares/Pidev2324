package edu.esprit.services;

import edu.esprit.entities.Matiere;
import edu.esprit.entities.ExistanteException;
import edu.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SeviceMatiere implements IService<Matiere> {
    @Override
    public void ajouter(Matiere v) throws SQLException , ExistanteException {
        Connection cnx = DataSource.getInstance().getCnx();
        if (!matiereExists(v.getNommatiere())) {

            String req = "INSERT INTO matiere(nom_matiere, description) VALUES (?, ?)";
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setString(1, v.getNommatiere());
            pstmt.setString(2, v.getDescription());

            pstmt.executeUpdate();
            System.out.println("Matiere ajoutee avec succes");
        } else {
            System.out.println("Matiere avec le meme nom existe deja");
            throw new ExistanteException("Matiere  existe déjà");
        }

    }
    private boolean matiereExists(String nomMatiere) throws SQLException {
        Connection cnx = DataSource.getInstance().getCnx();
        String req = "SELECT * FROM matiere WHERE nom_matiere=?";
        PreparedStatement pstmt = cnx.prepareStatement(req);
        pstmt.setString(1, nomMatiere);
        ResultSet rs = pstmt.executeQuery();

        // Si une ligne est renvoyée, la matière existe déjà
        return rs.next();
    }

    @Override
    public void modifier(Matiere v)  throws SQLException{
        Connection cnx = DataSource.getInstance().getCnx();


            String req = "UPDATE matiere SET nom_matiere=?, description=? WHERE id=?";
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setString(1, v.getNommatiere());
            pstmt.setString(2, v.getDescription());
            pstmt.setInt(3, v.getId());

            pstmt.executeUpdate();
            System.out.println("Matiere modifiee avec succes");


    }

    @Override
    public void supprimer(int id) throws SQLException {
        Connection cnx = DataSource.getInstance().getCnx();


            String req = "DELETE FROM matiere WHERE id=?";
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setInt(1, id); // Utilisez l'identifiant passé en paramètre

            pstmt.executeUpdate();
            System.out.println("Matiere supprimee avec succes");


    }

    @Override
    public ArrayList<Matiere> getAll()  throws SQLException {
        Connection cnx = DataSource.getInstance().getCnx();
        ArrayList<Matiere> matieres = new ArrayList<>();

        String req = "SELECT * FROM matiere";
        PreparedStatement pstmt = cnx.prepareStatement(req);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) { // Move the cursor to the next row
            int id = rs.getInt("id");
            String nomMatiere = rs.getString("nom_matiere");
            String description = rs.getString("description");

            Matiere matiere = new Matiere(id, nomMatiere, description);
            matieres.add(matiere);
        }

        return matieres;
    }

    @Override
    public Matiere getOne(int id)  throws SQLException {
        Connection cnx = DataSource.getInstance().getCnx();
        Matiere matiere = null;


            String req = "SELECT * FROM matiere WHERE id=?";
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String nomMatiere = rs.getString("nom_matiere");
                String description = rs.getString("description");

                matiere = new Matiere(id, nomMatiere, description);
            }

        return matiere;
    }
    public  Matiere test(Matiere mat ) throws SQLException{
        ArrayList<Matiere> matiers = new ArrayList<>();

        matiers=getAll();
        for(Matiere  ma :matiers)
        {
            if ( ma != null && ma.equals(mat)) return mat;
        }
        return null;

    }
    public ArrayList<Matiere> getMatiereByAlphabet(String alphabet) throws SQLException {
        Connection cnx = DataSource.getInstance().getCnx();
        ArrayList<Matiere> matieresByAlphabet = new ArrayList<>();

        // Utiliser une requête SQL pour récupérer les matières par l'alphabet
        String req = "SELECT * FROM matiere WHERE UPPER(SUBSTRING(nom_matiere, 1, 1)) = ?";
        PreparedStatement pstmt = cnx.prepareStatement(req);
        pstmt.setString(1, alphabet.toUpperCase()); // Convertir la lettre en majuscule pour correspondance
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            String nomMatiere = rs.getString("nom_matiere");
            String description = rs.getString("description");

            Matiere matiere = new Matiere(id, nomMatiere, description);
            matieresByAlphabet.add(matiere);
        }

        return matieresByAlphabet;
    }
}
