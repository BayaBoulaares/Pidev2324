package edu.esprit.entities;

public class Question {
    private int idQ;
    private int idQz;
    private String questionText;
    private String choix_1;
    private String choix_2;
    private String choix_3;
    private String choix_4;
    private String choix_correcte;

    public Question(){idQ = 0;}
    public Question(int idQ, int idQz, String questionText, String choix_1, String choix_2, String choix_3, String choix_4, String choix_correcte) {
        this.idQ = idQ;
        this.idQz = idQz;
        this.questionText = questionText;
        this.choix_1 = choix_1;
        this.choix_2 = choix_2;
        this.choix_3 = choix_3;
        this.choix_4 = choix_4;
        this.choix_correcte = choix_correcte;
    }

    public int getIdQ() {
        return idQ;
    }

    public void setIdQ(int idQ) {
        this.idQ = idQ;
    }

    public int getIdQz() {
        return idQz;
    }

    public void setIdQz(int idQz) {
        this.idQz = idQz;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getChoix_1() {
        return choix_1;
    }

    public void setChoix_1(String choix_1) {
        this.choix_1 = choix_1;
    }

    public String getChoix_2() {
        return choix_2;
    }

    public void setChoix_2(String choix_2) {
        this.choix_2 = choix_2;
    }

    public String getChoix_3() {
        return choix_3;
    }

    public void setChoix_3(String choix_3) {
        this.choix_3 = choix_3;
    }

    public String getChoix_4() {
        return choix_4;
    }

    public void setChoix_4(String choix_4) {
        this.choix_4 = choix_4;
    }

    public String getChoix_correcte() {
        return choix_correcte;
    }

    public void setChoix_correcte(String choix_correcte) {
        this.choix_correcte = choix_correcte;
    }
}
