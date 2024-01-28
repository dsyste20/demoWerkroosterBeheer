package com.anakarina.demowerkroosterbeheer.screens;

import com.anakarina.demowerkroosterbeheer.Database;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VacationRequest {
    private Stage stage;
    private Database database;

    public VacationRequest(Database database) {
        this.database = database;
        this.stage = new Stage();
        setupStage();
    }

    private void setupStage() {
        VBox layout = createVacationRequestLayout();
        Scene scene = new Scene(layout, 1000, 800);
        stage.setTitle("Vakantieaanvraag");
        stage.setScene(scene);
    }

    private VBox createVacationRequestLayout() {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        // Koprij
        HBox headerRow = new HBox(10);
        headerRow.setAlignment(Pos.CENTER_LEFT);
        headerRow.getChildren().addAll(
                new Label("Medewerker ID"),
                new Label("Naam"),
                new Label("Aanvraag"),
                new Label("Status"),
                new Label("Datum")
        );
        layout.getChildren().add(headerRow);

        Separator separator = new Separator();
        layout.getChildren().add(separator);

        try {
            Connection conn = database.getConnection();
            String sql = "SELECT v.id, v.medewerkerID, m.firstname, m.lastname, v.aanvraag, v.status, v.aanvraagDatum " +
                    "FROM vakantieaanvragen v " +
                    "JOIN medewerker m ON v.medewerkerID = m.id " +
                    "ORDER BY v.aanvraagDatum";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                HBox requestRow = createRequestRow(rs);
                layout.getChildren().addAll(requestRow, new Separator());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return layout;
    }


    private void updateRequestStatus(int requestId, String status, HBox requestRow) throws SQLException {
        Connection conn = database.getConnection();
        String sql = "UPDATE vakantieaanvragen SET status = ? WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, status);
        stmt.setInt(2, requestId);

        int affectedRows = stmt.executeUpdate();

        if (affectedRows > 0) {
            Label statusLabel = (Label) requestRow.getChildren().get(3);
            statusLabel.setText(status);

            Button approveButton = (Button) requestRow.getChildren().get(4);
            Button denyButton = (Button) requestRow.getChildren().get(5);
            requestRow.getChildren().removeAll(approveButton, denyButton);
        }
    }

    private HBox createRequestRow(ResultSet rs) throws SQLException {
        HBox requestRow = new HBox(10);
        requestRow.setAlignment(Pos.CENTER_LEFT);

        int requestId = rs.getInt("id");
        String medewerkerId = String.valueOf(rs.getInt("medewerkerID"));
        String naam = rs.getString("firstname") + " " + rs.getString("lastname");
        String aanvraag = rs.getString("aanvraag");
        String status = rs.getString("status");
        String aanvraagDatum = rs.getDate("aanvraagDatum").toString();

        Label employeeLabel = new Label(medewerkerId);
        Label nameLabel = new Label(naam);
        Label requestLabel = new Label(aanvraag);
        Label statusLabel = new Label(status);
        Label dateLabel = new Label(aanvraagDatum);

        Button approveButton = new Button("Goedkeuren");
        approveButton.setOnAction(e -> {
            try {
                updateRequestStatus(requestId, "Goedgekeurd", requestRow);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        Button denyButton = new Button("Afwijzen");
        denyButton.setOnAction(e -> {
            try {
                updateRequestStatus(requestId, "Afgewezen", requestRow);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        requestRow.getChildren().addAll(employeeLabel, nameLabel, requestLabel, statusLabel, dateLabel, approveButton, denyButton);

        return requestRow;
    }

    public void show() {
        stage.show();
    }
}