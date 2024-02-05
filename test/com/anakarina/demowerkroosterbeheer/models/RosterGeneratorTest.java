package com.anakarina.demowerkroosterbeheer.models;

import com.anakarina.demowerkroosterbeheer.Database;
import com.anakarina.demowerkroosterbeheer.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RosterGeneratorTest {

    @Mock
    private Database mockDatabase;
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockStatement;
    @Mock
    private ResultSet mockResultSet;

    private RosterGenerator rosterGenerator;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.initMocks(this);
        when(mockDatabase.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(Mockito.anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        rosterGenerator = new RosterGenerator(mockDatabase);
    }

    @Test
    void generateAndDisplayRosterTest() throws SQLException {
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getString(Mockito.anyString())).thenReturn("1");

        //simulate fetching employee availability
        Map<String, List<Employee>> roster = rosterGenerator.generateAndDisplayRoster();

        assertNotNull(roster, "Roster should not be null");
        assertFalse(roster.isEmpty(), "Roster should not be empty");
    }

    @Test
    void fetchEmployeesTest() throws SQLException {
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getString("id")).thenReturn("1");
        when(mockResultSet.getString("firstname")).thenReturn("John");
        when(mockResultSet.getString("lastname")).thenReturn("Doe");

        List<Employee> employees = rosterGenerator.fetchEmployees();

        assertNotNull(employees, "Employee list should not be null");
        assertFalse(employees.isEmpty(), "Employee list should not be empty");
        assertEquals(1, employees.size(), "Employee list size should be 1");
        assertEquals("John", employees.get(0).getFirstname(), "Employee's first name should be John");
        assertEquals("Doe", employees.get(0).getLastname(), "Employee's last name should be Doe");
    }
}