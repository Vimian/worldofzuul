module worldofzuul {
    requires javafx.controls;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires javafx.fxml;



    opens sdu.student to javafx.fxml, com.fasterxml.jackson.annotation, com.fasterxml.jackson.core, com.fasterxml.jackson.databind;
    opens sdu.student.editor to javafx.fxml, com.fasterxml.jackson.annotation, com.fasterxml.jackson.core, com.fasterxml.jackson.databind;
    opens worldofzuul to javafx.fxml, com.fasterxml.jackson.annotation, com.fasterxml.jackson.core, com.fasterxml.jackson.databind;
    opens worldofzuul.parsing to javafx.fxml, com.fasterxml.jackson.annotation, com.fasterxml.jackson.core, com.fasterxml.jackson.databind;
    opens worldofzuul.item to javafx.fxml, com.fasterxml.jackson.annotation, com.fasterxml.jackson.core, com.fasterxml.jackson.databind;
    opens worldofzuul.util to javafx.fxml, com.fasterxml.jackson.annotation, com.fasterxml.jackson.core, com.fasterxml.jackson.databind;
    opens worldofzuul.world to javafx.fxml, com.fasterxml.jackson.annotation, com.fasterxml.jackson.core, com.fasterxml.jackson.databind;

    exports sdu.student;
    exports sdu.student.editor;
    exports worldofzuul;
    exports worldofzuul.parsing;
    exports worldofzuul.item;
    exports worldofzuul.util;
    exports worldofzuul.world;
}