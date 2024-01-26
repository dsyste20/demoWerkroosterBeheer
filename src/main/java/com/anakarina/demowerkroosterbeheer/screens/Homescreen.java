package com.anakarina.demowerkroosterbeheer.screens;

import com.anakarina.demowerkroosterbeheer.Database;
import com.anakarina.demowerkroosterbeheer.Employee;
import com.anakarina.demowerkroosterbeheer.HelloApplication;
import com.anakarina.demowerkroosterbeheer.models.RosterGenerator;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class Homescreen {
    private final Scene homeScene;
    private Database database;
    private final String loggedInUsername;
    private Pane rosterSpace;

    public Homescreen(Stage stage, String name, Database database) {
        this.loggedInUsername = name;
        this.database = database;
        //an HBox as the main container to arrange sidebar and main content side by side
        HBox mainContainer = new HBox();
        homeScene = new Scene(mainContainer);
        homeScene.getStylesheets().add(HelloApplication.class.getResource("stylesheets/homescreen.css").toString());

        //add the sidebar and the main content to the HBox
        mainContainer.getChildren().addAll(getSidebar(homeScene), getMainContent());

        //set spacing between sidebar and main content
        mainContainer.setSpacing(50);

    }

    /**
     * This method returns a Pane that contains the main section of the home screen
     * @return Pane
     */
    private Pane getMainContent() {
        VBox mainContent = new VBox();
        mainContent.setSpacing(20); //space between the roster and the buttons
        mainContent.setPadding(new Insets(40, 0, 0, 0));

        //create the main area
        rosterSpace = new Pane(); //assign the Pane to the class member
        rosterSpace.setId("rosterSpace");
        rosterSpace.setPrefSize(900, 500);

        //create the buttons for generating roster and vacation requests
        Button btnGenerateRoster = new Button("Rooster Genereren");
        btnGenerateRoster.setId("buttonRoster");
        btnGenerateRoster.setOnAction(event -> {
            generateRoster(database);
        });


        Button btnVacationRequests = new Button("Vakantieaanvragen");
        btnVacationRequests.setId("buttonVakantie");

        //button container
        HBox buttonContainer = new HBox(30, btnGenerateRoster, btnVacationRequests);
        buttonContainer.setAlignment(Pos.CENTER);

        //add the black area and button container to the main content VBox
        mainContent.getChildren().addAll(rosterSpace, buttonContainer);

        return mainContent;
    }

    //method to generate and display the roster table
    private void generateRoster(Database database) {
        try {
            //create a new instance of RosterGenerator every time we generate the roster
            RosterGenerator rosterGenerator = new RosterGenerator(database);

            //generate the roster
            Map<String, List<Employee>> roster = rosterGenerator.generateAndDisplayRoster();

            //display the roster
            displayRosterTable(roster);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void displayRosterTable(Map<String, List<Employee>> finalRoster) {
        rosterSpace.getChildren().clear();

        //create a TableView for the roster
        TableView<Employee> rosterTableView = new TableView<>();
        rosterTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Employee, String> rolCol = new TableColumn<>("Rol");
        rolCol.setCellValueFactory(new PropertyValueFactory<>("rol"));

        TableColumn<Employee, String> nameCol = new TableColumn<>("Naam");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("fullname"));

        TableColumn<Employee, String> shiftCol = new TableColumn<>("Dienst");
        shiftCol.setCellValueFactory(new PropertyValueFactory<>("shift"));

        TableColumn<Employee, String> koffietCol = new TableColumn<>("Koffie");
        koffietCol.setCellValueFactory(new PropertyValueFactory<>("koffie"));

        TableColumn<Employee, String> lunhCol = new TableColumn<>("Lunch");
        lunhCol.setCellValueFactory(new PropertyValueFactory<>("lunch"));

        TableColumn<Employee, String> dinnerCol = new TableColumn<>("Dinner");
        dinnerCol.setCellValueFactory(new PropertyValueFactory<>("dinner"));

        rosterTableView.getColumns().addAll(rolCol, nameCol, shiftCol, koffietCol, lunhCol, dinnerCol);

        //fill the roster TableView with data
        for (Map.Entry<String, List<Employee>> entry : finalRoster.entrySet()) {
            List<Employee> employeesForDay = entry.getValue();
            for (Employee emp : employeesForDay) {

                rosterTableView.getItems().add(emp);
            }
        }

        //create another TableView for all employees with their availability
        TableView<Employee> allEmployeesTableView = new TableView<>();
        allEmployeesTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Employee, String> nameColumn = new TableColumn<>("Naam");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("fullname"));

        TableColumn<Employee, String> mondayColumn = new TableColumn<>("Maandag");
        mondayColumn.setCellValueFactory(new PropertyValueFactory<>("maandag"));

        TableColumn<Employee, String> tuesdayColumn = new TableColumn<>("Dinsdag");
        tuesdayColumn.setCellValueFactory(new PropertyValueFactory<>("dinsdag"));

        TableColumn<Employee, String> wednesdayColumn = new TableColumn<>("Woensdag");
        wednesdayColumn.setCellValueFactory(new PropertyValueFactory<>("woensdag"));

        TableColumn<Employee, String> thursdayColumn = new TableColumn<>("Donderdag");
        thursdayColumn.setCellValueFactory(new PropertyValueFactory<>("donderdag"));

        TableColumn<Employee, String> fridayColumn = new TableColumn<>("Vrijdag");
        fridayColumn.setCellValueFactory(new PropertyValueFactory<>("vrijdag"));

        TableColumn<Employee, String> saturdayColumn = new TableColumn<>("Zaterdag");
        saturdayColumn.setCellValueFactory(new PropertyValueFactory<>("zaterdag"));

        allEmployeesTableView.getColumns().addAll(nameColumn, mondayColumn, tuesdayColumn, wednesdayColumn, thursdayColumn, fridayColumn, saturdayColumn);

        //fetch all employees with their availability
        RosterGenerator rosterGenerator = new RosterGenerator(database);
        List<Employee> allEmployees = rosterGenerator.fetchEmployees();
        allEmployeesTableView.getItems().addAll(allEmployees);

        //layout to hold both the roster table and the all employees table
        HBox layout = new HBox(10);
        layout.getChildren().addAll(rosterTableView, allEmployeesTableView);

        rosterSpace.getChildren().add(layout); //add the HBox to the rosterSpace
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
        logoContainer.setPadding(new Insets(0, 0, 80, 40)); //space below the logo

        //load the user icon
        Image userImage = new Image(HelloApplication.class.getResource("images/icons/user.png").toString(), 20, 20, true, true);
        ImageView userIcon = new ImageView(userImage);
        userIcon.setFitHeight(30);
        userIcon.setFitWidth(30);

        //create a label for the username
        Label usernameLabel = new Label(loggedInUsername);
        usernameLabel.setId("usernameLabel");
        usernameLabel.setPadding(new Insets(5, 0, 0, 0));

        //adjust the userIconContainer to hold both the icon and the username label
        HBox userInfoContainer = new HBox(userIcon, usernameLabel);
        userInfoContainer.setAlignment(Pos.CENTER_LEFT);
        userInfoContainer.setSpacing(5); //space between icon and username

        //adjust the padding for the userIconContainer to position it correctly
        VBox userIconContainer = new VBox(userInfoContainer);
        userIconContainer.setAlignment(Pos.CENTER);
        userIconContainer.setPadding(new Insets(360, 0, 0, 0)); //bottom padding

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
        navItem.setHgap(10); //adjust the gap between elements

        ImageView iconView = null;
        if ("Home".equals(title)) {
            //load the home icon and set it up
            iconView = new ImageView(new Image(HelloApplication.class.getResource("images/icons/homeWhite.png").toString(), 20, 20, true, true));
        }

        Text navItemText = new Text(title);

        if (iconView != null) {
            //add the icon and the text to the nav item
            navItem.getChildren().addAll(iconView, navItemText);
        } else {
            //if there's no icon, just add the text
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
