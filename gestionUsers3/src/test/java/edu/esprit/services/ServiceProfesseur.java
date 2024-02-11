package edu.esprit.services;
import edu.esprit.entities.Administrateur;
import edu.esprit.entities.Parent;
import edu.esprit.entities.Professeur;
import edu.esprit.utilis.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ServiceProfesseur implements IService<Professeur>{
    Connection cnx = DataSource.getInsatnce().getConnection();
    @Override
    public void ajouter(Professeur pr) {
        String reqVerif = "SELECT COUNT(*) FROM `users` WHERE `nom` = ? AND `prenom` = ? AND `adresse` = ? AND `dob` = ? AND `login` = ?";
        String reqInsert = "INSERT INTO `users` (`nom`, `prenom`, `adresse`, `dob`, `login`, `tel`, `role`, `mdp`, `discipline`) VALUES (?,?,?,?,?,?,?,?,?)";

        try {
            // Vérification si les informations existent déjà
            PreparedStatement psVerif = cnx.prepareStatement(reqVerif);
            psVerif.setString(1, pr.getNom());
            psVerif.setString(2, pr.getPrenom());
            psVerif.setString(3, pr.getAdresse());
            psVerif.setString(4, pr.getDateNaissance());
            psVerif.setString(5, pr.getLogin());
            ResultSet rs = psVerif.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count > 0) {
                System.out.println("Les informations de l'administrateur existent déjà dans la base de données.");
                return; // Sortie de la méthode si les informations existent déjà
            }

            // Insertion des données si elles n'existent pas déjà
            PreparedStatement psInsert = cnx.prepareStatement(reqInsert);
            psInsert.setString(1, pr.getNom());
            psInsert.setString(2, pr.getPrenom());
            psInsert.setString(3, pr.getAdresse());
            psInsert.setString(4, pr.getDateNaissance());
            psInsert.setString(5, pr.getLogin());
            psInsert.setInt(6, pr.getTel());
            psInsert.setString(8, pr.getMdp());
            psInsert.setString(7, "Professeur");
            psInsert.setString(9, "NULL");

            psInsert.executeUpdate();
            System.out.println("Professeur ajouté avec succès !");

        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Professeur pr) {

        String req = "UPDATE `pidev2324`.`users` SET `nom`=?, `prenom`=?, `adresse`=?, `dob`=?, `login`=?, `tel`=?, `mdp`=?,`discipline`=? WHERE `users`.`idU`=?";

        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, pr.getNom());
            ps.setString(2, pr.getPrenom());
            ps.setString(3, pr.getAdresse());
            ps.setString(4, pr.getDateNaissance());
            ps.setString(5, pr.getLogin());
            ps.setInt(6, pr.getTel());
            ps.setString(7, pr.getMdp());
            ps.setString(8, pr.getDiscpline());
            ps.setInt(9, pr.getId()); // Suppose que l'objet Administrateur a une méthode getId() pour récupérer l'identifiant de l'administrateur

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Professeur "+pr.getId()+" mis à jour !");
            } else {
                System.out.println("Pas de Professeur trouvé avec cet ID.");
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM `users` WHERE `users`.`idU`=? AND `users`.`role`='Professeur'";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Professeur supprimé !");
            } else {
                System.out.println("Aucun Professeur trouvé avec l'ID spécifié.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public Professeur getOneById(int id) {

        Professeur pr = null;

        String query = "SELECT * FROM users WHERE idU = ? AND role = 'Professeur'";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    pr = new Professeur(
                            resultSet.getInt("idU"),
                            resultSet.getString("nom"),
                            resultSet.getString("prenom"),
                            resultSet.getString("adresse"),
                            resultSet.getString("dob"),
                            resultSet.getString("login"),
                            resultSet.getInt("tel"),
                            resultSet.getString("role"),
                            resultSet.getString("mdp")
                    );
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return pr;
    }


    @Override
    public Set<Professeur> getAll() {
        Set<Professeur> users = new HashSet<>();
        String req = "Select * from users WHERE `users`.`role`='Professeur'";
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
