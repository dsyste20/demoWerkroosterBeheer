package com.anakarina.demowerkroosterbeheer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private Connection conn;

    public Database() {
        //development mode in XAMPP
        String sUser = "root";
        String sWachtwoord = "";
        String sHost = "localhost";
        String dbNaam = "werkrooster";

        try {
            //connect to the database
            this.conn = DriverManager.getConnection("jdbc:mysql://"+sHost+"/"+dbNaam, sUser, sWachtwoord);
            System.out.println("Connected to the database successfully.");
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            // NOTE: maybe later to retry or exit the application here
        }
    }

    //retrieve the connection
    public Connection getConnection() {
        return conn;
    }

    //close the connection when the application stops
    public void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.err.println("Failed to close the database connection: " + e.getMessage());
            }
        }
    }
}
