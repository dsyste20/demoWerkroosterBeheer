<h1 align="center">
  <br>
  Werkrooster Beheersysteem
  <br>
</h1>

<h4 align="center">A comprehensive solution for managing employee work schedules.</h4>

<p align="center">
  <!-- Badges can be added here -->
</p>

<p align="center">
  <a href="#project-overview">Project Overview</a> •
  <a href="#installation">Installation</a> •
  <a href="#usage">Usage</a> •
  <a href="#roadmap">Roadmap</a> •
  <a href="#license">License</a>
</p>

## Project Overview
WerkroosterBeheer System is a JavaFX application designed to manage work schedules for employees of one department. It allows for recording employee availability, generating work schedules based on this availability, and viewing the schedules in a user-friendly interface. This system aims to streamline the process of managing work shifts, ensuring optimal staffing levels across all operational hours.

## Installation 
### Prerequisites
* Java Development Kit (JDK) 11 or newer
* MySQL Server 5.7 or newer
* JavaFX SDK 11 or newer
* MySQL Connector/J

### Steps for installation
* Install Java Development Kit (JDK): Download and install JDK 11 or newer from the official Oracle website.
* Install MySQL Server: Download and install MySQL Server from the official MySQL website. Remember the root password you set during installation.
* Install JavaFX SDK: Download JavaFX SDK from OpenJFX and unzip it to a known location on your computer (JavaFX 21 is already in the project, just unzip it)
* Clone the Repository: Clone the WerkroosterBeheer System repository to your local machine using Git: git clone https://github.com/yourusername/werkroosterbeheersysteem.git
* Database Setup: Log in to your MySQL server and create a new database named werkrooster.
* Import the SQL schema provided in the db folder of the project.
* Configure the Database Connection: Open the Database.java file and update the database connection details (username, password, and URL) to match your MySQL server configuration.
* Run the Application: Open the project in your IDE (e.g., IntelliJ IDEA, Eclipse) and configure the run configuration to include the JavaFX SDK as a module.
* Run the HelloApplication.java file to start the application.

### Configuration
Some configurations may be necessary depending on your specific setup:
* Database Configuration: Ensure the Database.java file contains the correct connection details for your MySQL instance.
* JavaFX Configuration: Make sure your IDE's run configuration includes the path to the JavaFX SDK libraries and includes the --module-path and --add-modules flags.

## Usage Guide
* Sign in
  - Add a user in the database, to sign in.

* Generating Work Schedules
   - Click the "rooster genereren" button to automatically create work schedules based on the recorded availabilities.
   - Click the "rooster opslaan" button to save current work schedule.
   - Click the "opgeslagen rooster" button to see current 2 saved work schedules.

* Viewing vacation requests
  - Click the "vakantieaanvragen" button to see the vacationrequests.
  - Click the green "Goedkeuren" button to accept the request.
  - Click the red "afwijzen" button to reject the request.

* Viewing shift requests
  - Click the "dienstwisselingen" button to see the shift request.
  - Click the green "Goedkeuren" button to accept the request.
  - Click the red "afwijzen" button to reject the request.

## Roadmap / Timeline
* Q1 2024: Implement feature for editing and deleting employee records.
* Q2 2024: Add logic to make work schedules for other departments.
* Q3 2024: Introduce an automated backup system for the database.

### Contributing
Contributions & Ideas to the WerkroosterBeheer System are welcome.

## License
This project is made for Ad.
