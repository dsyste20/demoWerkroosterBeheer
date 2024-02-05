package com.anakarina.demowerkroosterbeheer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {
    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee();
    }

    @Test
    void getIdAndSetId() {
        String expectedId = "1";
        employee.setId(expectedId);
        assertEquals(expectedId, employee.getId());
    }

    @Test
    void getFirstnameAndSetFirstname() {
        String expectedFirstname = "Jan";
        employee.setFirstname(expectedFirstname);
        assertEquals(expectedFirstname, employee.getFirstname());
    }

    @Test
    void getLastnameAndSetLastname() {
        String expectedLastname = "Jansen";
        employee.setLastname(expectedLastname);
        assertEquals(expectedLastname, employee.getLastname());
    }

    @Test
    void getFullname() {
        employee.setFirstname("Jan");
        employee.setLastname("Jansen");
        assertEquals("Jan Jansen", employee.getFullname());
    }

    @Test
    void getMaandagAndSetMaandag() {
        String expectedAvailability = "09:00-17:00";
        employee.setMaandag(expectedAvailability);
        assertEquals(expectedAvailability, employee.getMaandag());
    }

    @Test
    void getAvailabilityTimes() {
        employee.setMaandag("09:00 - 17:00");
        employee.setDinsdag("10:00 - 18:00");
        employee.setWoensdag("17:00 - 21:00");
        employee.setDonderdag("17:00 - 21:30");
        employee.setVrijdag("07:00 - 18:00");
        employee.setZaterdag("07:00 - 18:00");

        assertEquals("09:00 - 17:00", employee.getAvailabilityTimes().get("maandag"));
        assertEquals("10:00 - 18:00", employee.getAvailabilityTimes().get("dinsdag"));
        assertEquals("17:00 - 21:00", employee.getAvailabilityTimes().get("woensdag"));
        assertEquals("17:00 - 21:30", employee.getAvailabilityTimes().get("donderdag"));
        assertEquals("07:00 - 18:00", employee.getAvailabilityTimes().get("vrijdag"));
        assertEquals("07:00 - 18:00", employee.getAvailabilityTimes().get("zaterdag"));

        assertNotEquals("10:00 - 18:00", employee.getAvailabilityTimes().get("maandag"));
        assertNotEquals("07:00 - 18:00", employee.getAvailabilityTimes().get("dinsdag"));
        assertNotEquals("10:00 - 18:00", employee.getAvailabilityTimes().get("woensdag"));
        assertNotEquals("10:00 - 18:00", employee.getAvailabilityTimes().get("donderdag"));
        assertNotEquals("10:00 - 18:00", employee.getAvailabilityTimes().get("vrijdag"));
        assertNotEquals("10:00 - 18:00", employee.getAvailabilityTimes().get("zaterdag"));

        assertNull(employee.getAvailabilityTimes().get("zondag"));
    }
}
