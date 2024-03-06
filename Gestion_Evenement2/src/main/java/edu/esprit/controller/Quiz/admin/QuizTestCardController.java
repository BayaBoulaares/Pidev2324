package edu.esprit.controller.Quiz.admin;

import edu.esprit.entities.Quiz;
import edu.esprit.entities.QuizScore;
import edu.esprit.services.QuizScoreService;
import edu.esprit.services.QuizService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import javafx.embed.swing.SwingFXUtils;
import java.awt.image.BufferedImage;
import java.util.List;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javafx.scene.Node;

public class QuizTestCardController {

    @FXML
    private Circle imgQuizContainer;

    @FXML
    private Button QrCodeBtn;

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

        QuizScoreService quizScoreService = new QuizScoreService() ;

        List<QuizScore> qs = quizScoreService.getAll();
        QrCodeBtn.setOnMouseClicked(event -> {
            String text = "";
            for (int i = 0; i < qs.size(); i++) {
                text += "\n"+"Nom quiz : "+quizScoreService.getQuizNameById(quiz.getIdQz())
                        + "\n username : " + " ayoub "
                        + "\n Score: " + qs.get(i).getScore() + "\n ";
            }


            // Créer un objet QRCodeWriter pour générer le QR code
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            // Générer la matrice de bits du QR code à partir du texte saisi
            BitMatrix bitMatrix;
            try {
                bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);
                // Convertir la matrice de bits en image BufferedImage
                BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
                // Enregistrer l'image en format PNG
                // File outputFile = new File("qrcode.png");
                // ImageIO.write(bufferedImage, "png", outputFile);
                // Afficher l'image dans l'interface utilisateur

                ImageView qrCodeImg = (ImageView) ((Node) event.getSource()).getScene().lookup("#qrCodeImg");
                qrCodeImg.setImage(SwingFXUtils.toFXImage(bufferedImage, null));

                HBox qrCodeImgModal = (HBox) ((Node) event.getSource()).getScene().lookup("#qrCodeImgModal");
                qrCodeImgModal.setVisible(true);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        });

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
