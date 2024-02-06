package com.anakarina.demowerkroosterbeheer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
//    private String sUser = "root";
//    private String sWachtwoord = "";
//    private String sHost = "localhost";
//    private String dbNaam = "werkrooster";
    private String sUser = "dbu2609337";
    private String sWachtwoord = "Ana-karinaa0";
    private String sHost = "rdbms.strato.de";
    private String dbNaam = "dbs12557499";
    private Properties connectionProps;

    public Database() {
        connectionProps = new Properties();
        connectionProps.put("user", sUser);
        connectionProps.put("password", sWachtwoord);

        //include additional properties to manage connections better
        connectionProps.put("autoReconnect", "true");
        connectionProps.put("useSSL", "false");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found: " + e.getMessage());
        }
    }

    //retrieve a new connection
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://" + sHost + "/" + dbNaam + "?useTimezone=true&serverTimezone=UTC",
                connectionProps
        );
    }

    //close the connection when the application stops
//    public void closeConnection() {
//        if (conn != null) {
//            try {
//                conn.close();
//                System.out.println("Database connection closed.");
//            } catch (SQLException e) {
//                System.err.println("Failed to close the database connection: " + e.getMessage());
//            }
//        }
//    }
}
