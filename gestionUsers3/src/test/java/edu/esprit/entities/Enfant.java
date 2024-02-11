package edu.esprit.entities;

import java.util.Date;
import java.util.Objects;

public class Enfant {

    private int idE;
    private String nom;
    private String prenom;
    private String dateNaissance ;
    private int niveau;

    private Parent idp ;
    public Enfant(int idE, String nom, String prenom,int niveau,String dateNaissance,Parent idp ) {
        this.niveau = niveau ;
        this.idE = idE ;
         this.nom = nom;
         this.prenom = prenom;
         this.dateNaissance = dateNaissance;
         this.idp =  idp ;

    }

    public Enfant(String nom, String prenom, String dateNaissance,int niveau,Parent idp) {
        this.niveau = niveau ;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.idp =  idp ;
    }
    public Enfant(int idE,String nom, String prenom, int niveau,String dateNaissance) {

        this.idE = idE ;
        this.nom = nom;
        this.prenom = prenom;
        this.niveau = niveau ;
        this.dateNaissance = dateNaissance;

    }

    public int getNiveau() {
        return niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    public int getIdE() {
        return idE;
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

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public Parent getIdp() {
        return idp;
    }

    public void setIdp(Parent idp) {
        this.idp = idp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enfant enfant = (Enfant) o;
        return idE == enfant.idE && Objects.equals(nom, enfant.nom) && Objects.equals(prenom, enfant.prenom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idE, nom, prenom);
    }

    @Override
    public String toString() {
        return "Enfant{" +
                "idE=" + idE +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", dateNaissance=" + dateNaissance +
                ", niveau=" + niveau +
                ", idp=" + idp +
                '}';
    }
}
