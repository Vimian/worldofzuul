package sdu.student.editor;

import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;
import worldofzuul.world.Door;

import java.net.URL;
import java.util.ResourceBundle;

import static worldofzuul.util.Math.tryParse;

/**
 * The type Door editor.
 */
public class DoorEditor extends GameObjectEditor {
    /**
     * The Textfield bound to the Door's exit property.
     */
    public TextField exitStringTextField;
    /**
     * The textfield bound to the the Door's LinkedY property.
     */
    public TextField linkedXCoordTextField;
    /**
     * The textfield bound to the the Door's LinkedX property.
     */
    public TextField linkedYCoordTextField;

    /**
     * Instantiates a new Door editor.
     *
     * @param model the model
     */
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
            getModel().getLinkedLocation().setX(tryParse(linkedXCoordTextField.textProperty().get().replace(",", "").replace(".", ""), 0));
        });
        linkedYCoordTextField.textProperty().addListener(ev -> {
            getModel().getLinkedLocation().setY(tryParse(linkedYCoordTextField.textProperty().get().replace(",", "").replace(".", ""), 0));
        });
    }

    @Override
    public Door getModel() {
        return (Door) super.getModel();
    }
}
