package com.anakarina.demowerkroosterbeheer.models;

import com.anakarina.demowerkroosterbeheer.Employee;

public class RolAssignment {
    private String rol;
    private Employee employee;

    public RolAssignment(String rol) {
        this.rol = rol;
        this.employee = null;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
