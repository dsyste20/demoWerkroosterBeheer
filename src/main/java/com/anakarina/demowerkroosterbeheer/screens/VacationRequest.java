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
import java.time.ZoneId;

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
        stage.setScene(vacationScene);
    }

    private VBox createVacationRequestLayout() {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        //label boven de lijst met vakantieaanvragen
        Label titleLabel = new Label("Vakantieaanvragen");
        titleLabel.setStyle("-fx-font-size: 20px;");

        //koprij
        HBox headerRow = createHeaderRow();

        layout.getChildren().addAll(titleLabel, headerRow, new Separator());

        try {
            Connection conn = database.getConnection();
            String sql = "SELECT v.id, v.medewerkerID, m.firstname, m.lastname, v.aanvraag, v.status, v.aanvraagDatum, v.eindDatum " +
                    "FROM vakantieaanvragen v " +
                    "JOIN medewerker m ON v.medewerkerID = m.id " +
                    "ORDER BY v.aanvraagDatum";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                java.sql.Date requestDate = rs.getDate("aanvraagDatum");
                //controleer of de datum voorbij de huidige datum is
                java.util.Date currentDate = new java.util.Date();
                if (requestDate.toLocalDate().isAfter(currentDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())) {
                    //datum is nog niet verlopen, toon het verzoek
                    HBox requestRow = createRequestRow(rs);
                    layout.getChildren().addAll(requestRow, new Separator());
                }
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

        Label beginDatumLabel = new Label("Begin");
        beginDatumLabel.setMinWidth(100);

        Label eindDatumLabel = new Label("eind");
        eindDatumLabel.setMinWidth(100);

        headerRow.getChildren().addAll(medewerkerIdLabel, nameLabel, aanvraagLabel, statusLabel, beginDatumLabel, eindDatumLabel);
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

        Label startDateLabel = new Label(rs.getDate("aanvraagDatum").toString());
        startDateLabel.setMinWidth(100);

        Label endDateLabel = new Label(rs.getDate("eindDatum").toString());
        endDateLabel.setMinWidth(100);

        //controleer of de status "In behandeling" is voordat de knoppen worden toegevoegd
        if ("In behandeling".equals(statusLabel.getText())) {
            Button approveButton = new Button("Goedkeuren");
            approveButton.setId("approveButton");
            approveButton.setOnAction(e -> {
                try {
                    updateRequestStatus(requestId, "Goedgekeurd", requestRow);
                    showAlert(Alert.AlertType.INFORMATION, "Goedgekeurd", "Dienstwisseling goedgekeurd!");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });

            Button denyButton = new Button("Afwijzen");
            denyButton.setId("denyButton");
            denyButton.setOnAction(e -> {
                try {
                    updateRequestStatus(requestId, "Afgewezen", requestRow);
                    showAlert(Alert.AlertType.INFORMATION, "Afgewezen", "Dienstwisseling afgekeurdf!");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });

            requestRow.getChildren().addAll(idLabel, nameLabel, requestLabel, statusLabel, startDateLabel, endDateLabel, approveButton, denyButton);
        } else {
            //als de status niet "In behandeling" is, voeg alleen de labels toe
            requestRow.getChildren().addAll(idLabel, nameLabel, requestLabel, statusLabel, startDateLabel, endDateLabel);
        }

        return requestRow;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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

            requestRow.getChildren().removeIf(node -> node instanceof Button);
        }
    }

    public void show() {
        stage.show();
    }
}