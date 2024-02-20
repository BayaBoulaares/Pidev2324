package edu.esprit.services;

import edu.esprit.entities.ExistanteException;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IService <T>{
    public void ajouter(T v) throws SQLException , ExistanteException;
    public void modifier(T v) throws SQLException;
    public void supprimer(int id) throws SQLException;
    public ArrayList<T> getAll() throws SQLException;
    public T getOne(int id) throws SQLException;

}
