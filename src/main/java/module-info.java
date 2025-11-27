module BadmintonReservation {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.badminton.app.badmintonreservation to javafx.fxml;
    opens com.badminton.app.badmintonreservation.controller to javafx.fxml;
    opens com.badminton.app.badmintonreservation.controller.admin to javafx.fxml;
    opens com.badminton.app.badmintonreservation.model to javafx.base;

    exports com.badminton.app.badmintonreservation;
    exports com.badminton.app.badmintonreservation.controller;
    exports com.badminton.app.badmintonreservation.controller.admin;
    exports com.badminton.app.badmintonreservation.model;
}
