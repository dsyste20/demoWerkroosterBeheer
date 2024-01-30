package com.anakarina.demowerkroosterbeheer.models;

import com.anakarina.demowerkroosterbeheer.Database;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.util.List;
import java.util.Map;

public class RosterManager {
    private Database database;

    public RosterManager(Database database) {
        this.database = database;
    }

    public void saveCurrentRoster(Map<String, List<String>> rosterData) throws SQLException {
        int weekNumberPlusOne = LocalDate.now().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) + 1;
        String tableName = "rooster_" + weekNumberPlusOne;
        if (!checkTableExists(tableName)) {
            createRosterTable(tableName);
            fillRosterTable(tableName, rosterData);
            deleteOldRoster();
        }
    }

    private boolean checkTableExists(String tableName) throws SQLException {
        String checkTableExistsSQL = "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name = ?;";
        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(checkTableExistsSQL)) {
            stmt.setString(1, tableName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    private void createRosterTable(String tableName) throws SQLException {
        String createTableSQL = "CREATE TABLE " + tableName + " ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "periode VARCHAR(35), "
                + "maandag VARCHAR(250), "
                + "dinsdag VARCHAR(250), "
                + "woensdag VARCHAR(250), "
                + "donderdag VARCHAR(250), "
                + "vrijdag VARCHAR(250), "
                + "zaterdag VARCHAR(250)"
                + ");";

        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(createTableSQL)) {
            stmt.executeUpdate();
        }
    }

    private void fillRosterTable(String tableName, Map<String, List<String>> rosterData) throws SQLException {
        String insertSQL = "INSERT INTO " + tableName + " (periode, maandag, dinsdag, woensdag, donderdag, vrijdag, zaterdag) VALUES (?, ?, ?, ?, ?, ?, ?);";
        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertSQL)) {
            stmt.setString(1, "Week " + (LocalDate.now().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) + 1));
            stmt.setString(2, String.join(", ", rosterData.get("maandag")));
            stmt.setString(3, String.join(", ", rosterData.get("dinsdag")));
            stmt.setString(4, String.join(", ", rosterData.get("woensdag")));
            stmt.setString(5, String.join(", ", rosterData.get("donderdag")));
            stmt.setString(6, String.join(", ", rosterData.get("vrijdag")));
            stmt.setString(7, String.join(", ", rosterData.get("zaterdag")));
            stmt.executeUpdate();
        }
    }

    public void deleteOldRoster() throws SQLException {
        int weekNumberMinusOne = LocalDate.now().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) - 1;
        String tableName = "rooster_" + weekNumberMinusOne;
        if (checkTableExists(tableName)) {
            dropRosterTable(tableName);
        }
    }

    private void dropRosterTable(String tableName) throws SQLException {
        String dropTableSQL = "DROP TABLE IF EXISTS " + tableName + ";";
        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(dropTableSQL)) {
            stmt.executeUpdate();
        }
    }

    public void updateOrSaveCurrentRoster(Map<String, List<String>> rosterData) throws SQLException {
        int weekNumberPlusOne = LocalDate.now().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) + 1;
        String tableName = "rooster_" + weekNumberPlusOne;

        if (checkTableExists(tableName)) {
            updateRosterTable(tableName, rosterData, weekNumberPlusOne);
        } else {
            createRosterTable(tableName);
            fillRosterTable(tableName, rosterData);
        }
        deleteOldRoster();
    }

    private void updateRosterTable(String tableName, Map<String, List<String>> rosterData, int weekNumberPlusOne) throws SQLException {
        //update bestaande tabel met de nieuwe gegevens
        String updateSQL = "UPDATE " + tableName + " SET "
                + "maandag=?, dinsdag=?, woensdag=?, donderdag=?, vrijdag=?, zaterdag=? "
                + "WHERE periode=?;";
        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(updateSQL)) {
            stmt.setString(1, String.join(", ", rosterData.get("maandag")));
            stmt.setString(2, String.join(", ", rosterData.get("dinsdag")));
            stmt.setString(3, String.join(", ", rosterData.get("woensdag")));
            stmt.setString(4, String.join(", ", rosterData.get("donderdag")));
            stmt.setString(5, String.join(", ", rosterData.get("vrijdag")));
            stmt.setString(6, String.join(", ", rosterData.get("zaterdag")));
            stmt.setString(7, "Week " + weekNumberPlusOne);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                fillRosterTable(tableName, rosterData);
            }
        }
    }

}
