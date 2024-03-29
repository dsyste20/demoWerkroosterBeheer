package com.anakarina.demowerkroosterbeheer.models;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class EmployeeAvailability {
    private String employeeId;
    private String afdeling;
    private String day;
    private String timeSlot;

    public EmployeeAvailability(String employeeId, String afdeling, String day, String timeSlot) {
        this.employeeId = employeeId;
        this.afdeling = afdeling;
        this.day = day;
        this.timeSlot = timeSlot;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getAfdeling() {
        return afdeling;
    }

    public void setAfdeling(String afdeling) {
        this.afdeling = afdeling;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    /**
     * Checks if the employee's availability includes the time range specified by startTime and endTime.
     *
     * @param startTime The start time of the shift.
     * @param endTime   The end time of the shift.
     * @return true if the availability overlaps with the shift times, false otherwise.
     */
    public boolean includesTimeRange(String startTime, String endTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        try {
            //trim de tijden om zeker te zijn dat er geen overtollige spaties zijn
            startTime = startTime.trim();
            endTime = endTime.trim();

            //parse de tijden van de shift
            LocalTime shiftStart = LocalTime.parse(startTime, formatter);
            LocalTime shiftEnd = LocalTime.parse(endTime, formatter);

            //controleer of 'x' of andere niet-tijdsnotaties worden gebruikt
            if (timeSlot.equalsIgnoreCase("x")) {
                return false;
            }

            //trim ook de beschikbaarheidstijden en controleer of er twee tijden zijn
            String[] times = timeSlot.trim().split("-");
            if (times.length < 2) {
                //niet genoeg informatie om een tijdslot te parsen
                return false;
            }

            LocalTime availableStart = LocalTime.parse(times[0].trim(), formatter);
            LocalTime availableEnd = LocalTime.parse(times[1].trim(), formatter);

            //check of de shift binnen de beschikbaarheidstijden valt
            return !availableStart.isAfter(shiftStart) && !availableEnd.isBefore(shiftEnd);
        } catch (DateTimeParseException e) {
            System.err.println("Error parsing timeSlot in EmployeeAvailability: " + e.getMessage());
            return false;
        }
    }
}

