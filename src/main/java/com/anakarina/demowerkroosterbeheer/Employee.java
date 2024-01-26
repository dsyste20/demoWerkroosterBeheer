package com.anakarina.demowerkroosterbeheer;

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

}
