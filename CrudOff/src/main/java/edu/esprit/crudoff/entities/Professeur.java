package edu.esprit.crudoff.entities;


import java.util.Date;

public class Professeur extends Utilisateur{
    private String discpline;
    public Professeur()
    {
        super();
    }
    public Professeur(int id, String nom, String prenom, String adresse, Date dateNaissance, int tel,String login ,String mdp, String discpline) {
        super(id, nom, prenom, adresse, dateNaissance,tel, login, mdp);
        this.discpline = discpline;
    }
    public Professeur(int id,String nom, String prenom,String adresse,int tel, String email, String discipline)
    { super(id, nom, prenom, adresse,tel);
        this.discpline = discipline;

    }
    public Professeur(String nom, String prenom, String adresse, int tel, String login,String discpline) {
        super(nom, prenom, adresse, tel, login);
        this.discpline = discpline;
    }
    public Professeur(String nom, String prenom, String adresse, Date dateNaissance,  int tel, String login,String mdp,String discpline) {
        super(nom, prenom, adresse, dateNaissance, tel, login, mdp);
        this.discpline = discpline;
    }
    public Professeur(int id,String nom, String prenom, String adresse, Date dateNaissance,int tel,String discpline) {
        super(id,nom, prenom, adresse, dateNaissance, tel);
        this.discpline = discpline;
    }
    public Professeur(String nom, String prenom, String adresse, Date dateNaissance, int tel, String login, String discpline)
    {
        super(nom, prenom, adresse, dateNaissance, tel,login);
        this.discpline = discpline;
    }
    public Professeur(int id,String nom,String prenom, String adresse, int tel,String email,String mdp, String discpline)
    {
        super(id,nom, prenom, adresse,tel,email,mdp);
        this.discpline = discpline;
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
