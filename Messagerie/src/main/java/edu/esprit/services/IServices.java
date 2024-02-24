package edu.esprit.services;
import edu.esprit.entities.Messagerie;
import edu.esprit.entities.Reclamation;


import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public interface IServices<T> {
    void ajouter(T t) throws SQLException;
    void modifier(T t) throws SQLException;



    void supprimer(int id);

    T getOneById(int id);
    HashSet<T> getAll() throws SQLException;
}
