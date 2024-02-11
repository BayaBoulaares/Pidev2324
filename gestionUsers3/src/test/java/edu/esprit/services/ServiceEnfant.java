package edu.esprit.services;
import edu.esprit.entities.Administrateur;
import edu.esprit.entities.Enfant;
import edu.esprit.entities.Parent;
import edu.esprit.utilis.DataSource;
import java.sql.*;
import java.util.Set;

public class ServiceEnfant {
    Connection cnx = DataSource.getInsatnce().getConnection();

    public void ajouter(Enfant e, Parent p) {
        // Insérer d'abord le parent s'il n'existe pas déjà
        String selectUserQuery = "SELECT idU FROM users WHERE nom = ? AND prenom = ? AND adresse = ? AND dob = ? AND login = ? AND role = 'Parent'";
        String insertParentQuery = "INSERT INTO users (`nom`, `prenom`, `adresse`, `dob`, `login`, `tel`, `role`, `mdp`, `discipline`) VALUES (?, ?,?,?,?,?,?,?,?)";
        // Insérer l'enfant avec l'ID du parent correspondant
        String insertEnfantQuery = "INSERT INTO enfant (idE, nom, prenom, niveau, dob) VALUES (?, ?, ?, ?, ?)";

        try {
            // Vérifier si le parent avec ces informations existe
            PreparedStatement selectParentPs = cnx.prepareStatement(selectUserQuery);
            selectParentPs.setString(1, p.getNom());
            selectParentPs.setString(2, p.getPrenom());
            selectParentPs.setString(3, p.getAdresse());
            selectParentPs.setString(4, p.getDateNaissance());
            selectParentPs.setString(5, p.getLogin());
            ResultSet parentResult = selectParentPs.executeQuery();

            int parentId;
            if (parentResult.next()) {
                // Le parent existe, obtenir son ID
                parentId = parentResult.getInt("idU");
            } else {
                // Le parent n'existe pas, l'insérer d'abord dans la base de données
                PreparedStatement insertParentPs = cnx.prepareStatement(insertParentQuery, Statement.RETURN_GENERATED_KEYS);
                insertParentPs.setString(1, p.getNom());
                insertParentPs.setString(2, p.getPrenom());
                insertParentPs.setString(3, p.getAdresse());
                insertParentPs.setString(4, p.getDateNaissance());
                insertParentPs.setString(5, p.getLogin());
                insertParentPs.setInt(6, p.getTel());
                insertParentPs.setString(7, "Parent");
                insertParentPs.setString(9, "Null");
                insertParentPs.setString(8, p.getMdp());
                insertParentPs.executeUpdate();

                // Récupérer l'ID généré du parent nouvellement inséré
                ResultSet generatedKeys = insertParentPs.getGeneratedKeys();
                if (generatedKeys.next()) {
                    parentId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Échec de la récupération de l'ID généré du parent.");
                }
            }

            // Insérer l'enfant avec l'ID du parent
            PreparedStatement insertEnfantPs = cnx.prepareStatement(insertEnfantQuery);
            insertEnfantPs.setInt(1, parentId); // Utiliser l'ID du parent correspondant
            insertEnfantPs.setString(2, e.getNom());
            insertEnfantPs.setString(3, e.getPrenom());
            insertEnfantPs.setInt(4, e.getNiveau());
            insertEnfantPs.setString(5, e.getDateNaissance());
            insertEnfantPs.executeUpdate();

            System.out.println("Enfant ajouté avec succès !");
        } catch (SQLException ex) {
            System.out.println("Les informations de l'administrateur existent déjà dans la base de données : " + ex.getMessage());
        }
    }
    public void modifier(Enfant e) {
        String updateEnfantQuery = "UPDATE enfant SET nom = ?, prenom = ?, niveau = ?, dob = ? WHERE idE = ?";

        try {
            // Mettre à jour les informations de l'enfant
            PreparedStatement updateEnfantPs = cnx.prepareStatement(updateEnfantQuery);
            updateEnfantPs.setString(1, e.getNom());
            updateEnfantPs.setString(2, e.getPrenom());
            updateEnfantPs.setInt(3, e.getNiveau());
            updateEnfantPs.setString(4, e.getDateNaissance());
            updateEnfantPs.setInt(5, e.getIdE()); // Supposons que getIdE() retourne l'ID de l'enfant à mettre à jour
            updateEnfantPs.executeUpdate();

            System.out.println("Mise à jour de l'enfant effectuée avec succès !");
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la mise à jour de l'enfant : " + ex.getMessage());
        }
    }


    public Enfant getOneById(int id) {

        Enfant e = null;

        String query = "SELECT * FROM enfant WHERE idE = ? ";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    e = new Enfant(
                            resultSet.getInt("idE"),
                            resultSet.getString("nom"),
                            resultSet.getString("prenom"),
                            resultSet.getInt("niveau"),
                            resultSet.getString("dob")
                    );
                }
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return e;
    }
    public void supprimer(int id) {
        String req = "DELETE FROM `enfant` WHERE `enfant`.`idE`=? ";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Enfant supprimé !");
            } else {
                System.out.println("Aucun Enfant trouvé avec l'ID spécifié.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }





}
