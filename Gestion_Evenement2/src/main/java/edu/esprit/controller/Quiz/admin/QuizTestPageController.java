package edu.esprit.controller.Quiz.admin;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.regex.Pattern;

public class QuizTestPageController implements Initializable {
    @FXML
    private AnchorPane ajoutPageContainer;

    @FXML
    private TextField choix1Input;

    @FXML
    private TextField choix2Input;

    @FXML
    private TextField choix3Input;

    @FXML
    private TextField choix4Input;

    @FXML
    private AnchorPane editPageContainer;

    @FXML
    private Label errorImg;

    @FXML
    private Label errorImgEdit;

    @FXML
    private Label errorImgEdit1;

    @FXML
    private Label errorNom;

    @FXML
    private Label errorNomEdit;

    @FXML
    private Label errorNomEdit1;

    @FXML
    private Label errorQuestion;

    @FXML
    private ImageView imgQuizAjout;

    @FXML
    private ImageView imgQuizEdit;

    @FXML
    private VBox listQuestionContainer;

    @FXML
    private VBox listQuizContainer;

    @FXML
    private TextField nomAjoutCInput;

    @FXML
    private TextField nomEditInput;

    @FXML
    private TextField questionInput;

    @FXML
    private AnchorPane questionPageContainer;

    @FXML
    private ComboBox<String> reponseCB;

    @FXML
    private ComboBox<String> typeAjoutCInput;

    @FXML
    private ComboBox<String> typeEditCInput;
    private List<Quiz> quizzes;
    private String addQuizImgPath;
    private QuizService quizService;

    @FXML
    private ImageView qrCodeImg;
    @FXML
    private HBox qrCodeImgModal;

    private Quiz quiz;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        quizService = new QuizService();
        ajoutPageContainer.setVisible(false);
        editPageContainer.setVisible(false);
        questionPageContainer.setVisible(false);
        qrCodeImgModal.setVisible(false);

        addQuizImgPath ="";
        ObservableList<String> itemsType = FXCollections.observableArrayList(
                "Quiz","Test du niveau"
        );
        ObservableList<String> itemsRep = FXCollections.observableArrayList(
                "Choix 1","Choix 2","Choix 3","Choix 4"
        );
        typeAjoutCInput.setItems(itemsType);
        typeAjoutCInput.setValue("Quiz");
        typeEditCInput.setItems(itemsType);

