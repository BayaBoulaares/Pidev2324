package edu.esprit.entities;

import java.time.LocalDate;
import java.util.Objects;
import java.sql.Date;

public class Messagerie {
    private int id;
    private String nom;
    private String message;

    private Date date;

    public Messagerie(int id, String nom, Date date, String message) {
        this.id = id;
        this.nom = nom;
        this.date = date;
        this.message = message;
    }

    // Assuming you want to initialize the date with the current date and time
    public Messagerie(String nom, String date, String message) {
        this.nom = nom;

        long currentTimeMillis = System.currentTimeMillis();

        this.date = new Date(currentTimeMillis);
        this.message = message;
    }

    public Messagerie(int id, String nom, String message) {
        this.id = id;
        this.nom = nom;
        long currentTimeMillis = System.currentTimeMillis();

        this.date = new Date(currentTimeMillis);
        this.message = message;
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

    public String getMessage(){ return message;}
    public void setMessage(String message){this.message=message;}

    @Override
    public String toString() {
        return "Messagerie{" +
                ", id='" + id + '\'' +
                ", nom='" + nom + '\'' +
                ", date='" + date + '\'' +
                ", message='" + message + '\'' +
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