package com.anakarina.demowerkroosterbeheer.screens;

import com.anakarina.demowerkroosterbeheer.HelloApplication;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

public class Homescreen {
    private final Scene homeScene;

    public Homescreen() {
        Pane container = new Pane();
        homeScene = new Scene(container);
        homeScene.getStylesheets().add(HelloApplication.class.getResource("stylesheets/homescreen.css").toString());

        container.getChildren().addAll(getSidebar(homeScene));
    }

    /**
     * This method returns a Pane that contains the header section of the home screen
     * @return Pane
     */
    private Pane getSidebar(Scene scene) {
        FlowPane sidebar = new FlowPane();
        sidebar.setId("sidebar");
        sidebar.setPadding(new Insets(20, 0, 0, 0)); //top padding of 20px
        sidebar.setAlignment(Pos.TOP_CENTER); //align children to the top center
        sidebar.prefHeightProperty().bind(scene.heightProperty());
        sidebar.maxHeightProperty().bind(scene.heightProperty());
        sidebar.setOrientation(Orientation.VERTICAL);

        return sidebar;
    }

    public Scene getHomeScene() {return homeScene;}

}
