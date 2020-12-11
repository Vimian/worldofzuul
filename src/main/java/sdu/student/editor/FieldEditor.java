package sdu.student.editor;

import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;
import worldofzuul.world.Field;

import java.net.URL;
import java.util.ResourceBundle;

import static worldofzuul.util.Math.tryParse;

public class FieldEditor extends GameObjectEditor {


    public TextField nutritionTextField;
    public TextField depletionRateTextField;
    public TextField waterTextField;


    public TextField maxWaterTextField;
    public TextField maxNutritionTextField;
    public TextField phLevelTextField;


    public FieldEditor(Field fieldModel) {
        super(fieldModel);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bindProperties();


        nutritionTextField.textProperty().bindBidirectional(getModel().nutritionProperty(), new NumberStringConverter());
        depletionRateTextField.textProperty().bindBidirectional(getModel().depletionRateProperty(), new NumberStringConverter());
        waterTextField.textProperty().bindBidirectional(getModel().waterProperty(), new NumberStringConverter());

        maxWaterTextField.textProperty().bindBidirectional(getModel().maxWaterProperty(), new NumberStringConverter());
        maxNutritionTextField.textProperty().bindBidirectional(getModel().maxNutritionProperty(), new NumberStringConverter());
        phLevelTextField.textProperty().bindBidirectional(getModel().phProperty(), new NumberStringConverter());

        nutritionTextField.textProperty().addListener(ev -> {
            getModel().setNutrition(tryParse(nutritionTextField.textProperty().get().replace(",", "").replace(".", ""), 0));
        });
        depletionRateTextField.textProperty().addListener(ev -> {
            getModel().setDepletionRate(tryParse(depletionRateTextField.textProperty().get().replace(",", "").replace(".", ""), 0));
        });
        waterTextField.textProperty().addListener(ev -> {
            getModel().setWater(tryParse(waterTextField.textProperty().get().replace(",", "").replace(".", ""), 0));
        });

        maxWaterTextField.textProperty().addListener(ev -> {
            getModel().setMaxWater(tryParse(maxWaterTextField.textProperty().get().replace(",", "").replace(".", ""), 0));
        });
        maxNutritionTextField.textProperty().addListener(ev -> {
            getModel().setMaxNutrition(tryParse(maxNutritionTextField.textProperty().get().replace(",", "").replace(".", ""), 0));
        });
        phLevelTextField.textProperty().addListener(ev -> {
            getModel().setPH(tryParse(phLevelTextField.textProperty().get().replace(",", "").replace(".", ""), 0));
        });


    }

    @Override
    public Field getModel() {
        return (Field) super.getModel();
    }
}
