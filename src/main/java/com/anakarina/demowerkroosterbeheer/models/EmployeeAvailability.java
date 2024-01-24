package com.anakarina.demowerkroosterbeheer.models;

import java.util.HashMap;
import java.util.Map;

public class EmployeeAvailability {
    private String employeeId;
    private Map<String, String[]> availabilityMap;

    public EmployeeAvailability(String employeeId) {
        this.employeeId = employeeId;
        this.availabilityMap = new HashMap<>();
    }

    public void setAvailabilityForDay(String day, String startTime, String endTime) {
        availabilityMap.put(day, new String[]{startTime, endTime});
    }

    // Getter and other methods...
    public String getEmployeeId() {
        return employeeId;
    }

    public Map<String, String[]> getAvailabilityMap() {
        return availabilityMap;
    }
}
