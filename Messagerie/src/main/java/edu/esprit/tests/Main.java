package edu.esprit.tests;

import edu.esprit.entities.Messagerie;
import edu.esprit.services.ServiceMessagerie;
import edu.esprit.utils.DataSource;

import java.sql.Array;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        DataSource dataSource = DataSource.getInstance();
        ServiceMessagerie sm = new ServiceMessagerie();
        //Messagerie m = new Messagerie("baya","23/01/2024","aloooo");
        //sm.ajouter(m);
        //String nom = "baya";
        //sm.supprimer(4);
        //String nom = "baya";
        //System.out.println(sm.getOneById(m.getId()));


        /*HashSet<Messagerie> mm = sm.getAll();
        //System.out.println(mm);

        for(Messagerie me : mm) {
            System.out.println("*******************");
            System.out.println(sm.getOneById(me.getId()));
        }

         */

        Messagerie mt = sm.getOneById(7);
        mt.setNom("mariem");
        mt.setMessage("bybyyyy");


        sm.modifier(mt);


        //System.out.println(sm.getAll());


    }

}
