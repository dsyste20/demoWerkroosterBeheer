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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

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

        //load the logo image
        Image logoImage = new Image(HelloApplication.class.getResource("images/logoBlack.png").toString());
        ImageView logoView = new ImageView(logoImage);
        logoView.setFitWidth(70);
        logoView.setFitHeight(70);

        //create a container for the logo to add some space below it
        VBox logoContainer = new VBox(logoView);
        logoContainer.setPadding(new Insets(0, 0, 80, 40)); // Space below the logo

        sidebar.getChildren().addAll(
                logoContainer,
                generateNavItem("Home", true));

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
