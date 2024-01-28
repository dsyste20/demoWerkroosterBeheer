package com.anakarina.demowerkroosterbeheer.screens;

import com.anakarina.demowerkroosterbeheer.Database;
import com.anakarina.demowerkroosterbeheer.Employee;
import com.anakarina.demowerkroosterbeheer.HelloApplication;
import com.anakarina.demowerkroosterbeheer.models.RosterGenerator;
import javafx.beans.property.ReadOnlyStringWrapper;
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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Homescreen {
    private final Scene homeScene;
    private Database database;
    private final String loggedInUsername;
    private Pane rosterSpace;
    private Map<String, TableView<Employee>> dailyTables;
    private List<String> daysOfWeek;
    private int currentDayIndex;
    private Label currentDayLabel;

    public Homescreen(Stage stage, String name, Database database) {
        this.loggedInUsername = name;
        this.database = database;
        this.daysOfWeek = Arrays.asList("maandag", "dinsdag", "woensdag", "donderdag", "vrijdag", "zaterdag");
        this.currentDayIndex = 0;
        this.dailyTables = new HashMap<>();
        //an HBox as the main container to arrange sidebar and main content side by side
        HBox mainContainer = new HBox();
        homeScene = new Scene(mainContainer);
        homeScene.getStylesheets().add(HelloApplication.class.getResource("stylesheets/homescreen.css").toString());

        //add the sidebar and the main content to the HBox
        mainContainer.getChildren().addAll(getSidebar(homeScene), getMainContent());

        //set spacing between sidebar and main content
        mainContainer.setSpacing(50);

        //direct het rooster genereren bij initialisatie
        generateRoster(this.database);
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

        //add the navigation panel at the top of mainContent
        VBox navigationPanel = createNavigationPanel();
        mainContent.getChildren().add(0, navigationPanel); //add at the first position

        //button container
        HBox buttonContainer = new HBox(30, navigationPanel, btnGenerateRoster, btnVacationRequests);
        buttonContainer.setAlignment(Pos.CENTER);

        //het label voor de huidige dag
        currentDayLabel = new Label("");
        currentDayLabel.setAlignment(Pos.CENTER);
        currentDayLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        //add the black area and button container to the main content VBox
        mainContent.getChildren().addAll(currentDayLabel, rosterSpace, buttonContainer);

        return mainContent;
    }

    // Method to generate and display the roster table
    private void generateRoster(Database database) {
        try {
            RosterGenerator rosterGenerator = new RosterGenerator(database);
            Map<String, List<Employee>> roster = rosterGenerator.generateAndDisplayRoster();
//            displayRosterTable(roster);
            setupDailyTables(roster);
            displayRosterForCurrentDay();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to setup daily tables
//    private void setupDailyTables(Map<String, List<Employee>> finalRoster) {
//        for (String day : daysOfWeek) {
//            //create a TableView for the roster
//            TableView<Employee> rosterTableView = new TableView<>();
//            rosterTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//
//            TableColumn<Employee, String> nameCol = new TableColumn<>("Naam");
//            nameCol.setCellValueFactory(new PropertyValueFactory<>("fullname"));
//
////        TableColumn<Employee, String> shiftCol = new TableColumn<>("Dienst");
////        shiftCol.setCellValueFactory(new PropertyValueFactory<>("shift"));
//
////        TableColumn<Employee, String> koffietCol = new TableColumn<>("Koffie");
////        koffietCol.setCellValueFactory(new PropertyValueFactory<>("koffie"));
////
////        TableColumn<Employee, String> lunhCol = new TableColumn<>("Lunch");
////        lunhCol.setCellValueFactory(new PropertyValueFactory<>("lunch"));
////
////        TableColumn<Employee, String> dinnerCol = new TableColumn<>("Diner");
////        dinnerCol.setCellValueFactory(new PropertyValueFactory<>("diner"));
//
//            rosterTableView.getColumns().addAll(nameCol);
//
//            this.dailyTables.put(day, rosterTableView);
//        }
//
//        for (Map.Entry<String, List<Employee>> entry : finalRoster.entrySet()) {
//            TableView<Employee> table = dailyTables.get(entry.getKey());
//            table.getItems().addAll(entry.getValue());
//        }
//    }

    // Method to setup daily tables
    private void setupDailyTables(Map<String, List<Employee>> finalRoster) {
        for (String day : daysOfWeek) {
            //create a TableView for the roster
            TableView<Employee> rosterTableView = new TableView<>();
            rosterTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            TableColumn<Employee, String> nameCol = new TableColumn<>("Naam");
            nameCol.setCellValueFactory(new PropertyValueFactory<>("fullname"));

            TableColumn<Employee, String> shiftCol = new TableColumn<>("Dienst");
            shiftCol.setCellValueFactory(new PropertyValueFactory<>("shift"));

            rosterTableView.getColumns().addAll(nameCol, shiftCol);

            this.dailyTables.put(day, rosterTableView);
        }

        // Een set om bij te houden welke werknemers al zijn toegevoegd voor de huidige dag
        Set<String> addedEmployees;

        for (String day : daysOfWeek) {
            addedEmployees = new HashSet<>();
            TableView<Employee> table = dailyTables.get(day);
            List<Employee> employeesForDay = finalRoster.getOrDefault(day, new ArrayList<>());

            // Voeg werknemers toe tot je er 8 hebt voor de dag
            for (Employee employee : employeesForDay) {
                if (!addedEmployees.contains(employee.getId()) && addedEmployees.size() < 8) { // Gebruik getId() of een andere unieke identifier
                    table.getItems().add(employee);
                    addedEmployees.add(employee.getId());
                }
            }

            // Als er niet genoeg werknemers zijn, voeg dan lege plaatsen toe
            while (addedEmployees.size() < 8) {
                Employee emptyEmployee = new Employee(); // Zorg ervoor dat Employee een lege constructor heeft
                table.getItems().add(emptyEmployee);
                addedEmployees.add("Placeholder" + addedEmployees.size()); // Voeg een unieke placeholder toe
            }
        }
    }


    private void displayRosterForCurrentDay() {
        String currentDay = daysOfWeek.get(currentDayIndex);
        currentDayLabel.setText("Rooster voor " + currentDay);

        rosterSpace.getChildren().clear(); //clear the roster space

        //rol tabel
        TableView<String> rolTable = createRoleTableView();

        //get the daily roster table
        TableView<Employee> dailyRosterTable = dailyTables.get(currentDay);

        //create another TableView for all employees with their availability
        TableView<Employee> allEmployeesTableView = createAllEmployeesTableView();

        //layout to hold both the roster table and the all employees table
        HBox layout = new HBox(10); // 10 is the spacing between tables
        layout.getChildren().addAll(rolTable, dailyRosterTable, allEmployeesTableView);

        rosterSpace.getChildren().add(layout); // Add the HBox to the rosterSpace
    }

    private TableView<String> createRoleTableView() {
        TableView<String> roleTable = new TableView<>();
        roleTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        roleTable.setMinWidth(40);
        roleTable.setMaxWidth(80);

        TableColumn<String, String> roleColumn = new TableColumn<>("Rol");
        roleColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue()));
        roleColumn.setMinWidth(100); // Minimale breedte van de kolom
        roleColumn.setMaxWidth(200); // Maximale breedte van de kolom

        roleTable.getColumns().add(roleColumn);

        // Haal de rollen op uit de database en voeg ze toe aan de tabel
        List<String> roles = getRolesFromDatabase();
        roles.forEach(role -> roleTable.getItems().add(role));

        return roleTable;
    }

    private List<String> getRolesFromDatabase() {
        List<String> roles = new ArrayList<>();
        String sql = "SELECT rol FROM rollen"; // Veronderstelt dat je tabel 'rollen' heet en een kolom 'rol'

        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                roles.add(rs.getString("rol"));
            }

        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            // Afhandeling van de fout, bijvoorbeeld loggen of een foutbericht tonen
        }

        return roles;
    }

    //method to create the TableView for all employees with their availability
    private TableView<Employee> createAllEmployeesTableView() {
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

        return allEmployeesTableView;
    }

    //method to create navigation buttons
    private VBox createNavigationPanel() {
        Button prevButton = new Button("<");
        prevButton.setOnAction(e -> {
            currentDayIndex = (currentDayIndex - 1 + daysOfWeek.size()) % daysOfWeek.size();
            displayRosterForCurrentDay();
        });

        Button nextButton = new Button(">");
        nextButton.setOnAction(e -> {
            currentDayIndex = (currentDayIndex + 1) % daysOfWeek.size();
            displayRosterForCurrentDay();
        });

        HBox buttonBox = new HBox(10, prevButton, nextButton);
        buttonBox.setAlignment(Pos.CENTER_LEFT);

        VBox navigationPanel = new VBox(buttonBox);
        navigationPanel.setAlignment(Pos.TOP_LEFT); //align to the top left
        VBox.setMargin(buttonBox, new Insets(10, 0, 0, 10)); //top and left margin

        return navigationPanel;
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
