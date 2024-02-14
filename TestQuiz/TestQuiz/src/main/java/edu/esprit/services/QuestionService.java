package edu.esprit.services;

import edu.esprit.entities.Question;
import edu.esprit.entities.Quiz;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;

public class QuestionService implements IServices<Question> {

    private final Connection connection;

    public QuestionService() {
        connection = DataSource.getInstance().getCnx();
    }

    @Override
    public void add(Question question) {
        String query = "INSERT INTO question (questionText, correctAnswer) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, question.getQuestionText());
            preparedStatement.setString(2, question.getCorrectAnswer());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                question.setIdq(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Failed to add question, no ID obtained.");
            }
            System.out.println("Question added successfully: " + question);
        } catch (SQLException e) {
            throw new RuntimeException("Error adding question: " + e.getMessage());
        }
    }

    @Override
    public void update(Question question) {
        String query = "UPDATE question SET questionText = ?, correctAnswer = ? WHERE idq = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, question.getQuestionText());
            preparedStatement.setString(2, question.getCorrectAnswer());
            preparedStatement.setInt(3, question.getIdq());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No question found with ID: " + question.getIdq());
            }
            System.out.println("Question updated successfully: " + question);
        } catch (SQLException e) {
            throw new RuntimeException("Error updating question: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM question WHERE idq = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No question found with ID: " + id);
            }
            System.out.println("Question deleted successfully with ID: " + id);
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting question: " + e.getMessage());
        }
    }

    @Override
    public Question getById(int id) {
        String query = "SELECT * FROM question WHERE idq = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String questionText = resultSet.getString("questionText");
                String correctAnswer = resultSet.getString("correctAnswer");
                return new Question();
            } else {
                throw new SQLException("No question found with ID: " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting question by ID: " + e.getMessage());
        }
    }

    @Override
    public Quiz getByIdqz(int idqz) {
        return null;
    }

    @Override
    public HashSet<Question> getAll() {
        HashSet<Question> questions = new HashSet<>();
        String query = "SELECT * FROM question";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int idq = resultSet.getInt("idq");
                String questionText = resultSet.getString("questionText");
                String correctAnswer = resultSet.getString("correctAnswer");
                Question question = new Question();
                questions.add(question);
            }
            return questions;
        } catch (SQLException e) {
            throw new RuntimeException("Error getting all questions: " + e.getMessage());
        }
    }

    @Override
    public Question getByIdq(int idq) {
        return (Quiz) getById(idq);
    }

    @Override
    public Quiz getByIdrep(int id) {
        return null;
    }
}
