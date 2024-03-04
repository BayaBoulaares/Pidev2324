package edu.esprit.entities;
import java.sql.Date;
public class Document {
    int id;
    String  titre;
Type type;
String url;
Niveau niveau;
Date date;
    Matiere mat;

    public Document() {
    }

    public Document(int id) {
        this.id = id;
    }

    public Document(String titre, Type type, String url, Niveau niveau, Date date,Matiere mat) {
        this.titre = titre;
        this.type = type;
        this.url = url;
        this.niveau = niveau;
        this.date = date;
        this.mat=mat;
    }

    public Document(int id, String titre, Type type, String url, Niveau niveau, Date date,Matiere mat) {
        this.id = id;
        this.titre = titre;
        this.type = type;
        this.url = url;
        this.niveau = niveau;
        this.date = date;
        this.mat=mat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Matiere getMat() {
        return mat;
    }

    public void setMat(Matiere mat) {
        this.mat = mat;
    }

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", type=" + type +
                ", url='" + url + '\'' +
                ", niveau=" + niveau +
                ", date=" + date +
                ", mat=" + mat +
                '}';
    }
}
