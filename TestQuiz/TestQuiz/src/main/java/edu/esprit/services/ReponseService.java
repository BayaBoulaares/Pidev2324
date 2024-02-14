package edu.esprit.services;

import edu.esprit.entities.Question;
import edu.esprit.entities.Quiz;
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
    public void add(Reponse reponse) {
        String query = "INSERT INTO reponse (idq, reponseCorrecte, reponseIncorrecte) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, reponse.getQuestion().getIdq());
            preparedStatement.setString(2, reponse.getReponseCorrecte());
            preparedStatement.setString(3, reponse.getReponseIncorrecte());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                reponse.setIdrep(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Failed to add reponse, no ID obtained.");
            }
            System.out.println("Reponse added successfully: " + reponse);
        } catch (SQLException e) {
            throw new RuntimeException("Error adding reponse: " + e.getMessage());
        }
    }


    @Override
    public void update(Reponse reponse) {
        String query = "UPDATE reponse SET idq = ?, reponseCorrecte = ?, reponseIncorrecte = ? WHERE idrep = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, reponse.getQuestion().getIdq());
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
    public Reponse getById(int id) {
        String query = "SELECT * FROM reponse WHERE idrep = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int idq = resultSet.getInt("idq");
                String reponseCorrect = resultSet.getString("reponseCorrecte");
                String reponseIncorrect = resultSet.getString("reponseIncorrecte");

                // Fetch the associated question
                Question question = new QuestionService().getByIdq(idq);

                return new Reponse();
            } else {
                throw new SQLException("No reponse found with ID: " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting reponse by ID: " + e.getMessage());
        }
    }

    @Override
    public Quiz getByIdqz(int idqz) {
        return null;
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
                String reponseCorrecte = resultSet.getString("reponseCorrecte");
                String reponseIncorrecte = resultSet.getString("reponseIncorrecte");

                // Fetch the associated question
                Quiz question = (Quiz) new QuestionService().getByIdq(idq);

                Reponse reponse = new Reponse();
                reponses.add(reponse);
            }
            return reponses;
        } catch (SQLException e) {
            throw new RuntimeException("Error getting all reponses: " + e.getMessage());
        }
    }



    @Override
    public Question getByIdq(int idq) {
        String query = "SELECT * FROM reponse WHERE idq = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idq);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int idrep = resultSet.getInt("idrep");
                String reponseCorrecte = resultSet.getString("reponseCorrecte");
                String reponseIncorrecte = resultSet.getString("reponseIncorrecte");

                // Fetch the associated question
                int questionId = resultSet.getInt("idq");
                QuestionService questionService = new QuestionService(); // Instantiate QuestionService
                Question question = questionService.getByIdq(questionId);

                return new Reponse().getQuestion();
            } else {
                throw new SQLException("No reponse found with Question ID: " + idq);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting reponse by Question ID: " + e.getMessage());
        }
    }

    @Override
    public Quiz getByIdrep(int id) {
        return null;
    }


}
