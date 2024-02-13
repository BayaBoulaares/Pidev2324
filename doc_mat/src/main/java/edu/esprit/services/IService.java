package edu.esprit.services;

import java.util.ArrayList;

public interface IService <T>{
    public void ajouter(T v);
    public void modifier(T v);
    public void supprimer(int id);
    public ArrayList<T> getAll();
    public T getOne(int id);

}
