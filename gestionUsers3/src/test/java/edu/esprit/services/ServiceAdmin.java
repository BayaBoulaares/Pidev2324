package edu.esprit.services;
import edu.esprit.entities.Administrateur;
import edu.esprit.entities.Parent;
import edu.esprit.entities.Professeur;
import edu.esprit.entities.Utilisateur;
import edu.esprit.utilis.DataSource;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceAdmin implements IService<Administrateur>{

    Connection cnx = DataSource.getInsatnce().getConnection();
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
            psVerif.setString(4, a.getDateNaissance());
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
            psInsert.setString(4, a.getDateNaissance());
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

        String req = "UPDATE `pidev2324`.`users` SET `nom`=?, `prenom`=?, `adresse`=?, `dob`=?, `login`=?, `tel`=?, `mdp`=? WHERE `users`.`idU`=?";

        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, a.getNom());
            ps.setString(2, a.getPrenom());
            ps.setString(3, a.getAdresse());
            ps.setString(4, a.getDateNaissance());
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

        String query = "SELECT * FROM users WHERE idU = ? AND role = 'Administrateur'";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    a = new Administrateur(
                            resultSet.getInt("idU"),
                            resultSet.getString("nom"),
                            resultSet.getString("prenom"),
                            resultSet.getString("adresse"),
                            resultSet.getString("dob"),
                            resultSet.getString("login"),
                            resultSet.getInt("tel"),
                            resultSet.getString("mdp")
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
    public Set<Administrateur> getAll() {
        Set<Administrateur> users = new HashSet<>();
        String req = "Select * from users WHERE `users`.`role`='Adminstrateur'";
        try{
            PreparedStatement ps = cnx.prepareStatement(req);
            ResultSet res = ps.executeQuery(req);
            while(res.next())
            {
                System.out.println(res.getString(1));
                System.out.println(res.getString(2));
                System.out.println(res.getString(3));
            }
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());

        }
        return users ;
    }
}
