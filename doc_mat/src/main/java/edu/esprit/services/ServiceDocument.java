package edu.esprit.services;

import edu.esprit.entities.*;
import edu.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServiceDocument implements IService<Document> {

    private boolean docExists(String url, String titre) throws SQLException {
        Connection cnx = DataSource.getInstance().getCnx();
        String req = "SELECT * FROM document WHERE titre=? AND  url=?";
        PreparedStatement pstmt = cnx.prepareStatement(req);
        pstmt.setString(1, titre);
        pstmt.setString(2, url);
        ResultSet rs = pstmt.executeQuery();

        // Si une ligne est renvoyée, la matière existe déjà
        return rs.next();
    }
    @Override
    public void ajouter(Document v) throws SQLException , ExistanteException  {
        Connection cnx = DataSource.getInstance().getCnx();

        if (!docExists(v.getUrl(), v.getTitre())) {
            String req = "INSERT INTO document(titre,type,url,niveau,date,id_mat) VALUES (?, ?,?,?,?,?)";
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setString(1, v.getTitre());
            pstmt.setString(2, v.getType().toString());
            pstmt.setString(3, v.getUrl());
            pstmt.setString(4, v.getNiveau().toString());
            pstmt.setDate(5, v.getDate());
            pstmt.setInt(6, v.getMat().getId());
            pstmt.executeUpdate();
        } else {
            throw new ExistanteException("document existe déjà");}

    }
    @Override
    public void modifier(Document v) throws SQLException {
        Connection cnx = DataSource.getInstance().getCnx();


        String req = "UPDATE document SET titre=?, type=?, url=?, niveau=?, date=? WHERE id_doc=?";
        PreparedStatement pstmt = cnx.prepareStatement(req);
        pstmt.setString(1, v.getTitre());
        pstmt.setString(2, v.getType().toString());
        pstmt.setString(3, v.getUrl());
        pstmt.setString(4, v.getNiveau().toString());
        pstmt.setDate(5, v.getDate());

        pstmt.setInt(6, v.getId());

        pstmt.executeUpdate();


    }

    @Override
    public void supprimer(int id) throws  SQLException{
        Connection cnx = DataSource.getInstance().getCnx();


        String req = "DELETE FROM document WHERE id_doc=?";
        PreparedStatement pstmt = cnx.prepareStatement(req);
        pstmt.setInt(1, id);

        pstmt.executeUpdate();
        System.out.println("Document supprimé avec succès");


    }


    @Override
    public ArrayList<Document> getAll()  throws SQLException {
        Connection cnx = DataSource.getInstance().getCnx();
        ArrayList<Document> documents = new ArrayList<>();


        String req = "SELECT * FROM document";
        PreparedStatement pstmt = cnx.prepareStatement(req);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id_doc");
            String titre = rs.getString("titre");
            String typeStr = rs.getString("type");
            String url = rs.getString("url");
            String niveauStr = rs.getString("niveau");
            java.sql.Date date = rs.getDate("date");
            int id_mat = rs.getInt("id_mat");

            // Convertir les types String en Enum
            Type type = Type.valueOf(typeStr);
            Niveau niveau = Niveau.valueOf(niveauStr);
          SeviceMatiere ms=new SeviceMatiere();
             // Créer un objet Document
            Matiere matiere = ms.getOne(id_mat); // Vous devez probablement récupérer la Matiere à partir de la base de données
            Document document = new Document(id, titre, type, url, niveau, date, matiere);
            documents.add(document);
        }


        return documents;
    }

    @Override
    public Document getOne(int id)  throws SQLException{
        Connection cnx = DataSource.getInstance().getCnx();
        Document document = null;


        String req = "SELECT * FROM document WHERE id_doc=?";
        PreparedStatement pstmt = cnx.prepareStatement(req);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            String titre = rs.getString("titre");
            String typeStr = rs.getString("type");
            String url = rs.getString("url");
            String niveauStr = rs.getString("niveau");
            java.sql.Date date = rs.getDate("date");
            int id_mat = rs.getInt("id_mat");

            // Convertir les types String en Enum
            Type type = Type.valueOf(typeStr);
            Niveau niveau = Niveau.valueOf(niveauStr);

            SeviceMatiere ms=new SeviceMatiere();
            // Créer un objet Document
            Matiere matiere = ms.getOne(id_mat); // Vous devez probablement récupérer la Matiere à partir de la base de données
            document = new Document(id, titre, type, url, niveau, date, matiere);
        }


        return document;
    }
    public ArrayList<Document> getByLevel(Niveau niveau , Matiere mat) throws SQLException {
        Connection cnx = DataSource.getInstance().getCnx();
        ArrayList<Document> documents = new ArrayList<>();

        String req = "SELECT * FROM document WHERE niveau=? AND  id_mat=?";
        PreparedStatement pstmt = cnx.prepareStatement(req);
        pstmt.setString(1, niveau.toString());
        pstmt.setInt(2,mat.getId());
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id_doc");
            String titre = rs.getString("titre");
            String typeStr = rs.getString("type");
            String url = rs.getString("url");
            java.sql.Date date = rs.getDate("date");
            int id_mat = rs.getInt("id_mat");

            // Convertir les types String en Enum
            Type type = Type.valueOf(typeStr);

            SeviceMatiere ms=new SeviceMatiere();
            // Créer un objet Document
            Matiere matiere = ms.getOne(id_mat); // Vous devez probablement récupérer la Matiere à partir de la base de données
            Document document = new Document(id, titre, type, url, niveau, date, matiere);
            documents.add(document);
        }

        return documents;
    }
    public int getDocumentCountPerMatiere(Matiere mat)  {
        Connection cnx = DataSource.getInstance().getCnx();
        int uniqueMatiereCount = 0;
        try {


        String req = "SELECT COUNT(*) as doc_count FROM document WHERE id_mat=?";
        try (PreparedStatement pstmt = cnx.prepareStatement(req)) {
            pstmt.setInt(1, mat.getId());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                uniqueMatiereCount = rs.getInt("doc_count");
            }
        }  } catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return uniqueMatiereCount;
    }

    public ArrayList<Document> getAllTitle() throws SQLException {
        Connection cnx = DataSource.getInstance().getCnx();
        ArrayList<Document> documents = new ArrayList<>();

        String req = "SELECT * FROM document ORDER BY titre DESC"; // Added ORDER BY clause for sorting by date
        try (PreparedStatement pstmt = cnx.prepareStatement(req); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id_doc");
                String titre = rs.getString("titre");
                String typeStr = rs.getString("type");
                String url = rs.getString("url");
                String niveauStr = rs.getString("niveau");
                java.sql.Date date = rs.getDate("date");
                int id_mat = rs.getInt("id_mat");

                // Convertir les types String en Enum
                Type type = Type.valueOf(typeStr);
                Niveau niveau = Niveau.valueOf(niveauStr);
                SeviceMatiere ms = new SeviceMatiere();

                // Créer un objet Document
                Matiere matiere = ms.getOne(id_mat); // Vous devez probablement récupérer la Matiere à partir de la base de données
                Document document = new Document(id, titre, type, url, niveau, date, matiere);
                documents.add(document);
            }
        }

        return documents;
    }
    public ArrayList<Document> getByDate(java.sql.Date date,Matiere mat) throws SQLException {
        Connection cnx = DataSource.getInstance().getCnx();
        ArrayList<Document> documents = new ArrayList<>();

        String req = "SELECT * FROM document WHERE date=? AND id_mat=?";
        try (PreparedStatement pstmt = cnx.prepareStatement(req)) {
            pstmt.setDate(1, date);
            pstmt.setInt(2, mat.getId());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_doc");
                String titre = rs.getString("titre");
                String typeStr = rs.getString("type");
                String url = rs.getString("url");
                String niveauStr = rs.getString("niveau");
                java.sql.Date date2 = rs.getDate("date");
                int id_mat = rs.getInt("id_mat");

                // Convertir les types String en Enum
                Type type = Type.valueOf(typeStr);
                Niveau niveau = Niveau.valueOf(niveauStr);
                SeviceMatiere ms = new SeviceMatiere();

                // Créer un objet Document
                Matiere matiere = ms.getOne(id_mat); // Vous devez probablement récupérer la Matiere à partir de la base de données
                Document document = new Document(id, titre, type, url, niveau, date2, matiere);
                documents.add(document);
            }
        }

        return documents;
    }
     public  ArrayList<Document> getAllByMatiere(Matiere mat)  throws SQLException{
         Connection cnx = DataSource.getInstance().getCnx();
         ArrayList<Document> documents = new ArrayList<>();

         String req = "SELECT * FROM document WHERE id_mat=?";
         try (PreparedStatement pstmt = cnx.prepareStatement(req)) {
             pstmt.setInt(1, mat.getId());
             ResultSet rs = pstmt.executeQuery();

             while (rs.next()) {
                 int id = rs.getInt("id_doc");
                 String titre = rs.getString("titre");
                 String typeStr = rs.getString("type");
                 String url = rs.getString("url");
                 String niveauStr = rs.getString("niveau");
                 java.sql.Date date2 = rs.getDate("date");
                 int id_mat = rs.getInt("id_mat");

                 // Convertir les types String en Enum
                 Type type = Type.valueOf(typeStr);
                 Niveau niveau = Niveau.valueOf(niveauStr);
                 SeviceMatiere ms = new SeviceMatiere();

                 // Créer un objet Document
                 Matiere matiere = ms.getOne(id_mat); // Vous devez probablement récupérer la Matiere à partir de la base de données
                 Document document = new Document(id, titre, type, url, niveau, date2, matiere);
                 documents.add(document);
             }
         }

         return documents;
     }
}
