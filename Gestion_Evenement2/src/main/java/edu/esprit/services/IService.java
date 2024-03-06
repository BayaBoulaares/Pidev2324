package edu.esprit.services;

import edu.esprit.entities.Administrateur;
import edu.esprit.entities.ExistanteException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public interface IService <T>{
    public void ajouter(T v) throws SQLException , ExistanteException;
    public void modifier(T v) throws SQLException,ExistanteException;
    public void supprimer(int id) throws SQLException;
    public Collection<T> getAll() throws SQLException;
    public T getOne(int id) throws SQLException;
    public T getOneById(int id) throws SQLException;

}
