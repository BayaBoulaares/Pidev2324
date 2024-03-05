package edu.esprit.crudoff.services;

import edu.esprit.crudoff.entities.Professeur;
import edu.esprit.crudoff.utilis.DataSource;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class ServiceProfesseur implements IService<Professeur> {
    Connection cnx = DataSource.getInsatnce().getConnection();
    @Override
    public void ajouter(Professeur u) throws SQLException {
        String sql = "INSERT INTO utilisateurs (nom, prenom, adresse, dob, tel, login, mdp,role,discipline,nomenfant,prenomenfant,dobenfant,niveau) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?)";
        String hashedPassword = u.getMdp();
        try {
            hashedPassword = hashString("password");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Hashed password: " + hashedPassword);
        PreparedStatement statement = cnx.prepareStatement(sql);
        statement.setString(1, u.getNom());
        statement.setString(2, u.getPrenom());
        statement.setString(3, u.getAdresse());
        statement.setDate(4, new java.sql.Date(u.getDateNaissance().getTime()));
        statement.setInt(5, u.getTel());
        statement.setString(6, u.getLogin());
        statement.setString(7,hashedPassword);
        statement.setString(8, "Professeur");
        statement.setString(9, u.getDiscpline());
        statement.setString(10, "null");
        statement.setString(11, "null");
        statement.setNull(12, java.sql.Types.DATE);
        statement.setInt(13,0);



        //statement.setInt(11, parent.getNiveau());
        statement.executeUpdate();

    }

    @Override
    public void modifier(Professeur p) throws SQLException {
        int userId =  p.getId();
        String req = "UPDATE utilisateurs SET nom=?, prenom=?, adresse=?, tel=?, login=?, mdp=?, dob=?, discipline=? WHERE idu=? and role='Professeur'";
        String hashedPassword = p.getMdp();
        try {
            hashedPassword = hashString("password");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, p.getNom());
            ps.setString(2, p.getPrenom());
            ps.setString(3, p.getAdresse());
            // Supposons que vous avez déjà créé votre objet Professeur et que vous l'avez appelé "professeur"
            Date dateNaissance = p.getDateNaissance(); // Récupération de la date de naissance de l'objet Professeur

            // Convertir la date de naissance en java.sql.Date
            java.sql.Date sqlDateNaissance = new java.sql.Date(dateNaissance.getTime());

            // Maintenant, vous pouvez définir la date de naissance dans votre PreparedStatement
            ps.setDate(7, sqlDateNaissance);
            //ps.setDate(4, new java.sql.Date(ps.getDateNaissance().getTime()));*/

            ps.setString(5, p.getLogin());
            ps.setString(6, hashedPassword);
            ps.setInt(4, p.getTel());
            //ps.setString(5, p.getMdp());
            ps.setString(8, p.getDiscpline());
            ps.setInt(9, p.getId()); // Suppose que l'objet Administrateur a une méthode getId() pour récupérer l'identifiant de l'administrateur

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Prof "+p.getId()+" mis à jour !");
            } else {
                System.out.println("Pas de Prof trouvé avec cet ID.");
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void supprimer(int id) throws SQLException {
        try {
            String sql = "DELETE FROM utilisateurs WHERE idu=? AND role='Professeur'";
            PreparedStatement statement = cnx.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("spprimerserviceprof");
        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public Professeur getOneById(int id) throws SQLException {

        return null;
    }

    @Override
    public Collection<Professeur> getAll() {
        List<Professeur> users = new ArrayList<>();
        Professeur prof;
        String req = "SELECT * FROM utilisateurs where role='Professeur'";
        try{
            PreparedStatement ps = cnx.prepareStatement(req);
            ResultSet res = ps.executeQuery(req);
            while(res.next())
            {
                /*System.out.println(res.getString(1));
                System.out.println(res.getString(2));
                System.out.println(res.getString(3));
                System.out.println(res.getString(4));
                System.out.println(res.getString(5));
                System.out.println(res.getString(6));
                System.out.println(res.getString(7));
                System.out.println(res.getString(8));*/
                int id = res.getInt("idu");
                String nom = res.getString("nom");
                System.out.println(res.getString("nom"));
                String prenom = res.getString("prenom");
                System.out.println(res.getString("prenom"));
                String adresse = res.getString("adresse");
                System.out.println(res.getString("adresse"));
                String login = res.getString("login");
                System.out.println(res.getString("login"));
                Date dateNaissance = res.getDate("dob");
                System.out.println(res.getDate("dob"));
                int tel = res.getInt("tel");
                System.out.println(res.getInt("tel"));
                String role = res.getString("role");
                String disc = res.getString("discipline");
                if (role.equals("Professeur")  ) {
                    // Si vous avez une classe Professeur, instanciez-la ici
                    prof = new Professeur(nom, prenom, adresse, dateNaissance, tel, login,disc);
                    users.add(prof);
                }



            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());

        }
        return users ;
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
                return "Professeur"; // Gérez le cas où le rôle n'est pas trouvé
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du rôle de l'administrateur : " + e.getMessage());
            return "N/A"; // Gérez les exceptions
        }
    }
    public int recupereId(String login) throws SQLException {
        String query = "SELECT idu FROM utilisateurs WHERE login=? and role='Professeur'";
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
    public String obtenirRole(int idUtilisateur) throws SQLException {
        String role = null;
        // Établir la connexion à la base de données et exécuter la requête pour obtenir le rôle de l'utilisateur
        String req = "SELECT role FROM utilisateurs WHERE idu = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, idUtilisateur);
        ResultSet rs = ps.executeQuery();

        // Ajouter des instructions de débogage
        int rowCount = 0;
        while (rs.next()) {
            rowCount++;
            role = rs.getString("role");
            System.out.println("Role trouvé pour l'utilisateur avec ID " + idUtilisateur + ": " + role);
        }
        System.out.println("Nombre de lignes retournées : " + rowCount);

        return role;
    }
    public Professeur getByLogin(String login) {
        Professeur util = null;
        String sql = "SELECT idu, nom, prenom, adresse, dob, tel, login, mdp, discipline from  utilisateurs WHERE role='Professeur' and login = ? ";

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
                String discipline = resultSet.getString("discipline");


                util = new Professeur( id,  nom,  prenom,  adresse,  dateNaissance,  tel,  login,  mdp,discipline);
                //utilisateur = new ParentE(id,nom, prenom, adresse, dateNaissance, tel, login, mdp, nomE, prenomE, dateNaissanceE,image);
                System.out.println(util);




                System.out.println("got Parent : " + util);
            } else {
                System.out.println("Aucun utilisateur trouvé pour le login : " + login);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception
        }

        return util;
    }
    public static String hashString(String input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(input.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