        reponseCB.setItems(itemsRep);
        reponseCB.setValue("Choix 1");
        showQuiz(quizService.getAll());

    }
    public void showQuiz(List<Quiz> list) {
        this.quizzes = list;
        listQuizContainer.getChildren().clear();
        for(Quiz quiz : quizzes){
            try{
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/gui/Quiz/admin/quizTestCard.fxml"));
                Parent root = fxmlLoader.load();
                QuizTestCardController controller = fxmlLoader.getController();
                controller.setQuiz(quiz,this);
                listQuizContainer.getChildren().add(root);
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
    }
    public void setEditQuizPage(Quiz q){
        nomEditInput.setText(q.getName());
        typeEditCInput.setValue(q.getType());
        addQuizImgPath = q.getImage();
        imgQuizEdit.setImage(new Image(q.getImage()));
        this.quiz = q;
        editPageContainer.setVisible(true);
    }
    @FXML
    public void ajoutQuizBtnClicked(MouseEvent event){
        ajoutPageContainer.setVisible(true);
    }
    @FXML
    public void retourQuizPage(MouseEvent event){
        nomAjoutCInput.clear();
        addQuizImgPath = "";
        errorNom.setVisible(false);
        errorImg.setVisible(false);
        ajoutPageContainer.setVisible(false);
        editPageContainer.setVisible(false);
        showQuiz(quizService.getAll());
        questionPageContainer.setVisible(false);
    }
    @FXML
    public void choisirImgAjout(MouseEvent event){
        FileChooser imageCourseChooser = new FileChooser();
        imageCourseChooser.setTitle("Choose your quiz image");
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter(
                "Image Files", "*.png", "*.jpg", "*.gif");
        imageCourseChooser.getExtensionFilters().add(imageFilter);
        File selectedFile = imageCourseChooser.showOpenDialog(null);
        if (selectedFile != null) {
            addQuizImgPath = "file:/"+selectedFile.getAbsolutePath().replace("\\","/");
            imgQuizAjout.setImage(new Image(addQuizImgPath));
        }
    }
    @FXML
    void choisirImgEdit(MouseEvent event) {
        FileChooser imageCourseChooser = new FileChooser();
        imageCourseChooser.setTitle("Choose your quiz image");
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter(
                "Image Files", "*.png", "*.jpg", "*.gif");
        imageCourseChooser.getExtensionFilters().add(imageFilter);
        File selectedFile = imageCourseChooser.showOpenDialog(null);
        if (selectedFile != null) {
            addQuizImgPath = "file:/"+selectedFile.getAbsolutePath().replace("\\","/");
            imgQuizEdit.setImage(new Image(addQuizImgPath));
        }
    }

    @FXML
    void editQuiz(MouseEvent event) {
        String name = nomEditInput.getText();
        String type = typeEditCInput.getValue();
        if(name.isEmpty() || !Pattern.matches("^[a-zA-Z]+?", name)) errorNom.setVisible(true);
        else errorNomEdit.setVisible(false);
        if(addQuizImgPath.isEmpty()) errorImgEdit.setVisible(true);
        else errorImgEdit.setVisible(false);
        if(!name.isEmpty() && !addQuizImgPath.isEmpty()){
            if(quizService.modifier(new Quiz(quiz.getIdQz(),name,type,addQuizImgPath)))
            {
                addQuizImgPath = "";
                retourQuizPage(null);
            }
        }
    }

    @FXML
    public void addQuiz(MouseEvent event){
        String name = nomAjoutCInput.getText();
        String type = typeAjoutCInput.getValue();
        if(name.isEmpty() || !Pattern.matches("^[a-zA-Z]+$?", name)) errorNom.setVisible(true);
        else errorNom.setVisible(false);
        if(addQuizImgPath.isEmpty()) errorImg.setVisible(true);
        else errorImg.setVisible(false);
        if(!name.isEmpty() && !addQuizImgPath.isEmpty()){
            if(quizService.ajouter(new Quiz(0,name,type,addQuizImgPath)))
            {
                nomAjoutCInput.clear();
                addQuizImgPath = "";
                showQuiz(quizService.getAll());
            }
        }
    }

    // Question start
    public void showQuestion(Quiz q){
        QuestionService questionService = new QuestionService();
        List<Question> questions = questionService.getAll(q.getIdQz());
        listQuestionContainer.getChildren().clear();
        for(Question qq : questions){
            try{
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/gui/Quiz/admin/questionCard.fxml"));
                Parent root = fxmlLoader.load();
                QuestionCardController controller = fxmlLoader.getController();
                controller.setQuestion(qq,this,q);
                listQuestionContainer.getChildren().add(root);
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
        quiz = q;
        questionPageContainer.setVisible(true);
    }
    @FXML
    public void ajouterQuestion(MouseEvent event){
        String question = questionInput.getText();
        String choix1 = choix1Input.getText();
        String choix2 = choix2Input.getText();
        String choix3 = choix3Input.getText();
        String choix4 = choix4Input.getText();
        String correct = reponseCB.getValue();
        if(question.isEmpty() || choix1.isEmpty() || choix2.isEmpty() || choix3.isEmpty() || choix4.isEmpty() || correct.isEmpty()){
            errorQuestion.setVisible(true);
        } else{
            QuestionService questionService = new QuestionService();
            questionService.ajouter(new Question(
                    0,quiz.getIdQz(),question,choix1,choix2,choix3,choix4,correct
            ));
            errorQuestion.setVisible(false);
            showQuestion(quiz);
        }
    }

    @FXML
    void close_QrCodeModal(MouseEvent event) {
        qrCodeImgModal.setVisible(false);

    }



}
