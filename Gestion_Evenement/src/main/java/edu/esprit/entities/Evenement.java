package edu.esprit.entities;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Evenement {
    private int Id_Event;
    private String Nom_Event;
    private Date Date_Debut;
    private Date Date_Fin;
    private String Lieu_Event;
    private String Description;
    private int Nb_Max;
    private Status Status;

    public Evenement() {
    }
    public Evenement(int id) {
        this.Id_Event = id;
    }
    public Evenement(String Nom_Event, String Description, String Lieu_Event, Date Date_Debut, Date Date_Fin, int Nb_Max, Status Status) {
        this.Nom_Event = Nom_Event;
        this.Description = Description;
        this.Lieu_Event = Lieu_Event;
        this.Date_Debut = Date_Debut;
        this.Date_Fin = Date_Fin;
        this.Nb_Max = Nb_Max;
        this.Status = Status;
    }

    public Evenement(int Id_Event, String Nom_Event, String Description, String Lieu_Event, Date Date_Debut, Date Date_Fin, int Nb_Max, Status Status) {
        this.Id_Event = Id_Event;
        this.Nom_Event = Nom_Event;
        this.Description = Description;
        this.Lieu_Event = Lieu_Event;
        this.Date_Debut = Date_Debut;
        this.Date_Fin = Date_Fin;
        this.Nb_Max = Nb_Max;
        this.Status = Status;
    }



    public int getId_Event() {
        return Id_Event;
    }

    public void setId_Event(int Id_Event) {
        this.Id_Event = Id_Event;
    }

    public String getNom_Event() {
        return Nom_Event;
    }

    public void setNom_Event(String Nom_Event) {
        this.Nom_Event = Nom_Event;
    }

    public Date getDate_Debut() {
        return Date_Debut;
    }

    public void setDate_Debut(Date Date_Debut) {
        this.Date_Debut = Date_Debut;
    }

    public Date getDate_Fin() {
        return Date_Fin;
    }

    public void setDate_Fin(Date Date_Fin) {
        this.Date_Fin = Date_Fin;
    }

    public String getLieu_Event() {
        return Lieu_Event;
    }

    public void setLieu_Event(String Lieu_Event) {
        this.Lieu_Event = Lieu_Event;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public int getNb_Max() {
        return Nb_Max;
    }

    public void setNb_Max(int Nb_Max) {
        this.Nb_Max = Nb_Max;
    }

    public Status getStatus() {
        return Status;
    }

    public void setStatus(Status Status) {
        this.Status = Status;
    }



    @Override
    public String toString() {
        return "Evenement{" +
                "Nom_Event='" + Nom_Event + '\'' +
                ", Description='" + Description + '\'' +
                ", Lieu_Event='" + Lieu_Event + '\'' +
                ", Date_Debut=" + Date_Debut +
                ", Date_Fin=" + Date_Fin +
                ", Nb_Max=" + Nb_Max +
                ", Status=" + Status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Evenement evenement = (Evenement) o;
        return Id_Event == evenement.Id_Event;
    }


}
