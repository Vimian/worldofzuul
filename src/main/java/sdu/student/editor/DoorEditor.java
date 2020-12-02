package sdu.student.editor;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.util.converter.NumberStringConverter;
import worldofzuul.world.Door;

import java.net.URL;
import java.util.ResourceBundle;

import static worldofzuul.util.Math.tryParse;

public class DoorEditor implements Initializable {
    private final Door model;
    public TextField imageFileTextField;
    public TextField animationLengthTextField;
    public TextField exitStringTextField;
    public TextField linkedXCoordTextField;
    public TextField linkedYCoordTextField;
    public ToggleButton toggleButton;

    public DoorEditor(Door model) {
        this.model = model;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        toggleButton.setSelected(model.isColliding());

        imageFileTextField.textProperty().bindBidirectional(this.model.defaultImageFileProperty());
        exitStringTextField.textProperty().bindBidirectional(this.model.exitProperty());

        animationLengthTextField.textProperty().bindBidirectional(this.model.animationCycleLengthMillisProperty(), new NumberStringConverter());
        linkedXCoordTextField.textProperty().bindBidirectional(this.model.getLinkedLocation().xProperty(), new NumberStringConverter());
        linkedYCoordTextField.textProperty().bindBidirectional(this.model.getLinkedLocation().yProperty(), new NumberStringConverter());

        imageFileTextField.textProperty().addListener(ev -> {
            this.model.setDefaultImageFile(imageFileTextField.textProperty().getValue());
        });
        animationLengthTextField.textProperty().addListener(ev -> {
            this.model.setAnimationCycleLengthMillis(tryParse(animationLengthTextField.textProperty().get().replace(",", ""), 0));
        });
        exitStringTextField.textProperty().addListener(ev -> {
            this.model.setExit(exitStringTextField.textProperty().getValue());
        });

        linkedXCoordTextField.textProperty().addListener(ev -> {
            this.model.getLinkedLocation().setX(tryParse(linkedXCoordTextField.textProperty().get().replace(",", ""), 0));
        });
        linkedYCoordTextField.textProperty().addListener(ev -> {
            this.model.getLinkedLocation().setY(tryParse(linkedYCoordTextField.textProperty().get().replace(",", ""), 0));
        });
    }

    public void toggleColliding(ActionEvent actionEvent) {
        model.setColliding(!model.isColliding());
    }
}
