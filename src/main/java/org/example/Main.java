package org.example;

import java.sql.Connection;

public class Main {

    public static void main(String[] args) {

        try {
            Connection connection = DBConnection.getConnection();

            System.out.println("Database connected successfully!");

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}