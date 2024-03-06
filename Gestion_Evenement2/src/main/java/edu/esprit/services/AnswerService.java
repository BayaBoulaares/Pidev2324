package edu.esprit.services;

import edu.esprit.entities.Answer;
import edu.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AnswerService {
    public Connection cnx;
    public AnswerService(){
        cnx = DataSource.getInstance().getCnx();
    }


    public boolean ajouterReponse(Answer answer){
        int idUser = DashboardUser.ep.getId();
        String qry = "INSERT INTO ANSWER (question_id,answer_text,is_true,idUser) VALUES (?,?,?,?)";
        try {
            PreparedStatement statement = cnx.prepareStatement(qry);
            statement.setInt(1,answer.getIdq());
            statement.setString(2,answer.getAnswerText());
            statement.setBoolean(3,answer.isTrue());
            statement.setInt(4,idUser);
            if(statement.executeUpdate() == 1)
                return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

}
