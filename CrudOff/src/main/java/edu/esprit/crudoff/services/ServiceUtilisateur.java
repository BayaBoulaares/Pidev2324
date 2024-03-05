package edu.esprit.crudoff.services;

import edu.esprit.crudoff.entities.Administrateur;
import edu.esprit.crudoff.entities.ParentE;
import edu.esprit.crudoff.entities.Professeur;
import edu.esprit.crudoff.entities.Utilisateur;
import edu.esprit.crudoff.utilis.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class ServiceUtilisateur implements IService<Utilisateur>{

    Connection cnx = DataSource.getInsatnce().getConnection();

    public ServiceUtilisateur(){

    }




    @Override
    public void ajouter(Utilisateur u) throws SQLException {
        String sql = "INSERT INTO `utilisateurs` (`nom`, `prenom`, `adresse`, `dob`, `tel`, `login`, `role`, `mdp`, `discipline`, `nomenfant`, `prenomenfant`, `dobenfant`, `niveau`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement statement = cnx.prepareStatement(sql);
        statement.setString(1, u.getNom());
        statement.setString(2, u.getPrenom());
        statement.setString(3, u.getAdresse());
        statement.setDate(4, new java.sql.Date(u.getDateNaissance().getTime()));
        statement.setInt(5, u.getTel());
        statement.setString(6, u.getLogin());
        statement.setString(7, u.getMdp());

        if (u instanceof Administrateur) {
            statement.setString(8, "Administrateur");
            statement.setNull(9, java.sql.Types.VARCHAR);
            statement.setNull(10, java.sql.Types.VARCHAR);
            statement.setNull(11, java.sql.Types.DATE);
            statement.setNull(12, java.sql.Types.DATE);
            statement.setNull(13, java.sql.Types.INTEGER);
        } else if (u instanceof Professeur) {
            statement.setString(7, "Professeur");
            statement.setString(9, ((Professeur) u).getDiscpline());
            statement.setNull(8, java.sql.Types.VARCHAR);
            statement.setNull(10, java.sql.Types.VARCHAR);
            statement.setNull(11, java.sql.Types.DATE);
            statement.setNull(12, java.sql.Types.DATE);
            statement.setNull(13, java.sql.Types.INTEGER);
        } else if (u instanceof ParentE) {
            statement.setString(7, "Parent");
            statement.setNull(8, java.sql.Types.VARCHAR);
            statement.setNull(9, java.sql.Types.VARCHAR);
            statement.setNull(13, java.sql.Types.INTEGER);
            statement.setString(10, ((ParentE) u).getNomE());
            statement.setString(11, ((ParentE) u).getPrenomE());
            statement.setDate(12, new java.sql.Date(((ParentE) u).getDateNaissanceE().getTime()));
        }

        statement.executeUpdate();
    }



    @Override
    public void modifier(Utilisateur u) throws SQLException {
        // Obtenez l'ID de l'utilisateur à partir de l'objet ParentE
        int userId = u.getId();
        String sql = "UPDATE utilisateurs SET nom=?, prenom=?, adresse=?, dob=?, tel=?, login=?, mdp=?,discipline=? WHERE idu=?";
        PreparedStatement statement = cnx.prepareStatement(sql);
        statement.setString(1, u.getNom());
        statement.setString(2, u.getPrenom());
        statement.setString(3, u.getAdresse());
        statement.setDate(4, new java.sql.Date(u.getDateNaissance().getTime()));
        statement.setInt(5, u.getTel());
        statement.setString(6, u.getLogin());
        statement.setString(7, u.getMdp());
        //statement.setString(8, ((Professeur)u.getD()));

        //statement.setInt(11, parent.getNiveau());
        statement.setInt(9, userId);
        System.out.println(userId);

    }



    @Override
    public void supprimer(int idAdministrateur) throws SQLException{
        String req = "DELETE FROM utilisateurs WHERE idu=? ";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, idAdministrateur);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User supprimé !");
            } else {
                System.out.println("Aucun user trouvé avec l'ID spécifié.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    @Override
    public Utilisateur getOneById(int id) {

        Administrateur a = null;

        String query = "SELECT * FROM utilisateurs WHERE idu= ? AND role = 'Administrateur'";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    a = new Administrateur(
                            resultSet.getInt("idU"),
                            resultSet.getString("nom"),
                            resultSet.getString("prenom"),
                            resultSet.getString("adresse"),
                            resultSet.getDate("dob"),
                            resultSet.getInt("tel"),
                            resultSet.getString("login"),

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



    public Utilisateur getOneByEmail(String email) {

        Administrateur a = null;

        String query = "SELECT * FROM utilisateurs WHERE idu= ? AND role = 'Administrateur'";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setString(1, email);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    a = new Administrateur(
                            resultSet.getInt("idu"),
                            resultSet.getString("nom"),
                            resultSet.getString("prenom"),
                            resultSet.getString("adresse"),
                            resultSet.getDate("dob"),
                            resultSet.getInt("tel"),
                            resultSet.getString("login"),
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

    public Collection<Utilisateur> getAll() throws SQLException {
        List<Utilisateur> users = new ArrayList<>();
        String req = "SELECT * FROM utilisateurs where role='Professeur'";

        try (PreparedStatement ps = cnx.prepareStatement(req);
             ResultSet res = ps.executeQuery()) {
            while (res.next()) {
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

// Récupération du rôle depuis la base de données

                // En fonction du rôle, instanciez l'utilisateur approprié
                Utilisateur utilisateur;
                if (role.equals("Administrateur")) {
                    utilisateur = new Administrateur(nom, prenom, adresse, dateNaissance, tel, login);
                } else if (role.equals("Parent")) {
                    // Si vous avez une classe ParentE, instanciez-la ici
                    utilisateur = new ParentE(nom, prenom, adresse, dateNaissance, tel, login);
                } else if (role.equals("Professeur")) {
                    // Si vous avez une classe Professeur, instanciez-la ici
                    utilisateur = new Professeur(nom, prenom, adresse, dateNaissance, tel, login,disc);
                } else {
                    // Gérez le cas où le rôle est inconnu ou non pris en charge
                    System.out.println("Rôle non pris en charge : " + role);
                    utilisateur = null; // Ou une autre action appropriée
                }

                // Ajouter l'utilisateur à la liste
                if (utilisateur != null) {
                    users.add(utilisateur);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des utilisateurs : " + e.getMessage());
            throw e; // Vous pouvez choisir de traiter l'exception différemment si nécessaire
        }

        return users;


    }

    public Utilisateur getByLogin(String login) {
        Utilisateur utilisateur = null;
        String sql = "SELECT idu, nom, prenom, adresse, dob, tel, login, mdp, role, discipline, nomenfant, prenomenfant, dobenfant, image FROM utilisateurs WHERE login=? ";

        try (PreparedStatement statement = cnx.prepareStatement(sql)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("idu");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String adresse = resultSet.getString("adresse");
                Date dateNaissance = resultSet.getDate("dob");
                int tel = resultSet.getInt("tel");
                String mdp = resultSet.getString("mdp");
                String role = resultSet.getString("role");

                //System.out.println("Utilisateur trouvé dans la base de données : " + login);
               // System.out.println(role);
                switch (role) {
                    case "Parent":
                       // System.out.println("bb");
                        String nomE = resultSet.getString("nomenfant");
                        System.out.println(nomE);
                        String prenomE = resultSet.getString("prenomenfant");
                        System.out.println(prenomE);
                        Date dateNaissanceE = resultSet.getDate("dobenfant");
                        System.out.println(dateNaissanceE);
                        String image = resultSet.getString(14);
                        //int niveau = resultSet.getInt("niveau");
                        utilisateur = new ParentE(id,nom, prenom, adresse, dateNaissance, tel, login, mdp, nomE, prenomE, dateNaissanceE,image);
                       System.out.println(image);
                        System.out.println(utilisateur);
                        break;
                    case "Professeur":
                        String discipline = resultSet.getString("discipline");
                        utilisateur = new Professeur(id,nom, prenom, adresse, dateNaissance, tel, login, mdp, discipline);
                        System.out.println("hedha prof");
                        break;

                    case "Administrateur":
                        utilisateur = new Administrateur(id,nom, prenom, adresse, dateNaissance, tel, login, mdp);
                       // System.out.println("admin");
                        break;
                    default:
                        System.out.println("Rôle non reconnu : " + role);
                        break;
                }

                System.out.println("got User : " + utilisateur);
            } else {
                System.out.println("Aucun utilisateur trouvé pour le login : " + login);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception
        }

        return utilisateur;
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
    public String getUserByEmail(String email) throws SQLException {
        String role = null;
        // Établir la connexion à la base de données et exécuter la requête pour obtenir le rôle de l'utilisateur
        String req = "SELECT * FROM utilisateurs WHERE login = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, email); // Utiliser 1 au lieu de 6 car c'est le premier paramètre dans la requête
        ResultSet rs = ps.executeQuery();

        // Ajouter des instructions de débogage
        int rowCount = 0;
        while (rs.next()) {
            rowCount++;
            role = rs.getString("role");
            System.out.println("Role trouvé pour l'utilisateur avec email " + email + ": " + role);
        }
        System.out.println("Nombre de lignes retournées : " + rowCount);

        return role;
    }




}


