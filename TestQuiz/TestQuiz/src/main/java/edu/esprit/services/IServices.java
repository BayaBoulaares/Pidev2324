package edu.esprit.services;

import edu.esprit.entities.Reponse;
import edu.esprit.entities.Question;

import java.util.HashSet;

public interface IServices<T> {
    void add(T t);
    void update(T t);
    void delete(int idq);
    T getByIdq(int idq);
    Reponse getById(int id);
    HashSet<T> getAll();
    Reponse getByIdrep(int id);
}












