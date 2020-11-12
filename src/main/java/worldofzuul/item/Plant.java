package worldofzuul.item;

public class Plant extends Item {
    private GrowthStage state = GrowthStage.SEED;
    private float seedQuality = 1;
    private float waterNeeded = 1000;
    private float nutritionNeeded = 1000;
    private int growthTime = 1000;

    private int growTicks = 0;

    public Plant(){}
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


    public float getSeedQuality() {
        return seedQuality;
    }

    public void setSeedQuality(float seedQuality) {
        this.seedQuality = seedQuality;
    }

    public float getWaterNeeded() {
        return waterNeeded;
    }

    public void setWaterNeeded(float waterNeeded) {
        this.waterNeeded = waterNeeded;
    }

    public float getNutritionNeeded() {
        return nutritionNeeded;
    }

    public void setNutritionNeeded(float nutritionNeeded) {
        this.nutritionNeeded = nutritionNeeded;
    }

    public int getGrowthTime() {
        return growthTime;
    }

    public void setGrowthTime(int growthTime) {
        this.growthTime = growthTime;
    }

    public int getGrowTicks() {
        return growTicks;
    }

    public void setGrowTicks(int growTicks) {
        this.growTicks = growTicks;
    }
}
