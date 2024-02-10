package edu.esprit.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
    private String url = "jdbc:mysql://localhost:3306/pidev2324";
    private String user = "root";
    private String passwd = "";

    private Connection cnx;

    private static DataSource instance;

    private DataSource(){
        try {
            cnx = DriverManager.getConnection(url, user, passwd);
            System.out.println("Connected to DB!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static DataSource getInstance(){
        if(instance == null){
            instance = new DataSource();
        }
        return instance;
    }

    public Connection getCnx() {
        return cnx;
    }
}
