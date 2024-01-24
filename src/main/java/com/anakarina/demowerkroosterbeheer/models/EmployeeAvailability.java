package com.anakarina.demowerkroosterbeheer.models;

import java.util.HashMap;
import java.util.Map;

public class EmployeeAvailability {
    private String employeeId;
    private String afdeling;
    private Map<String, String> availabilityMap;

    public EmployeeAvailability(String employeeId, String afdeling) {
        this.employeeId = employeeId;
        this.afdeling = afdeling;
        this.availabilityMap = new HashMap<>();
    }

    public void setAvailability(String day, String availability) {
        availabilityMap.put(day, availability);
    }

    // Getter methods...
    public String getEmployeeId() {
        return employeeId;
    }

    public String getAfdeling() {
        return afdeling;
    }

    public Map<String, String> getAvailabilityMap() {
        return availabilityMap;
    }
}
