module worldofzuul {
    requires javafx.controls;
    requires javafx.fxml;

    opens sdu.student to javafx.fxml;
    opens worldofzuul to javafx.fxml;
    exports sdu.student;
}