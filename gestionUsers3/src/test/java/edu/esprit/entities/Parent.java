package edu.esprit.entities;

import java.util.Date;

public class Parent extends Utilisateur{
    public Parent(int id, String nom, String prenom, String adresse, String dateNaissance, String login, int tel, String mdp) {
        super(id, nom, prenom, adresse, dateNaissance, login, tel, mdp);
    }

    public Parent(String nom, String prenom, String adresse, String dateNaissance, String login, int tel, String mdp) {
        super(nom, prenom, adresse, dateNaissance, login, tel, mdp);
    }
}
