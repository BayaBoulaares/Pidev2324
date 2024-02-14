package edu.esprit.entities;

import java.util.Objects;

public class Reponse {
    private int idrep;
    private Question question; // Foreign key referencing Question table
    private String reponseCorrecte;
    private String reponseIncorrecte;

    public Reponse() {
        this.idrep = this.idrep;
        this.question = question;
        this.reponseCorrecte = reponseCorrecte;
        this.reponseIncorrecte = reponseIncorrecte;
    }

    // Getters and Setters...

    public int getIdrep() {
        return idrep;
    }

    public void setIdrep(int idrep) {
        this.idrep = idrep;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getReponseCorrecte() {
        return reponseCorrecte;
    }

    public void setReponseCorrecte(String reponseCorrecte) {
        this.reponseCorrecte = reponseCorrecte;
    }

    public String getReponseIncorrecte() {
        return reponseIncorrecte;
    }

    public void setReponseIncorrecte(String reponseIncorrecte) {
        this.reponseIncorrecte = reponseIncorrecte;
    }

    @Override
    public String toString() {
        return "Reponse{" +
                "idrep=" + idrep +
                ", question=" + question +
                ", reponseCorrecte='" + reponseCorrecte + '\'' +
                ", reponseIncorrecte='" + reponseIncorrecte + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reponse reponse = (Reponse) o;
        return idrep == reponse.idrep;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idrep);
    }
}
