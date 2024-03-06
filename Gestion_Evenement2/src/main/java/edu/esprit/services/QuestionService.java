package edu.esprit.services;

import edu.esprit.entities.Question;
import edu.esprit.utils.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionService{
    public Connection cnx;
    public QuestionService(){
        cnx = DataSource.getInstance().getCnx();
    }

    public boolean ajouter(Question q){
        String qry = "INSERT INTO QUESTION (idqz,questionText,choix_1,choix_2,choix_3,choix_4,choix_correcte) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setInt(1,q.getIdQz());
            stm.setString(2,q.getQuestionText());
            stm.setString(3,q.getChoix_1());
            stm.setString(4,q.getChoix_2());
            stm.setString(5,q.getChoix_3());
            stm.setString(6,q.getChoix_4());
            stm.setString(7,q.getChoix_correcte());
            if(stm.executeUpdate() == 1)
                return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    public List<Question> getAll(int id){
        List<Question> questions = new ArrayList<>();
        String qry = "SELECT * FROM QUESTION WHERE idqz = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setInt(1,id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()){
                questions.add(new Question(
                        rs.getInt("idq"),rs.getInt("idqz"),
                        rs.getString("questionText"),rs.getString("choix_1"),
                        rs.getString("choix_2"),rs.getString("choix_3"),rs.getString("choix_4"),
                        rs.getString("choix_correcte")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return questions;
    }
    public boolean supprimer(int id){
        String qry = "DELETE FROM QUESTION WHERE idq = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setInt(1,id);
            if(stm.executeUpdate() == 1)
                return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
