package org.example;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.sql.*;

public class DBConnection {

    private static String url;
    private static String userName;
    private static  String password;

    static{
        try{
               Properties properties= new Properties();

               InputStream input= DBConnection.class.getClassLoader().getResourceAsStream("db.properties");

               if(input==null){
                   throw new RuntimeException("db.properties not found");
               }
               properties.load(input);
               input.close();

               url= properties.getProperty("db.url");
               userName=properties.getProperty("db.username");
               password=properties.getProperty("db.password");


        }
        catch(IOException e){
            throw new RuntimeException("Failed to load db.properties",e);

        }
    }
    public  static  Connection getConnection(){
        try{
            return DriverManager.getConnection(url,userName,password);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect database",e);
        }
    }

}
