package edu.esprit.tests;

import edu.esprit.entities.Reclamation;
import edu.esprit.services.ServiceReclamation;
import edu.esprit.utils.DataSource;

import java.sql.Array;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        DataSource dataSource = DataSource.getInstance();
        ServiceReclamation sr = new ServiceReclamation();
        Reclamation r = new Reclamation("omar","abcdefgh","23/01/2024");
        //sr.ajouter(r);
        //String nom = "baya";
        //sr.supprimer(1);
        //String nom = "baya";
        //System.out.println(sr.getOneById(r.getId()));


        /*HashSet<Reclamation> rr = sr.getAll();
        //System.out.println(rr);

        for(Reclamation me : rr) {
            System.out.println("*******************");
            System.out.println(sr.getOneById(me.getId()));
        }

         */

        Reclamation mr = sr.getOneById(2);
        mr.setNom("samih");
        mr.setReclamation("123456");

        sr.modifier(mr);


        //System.out.println(sr.getAll());


    }

}
