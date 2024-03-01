package edu.esprit.controller.Quiz.user;

import edu.esprit.entities.Answer;
import edu.esprit.entities.Question;
import edu.esprit.services.AnswerService;
import edu.esprit.services.QuestionService;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class QuestionCardController {

    @FXML
    private CheckBox checkChoice1;

    @FXML
    private CheckBox checkChoice2;

    @FXML
    private CheckBox checkChoice3;

    @FXML
    private CheckBox checkChoice4;
    @FXML
    private Text questionTxt;
    private Question question;
    private QuizTestPageController controller;
    public void setQuestion(Question question,QuizTestPageController con){
        questionTxt.setText(question.getQuestionText());
        checkChoice1.setText(question.getChoix_1());
        checkChoice2.setText(question.getChoix_2());
        checkChoice3.setText(question.getChoix_3());
        checkChoice4.setText(question.getChoix_4());
        this.question = question;
        this.controller = con;
    }
    @FXML
    void selectChoice1(MouseEvent event) {
        Answer answer;
        if(question.getChoix_correcte().equals("Choix 1")){
            answer = new Answer(0,question.getIdQ(),1,true,question.getChoix_1(),"");
        } else
            answer = new Answer(0,question.getIdQ(),1,false,question.getChoix_1(),"");
        checkChoice2.setSelected(false);
        checkChoice3.setSelected(false);
        checkChoice4.setSelected(false);
        AnswerService answerService = new AnswerService();
        answerService.ajouterReponse(answer);
    }

    @FXML
    void selectChoice2(MouseEvent event) {
        Answer answer;
        if(question.getChoix_correcte().equals("Choix 2")){
            answer = new Answer(0,question.getIdQ(),1,true,question.getChoix_2(),"");
        } else
            answer = new Answer(0,question.getIdQ(),1,false,question.getChoix_2(),"");
        checkChoice1.setSelected(false);
        checkChoice3.setSelected(false);
        checkChoice4.setSelected(false);
        AnswerService answerService = new AnswerService();
        answerService.ajouterReponse(answer);
    }

    @FXML
    void selectChoice3(MouseEvent event) {
        Answer answer;
        if(question.getChoix_correcte().equals("Choix 3")){
            answer = new Answer(0,question.getIdQ(),1,true,question.getChoix_3(),"");
        } else
            answer = new Answer(0,question.getIdQ(),1,false,question.getChoix_3(),"");
        checkChoice1.setSelected(false);
        checkChoice2.setSelected(false);
        checkChoice4.setSelected(false);
        AnswerService answerService = new AnswerService();
        answerService.ajouterReponse(answer);
    }

    @FXML
    void selectChoice4(MouseEvent event) {
        Answer answer;
        if(question.getChoix_correcte().equals("Choix 4")){
            answer = new Answer(0,question.getIdQ(),1,true,question.getChoix_4(),"");
        } else
            answer = new Answer(0,question.getIdQ(),1,false,question.getChoix_4(),"");
        checkChoice2.setSelected(false);
        checkChoice3.setSelected(false);
        checkChoice1.setSelected(false);
        AnswerService answerService = new AnswerService();
        answerService.ajouterReponse(answer);
    }
}
