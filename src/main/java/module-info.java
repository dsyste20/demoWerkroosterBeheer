module com.anakarina.demowerkroosterbeheer {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.anakarina.demowerkroosterbeheer to javafx.fxml;
    exports com.anakarina.demowerkroosterbeheer;
}