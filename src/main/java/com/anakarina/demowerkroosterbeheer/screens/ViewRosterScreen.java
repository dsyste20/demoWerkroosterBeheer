package com.anakarina.demowerkroosterbeheer.screens;

import com.anakarina.demowerkroosterbeheer.Database;
import com.anakarina.demowerkroosterbeheer.RosterEntry;
import javafx.scene.Scene;
import javafx.util.Callback;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.IsoFields;

public class ViewRosterScreen {
    private Database database;

    public ViewRosterScreen(Database database) {
        this.database = database;
    }

    public void show() {
        Stage stage = new Stage();
        VBox layout = new VBox();
        Scene scene = new Scene(layout, 800, 600);

        TableView<RosterEntry> table = new TableView<>();
        setupTableColumns(table);

        try {
            populateTableWithData(table);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        layout.getChildren().add(table);
        stage.setScene(scene);
        stage.show();
    }

    private void setupTableColumns(TableView<RosterEntry> table) {
        //stel de kolommen van de tabel in
        String[] columnNames = {"Periode", "Maandag", "Dinsdag", "Woensdag", "Donderdag", "Vrijdag", "Zaterdag"};

        for (String columnName : columnNames) {
            TableColumn<RosterEntry, String> column = new TableColumn<>(columnName);
            column.setCellValueFactory(new PropertyValueFactory<>(columnName.toLowerCase()));
            column.setCellFactory(createCellFactory());
            table.getColumns().add(column);
        }
    }

    private void populateTableWithData(TableView<RosterEntry> table) throws SQLException {
        int weekNumber = LocalDate.now().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) + 1;
        String tableName = "rooster_" + weekNumber;

        String sql = "SELECT * FROM " + tableName + ";";
        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                RosterEntry entry = new RosterEntry(
                        rs.getString("periode"),
                        rs.getString("maandag"),
                        rs.getString("dinsdag"),
                        rs.getString("woensdag"),
                        rs.getString("donderdag"),
                        rs.getString("vrijdag"),
                        rs.getString("zaterdag")
                );
                table.getItems().add(entry);
            }
        }
    }

    private <T> Callback<TableColumn<T, String>, TableCell<T, String>> createCellFactory() {
        return column -> new TableCell<T, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item.replace(", ", "\n"));
                }
            }
        };
    }

}

