package edu.esprit.entities;

public class Sponsor {
    private int Id_Sponsor;
    private String NomSponsor;
    private String Description_s;
    private Fond fond;
    private Evenement evenement;

    public Sponsor() {
    }

    public Sponsor(int id_Sponsor, String nomSponsor, String description_s, Fond fond, Evenement evenement) {
        this.Id_Sponsor = id_Sponsor;
        this.NomSponsor = nomSponsor;
        this.Description_s = description_s;
        this.fond = fond;
        this.evenement = evenement;
    }
    public Sponsor(int id_Sponsor, String nomSponsor, String description_s, Fond fond) {
        this.Id_Sponsor = id_Sponsor;
        this.NomSponsor = nomSponsor;
        this.Description_s = description_s;
        this.fond = fond;

    }
    public int getId_Sponsor() {
        return Id_Sponsor;
    }

    public void setId_Sponsor(int id_Sponsor) {
        this.Id_Sponsor = id_Sponsor;
    }

    public String getNomSponsor() {
        return NomSponsor;
    }

    public void setNomSponsor(String nomSponsor) {
        this.NomSponsor = nomSponsor;
    }

    public String getDescription_s() {
        return Description_s;
    }

    public void setDescription_s(String description_s) {
        this.Description_s = description_s;
    }

    public Fond getFond() {
        return fond;
    }

    public void setFond(Fond fond) {
        this.fond = fond;
    }

    public Evenement getEvenement() {
        return evenement;
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }

    @Override
    public String toString() {
        return "Sponsor{" +
                "Id_Sponsor=" + Id_Sponsor +
                ", NomSponsor='" + NomSponsor + '\'' +
                ", Description_s='" + Description_s + '\'' +
                ", fond=" + fond +
                ", evenement=" + evenement +
                '}';
    }


}
