package com.anakarina.demowerkroosterbeheer.screens;

import com.anakarina.demowerkroosterbeheer.Database;
import com.anakarina.demowerkroosterbeheer.HelloApplication;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Alert;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignIn extends Stage {
    private Database database;
    private String userName;

    public SignIn(Stage owner, Database database) {
        this.database = database; //assigns the passed database instance to the local reference
        initOwner(owner);
        initModality(Modality.APPLICATION_MODAL); //block input events to other windows
        setTitle("Sign In");

        VBox signInForm = new VBox(10);
        signInForm.setId("signInForm");
        signInForm.setAlignment(Pos.CENTER);
        signInForm.setPadding(new Insets(10));

        Label usernameLabel = new Label("Gebruikersnaam:");
        usernameLabel.setId("usernameLabel");
        TextField usernameField = new TextField();
        usernameField.setId("usernameField");

        Label passwordLabel = new Label("Wachtwoord:");
        passwordLabel.setId("passwordLabel");
        PasswordField passwordField = new PasswordField();
        passwordField.setId("passwordField");

        Button signInButton = new Button("Inloggen");
        signInButton.setId("button");
        signInButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String name = authenticate(username, password);
            if (name != null) {
                this.userName = name; //set the userName field
                System.out.println("Authentication successful");
                this.close();

                //proceed to show the main application window
                Homescreen homescreen = new Homescreen(owner, name, database); //pass the retrieved name
                owner.setScene(homescreen.getHomeScene());
                owner.show();
            } else {
                //show authentication failed message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(this);
                alert.setTitle("Authentication Failed!");
                alert.setHeaderText(null);
                alert.setContentText("De gebruikersnaam of het wachtwoord is onjuist. Probeer het opnieuw!");
                alert.showAndWait();
            }
        });

        signInForm.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, signInButton);

        Scene scene = new Scene(signInForm, 300, 200); //set the size of the pop-up
        scene.getStylesheets().add(HelloApplication.class.getResource("stylesheets/signIn.css").toString());
        setScene(scene);
    }
    public String getUserName() {
        return userName;
    }
    protected String authenticate(String username, String password) {
        //SQL query to check for the user with the provided username and password
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (PreparedStatement pstmt = database.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    //return the user's name if authentication is successful
                    return resultSet.getString("name");
                }
            }
        } catch (SQLException e) {
            System.err.println("Authentication query failed: " + e.getMessage());
        }
        return null; //authentication failed
    }
}

