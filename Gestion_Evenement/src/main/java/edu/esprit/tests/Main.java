package edu.esprit.tests;

import edu.esprit.entities.Evenement;
import edu.esprit.entities.Sponsor;
import edu.esprit.entities.Status;
import edu.esprit.services.ServiceEvenement;
import edu.esprit.services.ServiceSponsor;

import java.util.Date;
import java.util.Set;

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

        // Ajout de l'événement "Journée FunKids"
        Evenement evenementFunKids = serviceEvenement.getAll().stream()
                .filter(e -> e.getNom_Event().equals("Journée FunKids"))
                .findFirst().orElse(null);

        if (evenementFunKids != null) {
            Sponsor sponsor = new Sponsor();
            sponsor.setNomSponsor("Super Entreprise");
            sponsor.setDescription_s("Une entreprise leader dans le domaine de la technologie, engagée à soutenir les événements culturels et éducatifs");
            sponsor.setFond(Sponsor.Fond.ARGENT);
            sponsor.setEvenement(evenementFunKids);

         //   serviceSponsor.ajouter(sponsor);
            System.out.println("Le sponsor a été ajouté avec succès à l'événement 'Journée FunKids' !");
        } else {
            System.out.println("Impossible de trouver l'événement 'Journée FunKids'.");
        }

        // Suppression de l'événement "Journée FunKids"
       /* Evenement evenementASupprimer = serviceEvenement.getAll().stream()
                .filter(e -> e.getNom_Event().equals("Evenement culturel"))
                .findFirst().orElse(null);

        if (evenementASupprimer != null) {
            serviceEvenement.supprimer(evenementASupprimer.getId_Event());
            System.out.println("L'événement  a été supprimé avec succès !");
        } else {
            System.out.println("Impossible de trouver l'événement à supprimer.");
        }*/

        // Modification de l'événement "Journée FunKids"
        Evenement evenementModifie = serviceEvenement.getOneById(evenementFunKids.getId_Event());

        if (evenementModifie != null) {
            evenementModifie.setDescription("Evenement contenant plein d'activités");
            evenementModifie.setDate_Fin(new Date(124, 1, 10));
            evenementModifie.setStatus(Status.ANNULE);

            serviceEvenement.modifier(evenementModifie);

            System.out.println("L'événement 'Journée FunKids' a été modifié avec succès !");
        } else {
            System.out.println("Impossible de trouver l'événement 'Journée FunKids' à modifier.");
        }


       /* // Affichage de la table des événements
        System.out.println("Table des événements :");
        for (Evenement evenement : serviceEvenement.getAll()) {
            System.out.println(evenement);
        }*/

        // Affichage de tous les sponsors
        /*Set<Sponsor> sponsors = serviceSponsor.getAll();
        System.out.println("Liste des sponsors :");
        for (Sponsor sponsor : sponsors) {
            System.out.println(sponsor);
        }*/

        // Modification de la description des sponsors
        /*for (Sponsor sponsor : sponsors) {
            sponsor.setDescription_s("No information found");
            serviceSponsor.modifier(sponsor);
        }*/

        // Affichage des sponsors après modification
        /*System.out.println("Liste des sponsors après modification de la description :");
        for (Sponsor sponsor : serviceSponsor.getAll()) {
            System.out.println(sponsor);
        }*/

        // Suppression du sponsor associé à l'événement "Journée FunKids"
        /*if (evenementFunKids != null) {
            Sponsor sponsorFunKids = serviceSponsor.getAll().stream()
                    .filter(s -> s.getEvenement().equals(evenementFunKids))
                    .findFirst().orElse(null);

            if (sponsorFunKids != null) {
                serviceSponsor.supprimer(sponsorFunKids.getId_Sponsor());
                System.out.println("Le sponsor associé à l'événement 'Journée FunKids' a été supprimé avec succès !");
            } else {
                System.out.println("Aucun sponsor associé à l'événement 'Journée FunKids' trouvé.");
            }
        } else {
            System.out.println("Impossible de trouver l'événement 'Journée FunKids'.");
        }*/
    }
}
