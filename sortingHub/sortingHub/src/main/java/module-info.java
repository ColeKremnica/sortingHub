module assignment.sortinghub {
    requires javafx.controls;
    requires javafx.fxml;


    opens assignment.sortinghub to javafx.fxml;
    exports assignment.sortinghub;
}