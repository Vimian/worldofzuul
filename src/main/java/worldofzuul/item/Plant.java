package worldofzuul.item;

import static worldofzuul.item.GrowthStage.*;
import worldofzuul.world.*;

public class Plant extends Item {
    private GrowthStage state = GrowthStage.SEED;
    private float seedQuality = 1;
    private float waterNeeded = 1000;
    private float nutritionNeeded = 1000;
    private int growthTime = 1000;
    private float maxWater = waterNeeded;
    private float maxNutrition = nutritionNeeded;
    private int timeTillDeath = 100;
    private int ticksNotWatered;
    private int growTicks = 0;

    public Plant(String name, Double value, Double sellbackRate) {
        super(name, value ,sellbackRate);
        this.growthTime = 0;
    }
    public void grow(float water, float nutrition, Double pH) {
        if (pH <= 16 && pH >= 26) {
            if (water == 0) {
                witherPlant();
            } else if (ticksNotWatered != 0) {
                ticksNotWatered = 0;
            }

            consumeNutrition(nutrition);
            consumeWater(water);

            if (readyForNextStage()) {
                advanceStage();
            }

            growTicks++;
        }
    }

    private void witherPlant() {
        ticksNotWatered++;
        
        if(ticksNotWatered >= timeTillDeath){
            state = GrowthStage.DEAD;
        }
        
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
        switch (state) {
            case SEED -> {
                if(maxWater / 4 > maxWater - waterNeeded && maxNutrition / 4 > maxWater - nutritionNeeded){
                    return true;
                }
            }
            case SPROUT -> {
                if((maxWater / 4) * 2 > maxWater - waterNeeded && (maxNutrition / 4) * 2 > maxWater - nutritionNeeded){
                    return true;
                }
            }
            case ADULT -> {
                if((maxWater / 4) * 3 > maxWater - waterNeeded && (maxNutrition / 4) * 3 > maxWater - nutritionNeeded){
                    return true;
                }
            }

            default -> {
                return false;
            }
        }

        return false;
    }

    private void advanceStage() {

        switch (state) {
            case SEED -> {
                state = SPROUT;
            }
            case SPROUT -> {
                state = ADULT;
            }
            case ADULT -> {
                state = RIPE;
            }
        }
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
