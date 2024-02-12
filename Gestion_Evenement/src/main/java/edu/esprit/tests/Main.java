package edu.esprit.tests;

import edu.esprit.entities.Evenement;
import edu.esprit.entities.Fond;
import edu.esprit.entities.Sponsor;
import edu.esprit.entities.Status;
import edu.esprit.services.ServiceEvenement;
import edu.esprit.services.ServiceSponsor;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        // Création d'une instance de ServiceEvenement pour manipuler les événements
        ServiceEvenement serviceEvenement = new ServiceEvenement();

        // Création d'un nouvel événement culturel
        Evenement evenementCulturel = new Evenement();
        evenementCulturel.setNom_Event("Evenement culturel");
        evenementCulturel.setDescription("Evenement pour apprendre la lecture");
        evenementCulturel.setDate_Debut(new Date(124, 1, 10));
        evenementCulturel.setDate_Fin(new Date(124, 1, 20));
        evenementCulturel.setNb_Max(40);
        evenementCulturel.setLieu_Event("Sfax");
        evenementCulturel.setStatus(Status.EN_COURS);

        // Ajout de l'événement
       // serviceEvenement.ajouter(evenementCulturel);
        // System.out.println("L'événement culturel a été ajouté avec succès !");

        // Création d'un nouvel événement amusant pour les enfants
        Evenement evenementAmusant = new Evenement();
        evenementAmusant.setNom_Event("Journée FunKids");
        evenementAmusant.setDescription("Journée amusante pour les enfants");
        evenementAmusant.setDate_Debut(new Date(124, 1, 15));
        evenementAmusant.setDate_Fin(new Date(124, 1, 18));
        evenementAmusant.setNb_Max(30);
        evenementAmusant.setLieu_Event("Tunis");
        evenementAmusant.setStatus(Status.EN_COURS);
        // Création d'une instance de ServiceSponsor pour manipuler les sponsors
        ServiceSponsor serviceSponsor = new ServiceSponsor();
//Ajout sponsor
// Récupération de l'événement "Journée FunKids"
        Evenement evenementFunKids = serviceEvenement.getAll().stream()
                .filter(e -> e.getNom_Event().equals("Journée FunKids"))
                .findFirst().orElse(null);

        if (evenementFunKids != null) {
            // Création d'un nouveau sponsor
            Sponsor sponsor = new Sponsor();
            sponsor.setNomSponsor("Super Entreprise");
            sponsor.setDescription_s("Une entreprise leader dans le domaine de la technologie, engagée à soutenir les événements culturels et éducatifs");
            sponsor.setFond(Sponsor.Fond.ARGENT); // Utilisez le bon type pour Fond
            sponsor.setEvenement(evenementFunKids); // Associer le sponsor à l'événement "Journée FunKids"

            // Ajout du sponsor
            serviceSponsor.ajouter(sponsor);
            System.out.println("Le sponsor a été ajouté avec succès à l'événement 'Journée FunKids' !");
        } else {
            System.out.println("Impossible de trouver l'événement 'Journée FunKids'.");
        }

        // Ajout de l'événement amusant pour les enfants
        //serviceEvenement.ajouter(evenementAmusant);
        // System.out.println("L'événement amusant pour enfants a été ajouté avec succès !");
        // Récupération de l'événement ajouté pour le supprimer
       Evenement evenementASupprimer = serviceEvenement.getAll().stream()
                .filter(e -> e.getNom_Event().equals("Journée FunKids"))
                .findFirst().orElse(null);

       /* // Vérification si l'événement à supprimer a été récupéré avec succès
        if (evenementASupprimer != null) {
            // Suppression de l'événement amusant pour les enfants
            serviceEvenement.supprimer(evenementASupprimer.getId_Event());
            System.out.println("L'événement amusant pour enfants a été supprimé avec succès !");
        } else {
            System.out.println("Impossible de trouver l'événement à supprimer.");
        }*/

        // Modification de l'événement culturel
        // Récupération de l'événement ajouté (pour obtenir son ID)
 /*      Evenement evenementModifie = serviceEvenement.getOneById(evenementCulturel.getId_Event());

        // Vérifier si l'événement a été récupéré avec succès
        if (evenementModifie != null) {
            // Modification des détails de l'événement
            evenementModifie.setDescription("Evenement plein d'activité");
            evenementModifie.setDate_Fin(new Date(124, 1, 10)); // Modifier la date de fin
            evenementModifie.setStatus(Status.TERMINE); // Définir l'état à "TERMINÉ"

            // Appel de la méthode modifier pour appliquer les modifications dans la base de données
            serviceEvenement.modifier(evenementModifie);

            System.out.println("L'événement culturel a été modifié avec succès !");
        } else {
            System.out.println("Impossible de trouver l'événement à modifier.");
        }
        // Affichage de la table de base
        System.out.println("Table des événements :");
        for (Evenement evenement : serviceEvenement.getAll()) {
            System.out.println(evenement);
        }*/

    }}

