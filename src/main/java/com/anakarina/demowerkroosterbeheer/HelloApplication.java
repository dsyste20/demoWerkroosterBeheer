package com.anakarina.demowerkroosterbeheer;

import com.anakarina.demowerkroosterbeheer.screens.Homescreen;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    public static int[] applicationSize = {1200, 650};

    @Override
    public void start(Stage stage) {
        stage.setWidth(applicationSize[0]);
        stage.setHeight(applicationSize[1]);
        stage.setResizable(false);
        stage.setTitle("WerkroosterBeheer");

        stage.setScene(new Homescreen().getHomeScene());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}