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
                String employeeId = resultSet.getString("medewerkerID");
                String afdeling = resultSet.getString("afdeling");
                EmployeeAvailability availability = new EmployeeAvailability(employeeId, afdeling);

                availability.setAvailability("maandag", resultSet.getString("maandag"));
                availability.setAvailability("dinsdag", resultSet.getString("dinsdag"));
                availability.setAvailability("woensdag", resultSet.getString("woensdag"));
                availability.setAvailability("donderdag", resultSet.getString("donderdag"));
                availability.setAvailability("vrijdag", resultSet.getString("vrijdag"));
                availability.setAvailability("zaterdag", resultSet.getString("zaterdag"));

                availabilities.add(availability);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return availabilities;
    }

}
