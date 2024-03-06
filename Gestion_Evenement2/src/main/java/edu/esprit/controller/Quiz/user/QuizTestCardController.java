package edu.esprit.controller.Quiz.user;

import edu.esprit.entities.Quiz;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class QuizTestCardController {
    @FXML
    private Circle imgQuizContainer;
    @FXML
    private Text quizNameTxt;
    @FXML
    private Text quizTypeTxt;
    private Quiz quiz;
    private QuizTestPageController quizTestPageController;
    public void setQuiz(Quiz quiz,QuizTestPageController con){
        imgQuizContainer.setFill(new ImagePattern(
                new Image(quiz.getImage())
        ));
        quizNameTxt.setText(quiz.getName());
        quizTypeTxt.setText(quiz.getType());
        this.quiz =quiz;
        this.quizTestPageController = con;
    }
    @FXML
    void showQuestions(MouseEvent event) {
        quizTestPageController.showRepondrePage(quiz);
    }
}
