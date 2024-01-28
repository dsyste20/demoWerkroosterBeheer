package com.anakarina.demowerkroosterbeheer;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Employee {
    private String id;
    private String firstname;
    private String lastname;
    private String fullname;
    private String maandag;
    private String dinsdag;
    private String woensdag;
    private String donderdag;
    private String vrijdag;
    private String zaterdag;
    private String rol;


    public Employee() {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.maandag = maandag;
        this.dinsdag = dinsdag;
        this.woensdag = woensdag;
        this.donderdag = donderdag;
        this.vrijdag = vrijdag;
        this.zaterdag = zaterdag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getMaandag() {
        return maandag;
    }

    public void setMaandag(String maandag) {
        this.maandag = maandag;
    }

    public String getDinsdag() {
        return dinsdag;
    }

    public void setDinsdag(String dinsdag) {
        this.dinsdag = dinsdag;
    }

    public String getWoensdag() {
        return woensdag;
    }

    public void setWoensdag(String woensdag) {
        this.woensdag = woensdag;
    }

    public String getDonderdag() {
        return donderdag;
    }

    public void setDonderdag(String donderdag) {
        this.donderdag = donderdag;
    }

    public String getVrijdag() {
        return vrijdag;
    }

    public void setVrijdag(String vrijdag) {
        this.vrijdag = vrijdag;
    }

    public String getZaterdag() {
        return zaterdag;
    }

    public void setZaterdag(String zaterdag) {
        this.zaterdag = zaterdag;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getFullname() {
        return firstname + " " + lastname;
    }

    public boolean isAvailableOn(String day) {
        //verkrijg de beschikbaarheid voor de opgegeven dag
        String availability = getAvailabilityTimes().get(day);
        return availability != null && !availability.equalsIgnoreCase("x");
    }

    //methode om te controleren of de medewerker beschikbaar is voor een gegeven tijdslot.
    public boolean isAvailableForShift(String shiftTime) {
        String[] parts = shiftTime.split(" - ");
        LocalTime shiftStart = LocalTime.parse(parts[0]);
        LocalTime shiftEnd = LocalTime.parse(parts[1]);

        for (String availableTime : this.getAvailabilityTimes().values()) {
            String[] availableParts = availableTime.split(" - ");
            LocalTime availableStart = LocalTime.parse(availableParts[0]);
            LocalTime availableEnd = LocalTime.parse(availableParts[1]);

            if (!shiftStart.isBefore(availableStart) && !shiftEnd.isAfter(availableEnd)) {
                return true;
            }
        }

        return false;
    }

    //methode die de beschikbaarheidstijden als een Map teruggeeft
    public Map<String, String> getAvailabilityTimes() {
        Map<String, String> availability = new HashMap<>();
        availability.put("maandag", this.getMaandag());
        availability.put("dinsdag", this.getDinsdag());
        availability.put("woensdag", this.getWoensdag());
        availability.put("donderdag", this.getDonderdag());
        availability.put("vrijdag", this.getVrijdag());
        availability.put("zaterdag", this.getZaterdag());

        return availability;
    }

}
