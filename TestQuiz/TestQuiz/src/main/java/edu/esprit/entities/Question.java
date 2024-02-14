package edu.esprit.entities;

import java.util.Objects;

public class Question {
    private int idq;
    private String questionText;
    private String correctAnswer;

    public Question(String questionText, String correctAnswer) {
        this.idq = idq;
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
    }

    public int getIdq() {
        return idq;
    }

    public void setIdq(int idq) {
        this.idq = idq;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + idq +
                ", questionText='" + questionText + '\'' +
                ", correctAnswer='" + correctAnswer + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return idq == question.idq;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idq);
    }
}
