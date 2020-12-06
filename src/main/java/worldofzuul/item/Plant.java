package worldofzuul.item;

import com.fasterxml.jackson.annotation.JsonIgnore;

import static worldofzuul.item.GrowthStage.*;
import static worldofzuul.item.crops.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import worldofzuul.world.*;
public class Plant extends Sellable {
    private final ObjectProperty<GrowthStage> state = new SimpleObjectProperty<>(SEED);
    private float seedQuality = 1;
    private float waterNeeded = 1000;
    private float nutritionNeeded = 1000;
    private int growthTime = 1000;
    private float maxWater = waterNeeded;
    private float maxNutrition = nutritionNeeded;

    
    private int maxTimeWithoutWater = 100;
    private int ticksNotWatered;

    private int growTicks = 0;
    private crops crops;

    public Plant(){}
    public Plant(String name) {
        super(name);
    }

    public Plant(String name, Double value, Double sellbackRate) {
        super(name);
        setValue(value);
        setSellBackRate(sellbackRate);
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


    public void grow(float water, float nutrition, Double pH) {
        switch (crops) {
            case CORN:
                if (pH <= 5.8 && pH >= 7) {
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
            case RICE:
                if (pH <= 5.5 && pH >= 7) {
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
            case CASHEW:
                if (pH <= 5 && pH >= 6.5) {
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
            case COWPEA:
                if (pH <= 5.5 && pH >= 6.5) {
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
            case MANGO:
                if (pH <= 6 && pH >= 7.2) {
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
    }

    private void witherPlant() {
        ticksNotWatered++;
        
        if(ticksNotWatered >= maxTimeWithoutWater){
           setState(DEAD);
        }
        
    }

    @JsonIgnore
    public boolean isRipe() {
        return waterNeeded <= 0 && nutritionNeeded <= 0 && growTicks >= growthTime && getState() == RIPE;
    }


    private void consumeWater(float water) {
        waterNeeded -= water * seedQuality;
    }

    private void consumeNutrition(float nutrition) {
        nutritionNeeded -= nutrition * seedQuality;
    }


    private boolean readyForNextStage() {
        switch (getState()) {
            case SEED -> {
                return requiredPropertiesReady(2);
            }
            case SPROUT -> {
                return requiredPropertiesReady(3);
            }
            case ADULT -> {
                return requiredPropertiesReady(4);
            }

            default -> {
                return false;
            }
        }

    }

    private boolean requiredPropertiesReady(int i) {
        return (maxWater / 4) * i < maxWater - waterNeeded &&
                (maxNutrition / 4) * i < maxWater - nutritionNeeded &&
                (growthTime / 4) * i < growTicks;
    }

    private void advanceStage() {
        switch (getState()) {
            case SEED -> {
                setState(SPROUT);
            }
            case SPROUT -> {
                setState(ADULT);
            }
            case ADULT -> {
                setState(RIPE);
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

    @JsonIgnore
    public int getGrowTicks() {
        return growTicks;
    }

    public void setGrowTicks(int growTicks) {
        this.growTicks = growTicks;
    }

    public int getMaxTimeWithoutWater() {
        return maxTimeWithoutWater;
    }

    public void setMaxTimeWithoutWater(int maxTimeWithoutWater) {
        this.maxTimeWithoutWater = maxTimeWithoutWater;
    }

    @JsonIgnore
    public GrowthStage getState() {
        return state.get();
    }

    @JsonIgnore
    public Property<GrowthStage> stateProperty() {
        return state;
    }

    @JsonIgnore
    public void setState(GrowthStage state) {
        this.state.set(state);
    }
}
