package edu.esprit.services;

import edu.esprit.entities.*;
import edu.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SeviceMatiere implements IService<Matiere> {
    @Override
    public void ajouter(Matiere v) throws SQLException , ExistanteException {
        Connection cnx = DataSource.getInstance().getCnx();
        if (!matiereExists(v.getNommatiere(),v.getAnnee())) {

            String req = "INSERT INTO matiere(nom_matiere, description,annee,categorie,idu) VALUES (?, ?, ?,?,?)";
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setString(1, v.getNommatiere());
            pstmt.setString(2, v.getDescription());
            pstmt.setString(3, v.getAnnee());
            pstmt.setString(4, v.getCategorie().toString());
            pstmt.setInt(5,v.getProf().getId());
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
    public Collection<Matiere> getAll()  throws SQLException {
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
            int  idprof=rs.getInt("idu");
            Professeur pf=getOneProfesseur(idprof);
            System.out.println("895642jtoqjt");
            System.out.println(pf);
            if(categorie.isEmpty())
            {  cat = null;} else
            { cat = CAT.valueOf(categorie);}
            Matiere matiere = new Matiere(id, nomMatiere, description, annee, cat,pf);
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
                int  idprof=rs.getInt("idu");
                ServiceProfesseur sp= new ServiceProfesseur();
                Professeur pf=sp.getOne(idprof);

                matiere = new Matiere(id, nomMatiere, description,annee,cat,pf);
            }

        return matiere;
    }

    @Override
    public Matiere getOneById(int id) throws SQLException {
        return null;
    }

    public  Matiere test(Matiere mat ) throws SQLException{
        ArrayList<Matiere> matiers = new ArrayList<>();

        matiers= (ArrayList<Matiere>) getAll();
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
            int  idprof=rs.getInt("idu");
            ServiceProfesseur sp= new ServiceProfesseur();
            Professeur pf=sp.getOne(idprof);
            Matiere matiere = new Matiere(id, nomMatiere, description,annee,cat,pf);
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
            int  idprof=rs.getInt("idu");
            ServiceProfesseur sp= new ServiceProfesseur();
            Professeur pf=sp.getOne(idprof);
            Matiere matiere = new Matiere(id,nomMatiere, description,anne,cat,pf);
            matieresByAnnee.add(matiere);
        }

        return matieresByAnnee;
    }
  public  void ajouterIdMatierzIduser(Matiere mat, ParentE ef) throws SQLException
  {
      Connection cnx = DataSource.getInstance().getCnx();
      String req = "INSERT INTO consultation(idmat,idut) VALUES (?, ?)";
      PreparedStatement pstmt = cnx.prepareStatement(req);
      pstmt.setInt(1, mat.getId());
      pstmt.setInt(2,ef.getId());

      pstmt.executeUpdate();
      System.out.println("Matiere ajoutee avec succes");

  }
  public Professeur getOneProfesseur(int id)
  {
      ServiceProfesseur ps=new ServiceProfesseur();
      Professeur pf=new Professeur();
      List<Professeur> pfs= (List<Professeur>) ps.getAll();
      for(Professeur pe:pfs)
      {
          if( pe!=null && pe.getId()==id)
          {
              return pe;
          }

      }

      return pf;
  }

}
