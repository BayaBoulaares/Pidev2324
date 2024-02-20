package edu.esprit.entities;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class Matiere {
     private int id;
   private String nommatiere;
   private String description;

    public Matiere(){ }
     public  Matiere(int id){
         this.id=id;
     }

    public Matiere(String nommatiere, String description) {
        this.nommatiere = nommatiere;
        this.description = description;
    }

    public Matiere(int id, String nommatiere, String description) {
        this.id = id;
        this.nommatiere = nommatiere;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNommatiere() {
        return nommatiere;
    }

    public void setNommatiere(String nommatiere) {
        this.nommatiere = nommatiere;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Matiere{" +
                "id=" + id +
                ", nommatiere='" + nommatiere + '\'' +
                ", description='" + description + '\'' +
              +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matiere matiere = (Matiere) o;
        return Objects.equals(nommatiere, matiere.nommatiere);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nommatiere);
    }
}
