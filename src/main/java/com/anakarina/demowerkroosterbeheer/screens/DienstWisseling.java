package com.anakarina.demowerkroosterbeheer.screens;

import com.anakarina.demowerkroosterbeheer.Database;
import com.anakarina.demowerkroosterbeheer.HelloApplication;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.IsoFields;

public class DienstWisseling {
    private Stage stage;
    private Database database;

    public DienstWisseling(Database database) {
        this.database = database;
        this.stage = new Stage();
        setupStage();
    }

    private void setupStage() {
        VBox layout = createShiftRequestLayout();
        Scene shiftScene = new Scene(layout, 1000, 800);
        shiftScene.getStylesheets().add(HelloApplication.class.getResource("stylesheets/dienstWisseling.css").toString());
        stage.setScene(shiftScene);
    }

    private VBox createShiftRequestLayout() {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        //label boven de lijst met vakantieaanvragen
        Label titleLabel = new Label("Dienstwisseling Verzoekingen");
        titleLabel.setStyle("-fx-font-size: 20px;");

        //koprij
        HBox headerRow = createHeaderRow();

        layout.getChildren().addAll(titleLabel, headerRow, new Separator());

        try {
            Connection conn = database.getConnection();
            String sql = "SELECT d.id, d.medewerkerID, m.firstname, m.lastname, d.dienst, d.reden, d.status " +
                    "FROM dienstwisseling d " +
                    "JOIN medewerker m ON d.medewerkerID = m.id " +
                    "ORDER BY d.dienst";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                java.sql.Date requestDate = rs.getDate("dienst");
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
        nameLabel.setMinWidth(150);

        Label dienstLabel = new Label("Dienst");
        dienstLabel.setMinWidth(150);

        Label redenLabel = new Label("reden");
        redenLabel.setMinWidth(150);

        Label statusLabel = new Label("status");
        statusLabel.setMinWidth(100);

        headerRow.getChildren().addAll(medewerkerIdLabel, nameLabel, dienstLabel, redenLabel, statusLabel);
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
        nameLabel.setMinWidth(150);

        Label dienstLabel = new Label(rs.getString("dienst"));
        dienstLabel.setMinWidth(150);

        Label redenLabel = new Label(rs.getString("reden"));
        redenLabel.setMinWidth(150);

        Label statusLabel = new Label(rs.getString("status"));
        statusLabel.setMinWidth(100);

        //controleer of de status "In behandeling" is voordat de knoppen worden toegevoegd
        if ("Aangevraagd".equals(statusLabel.getText())) {
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

            requestRow.getChildren().addAll(idLabel, nameLabel, dienstLabel, redenLabel, statusLabel, approveButton, denyButton);
        } else {
            //als de status niet "In behandeling" is, voeg alleen de labels toe
            requestRow.getChildren().addAll(idLabel, nameLabel, dienstLabel, redenLabel, statusLabel);
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
        String sql = "UPDATE dienstwisseling SET status = ? WHERE id = ?";
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
