package edu.esprit.entities;

import java.util.Objects;

public abstract class Utilisateur {
    private int id;
    private String nom;
    private String prenom;
    private String adresse;
    private String dateNaissance;
    private String login;
    private int tel;
    private String mdp;

    public Utilisateur()
    {}
    public Utilisateur(int id,String nom,String prenom,String adresse,String dateNaissance,String login,int tel,String mdp)
    {
           this.id = id;
           this.nom =  nom;
           this.prenom = prenom;
           this.adresse = adresse;
           this.dateNaissance = dateNaissance ;
           this.login = login;
           this.tel = tel;
           this.mdp = mdp;

    }
    public Utilisateur(String nom,String prenom,String adresse,String dateNaissance,String login,int tel,String mdp)
    {
        this.id = id;
        this.nom =  nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.dateNaissance = dateNaissance ;
        this.login = login;
        this.tel = tel;
        this.mdp = mdp;

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

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }


    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", dateNaissance=" + dateNaissance +
                ", login='" + login + '\'' +
                ", tel=" + tel +
                ", mdp='" + mdp + '\'' +

                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilisateur that = (Utilisateur) o;
        return id == that.id && Objects.equals(nom, that.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom);
    }
}
