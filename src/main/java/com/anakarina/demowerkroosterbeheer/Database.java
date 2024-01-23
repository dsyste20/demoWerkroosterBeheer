package com.anakarina.demowerkroosterbeheer;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class MySQLConnection {
    private Connection conn;

    public MySQLConnection() {
        String user = "root";
        String passwd = "root";
        String cString = "jdbc:mysql://localhost:8889/winkeldb?user=" + user + "&password=" + passwd;

        try {
            this.conn = DriverManager.getConnection(cString);
        } catch (SQLException var5) {
            System.out.println("Kan geen verbinding maken!");
        }

    }
}
