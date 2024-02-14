package edu.esprit.entities;

import java.sql.Date;

import static sun.security.pkcs11.wrapper.Functions.getId;

public class Quiz extends Questionnaire {
    private Date date;

    public Quiz(int id, String question, Date date) {
        super(id, question);
        this.date = date;
    }

    // Getters and setters for the date attribute
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + getId() +
                ", question='" + getQuestion() + '\'' +
                ", date=" + date +
                '}';
    }

    private String getId() {
    }
}
