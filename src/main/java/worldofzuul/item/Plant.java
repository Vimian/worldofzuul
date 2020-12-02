package worldofzuul.item;

import static worldofzuul.item.GrowthStage.*;

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


    public Plant(String name) {
        super(name);
        this.growthTime = 0;
    }
 

    public void grow(float water, float nutrition) {
        
        if(water == 0){
            witherPlant();
        } else if (ticksNotWatered != 0){
            ticksNotWatered = 0;
        }

        consumeNutrition(nutrition);
        consumeWater(water);

        if (readyForNextStage()) {
            advanceStage();
       }
  
        growTicks++;
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

}
