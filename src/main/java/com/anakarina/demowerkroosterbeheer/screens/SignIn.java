package com.anakarina.demowerkroosterbeheer.screens;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SignIn extends Stage {

    public SignIn(Stage owner) {
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
            //here comes the sign-in logic
            String username = usernameField.getText();
            String password = passwordField.getText();
            System.out.println("Sign in with: " + username + " " + password);
            //check credentials here and then close the pop-up if they're correct
            //this.close(); //close the dialog after checking credentials
        });

        signInForm.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, signInButton);

        Scene scene = new Scene(signInForm, 300, 200); //set the size of the pop-up
        setScene(scene);
    }
}

