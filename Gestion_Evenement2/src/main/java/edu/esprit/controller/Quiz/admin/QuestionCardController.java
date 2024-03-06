package edu.esprit.controller.Quiz.admin;

import edu.esprit.entities.Question;
import edu.esprit.entities.Quiz;
import edu.esprit.services.QuestionService;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class QuestionCardController {
    @FXML
    private Text choix1Txt;

    @FXML
    private Text choix2Txt;

    @FXML
    private Text choix3Txt;

    @FXML
    private Text choix4Txt;

    @FXML
    private Text choixCorrecte;

    @FXML
    private Text questionTxt;
    private Question question;
    private QuizTestPageController controller;
    private Quiz quiz;
    public void setQuestion(Question q, QuizTestPageController con, Quiz qq){
        questionTxt.setText(q.getQuestionText());
        choix1Txt.setText("1) "+q.getChoix_1());
        choix2Txt.setText("2) "+q.getChoix_2());
        choix3Txt.setText("3) "+q.getChoix_3());
        choix4Txt.setText("4) "+q.getChoix_4());
        choixCorrecte.setText(q.getChoix_correcte());
        this.question = q;
        this.quiz = qq;
        this.controller = con;
    }

    @FXML
    void deleteBtnClicked(MouseEvent event) {
        QuestionService questionService = new QuestionService();
        if(questionService.supprimer(question.getIdQ()))
            controller.showQuestion(quiz);
    }
}
