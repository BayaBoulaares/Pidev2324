package edu.esprit.controller.Quiz.admin;

import edu.esprit.entities.Quiz;
import edu.esprit.services.QuizService;
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
    private QuizTestPageController controller;
    private Quiz quiz;
    public void setQuiz(Quiz q,QuizTestPageController con){
        imgQuizContainer.setFill(new ImagePattern(new Image(
                q.getImage()
        )));
        quizNameTxt.setText(q.getName());
        quizTypeTxt.setText("Type : "+q.getType());
        this.quiz = q;
        this.controller = con;
    }
    @FXML
    void deleteBtnClicked(MouseEvent event) {
        QuizService quizService = new QuizService();
        if(quizService.supprimer(quiz.getIdQz()))
            controller.retourQuizPage(null);
    }

    @FXML
    public void editBtnClicked(MouseEvent event) {
        controller.setEditQuizPage(quiz);
    }
    @FXML
    public void showQuestions(MouseEvent event){
        controller.showQuestion(quiz);
    }
}
