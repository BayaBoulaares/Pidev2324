package edu.esprit.services;

import edu.esprit.entities.Question;
import edu.esprit.entities.Quiz;

import java.util.HashSet;

public interface IServices<T> {
    void add(T t);
    void update(T t);
    void delete(int id);
    T getById(int id);

    Quiz getByIdqz(int idqz);

    HashSet<T> getAll();

    Question getByIdq(int idq);

    Quiz getByIdrep(int id);
}
