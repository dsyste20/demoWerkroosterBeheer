package com.anakarina.demowerkroosterbeheer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {

    @Mock
    private Database mockDatabase;

    @Mock
    private Connection mockConnection;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
        try {
            when(mockDatabase.getConnection()).thenReturn(mockConnection); //mock the getConnection method
            when(mockConnection.isClosed()).thenReturn(false); //mock the connection to always be open
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getConnection_ShouldReturnValidConnection() {
        assertDoesNotThrow(() -> {
            Connection connection = mockDatabase.getConnection(); //the mocked method
            assertNotNull(connection);
            assertFalse(connection.isClosed());
        });
    }
}
