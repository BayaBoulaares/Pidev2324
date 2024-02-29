package edu.esprit.entities;

public class Answer {
    private int idA;
    private int idq;
    private int idUser;
    private boolean isTrue;
    private String answerText;
    private String dateAnswer;

    public Answer(int idA, int idq, int idUser, boolean isTrue, String answerText, String dateAnswer) {
        this.idA = idA;
        this.idq = idq;
        this.idUser = idUser;
        this.isTrue = isTrue;
        this.answerText = answerText;
        this.dateAnswer = dateAnswer;
    }

    public int getIdA() {
        return idA;
    }

    public void setIdA(int idA) {
        this.idA = idA;
    }

    public int getIdq() {
        return idq;
    }

    public void setIdq(int idq) {
        this.idq = idq;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public boolean isTrue() {
        return isTrue;
    }

    public void setTrue(boolean aTrue) {
        isTrue = aTrue;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public String getDateAnswer() {
        return dateAnswer;
    }

    public void setDateAnswer(String dateAnswer) {
        this.dateAnswer = dateAnswer;
    }
}
