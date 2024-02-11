package edu.esprit.services;
import edu.esprit.entities.Utilisateur;
import java.util.Set;

public interface IService <T>{
    public void ajouter(T u);
    public void modifier(T u);
    public void supprimer(int id);
    public T getOneById(int id);
    public Set<T> getAll();





}
