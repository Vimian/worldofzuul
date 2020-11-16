package sdu.student.editor;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.util.converter.NumberStringConverter;
import sdu.student.editor.model.BlockModel;

import java.net.URL;
import java.util.ResourceBundle;

import static worldofzuul.util.Math.tryParse;

public class BlockEditor implements Initializable {
    private final BlockModel model;
    public TextField imageFileTextField;
    public TextField animationLengthTextField;
    public ToggleButton toggleButton;

    public BlockEditor(BlockModel model) {
        this.model = model;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        toggleButton.setSelected(model.getColliding());
        imageFileTextField.textProperty().bindBidirectional(this.model.defaultImageFileProperty());
        animationLengthTextField.textProperty().bindBidirectional(this.model.animationCycleLengthMillisProperty(), new NumberStringConverter());
        imageFileTextField.textProperty().addListener(ev -> {
            this.model.setDefaultImageFile(imageFileTextField.textProperty().getValue());
        });
        animationLengthTextField.textProperty().addListener(ev -> {
            this.model.setAnimationCycleLengthMillis(tryParse(animationLengthTextField.textProperty().get().replace(",", ""), 0));
        });
    }

    public void toggleColliding(ActionEvent actionEvent) {
        model.setColliding(!model.getColliding());
    }
}
