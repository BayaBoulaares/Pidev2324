package edu.esprit.crudoff.services;


import java.sql.SQLException;
import java.util.Collection;

public interface IService <T>{
    public void ajouter(T u) throws SQLException;


    public void modifier(T u)throws SQLException;

    public void supprimer(int id) throws SQLException;
    public T getOneById(int id) throws SQLException;
    public Collection<T> getAll() throws SQLException;





}