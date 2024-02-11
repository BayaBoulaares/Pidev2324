package edu.esprit.entities;

import java.util.Date;

public class Professeur extends Utilisateur{
    private String discpline;

    public Professeur(int id, String nom, String prenom, String adresse, String dateNaissance, String login, int tel, String mdp, String discpline) {
        super(id, nom, prenom, adresse, dateNaissance, login, tel, mdp);
        discpline = this.discpline;
    }


    public Professeur(String nom, String prenom, String adresse, String dateNaissance, String login, int tel, String mdp,String discpline) {
        super(nom, prenom, adresse, dateNaissance, login, tel, mdp);
        discpline = this.discpline;
    }
    public String getDiscpline() {
        return discpline;
    }

    public void setDiscpline(String discpline) {
        this.discpline = discpline;
    }

    @Override
    public String toString() {
        return "Professeur{" +super.toString()+
                "discpline='" + discpline + '\'' +
                '}';
    }
}
