package com.anakarina.demowerkroosterbeheer.screens;

import com.anakarina.demowerkroosterbeheer.HelloApplication;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Homescreen {
    private final Scene homeScene;

    public Homescreen(Stage stage) {
        //create an HBox as the main container to arrange sidebar and main content side by side
        HBox mainContainer = new HBox();
        homeScene = new Scene(mainContainer);
        homeScene.getStylesheets().add(HelloApplication.class.getResource("stylesheets/homescreen.css").toString());

        //add the sidebar and the main content to the HBox
        mainContainer.getChildren().addAll(getSidebar(homeScene), getMainContent());

        //set spacing between sidebar and main content
        mainContainer.setSpacing(50);

        // Show sign in dialog
        SignIn signIn = new SignIn(stage);
        signIn.showAndWait(); // This will show the sign in dialog and wait for it to close before moving on
    }

    /**
     * This method returns a Pane that contains the main section of the home screen
     * @return Pane
     */
    private Pane getMainContent() {
        VBox mainContent = new VBox();
        mainContent.setSpacing(20); //space between the roster and the buttons
        mainContent.setPadding(new Insets(40, 0, 0, 0));


        //create the black area
        Pane rosterSpace = new Pane();
        rosterSpace.setId("rosterSpace");
        rosterSpace.setPrefSize(900, 500);

        //create the buttons for generating roster and vacation requests
        Button btnGenerateRoster = new Button("Rooster Genereren");
        btnGenerateRoster.setId("buttonRoster");
        Button btnVacationRequests = new Button("Vakantieaanvragen");
        btnVacationRequests.setId("buttonVakantie");

        //button container
        HBox buttonContainer = new HBox(30, btnGenerateRoster, btnVacationRequests);
        buttonContainer.setAlignment(Pos.CENTER);

        //add the black area and button container to the main content VBox
        mainContent.getChildren().addAll(rosterSpace, buttonContainer);

        return mainContent;
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

        //load the logo image
        Image logoImage = new Image(HelloApplication.class.getResource("images/logoBlack.png").toString());
        ImageView logoView = new ImageView(logoImage);
        logoView.setFitWidth(70);
        logoView.setFitHeight(70);

        //create a container for the logo to add some space below it
        VBox logoContainer = new VBox(logoView);
        logoContainer.setPadding(new Insets(0, 0, 80, 40)); // Space below the logo

        //load the user icon
        Image userImage = new Image(HelloApplication.class.getResource("images/icons/user.png").toString(), 20, 20, true, true);
        ImageView userIcon = new ImageView(userImage);
        userIcon.setFitHeight(30);
        userIcon.setFitWidth(30);

        //create a container for the user icon with padding at the bottom
        VBox userIconContainer = new VBox(userIcon);
        userIconContainer.setAlignment(Pos.CENTER);
        userIconContainer.setPadding(new Insets(360, 0, 0, 0)); // Bottom padding

        sidebar.getChildren().addAll(
                logoContainer,
                generateNavItem("Home", true),
                userIconContainer);

        return sidebar;
    }

    private FlowPane generateNavItem(String title, boolean active) {
        FlowPane navItem = new FlowPane();
        navItem.setPadding(new Insets(0, 0, 0, 20));
        navItem.setAlignment(Pos.CENTER_LEFT);
        navItem.setPrefSize(165, 35);
        navItem.setHgap(10); // Adjust the gap between elements

        ImageView iconView = null;
        if ("Home".equals(title)) {
            // Load the home icon and set it up
            iconView = new ImageView(new Image(HelloApplication.class.getResource("images/icons/homeWhite.png").toString(), 20, 20, true, true));
        }

        Text navItemText = new Text(title);

        if (iconView != null) {
            // Add the icon and the text to the nav item
            navItem.getChildren().addAll(iconView, navItemText);
        } else {
            // If there's no icon, just add the text
            navItem.getChildren().add(navItemText);
        }

        if (active) {
            navItem.setId("navItem-active");
        } else {
            navItem.setId("navItem");
        }

        return navItem;
    }

    public Scene getHomeScene() {return homeScene;}

}
