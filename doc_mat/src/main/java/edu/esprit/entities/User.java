package edu.esprit.entities;

import java.util.Date;

public class User {
    private int id;
    private String nom;
    private String prenom;
    private String adresse;
    private Date dob; // Date of birth
    private String login;
    private String tel;
    private String role;
    private String mdp; // Password
    private String discipline;
    private String nomEnfant;
    private String prenomEnfant;
    private Date dobEnfant; // Date of birth of child
    private String niveau;
    private String image;

    public User(int id, String nom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.dob = dob;
        this.login = login;
        this.tel = tel;
        this.role = role;
        this.mdp = mdp;
        this.discipline = discipline;
        this.nomEnfant = nomEnfant;
        this.prenomEnfant = prenomEnfant;
        this.dobEnfant = dobEnfant;
        this.niveau = niveau;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public String getNomEnfant() {
        return nomEnfant;
    }

    public void setNomEnfant(String nomEnfant) {
        this.nomEnfant = nomEnfant;
    }

    public String getPrenomEnfant() {
        return prenomEnfant;
    }

    public void setPrenomEnfant(String prenomEnfant) {
        this.prenomEnfant = prenomEnfant;
    }

    public Date getDobEnfant() {
        return dobEnfant;
    }

    public void setDobEnfant(Date dobEnfant) {
        this.dobEnfant = dobEnfant;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
