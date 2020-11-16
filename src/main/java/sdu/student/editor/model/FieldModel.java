package sdu.student.editor.model;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import worldofzuul.world.Field;

public class FieldModel extends GameObjectModel {

    private final Field field;
    private final FloatProperty nutrition = new SimpleFloatProperty();
    private final FloatProperty depletionRate = new SimpleFloatProperty();
    private final FloatProperty water = new SimpleFloatProperty();

    public FieldModel(Field field) {
        super(field);
        this.field = field;
        nutrition.setValue(field.getNutrition());
        depletionRate.setValue(field.getDepletionRate());
        water.setValue(field.getWater());

    }


    public final Float getNutrition() {
        return nutrition.get();
    }

    public final void setNutrition(float value) {
        field.setNutrition(value);
        nutrition.set(value);
    }

    public FloatProperty nutritionProperty() {
        return nutrition;
    }


    public float getDepletionRate() {
        return depletionRate.get();
    }

    public void setDepletionRate(float depletionRate) {
        field.setDepletionRate(depletionRate);
        this.depletionRate.set(depletionRate);
    }

    public FloatProperty depletionRateProperty() {
        return depletionRate;
    }

    public float getWater() {
        return water.get();
    }

    public void setWater(float water) {
        field.setWater(water);
        this.water.set(water);
    }

    public FloatProperty waterProperty() {
        return water;
    }

    public Field getField() {
        return field;
    }

}
