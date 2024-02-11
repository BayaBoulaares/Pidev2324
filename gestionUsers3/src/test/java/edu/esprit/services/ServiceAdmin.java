package edu.esprit.services;
import edu.esprit.entities.Administrateur;
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
    public void modifier(Administrateur u) {

    }

    @Override
    public void supprimer(int id) {

    }

    @Override
    public Administrateur getOneById(int id) {
        return null;
    }

    @Override
    public Set<Administrateur> getAll() {
        return null;
    }
}
