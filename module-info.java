module com.example.sportshop {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.sportshop to javafx.fxml;
    exports com.example.sportshop;
}