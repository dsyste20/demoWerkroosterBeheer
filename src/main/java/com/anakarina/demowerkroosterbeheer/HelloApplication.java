package com.anakarina.demowerkroosterbeheer;

import com.anakarina.demowerkroosterbeheer.screens.Homescreen;
import com.anakarina.demowerkroosterbeheer.screens.SignIn;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    public static int[] applicationSize = {1200, 650};
    private Database database;

    @Override
    public void start(Stage stage) {
        database = new Database();
        SignIn signIn = new SignIn(stage, database);
        signIn.showAndWait();

        if (signIn.getUserName() != null) {
            //use the username from the SignIn dialog
            Homescreen homescreen = new Homescreen(stage, signIn.getUserName());
            stage.setScene(homescreen.getHomeScene());
        } else {
            stage.close();
        }

        stage.setWidth(applicationSize[0]);
        stage.setHeight(applicationSize[1]);
        stage.setResizable(false);
        stage.setTitle("WerkroosterBeheer");
        stage.show();
    }
    @Override
    public void stop() {
        //close the database connection when the application is stopped
        if (database != null) {
            database.closeConnection();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}