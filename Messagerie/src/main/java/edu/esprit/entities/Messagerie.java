package edu.esprit.entities;

import java.sql.Date;
import java.sql.Timestamp; // Import Timestamp for handling datetime
import java.util.Objects;

public class Messagerie {
    private int id;
    private String nom;
    private Date date;
    private String message;
    private int idu; // Assuming this corresponds to the "idu" field in the database

    public Messagerie(int id, String nom, String message) {
        this.id = id;
        this.nom = nom;
        this.message = message;
    }

    // Assuming you want to initialize the date with the current date and time
    public Messagerie(String nom, String message, int idu) {
        this.nom = nom;

        this.message = message;
        this.idu = idu;
    }
   

    public Messagerie(int id, String nom, String message, int idu) {
        this.id = id;
        this.nom = nom;
        long currentTimeMillis = System.currentTimeMillis();

        this.date = new Date(currentTimeMillis);
        this.message = message;
        this.idu = idu;
    }

    public Messagerie(int id, String nom, Date date, String message) {
        this.id = id;
        this.nom = nom;
        this.date = date;
        this.message = message;
    }



    public Messagerie(String nom, Date date, String message, int idu) {
        this.nom = nom;
        // Assuming date is a String, you may need to convert it to a Date or Timestamp
        long currentTimeMillis = System.currentTimeMillis();

        this.date = new Date(currentTimeMillis);
        this.message = message;
        this.idu = idu;
    }

    public Messagerie(int id, String nom, Timestamp date, String message) {
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getIdu() {
        return idu;
    }

    public void setIdu(int idu) {
        this.idu = idu;
    }

    @Override
    public String toString() {
        return "Messagerie{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", date=" + date +
                ", message='" + message + '\'' +
                ", idu=" + idu +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Messagerie that = (Messagerie) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
