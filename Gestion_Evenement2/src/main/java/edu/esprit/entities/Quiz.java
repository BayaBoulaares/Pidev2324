package edu.esprit.entities;

public class Quiz {
    private int idQz;
    private String name;
    private String type;
    private String image;

    public Quiz(){idQz=0;}
    public Quiz(int idQz, String name, String type, String image) {
        this.idQz = idQz;
        this.name = name;
        this.type = type;
        this.image = image;
    }

    public int getIdQz() {
        return idQz;
    }

    public void setIdQz(int idQz) {
        this.idQz = idQz;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
