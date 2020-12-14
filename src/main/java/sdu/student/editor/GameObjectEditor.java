package sdu.student.editor;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.util.converter.NumberStringConverter;
import worldofzuul.world.GameObject;

import java.net.URL;
import java.util.ResourceBundle;

import static worldofzuul.util.Math.tryParse;

/**
 * The type Game object editor.
 */
public abstract class GameObjectEditor implements Initializable {
    /**
     * The Model.
     */
    private final GameObject model;
    /**
     * The Image file text field.
     */
    public TextField imageFileTextField;
    /**
     * The Animation length text field.
     */
    public TextField animationLengthTextField;
    /**
     * The Toggle button.
     */
    public ToggleButton toggleButton;

    /**
     * Instantiates a new Game object editor.
     *
     * @param model the model
     */
    public GameObjectEditor(GameObject model) {
        this.model = model;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindProperties();
    }

    /**
     * Bind properties.
     */
    public void bindProperties() {
        toggleButton.setSelected(model.isColliding());
        imageFileTextField.textProperty().bindBidirectional(this.model.defaultImageFileProperty());
        animationLengthTextField.textProperty().bindBidirectional(this.model.animationCycleLengthMillisProperty(), new NumberStringConverter());
        imageFileTextField.textProperty().addListener(ev -> {
            this.model.setDefaultImageFile(imageFileTextField.textProperty().getValue());
        });
        animationLengthTextField.textProperty().addListener(ev -> {
            animationLengthTextField.textProperty().get().replace(".", "");
            this.model.setAnimationCycleLengthMillis(tryParse(animationLengthTextField.textProperty().get().replace(".", "").replace(",", ""), 0));
        });
    }

    /**
     * Toggle colliding.
     *
     * @param actionEvent the action event
     */
    public void toggleColliding(ActionEvent actionEvent) {
        model.setColliding(!model.isColliding());
    }

    /**
     * Gets model.
     *
     * @return the model
     */
    public GameObject getModel() {
        return model;
    }
}
