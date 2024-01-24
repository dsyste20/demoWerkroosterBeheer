package com.anakarina.demowerkroosterbeheer.models;

import com.anakarina.demowerkroosterbeheer.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RosterGenerator {
    private Database database;

    public RosterGenerator(Database database) {
        this.database = database;
    }

    public List<EmployeeAvailability> fetchAvailabilities() {
        List<EmployeeAvailability> availabilities = new ArrayList<>();
        String sql = "SELECT * FROM beschikbaarheid";

        try (Connection conn = database.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                //table columns
                String employeeId = resultSet.getString("id");
                String day = resultSet.getString("medewerkerID");
                String afdeling = resultSet.getString("addeling");
                String maandag = resultSet.getString("maandag");
                String dinsdag = resultSet.getString("dinsdag");
                String woensdag = resultSet.getString("woensdag");
                String donderdag = resultSet.getString("donderdag");
                String vrijdag = resultSet.getString("vrijdag");
                String zaterdag = resultSet.getString("zaterdag");

                //create an EmployeeAvailability object for each record
                EmployeeAvailability availability = new EmployeeAvailability(employeeId);
                availability.setAvailabilityForDay(day, startTime, endTime);
                availabilities.add(availability);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return availabilities;
    }

}
