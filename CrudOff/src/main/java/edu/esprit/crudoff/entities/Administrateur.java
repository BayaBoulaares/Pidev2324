package edu.esprit.crudoff.entities;


import java.util.Date;

public class Administrateur extends Utilisateur{
    public Administrateur()
    {
        super();
    }
    public Administrateur(int id, String nom, String prenom, String adresse, Date dateNaissance, int tel, String login, String mdp) {
        super(id,nom, prenom, adresse, dateNaissance,  tel,login, mdp);
    }
    public Administrateur(int id, String nom, String prenom, String adresse, Date dateNaissance, int tel, String login) {
        super(id,nom, prenom, adresse, dateNaissance,  tel,login);
    }
    public Administrateur(String nom, String prenom, String adresse, Date dateNaissance, int tel,String login, String mdp) {
        super(nom, prenom, adresse, dateNaissance,tel,  login, mdp);
    }
    public Administrateur(String nom, String prenom, String adresse, Date dateNaissance, int tel,String login) {
        super(nom, prenom, adresse, dateNaissance,tel,  login);
    }
    public Administrateur(String nom, String prenom, String adresse, Date dateNaissance, int tel) {
        super(nom, prenom, adresse, dateNaissance, tel);
    }


}