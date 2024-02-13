package edu.esprit.entities;

import java.util.Objects;
import java.sql.Date;

public class Messagerie {
    private int id;
    private String nom;
    private Date date;

    public Messagerie(int id, String nom, Date date) {
        this.id = id;
        this.nom = nom;
        this.date = date;
    }

    // Assuming you want to initialize the date with the current date and time
    public Messagerie(String nom, String date ) {
        this.nom = nom;

        long currentTimeMillis = System.currentTimeMillis();

        this.date = new Date(currentTimeMillis);
    }

    public Messagerie(int id, String nom, String prenom) {
        this.id = id;
        this.nom = nom;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Messagerie{" +
                ", id='" + id + '\'' +
                ", nom='" + nom + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Messagerie messagerie = (Messagerie) o;
        return id == messagerie.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
