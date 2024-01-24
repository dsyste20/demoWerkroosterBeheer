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
                // Assuming the table has columns like 'employee_id', 'day', 'start_time', 'end_time'
                String employeeId = resultSet.getString("employee_id");
                String day = resultSet.getString("day");
                String startTime = resultSet.getString("start_time");
                String endTime = resultSet.getString("end_time");

                // Create an EmployeeAvailability object for each record
                EmployeeAvailability availability = new EmployeeAvailability(employeeId);
                availability.setAvailabilityForDay(day, startTime, endTime);
                availabilities.add(availability);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }

        return availabilities;
    }

    // Other methods will be added later...
}
