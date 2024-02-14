package edu.esprit.services;

import edu.esprit.entities.Question;
import edu.esprit.entities.Quiz;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;

public class QuizService implements IServices<Quiz> {

    private final Connection connection;

    public QuizService() {
        connection = DataSource.getInstance().getCnx();
    }

    @Override
    public void add(Quiz quiz) {
        String query = "INSERT INTO quiz (type, score) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, quiz.getType().toString());
            preparedStatement.setInt(2, quiz.getScore());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                quiz.setIdqz(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Failed to add quiz, no ID obtained.");
            }
            System.out.println("Quiz added successfully: " + quiz);
        } catch (SQLException e) {
            throw new RuntimeException("Error adding quiz: " + e.getMessage());
        }
    }

    @Override
    public void update(Quiz quiz) {
        String query = "UPDATE quiz SET type = ?, score = ? WHERE idqz = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, quiz.getType().toString());
            preparedStatement.setInt(2, quiz.getScore());
            preparedStatement.setInt(3, quiz.getIdqz());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No quiz found with ID: " + quiz.getIdqz());
            }
            System.out.println("Quiz updated successfully: " + quiz);
        } catch (SQLException e) {
            throw new RuntimeException("Error updating quiz: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM quiz WHERE idqz = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No quiz found with ID: " + id);
            }
            System.out.println("Quiz deleted successfully with ID: " + id);
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting quiz: " + e.getMessage());
        }
    }

    @Override
    public Quiz getById(int id) {
        return null;
    }

    @Override
    public Quiz getByIdqz(int idqz) {
        return null;
    }

    @Override
    public Question getByIdq(int id) {
        String query = "SELECT * FROM quiz WHERE idqz = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String type = resultSet.getString("type");
                int score = resultSet.getInt("score");
                return new Quiz(id, Quiz.Type.valueOf(type), score);
            } else {
                throw new SQLException("No quiz found with ID: " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting quiz by ID: " + e.getMessage());
        }
    }

    @Override
    public HashSet<Quiz> getAll() {
        HashSet<Quiz> quizzes = new HashSet<>();
        String query = "SELECT * FROM quiz";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int idqz = resultSet.getInt("idqz");
                String type = resultSet.getString("type");
                int score = resultSet.getInt("score");
                Quiz quiz = new Quiz(idqz, Quiz.Type.valueOf(type), score);
                quizzes.add(quiz);
            }
            return quizzes;
        } catch (SQLException e) {
            throw new RuntimeException("Error getting all quizzes: " + e.getMessage());
        }
    }

    @Override
    public Quiz getByIdrep(int id) {
        // Implement this method according to your requirements
        return null;
    }
}
