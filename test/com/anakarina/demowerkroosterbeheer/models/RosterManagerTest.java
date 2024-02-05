package com.anakarina.demowerkroosterbeheer.models;

import com.anakarina.demowerkroosterbeheer.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

class RosterManagerTest {

    @Mock
    private Database mockDatabase;
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;

    @InjectMocks
    private RosterManager rosterManager;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        when(mockDatabase.getConnection()).thenReturn(mockConnection);
    }

    @Test
    void deleteOldRosterTest() throws SQLException {
        String oldTableName = "rooster_10";

        //mock het gedrag voor het controleren of de tabel bestaat
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(1);

        //voer de methode uit die we willen testen
        rosterManager.deleteOldRoster();

        //verifieren dat de juiste SQL-opdrachten worden aangeroepen
        verify(mockConnection).prepareStatement(startsWith("SELECT COUNT(*) FROM information_schema.tables"));
        verify(mockConnection).prepareStatement(startsWith("DROP TABLE IF EXISTS"));
    }

    @Test
    void updateOrSaveCurrentRosterTest() throws SQLException {
        //mock de database interactie
        when(mockDatabase.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        //simuleren dat de tabel niet bestaat
        when(mockResultSet.next()).thenReturn(false);

        //voorbereiden van een niet-null roosterData map
        Map<String, List<String>> rosterData = Map.of(
                "maandag", List.of("Jan Jansen"),
                "dinsdag", List.of(),
                "woensdag", List.of(),
                "donderdag", List.of(),
                "vrijdag", List.of(),
                "zaterdag", List.of()
        );

        //voer de methode uit
        rosterManager.updateOrSaveCurrentRoster(rosterData);

        //verifieren dat de juiste SQL commando's worden uitgevoerd
        verify(mockConnection, atLeastOnce()).prepareStatement(startsWith("CREATE TABLE"));
        verify(mockConnection, atLeastOnce()).prepareStatement(startsWith("INSERT INTO"));
    }

    @Test
    void CreateNewTableTest() throws SQLException {
        Map<String, List<String>> rosterData = Map.of("maandag", List.of("Jan Jansen"));

        //mock het PreparedStatement voor alle SQL queries
        PreparedStatement mockCreateTableStmt = mock(PreparedStatement.class);
        PreparedStatement mockInsertStmt = mock(PreparedStatement.class);

        when(mockConnection.prepareStatement(startsWith("SELECT COUNT(*)"))).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(0); // Tabel bestaat niet

        //zorg ervoor dat de juiste PreparedStatement mocks worden teruggegeven voor de CREATE TABLE en INSERT INTO queries
        when(mockConnection.prepareStatement(contains("CREATE TABLE"))).thenReturn(mockCreateTableStmt);
        when(mockConnection.prepareStatement(startsWith("INSERT INTO"))).thenReturn(mockInsertStmt);

        rosterManager.updateOrSaveCurrentRoster(rosterData);

        //verifieren dat de juiste SQL-commando's worden uitgevoerd
        verify(mockConnection).prepareStatement(contains("CREATE TABLE"));
        verify(mockCreateTableStmt).executeUpdate();

        verify(mockConnection).prepareStatement(startsWith("INSERT INTO"));
        verify(mockInsertStmt).executeUpdate();
    }

}
