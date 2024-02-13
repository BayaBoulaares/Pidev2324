package edu.esprit.entities;

import java.util.Objects;
import java.sql.Date;

public class Reclamation {
    private int id;
    private String nom;

    private String reclamation;
    private Date date;

    public Reclamation(int id, String nom,String reclamation, Date date) {
        this.id = id;
        this.nom = nom;
        this.reclamation = reclamation;
        this.date = date;
    }

    // Assuming you want to initialize the date with the current date and time
    public Reclamation(String nom,String reclamation, String date ) {
        this.nom = nom;

        long currentTimeMillis = System.currentTimeMillis();

        this.date = new Date(currentTimeMillis);
        this.reclamation = reclamation;
    }

    public Reclamation(int id, String nom, String reclamation) {
        this.id = id;
        this.nom = nom;
        this.reclamation = reclamation;
        long currentTimeMillis = System.currentTimeMillis();

        this.date = new Date(currentTimeMillis);
    }

    // Getter and Setter methods...

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
public void setReclamation(String reclamation){
        this.reclamation= reclamation;
}

public String getReclamation(){
        return reclamation;
}


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Reclamation{" +
                ", id='" + id + '\'' +
                ", nom='" + nom + '\'' +
                ", reclamation='" + reclamation + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reclamation Reclamation = (Reclamation) o;
        return id == Reclamation.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
