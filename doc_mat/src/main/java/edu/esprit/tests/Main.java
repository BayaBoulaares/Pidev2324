package edu.esprit.tests;

import edu.esprit.entities.Document;
import edu.esprit.entities.Matiere;
import edu.esprit.entities.Niveau;
import edu.esprit.entities.Type;
import edu.esprit.services.*;
import edu.esprit.utils.DataSource;

import java.sql.Date;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        DataSource dataSource = DataSource.getInstance();
        SeviceMatiere sm = new SeviceMatiere();
        ServiceDocument sd = new ServiceDocument();

        // Ajouter une matiere
       Matiere nouvelleMatiere = new Matiere("anglais", "Cours sur les mathématiques");
       sm.ajouter(nouvelleMatiere);

        // Afficher toutes les matières
       System.out.println("Liste des matières:");
        ArrayList<Matiere> toutesLesMatieres = sm.getAll();
        for (Matiere matiere : toutesLesMatieres) {
            System.out.println("******************");
            System.out.println(sm.getOne(matiere.getId()));

        }



        // Modifier une matiere
          Matiere mtm = sm.getOne(1); // Suppose que la matière avec l'ID 1 existe

        mtm.setDescription("Nouvelle description pour les ématiques");
        sm.modifier(mtm);

        // Afficher toutes les matières après modification
        System.out.println("Liste des matières après modification:");
        System.out.println(sm.getAll());


        // Supprimer une matiere
        Matiere matiereASupprimer = sm.getOne(4); // Suppose que la matière avec l'ID 2 existe
        sm.supprimer(matiereASupprimer.getId());

        // Afficher toutes les matières après suppression
        System.out.println("Liste des matières après suppression:");
        System.out.println(sm.getAll());
        //document
        System.out.println("*******document****************************************");
        Matiere mdoc=sm.getOne(6);
        // Ajouter un document lié à la matière ajoutée
        Document nouveauDocument = new Document("Document d'anglais", Type.PDF, "https://exemple.com", Niveau.LEVEL_2, Date.valueOf("2024-02-10"), mdoc);
        sd.ajouter(nouveauDocument);

        // Afficher tous les documents
        System.out.println("Liste des documents :");
        ArrayList<Document> tousLesDocuments = sd.getAll();
        for (Document document : tousLesDocuments) {
            System.out.println("******************");
            System.out.println(sd.getOne(document.getId()));
        }

        // Modifier un document
        Document documentAModifier = sd.getOne(1); // Suppose que le document avec l'ID 1 existe
        documentAModifier.setTitre("chapitre1");
        sd.modifier(documentAModifier);
        System.out.println("Liste des documents après modification:");
        System.out.println(sd.getAll());
        // Supprimer un document
        Document documentASupprimer = sd.getOne(5); // Suppose que le document avec l'ID 2 existe
        sd.supprimer(documentASupprimer.getId());

        // Afficher tous les documents après suppression
        System.out.println("Liste des documents après suppression:");
        System.out.println(sd.getAll());
    }

}