package edu.esprit.controller.Quiz.user;

import edu.esprit.controller.AffichageMatiereController;
import edu.esprit.entities.Answer;
import edu.esprit.entities.Question;
import edu.esprit.entities.Quiz;
import edu.esprit.entities.QuizScore;
import edu.esprit.services.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;
import facebook4j.FacebookException;


public class QuizTestPageController implements Initializable {
    @FXML
    private VBox listQuestionContainer;

    @FXML
    private VBox listQuizContainer;

    @FXML
    private AnchorPane questionPageContainer;
    @FXML
    private Button repondreBtn;
    @FXML private ScrollPane questionsScroll;
    @FXML private HBox scoreContainer;
    @FXML private Text scoreText;
    private int idUser;
    private List<Quiz> quizzes;
    private Quiz quiz;
    private QuizService quizService = new QuizService();
    private QuizScoreService serviceQuizScore = new QuizScoreService();
    private AnswerService answerService = new AnswerService();
    private List<Answer> answers = new ArrayList<>();
    private int nbrTotQuestion;

    @FXML
    private Button ShareFbBtn;

    private  String scoreFormate ="";


    @Override
    public void initialize(URL url, ResourceBundle rb){
        showQuizzes(quizService.getAll());
         ShareFbBtn.setVisible(false);

         idUser = DashboardUser.ep.getId();

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
        quiz = q;
        answers.clear();
        QuizScore quizScore = serviceQuizScore.getScore(idUser,quiz.getIdQz());
        if(quizScore==null){
            questionsScroll.setVisible(true);
            repondreBtn.setVisible(true);
            scoreContainer.setVisible(false);
            listQuestionContainer.getChildren().clear();
            QuestionService questionService = new QuestionService();
            List<Question> questions = questionService.getAll(q.getIdQz());
            nbrTotQuestion = questions.size();
            for(Question qq : questions){
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
        } else{
            questionsScroll.setVisible(false);
            repondreBtn.setVisible(false);
            scoreContainer.setVisible(true);
            ShareFbBtn.setVisible(true);
            DecimalFormat df = new DecimalFormat("#.##");
             scoreFormate = df.format(quizScore.getScore());
            scoreText.setText("Votre score est : "+scoreFormate+"%");
        }
        questionPageContainer.setVisible(true);
    }
    @FXML
    void repondreQuiz(MouseEvent event) {
        int nbrcorrect = 0;
        for(Answer answer : answers){
            if(answer.isTrue()) nbrcorrect++;
            answerService.ajouterReponse(answer);
        }
        float score = (float) nbrcorrect/nbrTotQuestion;
        float scoreF = score*100;
        serviceQuizScore.ajouter(new QuizScore(
                0,quiz.getIdQz(),idUser,scoreF
        ));
        System.out.println(score);
        showRepondrePage(quiz);
    }
    public void addAnswer(Answer answer){ answers.add(answer); }

    @FXML
    void retourQuizPage(MouseEvent event) {
        listQuizContainer.getChildren().clear();
        showQuizzes(quizService.getAll());
        questionPageContainer.setVisible(false);
    }



    @FXML
    void ShareFB(MouseEvent event) {
        String appId = "320408294342379";
        String appSecret = "f3b9d2c1995b97a9b55ec79b2f330270";
        String accessTokenString = "EAAEjaN6RPusBO8TLP6d0oZBZCwrCZCjnke3ATZAXbjW6hJWboojdwsxG0DwsDp1kX8xSizYAGOCb6BNbnB5raxWZBt8ZAXkJJ69a5nTehAMupXmJovbNjWWE4aj2ACmfWCQBtKaUvojHs9VbBF67QbeM2ZAA0LiGKUeCzrcAYmlTfwEQRzfwYTgEJ6fXalNq3ihHNM6RTWMs7qK5zeB5PP3jX4ZD";

        Facebook facebook = new FacebookFactory().getInstance();
        facebook.setOAuthAppId(appId, appSecret);
        facebook.setOAuthAccessToken(new AccessToken(accessTokenString, null));

        String msg = "Felicitations Mohamed "
                + "\n*** Votre score est : "
                + scoreFormate
                //+ "\n*** Felicitations : "
                ;
                //+ "\n***Date: "
               // + cours.getNiveau() ;

        try {
            facebook.postStatusMessage(msg);
            System.out.println("Post shared successfully.");
        } catch (FacebookException e) {
            throw new RuntimeException(e);
        }
    }
}
