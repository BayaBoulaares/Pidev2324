package edu.esprit.entities;

public class QuizScore {
    private int idScore;
    private int idQuiz;
    private int idUser;
    private float score;

    public QuizScore(){ idScore = 0;}
    public QuizScore(int idScore, int idQuiz, int idUser, float score) {
        this.idScore = idScore;
        this.idQuiz = idQuiz;
        this.idUser = idUser;
        this.score = score;
    }

    public int getIdScore() {
        return idScore;
    }

    public void setIdScore(int idScore) {
        this.idScore = idScore;
    }

    public int getIdQuiz() {
        return idQuiz;
    }

    public void setIdQuiz(int idQuiz) {
        this.idQuiz = idQuiz;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
