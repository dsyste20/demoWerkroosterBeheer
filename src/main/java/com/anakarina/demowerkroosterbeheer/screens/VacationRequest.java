package com.anakarina.demowerkroosterbeheer.screens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VacationRequest {
    private Stage stage;

    public VacationRequest() {
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
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.getChildren().add(new Label("Vakantieaanvraag"));

        return layout;
    }

    public void show() {
        stage.show();
    }

}
