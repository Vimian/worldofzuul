package worldofzuul.item;

import worldofzuul.util.BetterArrays;

public class Plant extends Item {
    private GrowthStage state = GrowthStage.SEED;
    private float seedQuality = 1;
    private float waterNeeded = 1000;
    private float nutritionNeeded = 1000;
    private int growthTime = 1000;
    private String[] fieldTypeRequired = {};

    private int growTicks = 0;

    public Plant(String name) {
        super(name);
    }

    public void grow(float water, float nutrition) {

        consumeNutrition(nutrition);
        consumeWater(water);

        if (readyForNextStage()) {
            advanceStage();
        }

        growTicks++;
    }

    public boolean isRipe() {
        return waterNeeded <= 0 && nutritionNeeded <= 0 && growTicks >= growthTime;
    }

    private void consumeWater(float water) {
        waterNeeded = -water * seedQuality;
    }

    private void consumeNutrition(float nutrition) {
        nutritionNeeded = -nutrition * seedQuality;
    }

    private boolean readyForNextStage() {
        return false;
    }

    private void advanceStage() {

    }

    public String[] getFieldTypeRequired() { return this.fieldTypeRequired; }

    public void setFieldTypeRequired(String[] fieldType) { this.fieldTypeRequired = fieldType; }

    public void setFieldTypeRequired(String fieldType) { this.fieldTypeRequired = BetterArrays.push(this.fieldTypeRequired, fieldType); }

    public void addFieldTypeRequired(String fieldType) { this.fieldTypeRequired = BetterArrays.push(this.fieldTypeRequired, fieldType); }

    public void addFieldTypeRequired(String[] fieldTypes) { this.fieldTypeRequired = BetterArrays.merge(this.fieldTypeRequired, fieldTypes); }


}
