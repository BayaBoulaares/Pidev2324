package edu.esprit.services;

import edu.esprit.entities.Quiz;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceQuiz {
    private final Connection cnx = DataSource.getInstance().getCnx();

    public ServiceQuiz() {
    }

    public void addQuiz(Quiz quiz) {
        String sql = "INSERT INTO quiz (idq, question, date) VALUES (?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setInt(1, quiz.getId());
            ps.setString(2, quiz.getQuestion());
            ps.setDate(3, quiz.getDate());
            ps.executeUpdate();
            System.out.println("Quiz added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Quiz getQuizById(int id) {
        Quiz quiz = null;
        String sql = "SELECT * FROM quiz WHERE idq = ?";
        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    quiz = new Quiz(rs.getInt("idq"), rs.getString("question"), rs.getDate("date"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quiz;
    }

    // Implement other CRUD methods (update, delete, get all) as needed
}
