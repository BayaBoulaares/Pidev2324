package edu.esprit.APIapploadfichier;

import java.sql.Date;

public class Statistique {
    private String titre;

    private int nembre; // Ajout du champ date


    public Statistique(String titre, int nembre) {
        this.titre = titre;
        this.nembre = nembre;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public int getNembre() {
        return nembre;
    }

    public void setNembre(int nembre) {
        this.nembre = nembre;
    }
}
