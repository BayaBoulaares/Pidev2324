package edu.esprit.tests;

import edu.esprit.entities.Evenement;
import edu.esprit.services.ServiceEvenement;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        // Création d'une instance de ServiceEvenement pour manipuler les événements
        ServiceEvenement serviceEvenement = new ServiceEvenement();

        // Création des deux exemples d'événements
        Evenement event1 = new Evenement("Event1", "Description de l'Event1", "Lieu de l'Event1",
                new Date(), new Date(), 50, Evenement.Status.EN_COURS);

        Evenement event2 = new Evenement("Event2", "Description de l'Event2", "Lieu de l'Event2",
                new Date(), new Date(), 100, Evenement.Status.TERMINE);

        // Ajout des événements à la base de données
        serviceEvenement.ajouter(event1);
        serviceEvenement.ajouter(event2);

        System.out.println("Deux événements ont été ajoutés à la base de données.");
    }
}
