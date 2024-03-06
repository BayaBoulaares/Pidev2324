package edu.esprit.crudoff.entities;

import java.util.Date;
import java.util.Objects;

public class ParentE extends Utilisateur{
    private String nomE;
    private String prenomE;
    private Date dateNaissanceE ;
    private int niveau;
    private String image;

    public ParentE(int i, String nom, String prenom, String adresse, java.sql.Date dateNaissance, int tel, String nomEnfant, String prenomEnfant, java.sql.Date dateNaissance2, String image) {
        super(i, nom, prenom, adresse, dateNaissance, tel);
        this.nomE = nomEnfant;
        this.prenomE = prenomEnfant;
        this.dateNaissanceE = dateNaissance2;
        this.image=image;
    }
    public ParentE(String nom, String prenom, String login) {
        super(nom, prenom,login);

    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ParentE(int idu, String nom, String prenom, String adresse, java.sql.Date dob, int tel, String login, String mdp, String nomE, String prenomE, java.sql.Date dobenfant, String image)
    {     super(idu, nom, prenom, adresse, dob, tel,login, mdp);
        this.nomE = nomE;
        this.prenomE = prenomE;
        this.dateNaissanceE = dobenfant;
        this.image=image;

    }
    public ParentE(int id, String nom, String prenom, String adresse, Date dateNaissance, int tel, String login, String mdp, String nomE, String prenomE, Date dateNaissanceE,  String image) {
         super(id, nom, prenom, adresse, dateNaissance, tel,login, mdp);
         this.nomE = nomE;
         this.prenomE = prenomE;
         this.dateNaissanceE = dateNaissanceE;
         this.image = image;
    }

    public ParentE(String nomParent, String prenomParent, String adresseParent, Date dobParent, int  telephone, String emailParent, String mdpParent, String nomEnfant, String prenomEnfant, Date dobEnfant, String imagePath)
    {
        super( nomParent, prenomParent, adresseParent, dobParent, telephone,emailParent, mdpParent);
        this.nomE = nomEnfant;
        this.prenomE = prenomEnfant;
        this.dateNaissanceE = dobEnfant;
        this.image = imagePath;
    }

    public ParentE(int id, String nom, String prenom, String adresse, Date dateNaissance, int tel, String login, String mdp, String nomE, String prenomE, Date dateNaissanceE, int niveau) {
        super(id, nom, prenom, adresse, dateNaissance, tel,login, mdp);
        this.nomE = nomE;
        this.prenomE = prenomE;
        this.dateNaissanceE = dateNaissanceE;

    }
    public ParentE(int id, String nom, String prenom, String adresse, Date dateNaissance, int tel, String nomE, String prenomE, Date dateNaissanceE) {
        super(id, nom, prenom, adresse, dateNaissance, tel);
        this.nomE = nomE;
        this.prenomE = prenomE;
        this.dateNaissanceE = dateNaissanceE;

    }


    public ParentE(String nom, String prenom,String adresse, Date dateNaissance, int tel,String login)
    {
        super(nom, prenom, adresse, dateNaissance, tel,login);
        this.nomE = nomE;
        this.prenomE = prenomE;


    }
    public ParentE(int id, String nom,String prenom, String adresse,int  tel, String nomEnfant,String prenomEnfant)
    {
        super(id,nom, prenom, adresse, tel);
        this.nomE = nomEnfant;
        this.prenomE = prenomEnfant;
    }

    public ParentE(String nom, String prenom, String adresse, Date dateNaissance, int tel, String login, String mdp, String nomE, String prenomE, Date dateNaissanceE, int niveau) {
        super(nom, prenom, adresse, dateNaissance, tel,login, mdp);
        this.nomE = nomE;
        this.prenomE = prenomE;
        this.dateNaissanceE = dateNaissanceE;
        this.niveau = niveau;
    }

    public ParentE(String nom, String prenom, String adresse, Date dateNaissance, int tel, String login, String mdp, String nomE, String prenomE, Date dateNaissanceE) {
        super(nom, prenom, adresse, dateNaissance, tel,login, mdp);
        this.nomE = nomE;
        this.prenomE = prenomE;
        this.dateNaissanceE = dateNaissanceE;

    }
    public ParentE(String nom, String prenom, String adresse, Date dateNaissance, int tel, String nomE, String prenomE, Date dateNaissanceE, int niveau) {
        super(nom, prenom, adresse, dateNaissance, tel);
        this.nomE = nomE;
        this.prenomE = prenomE;
        this.dateNaissanceE = dateNaissanceE;
        this.niveau = niveau;
    }

    public ParentE(String nom, String prenom, String adresse, int tel, String nomEnfant, String prenomEnfant) {
        super(nom, prenom, adresse, tel);
        this.nomE = nomE;
        this.prenomE = prenomE;
    }


    public String getNomE() {
        return nomE;
    }

    public void setNomE(String nomE) {
        this.nomE = nomE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ParentE parent = (ParentE) o;
        return Objects.equals(nomE, parent.nomE) && Objects.equals(prenomE, parent.prenomE);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), nomE, prenomE);
    }

    public String getPrenomE() {
        return prenomE;
    }

    public void setPrenomE(String prenomE) {
        this.prenomE = prenomE;
    }

    public Date getDateNaissanceE() {
        return dateNaissanceE;
    }

    public void setDateNaissanceE(Date dateNaissanceE) {
        this.dateNaissanceE = dateNaissanceE;
    }

    public int getNiveau() {
        return niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    @Override
    public String toString() {
        return "ParentE{" +
                "nomE='" + nomE + '\'' +
                ", prenomE='" + prenomE + '\'' +
                ", dateNaissanceE=" + dateNaissanceE +
                ", image='" + image + '\'' +
                '}';
    }
}
