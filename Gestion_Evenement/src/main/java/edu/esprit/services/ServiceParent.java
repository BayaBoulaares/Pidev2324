package edu.esprit.services;

import edu.esprit.entities.ParentE;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class ServiceParent implements IService<ParentE> {
    Connection cnx = DataSource.getInstance().getCnx();

    // Méthode pour ajouter un parent à la base de données
    @Override
    public void ajouter(ParentE parent) throws SQLException {
        String sql = "INSERT INTO utilisateurs (nom, prenom, adresse, dob, tel, login, mdp, nomenfant, prenomenfant, dobenfant,role,discipline,niveau,image) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?)";
        PreparedStatement statement = cnx.prepareStatement(sql);
        statement.setString(1, parent.getNom());
        statement.setString(2, parent.getPrenom());
        statement.setString(3, parent.getAdresse());
        statement.setDate(4, new java.sql.Date(parent.getDateNaissance().getTime()));
        statement.setInt(5, parent.getTel());
        statement.setString(6, parent.getLogin());
        statement.setString(7, parent.getMdp());
        statement.setString(8, parent.getNomE());
        statement.setString(9, parent.getPrenomE());
        statement.setDate(10, new java.sql.Date(parent.getDateNaissanceE().getTime()));
        statement.setString(11, "Parent");
        statement.setString(12, "Null");
        statement.setInt(13, 0);
        statement.setString(14, parent.getImage());

        //statement.setInt(11, parent.getNiveau());
        statement.executeUpdate();
    }

    @Override

    public void modifier(ParentE parent) throws SQLException {

        // Obtenez l'ID de l'utilisateur à partir de l'objet ParentE
            try {
                String sql = "UPDATE utilisateurs SET nom=?, prenom=?, adresse=?, tel=?, dob=?, nomenfant=?, prenomenfant=?, dobenfant=?, image=? WHERE idu=? and role='Parent'";
                PreparedStatement statement = cnx.prepareStatement(sql);
                statement.setString(1, parent.getNom());
                statement.setString(2, parent.getPrenom());
                statement.setString(3, parent.getAdresse());
                statement.setDate(5, new java.sql.Date(parent.getDateNaissance().getTime()));
                statement.setInt(4, parent.getTel());
                //statement.setString(6, parent.getLogin());
                //statement.setString(7, parent.getMdp());
                statement.setString(6, parent.getNomE());
                statement.setString(7, parent.getPrenomE());
                statement.setDate(8, new java.sql.Date(parent.getDateNaissanceE().getTime()));
                //statement.setInt(11, parent.getNiveau());
                statement.setInt(10, parent.getId());
                statement.setString(9, parent.getImage());
                System.out.println(parent.getImage()+"imagz jdida");
                statement.executeUpdate();
                System.out.println("update parent ");

            }catch (SQLException e)
            {
                System.out.println(e.getMessage());
            }


    }

    // Méthode pour modifier un parent dans la base de données


    // Méthode pour supprimer un parent de la base de données en fonction de son identifiant
    @Override
    public void supprimer(int id)  {
        try {
            String sql = "DELETE FROM utilisateurs WHERE idu=? AND role='Parent'";
            PreparedStatement statement = cnx.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("spprimerserviceparent");
        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }


    // Méthode pour récupérer un parent de la base de données en fonction de son identifiant
    @Override
    public ParentE getOneById(int id) throws SQLException {
        String sql = "SELECT * FROM parents WHERE idU=?";
        PreparedStatement statement = cnx.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return new ParentE(
                    resultSet.getInt("id"),
                    resultSet.getString("nom"),
                    resultSet.getString("prenom"),
                    resultSet.getString("adresse"),
                    resultSet.getDate("date_naissance"),
                    resultSet.getInt("tel"),
                    resultSet.getString("login"),
                    resultSet.getString("mdp"),
                    resultSet.getString("nomE"),
                    resultSet.getString("prenomE"),
                    resultSet.getDate("date_naissance_enfant"),
                    resultSet.getInt("niveau"));
                    //String.valueOf(image));
        }
        return null;
    }

    // Méthode pour récupérer tous les parents de la base de données
    @Override
    public Collection<ParentE> getAll() throws SQLException {
        Collection<ParentE> parents = new ArrayList<>();
        String sql = "SELECT * FROM utilisateurs where role='Parent'";
        PreparedStatement statement = cnx.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            parents.add(new ParentE(
                    resultSet.getInt("idu"),
                    resultSet.getString("nom"),
                    resultSet.getString("prenom"),
                    resultSet.getString("adresse"),
                    resultSet.getDate("dob"),
                    resultSet.getInt("tel"),
                    resultSet.getString("login"),
                    resultSet.getString("mdp"),
                    resultSet.getString("nomenfant"),
                    resultSet.getString("prenomenfant"),
                    resultSet.getDate("dobenfant"),
                    resultSet.getInt("niveau")

            ));
        }
        return parents;
    }

    @Override
    public ParentE getOne(int id) throws SQLException {
        return null;
    }

    public int getIdUtilisateurParLogin(String login)  {
        String sql = "SELECT idu FROM utilisateurs WHERE login = ? and role='Parent'";
        try (PreparedStatement statement = cnx.prepareStatement(sql)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("idu");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception
        }
        return -1; // Retourne -1 si aucun utilisateur correspondant n'est trouvé
    }
    public String getRole(int userId) {
        // Logique pour récupérer le rôle de l'administrateur à partir de la base de données en utilisant l'ID
        // Vous devez remplacer ce code par votre propre logique
        // Supposons que vous ayez une table "administrateurs" avec une colonne "role"
        try {
            String req = "SELECT role FROM utilisateurs WHERE idu = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("role");
            } else {
                return "Parentee"; // Gérez le cas où le rôle n'est pas trouvé
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du rôle de l'administrateur : " + e.getMessage());
            return "N/A"; // Gérez les exceptions
        }
    }
    public int recupereId(String login) throws SQLException {
        String query = "SELECT idu FROM utilisateurs and role='Parent' WHERE login=?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        statement = cnx.prepareStatement(query);

        // Remplacez le '?' par les valeurs de nom et prénom spécifiées
        statement.setString(1, login);


        // Exécutez la requête SQL et obtenez un résultat
        resultSet = statement.executeQuery();

        // Si un résultat est trouvé, récupérez l'ID de l'utilisateur
        if (resultSet.next()) {
            int  userId = resultSet.getInt("idu");
            System.out.println("loulaamta recup id");
            return userId;
        }
        return -1;
    }
    public ParentE getByLogin(String login) {
        ParentE util = null;
        String sql = "SELECT idu, nom, prenom, adresse, dob, tel, login, mdp, nomenfant, prenomenfant, dobenfant, image from  utilisateurs WHERE role='Parent' and login = ? ";

        try (PreparedStatement statement = cnx.prepareStatement(sql)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("idu");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String adresse = resultSet.getString("adresse");
                Date dateNaissance = resultSet.getDate("dob");
                String mdp = resultSet.getString("mdp");
                int tel = resultSet.getInt("tel");


                        // System.out.println("bb");
                        String nomE = resultSet.getString("nomenfant");
                        System.out.println(nomE);
                        String prenomE = resultSet.getString("prenomenfant");
                        System.out.println(prenomE);
                        Date dateNaissanceE = resultSet.getDate("dobenfant");
                        //String image = resultSet.getString(12);

                        String image = resultSet.getString("image");
                        System.out.println(image);
                        System.out.println(dateNaissanceE);
                        //int niveau = resultSet.getInt("niveau");
                        util = new ParentE( id,  nom,  prenom,  adresse,  dateNaissance,  tel,  login,  mdp,  nomE,  prenomE, dateNaissanceE, image);
                        //utilisateur = new ParentE(id,nom, prenom, adresse, dateNaissance, tel, login, mdp, nomE, prenomE, dateNaissanceE,image);
                         System.out.println(util);




                System.out.println("got Parent : " + (ParentE)util);
            } else {
                System.out.println("Aucun utilisateur trouvé pour le login : " + login);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception
        }

        return util;
    }


}
