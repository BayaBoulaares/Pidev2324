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
    public void modifier(Parent u) {

    }

    @Override
    public void supprimer(int id) {

    }

    @Override
    public Parent getOneById(int id) {
        return null;
    }

    @Override
    public Set<Parent> getAll() {
        return null;
    }
}
