package edu.esprit.services;
import edu.esprit.entities.Messagerie;

import java.util.HashSet;
import java.util.Set;

public interface IServices<T> {
    void ajouter(T t);
    void modifier(T t);



    void supprimer(int id);

    T getOneById(int id);
    HashSet<T> getAll();
}
