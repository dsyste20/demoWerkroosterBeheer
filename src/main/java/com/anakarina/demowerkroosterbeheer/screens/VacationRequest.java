package com.anakarina.demowerkroosterbeheer.screens;

import com.anakarina.demowerkroosterbeheer.Database;
import com.anakarina.demowerkroosterbeheer.HelloApplication;
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
        Scene vacationScene = new Scene(layout, 1000, 800);
        vacationScene.getStylesheets().add(HelloApplication.class.getResource("stylesheets/vacationRequest.css").toString());
        stage.setTitle("Vakantieaanvraag");
        stage.setScene(vacationScene);
    }

    private VBox createVacationRequestLayout() {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        // Koprij met vaste breedtes voor elke kolom
        HBox headerRow = createHeaderRow();
        layout.getChildren().add(headerRow);

        // Horizontale lijn na de koptekst
        layout.getChildren().add(new Separator());

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

    private HBox createHeaderRow() {
        HBox headerRow = new HBox(10);
        headerRow.setAlignment(Pos.CENTER_LEFT);

        Label medewerkerIdLabel = new Label("mID");
        medewerkerIdLabel.setMinWidth(50);

        Label nameLabel = new Label("Naam");
        nameLabel.setMinWidth(100);

        Label aanvraagLabel = new Label("Aanvraag");
        aanvraagLabel.setMinWidth(150);

        Label statusLabel = new Label("Status");
        statusLabel.setMinWidth(100);

        Label datumLabel = new Label("Datum");
        datumLabel.setMinWidth(100);

        headerRow.getChildren().addAll(medewerkerIdLabel, nameLabel, aanvraagLabel, statusLabel, datumLabel);
        return headerRow;
    }

    private HBox createRequestRow(ResultSet rs) throws SQLException {
        HBox requestRow = new HBox(10);
        requestRow.setAlignment(Pos.CENTER_LEFT);

        int requestId = rs.getInt("id");
        Label idLabel = new Label(String.valueOf(rs.getInt("medewerkerID")));
        idLabel.setMinWidth(50);

        String fullName = rs.getString("firstname") + " " + rs.getString("lastname");
        Label nameLabel = new Label(fullName);
        nameLabel.setMinWidth(100);

        Label requestLabel = new Label(rs.getString("aanvraag"));
        requestLabel.setMinWidth(150);

        Label statusLabel = new Label(rs.getString("status"));
        statusLabel.setMinWidth(100);

        Label dateLabel = new Label(rs.getDate("aanvraagDatum").toString());
        dateLabel.setMinWidth(100);

        Button approveButton = new Button("Goedkeuren");
        approveButton.setId("approveButton");
        approveButton.setOnAction(e -> {
            try {
                updateRequestStatus(requestId, "Goedgekeurd", requestRow);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        Button denyButton = new Button("Afwijzen");
        denyButton.setId("denyButton");
        denyButton.setOnAction(e -> {
            try {
                updateRequestStatus(requestId, "Afgewezen", requestRow);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        requestRow.getChildren().addAll(idLabel, nameLabel, requestLabel, statusLabel, dateLabel, approveButton, denyButton);
        return requestRow;
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

    public void show() {
        stage.show();
    }
}