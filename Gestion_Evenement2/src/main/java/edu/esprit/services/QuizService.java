package edu.esprit.services;

import edu.esprit.entities.Quiz;
import edu.esprit.iservices.IServices;
import edu.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuizService implements IServices<Quiz> {
    public Connection cnx;
    public QuizService(){
        cnx = DataSource.getInstance().getCnx();
    }
    @Override
    public boolean ajouter(Quiz q){
        String qry = "INSERT INTO QUIZ (name,type,image) VALUES (?,?,?)";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1,q.getName());
            stm.setString(2,q.getType());
            stm.setString(3,q.getImage());
            if(stm.executeUpdate() == 1)
                return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    @Override
    public boolean modifier(Quiz q){
        String qry = "UPDATE QUIZ SET name = ?, type = ?, image = ? WHERE idqz = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(qry);
            stm.setString(1,q.getName());
            stm.setString(2,q.getType());
            stm.setString(3,q.getImage());
            stm.setInt(4,q.getIdQz());
            if(stm.executeUpdate() == 1)
                return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    @Override
    public List<Quiz> getAll(){
        List<Quiz> quizzes = new ArrayList<>();
        String qry = "SELECT * FROM QUIZ";
        try {
            Statement stm = cnx.createStatement();
            stm.executeQuery(qry);
            ResultSet rs = stm.getResultSet();
            while (rs.next()){
                quizzes.add(new Quiz(rs.getInt("idqz"),rs.getString("name")
                ,rs.getString("type"),rs.getString("image")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return quizzes;
    }
    @Override
    public boolean supprimer(int id){
        String qry = "DELETE FROM QUIZ WHERE idqz = ?";
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
