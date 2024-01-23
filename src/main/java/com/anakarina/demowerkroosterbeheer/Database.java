package com.anakarina.demowerkroosterbeheer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private Connection conn;

    public Database(){
        //Developmentmode in XAMPP
        String sUser = "root";
        String sWachtwoord = "";
        String sHost = "localhost";
        String dbNaam = "werkrooster";

        try {
            //verbinding maken met de database
//      Mac ->     this.conn = DriverManager.getConnection("jdbc:mysql://"+sHost+":8889/"+dbNaam, sUser, sWachtwoord);
            this.conn = DriverManager.getConnection("jdbc:mysql://"+sHost+"/"+dbNaam, sUser, sWachtwoord);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
