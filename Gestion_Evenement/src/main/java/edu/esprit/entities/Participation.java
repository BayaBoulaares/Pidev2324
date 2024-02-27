package edu.esprit.entities;

public class Participation {

    private Evenement evenement;
    private User user;

    public Participation() {
    }

    public Participation(Evenement evenement, User user) {
        this.evenement = evenement;
        this.user = user;
    }

    public Evenement getEvenement() {
        return evenement;
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Participation{" +
                "evenement=" + evenement +
                ", user=" + user +
                '}';
    }
}
