package com.anakarina.demowerkroosterbeheer.screens;

import com.anakarina.demowerkroosterbeheer.Database;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignIn extends Stage {
    private Database database;

    public SignIn(Stage owner, Database database) {
        this.database = database; //assigns the passed database instance to the local reference
        initOwner(owner);
        initModality(Modality.APPLICATION_MODAL); //block input events to other windows
        setTitle("Sign In");

        VBox signInForm = new VBox(10);
        signInForm.setAlignment(Pos.CENTER);
        signInForm.setPadding(new Insets(10));

        Label usernameLabel = new Label("Gebruikersnaam:");
        TextField usernameField = new TextField();

        Label passwordLabel = new Label("Wachtwoord:");
        PasswordField passwordField = new PasswordField();

        Button signInButton = new Button("Inloggen");
        signInButton.setOnAction(event -> {
            //retrieve user input
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (authenticate(username, password)) {
                System.out.println("Authentication successful");
                //close the sign-in window
                this.close();
            } else {
                System.out.println("Authentication failed");

                // Display an authentication failed message to the user using an Alert
                Alert alert = new Alert(AlertType.ERROR);
                alert.initOwner(this);
                alert.setTitle("Authentication Failed!");
                alert.setHeaderText(null);
                alert.setContentText("De gebruikersnaam of het wachtwoord is onjuist. Probeer het opnieuw!");

                alert.showAndWait();
            }
        });

        signInForm.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, signInButton);

        Scene scene = new Scene(signInForm, 300, 200); //set the size of the pop-up
        setScene(scene);
    }
    private boolean authenticate(String username, String password) {
        //SQL query to check for the user with the provided username and password
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (PreparedStatement pstmt = database.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                //if the result set is not empty, we have a match
                return resultSet.next();
            }
        } catch (SQLException e) {
            System.err.println("Authentication query failed: " + e.getMessage());
            return false;
        }
    }
}

