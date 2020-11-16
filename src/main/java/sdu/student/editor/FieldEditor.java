package sdu.student.editor;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.util.converter.NumberStringConverter;
import sdu.student.editor.model.FieldModel;

import java.net.URL;
import java.util.ResourceBundle;

import static worldofzuul.util.Math.tryParse;

public class FieldEditor implements Initializable {

    private final FieldModel model;
    public TextField imageFileTextField;
    public TextField animationLengthTextField;
    public TextField nutritionTextField;
    public TextField depletionRateTextField;
    public TextField waterTextField;
    public ToggleButton toggleButton;

    public FieldEditor(FieldModel fieldModel) {
        this.model = fieldModel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        toggleButton.setSelected(model.getColliding());

        imageFileTextField.textProperty().bindBidirectional(this.model.defaultImageFileProperty());

        animationLengthTextField.textProperty().bindBidirectional(this.model.animationCycleLengthMillisProperty(), new NumberStringConverter());
        nutritionTextField.textProperty().bindBidirectional(this.model.nutritionProperty(), new NumberStringConverter());
        depletionRateTextField.textProperty().bindBidirectional(this.model.depletionRateProperty(), new NumberStringConverter());
        waterTextField.textProperty().bindBidirectional(this.model.waterProperty(), new NumberStringConverter());

        imageFileTextField.textProperty().addListener(ev -> {
            this.model.setDefaultImageFile(imageFileTextField.textProperty().getValue());
        });
        animationLengthTextField.textProperty().addListener(ev -> {
            this.model.setAnimationCycleLengthMillis(tryParse(animationLengthTextField.textProperty().get().replace(",", ""), 0));
        });

        nutritionTextField.textProperty().addListener(ev -> {
            this.model.setNutrition(tryParse(nutritionTextField.textProperty().get().replace(",", ""), 0));
        });
        depletionRateTextField.textProperty().addListener(ev -> {
            this.model.setDepletionRate(tryParse(depletionRateTextField.textProperty().get().replace(",", ""), 0));
        });
        waterTextField.textProperty().addListener(ev -> {
            this.model.setWater(tryParse(waterTextField.textProperty().get().replace(",", ""), 0));
        });
    }


    public void toggleColliding(ActionEvent actionEvent) {
        model.setColliding(!model.getColliding());
    }
}
