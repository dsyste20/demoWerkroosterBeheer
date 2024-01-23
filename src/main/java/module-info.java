module com.anakarina.demowerkroosterbeheer {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.anakarina.demowerkroosterbeheer to javafx.fxml;
    exports com.anakarina.demowerkroosterbeheer;
}