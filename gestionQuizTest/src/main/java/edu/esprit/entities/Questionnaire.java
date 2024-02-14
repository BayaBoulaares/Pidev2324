package edu.esprit.entities;

import java.sql.*;

public class Questionnaire {
    private int idq;
    private String question;

    // Constructor
    public Questionnaire(int id, String question) {
        this.idq = idq;
        this.question = this.question;
    }

    // Getters and setters
    public int getIdq() {
        return idq;
    }

    public void setIdq(int idq) {
        this.idq = idq;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    // CRUD operations
    public void create(Connection conn) throws SQLException {
        String sql = "INSERT INTO questionnaire ( question) VALUES ( ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, question);
            stmt.executeUpdate();
            System.out.println("Questionnaire record created successfully.");
        }
    }

    public static Questionnaire read(Connection conn, int idq) throws SQLException {
        Questionnaire questionnaire = null;
        String sql = "SELECT * FROM questionnaire WHERE idq = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idq);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    questionnaire = new Questionnaire(rs.getInt("id"), rs.getString("question"));
                } else {
                    System.out.println("Questionnaire not found with ID: " + idq);
                }
            }
        }
        return questionnaire;
    }

    public void update(Connection conn) throws SQLException {
        String sql = "UPDATE questionnaire SET question = ? WHERE idq = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, question);
            stmt.setInt(2, idq);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Questionnaire record updated successfully.");
            } else {
                System.out.println("No Questionnaire found with ID: " + idq);
            }
        }
    }

    public static void delete(Connection conn, int idq) throws SQLException {
        String sql = "DELETE FROM questionnaire WHERE idq = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idq);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Questionnaire record deleted successfully.");
            } else {
                System.out.println("No Questionnaire found with ID: " + idq);
            }
        }
    }
}
