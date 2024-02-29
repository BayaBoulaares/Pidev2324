package edu.esprit.controller.Quiz.user;

import edu.esprit.entities.Question;
import edu.esprit.entities.Quiz;
import edu.esprit.services.QuestionService;
import edu.esprit.services.QuizService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class QuizTestPageController implements Initializable {
    @FXML
    private VBox listQuestionContainer;

    @FXML
    private VBox listQuizContainer;

    @FXML
    private AnchorPane questionPageContainer;
    private int idUser;
    private List<Quiz> quizzes;
    private Quiz quiz;
    private QuizService quizService = new QuizService();

    @Override
    public void initialize(URL url, ResourceBundle rb){
        showQuizzes(quizService.getAll());
    }
    public void showQuizzes(List<Quiz> quizzes){
        listQuizContainer.getChildren().clear();
        for(Quiz q : quizzes){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/gui/Quiz/user/quizTestCard.fxml"));
                Parent root = fxmlLoader.load();
                QuizTestCardController controller = fxmlLoader.getController();
                controller.setQuiz(q,this);
                listQuizContainer.getChildren().add(root);
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
        this.quizzes = quizzes;
    }
    public void showRepondrePage(Quiz q){
        listQuizContainer.getChildren().clear();
        QuestionService questionService = new QuestionService();
        for(Question qq : questionService.getAll(q.getIdQz())){
            try{
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/gui/Quiz/user/questionCard.fxml"));
                Parent root = fxmlLoader.load();
                QuestionCardController controller = fxmlLoader.getController();
                controller.setQuestion(qq,this);
                listQuestionContainer.getChildren().add(root);
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
        questionPageContainer.setVisible(true);
    }
    @FXML
    void repondreQuiz(MouseEvent event) {

    }

    @FXML
    void retourQuizPage(MouseEvent event) {
        showQuizzes(quizService.getAll());
        questionPageContainer.setVisible(false);
    }
}
