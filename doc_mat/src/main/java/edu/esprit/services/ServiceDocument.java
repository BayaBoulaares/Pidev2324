package edu.esprit.services;

import edu.esprit.entities.Document;
import edu.esprit.entities.Matiere;
import edu.esprit.entities.Niveau;
import edu.esprit.entities.Type;
import edu.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServiceDocument implements IService<Document> {
    @Override
    public void ajouter(Document v) {
        Connection cnx = DataSource.getInstance().getCnx();

        try {
            String req = "INSERT INTO document(titre,type,url,niveau,date,id_mat) VALUES (?, ?,?,?,?,?)";
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setString(1, v.getTitre());
            pstmt.setString(2, v.getType().toString());
            pstmt.setString(3, v.getUrl());
            pstmt.setString(4, v.getNiveau().toString());
            pstmt.setDate(5, v.getDate());
            pstmt.setInt(6, v.getMat().getId());
            pstmt.executeUpdate();
            System.out.println("Document ajoutee avec succes");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }
    @Override
    public void modifier(Document v) {
        Connection cnx = DataSource.getInstance().getCnx();

        try {
            String req = "UPDATE document SET titre=?, type=?, url=?, niveau=?, date=?, id_mat=? WHERE id_doc=?";
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setString(1, v.getTitre());
            pstmt.setString(2, v.getType().toString());
            pstmt.setString(3, v.getUrl());
            pstmt.setString(4, v.getNiveau().toString());
            pstmt.setDate(5, v.getDate());
            pstmt.setInt(6, v.getMat().getId());
            pstmt.setInt(7, v.getId());

            pstmt.executeUpdate();
            System.out.println("Document modifié avec succès");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void supprimer(int id){
        Connection cnx = DataSource.getInstance().getCnx();

        try {
            String req = "DELETE FROM document WHERE id_doc=?";
            PreparedStatement pstmt = cnx.prepareStatement(req);
            pstmt.setInt(1, id);

            pstmt.executeUpdate();
            System.out.println("Document supprimé avec succès");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public ArrayList<Document> getAll() {
        Connection cnx = DataSource.getInstance().getCnx();
        ArrayList<Document> documents = new ArrayList<>();

        try {
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

                // Créer un objet Document
                Matiere matiere = new Matiere(id_mat); // Vous devez probablement récupérer la Matiere à partir de la base de données
                Document document = new Document(id, titre, type, url, niveau, date, matiere);
                documents.add(document);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return documents;
    }

    @Override
    public Document getOne(int id) {
        Connection cnx = DataSource.getInstance().getCnx();
        Document document = null;

        try {
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

                // Créer un objet Document
                Matiere matiere = new Matiere(id_mat); // Vous devez probablement récupérer la Matiere à partir de la base de données
                document = new Document(id, titre, type, url, niveau, date, matiere);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return document;
    }
}
