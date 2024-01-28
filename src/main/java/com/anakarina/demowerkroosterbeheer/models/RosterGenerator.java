package com.anakarina.demowerkroosterbeheer.models;

import com.anakarina.demowerkroosterbeheer.Database;
import com.anakarina.demowerkroosterbeheer.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class RosterGenerator {
    private Database database;

    public RosterGenerator(Database database) {
        this.database = database;
    }

    public Map<String, List<Employee>> generateAndDisplayRoster() throws SQLException {
        //ophalen van alle medewerkers en hun beschikbaarheid
        List<Employee> allEmployees = fetchEmployees();
        Map<String, List<EmployeeAvailability>> availabilitiesByDay = fetchAvailabilitiesGroupedByDay();

        Map<String, List<Employee>> finalRoster = new HashMap<>();

        //voor elke dag medewerkers selecteren voor de benodigde shifts
        for (String day : availabilitiesByDay.keySet()) {
            List<EmployeeAvailability> todaysAvailabilities = availabilitiesByDay.get(day);

            //de tijden voor elke dienst
            List<Employee> morningShift = selectEmployeesForShift(todaysAvailabilities, allEmployees, "07:00", "17:00", 4);
            List<Employee> afternoonShift = selectEmployeesForShift(todaysAvailabilities, allEmployees, "12:00", "17:00", 1);
            List<Employee> eveningShift = selectEmployeesForShift(todaysAvailabilities, allEmployees, "17:00", "21:30", 3);

            //combineer alle shifts voor de dag in een lijst
            List<Employee> employeesForDay = new ArrayList<>();
            employeesForDay.addAll(morningShift);
            employeesForDay.addAll(afternoonShift);
            employeesForDay.addAll(eveningShift);

            //voeg de lijst van medewerkers voor de dag toe aan het finale rooster
            finalRoster.put(day, employeesForDay);
        }

        //rooster weergeven
        displayRoster(finalRoster);

        return finalRoster;
    }

    public List<Employee> fetchEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT m.id, m.firstname, m.lastname, a.maandag, a.dinsdag, a.woensdag, a.donderdag, a.vrijdag, a.zaterdag "
                + "FROM medewerker m LEFT JOIN beschikbaarheid a ON m.id = a.medewerkerID";

        try (Connection conn = database.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setId(resultSet.getString("id"));
                employee.setFirstname(resultSet.getString("firstname"));
                employee.setLastname(resultSet.getString("lastname"));
                employee.setMaandag(resultSet.getString("maandag"));
                employee.setDinsdag(resultSet.getString("dinsdag"));
                employee.setWoensdag(resultSet.getString("woensdag"));
                employee.setDonderdag(resultSet.getString("donderdag"));
                employee.setVrijdag(resultSet.getString("vrijdag"));
                employee.setZaterdag(resultSet.getString("zaterdag"));

                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    private Map<String, List<EmployeeAvailability>> fetchAvailabilitiesGroupedByDay() {
        //initialize the map with the days of the week as keys and empty lists as values
        Map<String, List<EmployeeAvailability>> availabilitiesByDay = new HashMap<>();
        String[] daysOfWeek = {"maandag", "dinsdag", "woensdag", "donderdag", "vrijdag", "zaterdag"};

        for (String day : daysOfWeek) {
            availabilitiesByDay.put(day, new ArrayList<>());
        }

        String sql = "SELECT medewerkerID, afdeling, maandag, dinsdag, woensdag, donderdag, vrijdag, zaterdag FROM beschikbaarheid"; // Replace 'beschikbaarheid' with your actual table name

        try (Connection conn = database.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String employeeId = resultSet.getString("medewerkerID");
                String afdeling = resultSet.getString("afdeling");

                //loop through each day and create an EmployeeAvailability object if there's a time slot available
                for (String day : daysOfWeek) {
                    String availability = resultSet.getString(day);
                    if (availability != null && !availability.trim().isEmpty()) {
                        EmployeeAvailability employeeAvailability = new EmployeeAvailability(employeeId, afdeling, day, availability);
                        availabilitiesByDay.get(day).add(employeeAvailability);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return availabilitiesByDay;
    }

    private List<Employee> selectEmployeesForDay(Map<String, List<EmployeeAvailability>> availabilitiesByDay, List<Employee> allEmployees, String day) {
        List<EmployeeAvailability> todaysAvailabilities = availabilitiesByDay.get(day);
        Set<String> selectedEmployeeIds = new HashSet<>();
        List<Employee> selectedEmployeesForDay = new ArrayList<>();

        while (selectedEmployeesForDay.size() < 8) {
            Collections.shuffle(todaysAvailabilities); // Shuffle om willekeurigheid te garanderen

            for (EmployeeAvailability availability : todaysAvailabilities) {
                if (selectedEmployeeIds.size() >= 8) break; // Stop als we genoeg medewerkers hebben

                if (isEmployeeAvailable(availability, "07:00", "21:30") && !selectedEmployeeIds.contains(availability.getEmployeeId())) {
                    Employee employee = findEmployeeById(allEmployees, availability.getEmployeeId());
                    if (employee != null) {
                        selectedEmployeesForDay.add(employee);
                        selectedEmployeeIds.add(employee.getId());
                    }
                }
            }
        }
        return selectedEmployeesForDay;
    }

    private List<Employee> selectEmployeesForShift(List<EmployeeAvailability> availabilities, List<Employee> allEmployees, String startTime, String endTime, int needed) {
        List<Employee> selectedForShift = new ArrayList<>();
        Collections.shuffle(availabilities); // Shuffle de lijst om willekeurigheid te garanderen

        for (EmployeeAvailability availability : availabilities) {
            if (isEmployeeAvailable(availability, startTime, endTime) && selectedForShift.size() < needed) {
                Employee employee = findEmployeeById(allEmployees, availability.getEmployeeId());
                if (employee != null && !selectedForShift.contains(employee)) {
                    selectedForShift.add(employee);
                }
            }
        }
        return selectedForShift;
    }

    private boolean isEmployeeAvailable(EmployeeAvailability availability, String startTime, String endTime) {
        //implementation of includesTimeRange method in EmployeeAvailability class
        return availability.includesTimeRange(startTime, endTime);
    }

    private Employee findEmployeeById(List<Employee> employees, String employeeId) {
        for (Employee employee : employees) {
            if (employee.getId().equals(employeeId)) {
                return employee;
            }
        }
        return null;
    }

    private void displayRoster(Map<String, List<Employee>> finalRoster) {
        //for each day of the week
        for (Map.Entry<String, List<Employee>> entry : finalRoster.entrySet()) {
            String day = entry.getKey();
            List<Employee> employeesForDay = entry.getValue();

            System.out.println(day.toUpperCase() + ":");

//            //order of employees in the list matches the shifts
//            String[] shifts = {"", "Kassa 1", "Kassa 2", "Kassa 3", "Sco 1", "Sco 2", "Counter 1", "Counter 2", "Bestellingen"};
//            int shiftIndex = 0;

            //print alleen de namen van de geselecteerde medewerkers voor de dag
            System.out.println(day.toUpperCase() + ":");
            for (Employee employee : employeesForDay) {
                System.out.println(employee.getFullname());
            }

            //print a separator for readability
            System.out.println("----------------------------------------");
        }
    }

//    public List<Employee> generateEmployeesWithRoles(List<Employee> employees) {
//        String[] roles = {"Kassa 1", "Kassa 2", "Kassa 3", "Sco 1", "Sco 2", "Counter 1", "Counter 2", "Bestellingen"};
//        int index = 0;
//
//        for (Employee employee : employees) {
//            //wijst elke werknemer een unieke rol toe uit de rollenlijst
//            if (index < roles.length) {
//                employee.setRol(roles[index]);
//                index++;
//            }
//        }
//
//        return employees;
//    }
}
