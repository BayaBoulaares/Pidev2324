package edu.esprit.tests;

import edu.esprit.entities.Evenement;
import edu.esprit.services.ServiceEvenement;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        // Création d'une instance de ServiceEvenement pour manipuler les événements
        ServiceEvenement serviceEvenement = new ServiceEvenement();

        // Suppression de l'événement
        serviceEvenement.supprimer(2);

        System.out.println("L'événement a été supprimé de la base de données.");
    }
}
