package edu.esprit.crudoff.utilis;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataSource {
    private String url = "jdbc:mysql://localhost:3306/pidev2324";
    private String user = "root";

    private String passwd = "";

    private Connection cnx ;
    private static DataSource insatnce;  //2- Declare une variable static de type class



    private  DataSource(){   // 1-Singleton Constructeur Private

        try{
            cnx = DriverManager.getConnection(url,user,passwd);
            System.out.println("Connected Successfully !");
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    public static DataSource getInsatnce() { // 3-Implementation la getter de la variable static
        if(insatnce==null)
        {
            insatnce= new DataSource();
        }
        return insatnce;
    }

    public Connection getConnection() {
        return cnx;
    }




}
