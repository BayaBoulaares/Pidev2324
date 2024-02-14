package edu.esprit.entities;

import java.util.Objects;

public class Reponse {
    private int idrep;
    private int idq; // Foreign key referencing id column of Question table
    private String reponseCorrecte;
    private String reponseIncorrecte;

    public Reponse(int idrep, String reponseCorrecte, String reponseIncorrecte) {
        this.idrep = idrep;
        this.idq = idq;
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

    public int getIdq() {
        return idq;
    }

    public void setIdq(int idq) {
        this.idq = idq;
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
                ", idq=" + idq +
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
