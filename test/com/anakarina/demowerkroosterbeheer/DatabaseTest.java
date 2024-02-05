package com.anakarina.demowerkroosterbeheer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {

    private Database database;

    @BeforeEach
    void setUp() {
        database = new Database();
    }

    @Test
    void getConnection_ShouldReturnValidConnection() {
        //retrieve a connection and assert that it's not null and open
        assertDoesNotThrow(() -> {
            try (Connection connection = database.getConnection()) {
                assertNotNull(connection);
                assertTrue(!connection.isClosed());
            }
        });
    }
}


