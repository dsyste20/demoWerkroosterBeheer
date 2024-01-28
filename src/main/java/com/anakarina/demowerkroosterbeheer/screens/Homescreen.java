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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

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

    //method to generate and display the roster table
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

    //method to setup daily tables
    private void setupDailyTables(Map<String, List<Employee>> finalRoster) {
        for (String day : daysOfWeek) {
            //create a TableView for the roster
            TableView<Employee> rosterTableView = new TableView<>();
            rosterTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            TableColumn<Employee, String> nameCol = new TableColumn<>("Naam");
            nameCol.setCellValueFactory(new PropertyValueFactory<>("fullname"));

            //shift column for the specific day
            TableColumn<Employee, String> shiftCol = new TableColumn<>(day.substring(0, 1).toUpperCase() + day.substring(1)); // Capitalize the day
            shiftCol.setCellValueFactory(cellData -> {
                try {
                    //use reflection to get the availability based on the day of the week
                    String methodName = "get" + day.substring(0, 1).toUpperCase() + day.substring(1);
                    Method getAvailabilityMethod = Employee.class.getMethod(methodName);
                    return new ReadOnlyStringWrapper((String) getAvailabilityMethod.invoke(cellData.getValue()));
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                    return new ReadOnlyStringWrapper("Niet beschikbaar");
                }
            });

            TableColumn<Employee, String> spaceCol = new TableColumn<>("");
            spaceCol.setCellValueFactory(new PropertyValueFactory<>(""));

            //koffieCol
            TableColumn<Employee, String> koffieCol = new TableColumn<>("Koffie");

            //lunchCol
            TableColumn<Employee, String> lunhCol = new TableColumn<>("Lunch");

            //theeCol
            TableColumn<Employee, String> theeCol = new TableColumn<>("Thee");

            rosterTableView.getColumns().addAll(nameCol, shiftCol, spaceCol, koffieCol, lunhCol, theeCol);

            koffieCol.setCellValueFactory(cellData -> {
                String shiftTime = shiftCol.getCellData(cellData.getValue());
                if ("07:00 - 17:00".equals(shiftTime)) {
                    return new ReadOnlyStringWrapper("15");
                } else {
                    return new ReadOnlyStringWrapper("");
                }
            });

            lunhCol.setCellValueFactory(cellData -> {
                String shiftTime = shiftCol.getCellData(cellData.getValue());
                if ("07:00 - 17:00".equals(shiftTime)) {
                    return  new ReadOnlyStringWrapper("30");
                } else if ("12:00 - 17:00".equals(shiftTime)) {
                    return  new ReadOnlyStringWrapper("15");
                } else {
                    return  new ReadOnlyStringWrapper("");
                }
            });

            theeCol.setCellValueFactory(cellData -> {
                String shiftTime = shiftCol.getCellData(cellData.getValue());
                if ("17:00 - 21:30".equals(shiftTime)) {
                    return  new ReadOnlyStringWrapper("15");
                } else {
                    return  new ReadOnlyStringWrapper("");
                }
            });

            this.dailyTables.put(day, rosterTableView);
        }

        //array met gewenste beschikbaarheden
        String[] desiredShifts = {
                "07:00 - 17:00",
                "07:00 - 17:00",
                "17:00 - 21:30",
                "07:00 - 17:00",
                "17:00 - 21:30",
                "07:00 - 17:00",
                "17:00 - 21:30",
                "12:00 - 17:00"
        };

        for (String day : daysOfWeek) {
            TableView<Employee> rosterTableView = dailyTables.get(day);
            rosterTableView.getItems().clear();

            List<Employee> employeesForDay = finalRoster.getOrDefault(day, new ArrayList<>());
            for (Employee employee : employeesForDay) {
                rosterTableView.getItems().add(employee);
            }

            //stel de kolommen in met de juiste diensttijden
            TableColumn<Employee, String> shiftCol = (TableColumn<Employee, String>) rosterTableView.getColumns().get(1); // Index van dienstkolom
            shiftCol.setCellValueFactory(cellData -> {
                int index = rosterTableView.getItems().indexOf(cellData.getValue());
                return new ReadOnlyStringWrapper(desiredShifts[index % desiredShifts.length]);
            });
        }
    }

    //methode om werknemers te selecteren op basis van beschikbaarheid
    private List<Employee> selectEmployeesBasedOnAvailability(List<Employee> employees, String[] availabilities, String day) {
        List<Employee> selectedEmployees = new ArrayList<>();
        for (String availability : availabilities) {
            boolean found = false;
            for (Iterator<Employee> iterator = employees.iterator(); iterator.hasNext();) {
                Employee employee = iterator.next();
                if (employeeAvailabilityMatches(employee, availability, day)) {
                    selectedEmployees.add(employee);
                    iterator.remove();
                    found = true;
                    break;
                }
            }
            if (!found) {
                selectedEmployees.add(null);
            }
        }
        return selectedEmployees.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    //methode om de beschikbaarheid van een medewerker te controleren
    private boolean employeeAvailabilityMatches(Employee employee, String desiredAvailability, String day) {
        try {
            //gebruik reflectie om de getter-methode voor de huidige dag op te halen
            String methodName = "get" + day.substring(0, 1).toUpperCase() + day.substring(1);
            Method getAvailabilityMethod = Employee.class.getMethod(methodName);
            String availability = (String) getAvailabilityMethod.invoke(employee);

            //NOTICE: voor nu controleert het alleen directe overeenkomst
            return availability.equals(desiredAvailability);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return false; //return false bij een exception
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

        rosterSpace.getChildren().add(layout);
    }

    private TableView<String> createRoleTableView() {
        TableView<String> roleTable = new TableView<>();
        roleTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        roleTable.setMinWidth(40);
        roleTable.setMaxWidth(80);

        TableColumn<String, String> roleColumn = new TableColumn<>("Rol");
        roleColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue()));
        roleColumn.setMinWidth(100);
        roleColumn.setMaxWidth(200);

        roleTable.getColumns().add(roleColumn);

        //haal de rollen op uit de database en voeg ze toe aan de tabel
        List<String> roles = getRolesFromDatabase();
        roles.forEach(role -> roleTable.getItems().add(role));

        return roleTable;
    }

    private List<String> getRolesFromDatabase() {
        List<String> roles = new ArrayList<>();
        String sql = "SELECT rol FROM rollen";

        try (Connection conn = database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                roles.add(rs.getString("rol"));
            }

        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
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
        nameColumn.setMinWidth(150);

        TableColumn<Employee, String> mondayColumn = new TableColumn<>("Maandag");
        mondayColumn.setCellValueFactory(new PropertyValueFactory<>("maandag"));
        mondayColumn.setMinWidth(100);

        TableColumn<Employee, String> tuesdayColumn = new TableColumn<>("Dinsdag");
        tuesdayColumn.setCellValueFactory(new PropertyValueFactory<>("dinsdag"));
        tuesdayColumn.setMinWidth(100);

        TableColumn<Employee, String> wednesdayColumn = new TableColumn<>("Woensdag");
        wednesdayColumn.setCellValueFactory(new PropertyValueFactory<>("woensdag"));
        wednesdayColumn.setMinWidth(100);

        TableColumn<Employee, String> thursdayColumn = new TableColumn<>("Donderdag");
        thursdayColumn.setCellValueFactory(new PropertyValueFactory<>("donderdag"));
        thursdayColumn.setMinWidth(100);

        TableColumn<Employee, String> fridayColumn = new TableColumn<>("Vrijdag");
        fridayColumn.setCellValueFactory(new PropertyValueFactory<>("vrijdag"));
        fridayColumn.setMinWidth(100);

        TableColumn<Employee, String> saturdayColumn = new TableColumn<>("Zaterdag");
        saturdayColumn.setCellValueFactory(new PropertyValueFactory<>("zaterdag"));
        saturdayColumn.setMinWidth(100);

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
        navigationPanel.setAlignment(Pos.TOP_LEFT);
        VBox.setMargin(buttonBox, new Insets(10, 0, 0, 10));

        return navigationPanel;
    }

    /**
     * This method returns a Pane that contains the header section of the home screen
     * @return Pane
     */
    private Pane getSidebar(Scene scene) {
        FlowPane sidebar = new FlowPane();
        sidebar.setId("sidebar");
        sidebar.setPadding(new Insets(20, 0, 0, 0));
        sidebar.setAlignment(Pos.TOP_CENTER);
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
        logoContainer.setPadding(new Insets(0, 0, 80, 40));

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
