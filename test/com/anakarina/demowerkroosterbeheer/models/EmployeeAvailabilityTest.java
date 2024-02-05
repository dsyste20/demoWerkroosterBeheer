package com.anakarina.demowerkroosterbeheer.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeAvailabilityTest {

    @Test
    void ReturnCorrectEmployeeIdTest() {
        String expectedEmployeeId = "12345";
        String afdeling = "IT";
        String day = "Maandag";
        String timeSlot = "09:00-17:00";

        EmployeeAvailability employeeAvailability = new EmployeeAvailability(expectedEmployeeId, afdeling, day, timeSlot);
        String actualEmployeeId = employeeAvailability.getEmployeeId();

        //assert
        assertEquals(expectedEmployeeId, actualEmployeeId, "De methode getEmployeeId() moet de correcte employeeId teruggeven die bij instantiatie is opgegeven.");
    }
}
