package edu.esprit.tests;

import edu.esprit.entities.Administrateur;
import edu.esprit.entities.Enfant;
import edu.esprit.entities.Parent;
import edu.esprit.entities.Professeur;
import edu.esprit.services.ServiceAdmin;
import edu.esprit.services.ServiceEnfant;
import edu.esprit.services.ServiceParent;
import edu.esprit.services.ServiceProfesseur;

public class Main {
    public static void main(String[] args) {
        //Ajout Administrateur
        //ServiceAdmin su = new ServiceAdmin();
        /*Administrateur admin1 = new Administrateur( "Baya", "Boulaares", "Petite Ariana", "2000-08-12", "12082000", 54703286, "bayaBoulaares");
        su.ajouter(admin1);
        //Ajout Professeur
        ServiceProfesseur sp = new ServiceProfesseur();
        Professeur pr1 = new Professeur("Aziz", "Hzami", "Petite Ariana", "2000-10-30", "kkkkkkkk", 5470389, "azizHzami","math");
        sp.ajouter(pr1);
        //Ajout Parent
        ServiceParent spa = new ServiceParent();
        Parent pa1 = new Parent("Lina", "Boulaares", "Petite Ariana", "2000-08-13", "kdsjjds", 54703266, "mldsjku");
        spa.ajouter(pa1);*/
        //Ajout Enfant
        /*ServiceEnfant se = new ServiceEnfant();
        Enfant en = new Enfant("Mariem","Marzouk","11-08-2000",3,pa1);
        se.ajouter(en,pa1);*/
        //Modifier Administrateur
        /*ServiceAdmin su = new ServiceAdmin();
        Administrateur admin2 = su.getOneById(77);
        System.out.println("Administrateur trouvé : " + admin2);
        admin2.setLogin("Lina@example.com");
        su.modifier(admin2);*/
        //Modifier Parent
        /*ServiceParent sp = new ServiceParent();
        Parent pa2 = sp.getOneById(81);
        System.out.println("Parent trouvé : " + pa2);
        pa2.setNom("Seima");
        sp.modifier(pa2);*/
        //Modifier Professeur
        /*ServiceProfesseur spr = new ServiceProfesseur();
        Professeur pr2 = spr.getOneById(79);
        System.out.println("Parent trouvé : " + pr2);
        pr2.setNom("Seima");
        spr.modifier(pr2);*/
        //Modifier Enfant
        /*ServiceEnfant se = new ServiceEnfant();
        Enfant e2 = se.getOneById(80);
        System.out.println("Parent trouvé : " + e2);
        e2.setNom("Seima");
        se.modifier(e2);*/
        //Supprimer Enfant
        /*ServiceEnfant se = new ServiceEnfant();
        se.supprimer(80);*/
        //Supprimer Enfant
        /*ServiceParent sp = new ServiceParent();
        sp.supprimer(80);*/
        //Supprimer Professeur
        /*ServiceProfesseur sp =new ServiceProfesseur();
        sp.supprimer(79);*/
        // Supprimer Administrateur
        /*ServiceAdmin sa = new ServiceAdmin();
        sa.supprimer(77);*/
        //GetALL Parent
        /*ServiceParent sp = new ServiceParent();
        sp.getAll();*/
        //GetALL
        ServiceParent spa = new ServiceParent();
        Parent pa1 = new Parent("Lina", "Boulaares", "Petite Ariana", "2000-08-13", "kdsjjds", 54703266, "mldsjku");
        spa.ajouter(pa1);
        spa.getAll();
        ServiceEnfant se = new ServiceEnfant();
        Enfant en = new Enfant("Mariem","Marzouk","11-08-2000",3,pa1);
        se.ajouter(en,pa1);
        se.getAll();
        ServiceProfesseur sp = new ServiceProfesseur();
        sp.getAll();






    }

}
