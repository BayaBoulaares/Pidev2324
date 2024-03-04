package edu.esprit.entities;

import java.sql.Date;
import java.time.LocalDate;

public class Reclamation {
    private int id;
    private String nom;
    private String reclamation;
    private Date date;
    private String rating;
    private int idu; // Added idu field

    public Reclamation(int id, String nom, String reclamation, LocalDate date, String rating) {
        this.id = id;
        this.nom = nom;
        this.reclamation = reclamation;
        this.date = Date.valueOf(date);
        this.rating = rating;
        this.idu = idu;
    }

    // Assuming you want to initialize the date with the current date and time
    public Reclamation(String nom, String reclamation, Date date, String rating, int idu) {
        this.nom = nom;
        long currentTimeMillis = System.currentTimeMillis();
        this.date = new Date(currentTimeMillis);
        this.reclamation = reclamation;
        this.rating = rating;
        this.idu = idu;
    }
    public Reclamation(int id, String nom, String reclamation, LocalDate date, String rating, int idu) {
        this.id = id;
        this.nom = nom;
        this.reclamation = reclamation;
        this.date = Date.valueOf(date);
        this.rating = rating;
        this.idu = idu;
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

    public String getReclamation() {
        return reclamation;
    }

    public void setReclamation(String reclamation) {
        this.reclamation = reclamation;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getIdu() {
        return idu;
    }

    public void setIdu(int idu) {
        this.idu = idu;
    }

    @Override
    public String toString() {
        return "Reclamation{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", reclamation='" + reclamation + '\'' +
                ", date=" + date +
                ", rating='" + rating + '\'' +
                ", idu=" + idu +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reclamation that = (Reclamation) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
