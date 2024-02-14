package edu.esprit.services;

import edu.esprit.entities.Questionnaire;
import edu.esprit.entities.Quiz;
import edu.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public abstract class ServiceQuestionnaire {
    private final Connection cnx = DataSource.getInstance().getCnx();

    public ServiceQuestionnaire() {
    }

    public void addQuestionnaire(Questionnaire questionnaire) {
        String sql = "INSERT INTO questionnaire (question) VALUES (?)";
        try (PreparedStatement ps = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, questionnaire.getQuestion());
            ps.executeUpdate();
            System.out.println("Questionnaire added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Questionnaire getQuestionnaireById(int id) {
        Questionnaire questionnaire = null;
        String sql = "SELECT * FROM questionnaire WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    questionnaire = new Questionnaire(rs.getInt("id"), rs.getString("question"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questionnaire;
    }

    public abstract void ajouter(Quiz q);

    // Implement other CRUD methods (update, delete, get all) as needed
}
