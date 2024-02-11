package edu.esprit.services;
import edu.esprit.entities.Administrateur;
import edu.esprit.entities.Parent;
import edu.esprit.utilis.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

public class ServiceParent implements IService<Parent>{
    Connection cnx = DataSource.getInsatnce().getConnection();

    @Override
    public void ajouter(Parent pa) {

        String reqVerif = "SELECT COUNT(*) FROM `users` WHERE `nom` = ? AND `prenom` = ? AND `adresse` = ? AND `dob` = ? AND `login` = ?";
        String reqInsert = "INSERT INTO `users` (`nom`, `prenom`, `adresse`, `dob`, `login`, `tel`, `role`, `mdp`, `discipline`) VALUES (?,?,?,?,?,?,?,?,?)";

        try {
            // Vérification si les informations existent déjà
            PreparedStatement psVerif = cnx.prepareStatement(reqVerif);
            psVerif.setString(1, pa.getNom());
            psVerif.setString(2, pa.getPrenom());
            psVerif.setString(3, pa.getAdresse());
            psVerif.setString(4, pa.getDateNaissance());
            psVerif.setString(5, pa.getLogin());
            ResultSet rs = psVerif.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count > 0) {
                System.out.println("Les informations de l'administrateur existent déjà dans la base de données.");
                return; // Sortie de la méthode si les informations existent déjà
            }

            // Insertion des données si elles n'existent pas déjà
            PreparedStatement psInsert = cnx.prepareStatement(reqInsert);
            psInsert.setString(1, pa.getNom());
            psInsert.setString(2, pa.getPrenom());
            psInsert.setString(3, pa.getAdresse());
            psInsert.setString(4, pa.getDateNaissance());
            psInsert.setString(5, pa.getLogin());
            psInsert.setInt(6, pa.getTel());
            psInsert.setString(8, pa.getMdp());
            psInsert.setString(7, "Parent");
            psInsert.setString(9, "NULL");

            psInsert.executeUpdate();
            System.out.println("Parent ajouté avec succès !");

        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Parent pa) {

        String req = "UPDATE `pidev2324`.`users` SET `nom`=?, `prenom`=?, `adresse`=?, `dob`=?, `login`=?, `tel`=?, `mdp`=? WHERE `users`.`idU`=?";

        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, pa.getNom());
            ps.setString(2, pa.getPrenom());
            ps.setString(3, pa.getAdresse());
            ps.setString(4, pa.getDateNaissance());
            ps.setString(5, pa.getLogin());
            ps.setInt(6, pa.getTel());
            ps.setString(7, pa.getMdp());
            ps.setInt(8, pa.getId()); // Suppose que l'objet Administrateur a une méthode getId() pour récupérer l'identifiant de l'administrateur

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Parent "+pa.getId()+" mis à jour !");
            } else {
                System.out.println("Pas de parent trouvé avec cet ID.");
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void supprimer(int id) {

    }

    @Override
    public Parent getOneById(int id) {

        Parent pa = null;

        String query = "SELECT * FROM users WHERE idU = ? AND role = 'Parent'";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    pa = new Parent(
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
        return pa;
    }


    @Override
    public Set<Parent> getAll() {
        return null;
    }
}
