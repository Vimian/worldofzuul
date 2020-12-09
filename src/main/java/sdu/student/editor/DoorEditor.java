package sdu.student.editor;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.util.converter.NumberStringConverter;
import worldofzuul.world.Door;
import worldofzuul.world.GameObject;

import java.net.URL;
import java.util.ResourceBundle;

import static worldofzuul.util.Math.tryParse;

public class DoorEditor extends GameObjectEditor {
    public TextField exitStringTextField;
    public TextField linkedXCoordTextField;
    public TextField linkedYCoordTextField;

    public DoorEditor(Door model) {
        super(model);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindProperties();

        exitStringTextField.textProperty().bindBidirectional(getModel().exitProperty());

        linkedXCoordTextField.textProperty().bindBidirectional(getModel().getLinkedLocation().xProperty(), new NumberStringConverter());
        linkedYCoordTextField.textProperty().bindBidirectional(getModel().getLinkedLocation().yProperty(), new NumberStringConverter());

        exitStringTextField.textProperty().addListener(ev -> {
            getModel().setExit(exitStringTextField.textProperty().getValue());
        });

        linkedXCoordTextField.textProperty().addListener(ev -> {
            getModel().getLinkedLocation().setX(tryParse(linkedXCoordTextField.textProperty().get().replace(",", ""), 0));
        });
        linkedYCoordTextField.textProperty().addListener(ev -> {
            getModel().getLinkedLocation().setY(tryParse(linkedYCoordTextField.textProperty().get().replace(",", ""), 0));
        });
    }

    @Override
    public Door getModel() {
        return (Door) super.getModel();
    }
}
