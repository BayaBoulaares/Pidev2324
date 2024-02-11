package edu.esprit.entities;

import java.util.Date;

public class Administrateur extends Utilisateur{
    public Administrateur()
    {
     super();
    }
    public Administrateur(int id,String nom, String prenom, String adresse, String dateNaissance, String login, int tel, String mdp) {
        super(id,nom, prenom, adresse, dateNaissance, login, tel, mdp);
    }
    public Administrateur(String nom, String prenom, String adresse, String dateNaissance, String login, int tel, String mdp) {
        super(nom, prenom, adresse, dateNaissance, login, tel, mdp);
    }

}
