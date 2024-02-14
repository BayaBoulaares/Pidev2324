package edu.esprit.services;

import edu.esprit.entities.Reponse;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;

public class ReponseService implements IServices<Reponse> {

    private final Connection connection;

    public ReponseService() {
        connection = DataSource.getInstance().getCnx();
    }

    @Override
    public void add(Reponse r) {
        String req = "INSERT INTO reponse (idq, ReponseCorrecte, reponseIncorrecte) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, r.getIdq());
            ps.setString(2, r.getReponseCorrecte());
            ps.setString(3, r.getReponseIncorrecte());
            ps.executeUpdate();
            System.out.println("Reponse added!");
        } catch (SQLException e) {
            throw new RuntimeException("Error adding reponse: " + e.getMessage());
        }
    }

    @Override
    public void update(Reponse reponse) {
        String query = "UPDATE reponse SET idq = ?, reponseCorrecte = ?, reponseIncorrecte = ? WHERE idrep = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, reponse.getIdq());
            preparedStatement.setString(2, reponse.getReponseCorrecte());
            preparedStatement.setString(3, reponse.getReponseIncorrecte());
            preparedStatement.setInt(4, reponse.getIdrep());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No reponse found with ID: " + reponse.getIdrep());
            }
            System.out.println("Reponse updated successfully: " + reponse);
        } catch (SQLException e) {
            throw new RuntimeException("Error updating reponse: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM reponse WHERE idrep = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No reponse found with ID: " + id);
            }
            System.out.println("Reponse deleted successfully with ID: " + id);
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting reponse: " + e.getMessage());
        }
    }

    @Override
    public Reponse getByIdq(int idq) {
        return null;
    }

    @Override
    public Reponse getById(int id) {
        String query = "SELECT * FROM reponse WHERE idrep = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int idq = resultSet.getInt("idq");
                String reponseCorrect = resultSet.getString("reponseCorrecte");
                String reponseIncorrect = resultSet.getString("reponseIncorrecte");
                return new Reponse(id, reponseCorrect, reponseIncorrect);
            } else {
                throw new SQLException("No reponse found with ID: " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting reponse by ID: " + e.getMessage());
        }
    }

    @Override
    public HashSet<Reponse> getAll() {
        HashSet<Reponse> reponses = new HashSet<>();
        String query = "SELECT * FROM reponse";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int idrep = resultSet.getInt("idrep");
                int idq = resultSet.getInt("idq");
                String reponseCorrect = resultSet.getString("reponseCorrecte");
                String reponseIncorrect = resultSet.getString("reponseIncorrecte");
                Reponse reponse = new Reponse(idrep, reponseCorrect, reponseIncorrect);
                reponses.add(reponse);
            }
            return reponses;
        } catch (SQLException e) {
            throw new RuntimeException("Error getting all reponses: " + e.getMessage());
        }
    }

    @Override
    public Reponse getByIdrep(int id) {
        return null;
    }


}
