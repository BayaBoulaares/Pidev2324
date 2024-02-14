package edu.esprit.services;

import edu.esprit.entities.Question;
import edu.esprit.entities.Reponse;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;

public class QuestionService implements IServices<Question> {

    Connection cnx = DataSource.getInstance().getCnx();

    @Override
    public void add(Question question) {
        String query = "INSERT INTO question (question_text, correct_answer) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setString(1, question.getQuestionText());
            preparedStatement.setString(2, question.getCorrectAnswer());
            preparedStatement.executeUpdate();
            System.out.println("Question added successfully: " + question);
        } catch (SQLException e) {
            System.err.println("Error adding question: " + e.getMessage());
        }
    }

    @Override
    public void update(Question question) {
        String query = "UPDATE question SET question_text = ?, correct_answer = ? WHERE idq = ?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setString(1, question.getQuestionText());
            preparedStatement.setString(2, question.getCorrectAnswer());
            preparedStatement.setInt(3, question.getIdq());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No question found with ID: " + question.getIdq());
            }
            System.out.println("Question updated successfully: " + question);
        } catch (SQLException e) {
            System.err.println("Error updating question: " + e.getMessage());
        }
    }

    @Override
    public void delete(int idq) {
        String query = "DELETE FROM question WHERE idq = ?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, idq);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No question found with ID: " + idq);
            }
            System.out.println("Question with ID " + idq + " deleted successfully.");
        } catch (SQLException e) {
            System.err.println("Error deleting question: " + e.getMessage());
        }
    }

    @Override
    public Question getByIdq(int idq) {
        String query = "SELECT * FROM question WHERE idq = ?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, idq);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String questionText = resultSet.getString("question_text");
                String correctAnswer = resultSet.getString("correct_answer");
                return new Question(questionText, correctAnswer);
            } else {
                throw new SQLException("No question found with ID: " + idq);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving question by ID: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Reponse getById(int id) {
        return null;
    }

    @Override
    public HashSet<Question> getAll() {
        HashSet<Question> questions = new HashSet<>();
        String query = "SELECT * FROM question";
        try (Statement statement = cnx.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                //int id = resultSet.getInt("id");
                String questionText = resultSet.getString("question_text");
                String correctAnswer = resultSet.getString("correct_answer");
                questions.add(new Question(questionText, correctAnswer));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving all questions: " + e.getMessage());
        }
        return questions;
    }

    @Override
    public Reponse getByIdrep(int id) {
        return null;
    }
}
