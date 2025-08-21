module calculator.app {
    requires javafx.controls;
    requires javafx.fxml;

    opens main to javafx.graphics, javafx.fxml;
    opens controller to javafx.fxml;
    exports main;
}
