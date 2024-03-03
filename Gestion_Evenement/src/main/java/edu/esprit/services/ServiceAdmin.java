package edu.esprit.services;

import edu.esprit.entities.Administrateur;
import edu.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ServiceAdmin implements IService<Administrateur>{
    Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void ajouter(Administrateur a) {
        String reqVerif = "SELECT COUNT(*) FROM `users` WHERE `nom` = ? AND `prenom` = ? AND `adresse` = ? AND `dob` = ? AND `login` = ?";
        String reqInsert = "INSERT INTO `users` (`nom`, `prenom`, `adresse`, `dob`, `login`, `tel`, `role`, `mdp`, `discipline`) VALUES (?,?,?,?,?,?,?,?,?)";

        try {
            // Vérification si les informations existent déjà
            PreparedStatement psVerif = cnx.prepareStatement(reqVerif);
            psVerif.setString(1, a.getNom());
            psVerif.setString(2, a.getPrenom());
            psVerif.setString(3, a.getAdresse());
            //psVerif.setDate(4,java.sql.Date.valueOf(a.getDateNaissance()));
            psVerif.setString(5, a.getLogin());
            ResultSet rs = psVerif.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count > 0) {
                System.out.println("Les informations de l'administrateur existent déjà dans la base de données.");
                return; // Sortie de la méthode si les informations existent déjà
            }

            // Insertion des données si elles n'existent pas déjà
            PreparedStatement psInsert = cnx.prepareStatement(reqInsert);
            psInsert.setString(1, a.getNom());
            psInsert.setString(2, a.getPrenom());
            psInsert.setString(3, a.getAdresse());
            //psInsert.setDate(4, java.sql.Date.valueOf(a.getDateNaissance()));
            psInsert.setString(5, a.getLogin());
            psInsert.setInt(6, a.getTel());
            psInsert.setString(8, a.getMdp());
            psInsert.setString(7, "Administrateur");
            psInsert.setString(9, "NULL");

            psInsert.executeUpdate();
            System.out.println("Administrateur ajouté avec succès !");

        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void modifier(Administrateur a) {

        String req = "UPDATE utilisateurs SET nom=?, prenom=?, adresse=?, dob=?, login=?, tel=?, mdp=? WHERE idu=?";

        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, a.getNom());
            ps.setString(2, a.getPrenom());
            ps.setString(3, a.getAdresse());
            //ps.setDate(4, java.sql.Date.valueOf(a.getDateNaissance()));
            ps.setString(5, a.getLogin());
            ps.setInt(6, a.getTel());
            ps.setString(7, a.getMdp());
            ps.setInt(8, a.getId()); // Suppose que l'objet Administrateur a une méthode getId() pour récupérer l'identifiant de l'administrateur

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Administarteur "+a.getId()+" mis à jour !");
            } else {
                System.out.println("Pas de Administarteur trouvé avec cet ID.");
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void supprimer(int idAdministrateur) {
        String req = "DELETE FROM `users` WHERE `users`.`idU`=? AND `users`.`role`='Administrateur'";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, idAdministrateur);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Administrateur supprimé !");
            } else {
                System.out.println("Aucun administrateur trouvé avec l'ID spécifié.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public Administrateur getOneById(int id) {

        Administrateur a = null;

        String query = "SELECT * FROM utilisateurs WHERE idu = ? AND role = 'Administrateur'";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    a = new Administrateur(
                            resultSet.getInt("idu"),
                            resultSet.getString("nom"),
                            resultSet.getString("prenom"),
                            resultSet.getString("adresse"),
                            resultSet.getDate("dob"),
                            resultSet.getInt("tel"),
                            resultSet.getString("login")

                            //resultSet.getString("mdp")
                    );
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

    @Override
    public Collection<Administrateur> getAll() {
        List<Administrateur> users = new ArrayList<>();
        String req = "Select nom,prenom,dob,adresse,tel,login,role from utilisateurs WHERE `utilisateurs`.`role`='Adminstrateur'";
        try{
            PreparedStatement ps = cnx.prepareStatement(req);
            ResultSet res = ps.executeQuery(req);
            while(res.next())
            {
                System.out.println(res.getString(1));
                System.out.println(res.getString(2));
                System.out.println(res.getString(3));
                System.out.println(res.getString(4));
                System.out.println(res.getString(5));
                System.out.println(res.getString(6));
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());

        }
        return users ;
    }

    @Override
    public Administrateur getOne(int id) throws SQLException {
        return null;
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
                return "Administrateur"; // Gérez le cas où le rôle n'est pas trouvé
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du rôle de l'administrateur : " + e.getMessage());
            return "N/A"; // Gérez les exceptions
        }
    }
    public int recupereId(String login) throws SQLException {
        String query = "SELECT idu FROM utilisateurs WHERE login=?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        statement = cnx.prepareStatement(query);

        // Remplacez le '?' par les valeurs de nom et prénom spécifiées
        statement.setString(1, login);


        // Exécutez la requête SQL et obtenez un résultat
        resultSet = statement.executeQuery();

        // Si un résultat est trouvé, récupérez l'ID de l'utilisateur
        if (resultSet.next()) {
          int  userId = resultSet.getInt("id");
          return userId;
        }
        return -1;
    }
}
