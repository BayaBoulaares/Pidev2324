package edu.esprit.services;

import edu.esprit.entities.CAT;
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
        if (!matiereExists(v.getNommatiere(),v.getAnnee())) {

            String req = "INSERT INTO matiere(nom_matiere, description,annee,categorie) VALUES (?, ?, ?,?)";
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setString(1, v.getNommatiere());
            pstmt.setString(2, v.getDescription());
            pstmt.setString(3, v.getAnnee());
            pstmt.setString(4, v.getCategorie().toString());
            pstmt.executeUpdate();
            System.out.println("Matiere ajoutee avec succes");
        } else {
            System.out.println("Matiere avec le meme nom existe deja");
            throw new ExistanteException("Matiere  existe déjà");
        }

    }
    private boolean matiereExists(String nomMatiere , String annee) throws SQLException {
        Connection cnx = DataSource.getInstance().getCnx();
        String req = "SELECT * FROM matiere WHERE nom_matiere=? AND annee=?";
        PreparedStatement pstmt = cnx.prepareStatement(req);
        pstmt.setString(1, nomMatiere);
        pstmt.setString(2, annee);
        ResultSet rs = pstmt.executeQuery();

        // Si une ligne est renvoyée, la matière existe déjà
        return rs.next();
    }

    @Override
    public void modifier(Matiere v)  throws SQLException,ExistanteException{
        Connection cnx = DataSource.getInstance().getCnx();

        if (!matiereExists(v.getNommatiere(),v.getAnnee())) {
            String req = "UPDATE matiere SET nom_matiere=?, description=?, annee=? , categorie=? WHERE id=?";
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setString(1, v.getNommatiere());
            pstmt.setString(2, v.getDescription());
        pstmt.setString(3, v.getAnnee());
        pstmt.setString(4, v.getCategorie().toString());
            pstmt.setInt(5, v.getId());

            pstmt.executeUpdate();
            System.out.println("Matiere modifiee avec succes");
        } else {
            System.out.println("Matiere avec le meme nom existe deja");
            throw new ExistanteException("Matiere  existe déjà");
        }


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
            String annee= rs.getString("annee");
            String categorie=rs.getString("categorie");
            CAT cat;
            if(categorie.isEmpty())
            {  cat = null;} else
            { cat = CAT.valueOf(categorie);}
            Matiere matiere = new Matiere(id, nomMatiere, description, annee, cat);
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
                String annee=rs.getString("annee");
                String categorie=rs.getString("categorie");
                CAT cat=CAT.valueOf(categorie);

                matiere = new Matiere(id, nomMatiere, description,annee,cat);
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
            String annee =rs.getString("annee");
            String categorie=rs.getString("categorie");
            CAT cat=CAT.valueOf(categorie);
            Matiere matiere = new Matiere(id, nomMatiere, description,annee,cat);
            matieresByAlphabet.add(matiere);
        }

        return matieresByAlphabet;
    }

    public ArrayList<Matiere> getOneByAnnee(String annee)  throws SQLException {
        Connection cnx = DataSource.getInstance().getCnx();
        ArrayList<Matiere> matieresByAnnee = new ArrayList<>();


        String req = "SELECT * FROM matiere WHERE annee =?";
        PreparedStatement pstmt = cnx.prepareStatement(req);
        pstmt.setString(1, annee);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            String nomMatiere = rs.getString("nom_matiere");
            String description = rs.getString("description");
            String anne=rs.getString("annee");
            String categorie=rs.getString("categorie");
            CAT cat=CAT.valueOf(categorie);

            Matiere matiere = new Matiere(id,nomMatiere, description,anne,cat);
            matieresByAnnee.add(matiere);
        }

        return matieresByAnnee;
    }

}
