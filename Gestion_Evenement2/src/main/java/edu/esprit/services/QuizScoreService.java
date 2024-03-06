package edu.esprit.services;

import edu.esprit.entities.Question;
import edu.esprit.entities.QuizScore;
import edu.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuizScoreService {
    public Connection cnx;
    public QuizScoreService(){
        cnx = DataSource.getInstance().getCnx();
    }




    public boolean ajouter(QuizScore q){
        String qry = "INSERT INTO QUIZSCORE (idQuiz,idUser,score) VALUES (?,?,?)";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setInt(1,q.getIdQuiz());
            stm.setInt(2,q.getIdUser());
            stm.setFloat(3,q.getScore());
            if(stm.executeUpdate() == 1)
                return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    public QuizScore getScore(int id,int quiz){
        QuizScore quizScore = null;
        String qry = "SELECT * FROM QUIZSCORE WHERE idUser = ? AND idQuiz = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setInt(1,id);
            stm.setInt(2,quiz);
            ResultSet rs = stm.executeQuery();
            while (rs.next()){
                quizScore = new QuizScore(
                        rs.getInt("idScore"),rs.getInt("idQuiz"),
                        rs.getInt("idUser"),rs.getFloat("score")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return quizScore;
    }


    public List<QuizScore> getAll() {
        List<QuizScore> quizScores = new ArrayList<>();
        String qry = "SELECT * FROM QUIZSCORE";
        try {
            PreparedStatement statement = cnx.prepareStatement(qry);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                QuizScore quizScore = new QuizScore(
                        resultSet.getInt("idScore"),
                        resultSet.getInt("idQuiz"),
                        resultSet.getInt("idUser"),
                        resultSet.getFloat("score")
                );
                quizScores.add(quizScore);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return quizScores;
    }

    public String getQuizNameById(int quizId) {
        String quizName = null;
        String qry = "SELECT q.name FROM QUIZSCORE qs JOIN QUIZ q ON qs.idQuiz = q.idQz WHERE qs.idQuiz = ?";
        try {
            PreparedStatement statement = cnx.prepareStatement(qry);
            statement.setInt(1, quizId); // Set the quiz ID parameter
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                quizName = resultSet.getString("name");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return quizName;
    }


}
