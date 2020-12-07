package worldofzuul.world;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import worldofzuul.item.*;
import worldofzuul.parsing.Command;
import worldofzuul.parsing.CommandWord;
import worldofzuul.util.MessageHelper;

import java.util.ArrayList;

public class Field extends GameObject {



    private Fertilizer fertilizer;
    private Plant plant;
    private ArrayList<Plant> plants;


    private DoubleProperty pH = new SimpleDoubleProperty();
    private final FloatProperty water = new SimpleFloatProperty(20000);
    private final FloatProperty nutrition = new SimpleFloatProperty(10000);
    private final FloatProperty depletionRate = new SimpleFloatProperty(5);
    private FloatProperty maxWater = new SimpleFloatProperty(20000);
    private FloatProperty maxNutrition = new SimpleFloatProperty(20000);

    private boolean ripePlantSeen = false;

    public Field() {
    }

    public Field(Fertilizer fertilizer) {
        this.fertilizer = fertilizer;
    }

    public Field(Fertilizer fertilizer, float water) {
        this(fertilizer);
        setWater(water);
    }

    public Field(Fertilizer fertilizer, float water, Double pH) {
        this(fertilizer);
        setWater(water);

    }


    public void addWater(float water) {
        if(getWater() + water <= getMaxWater()){
            setWater(getWater() + water);
        }
    }

    @Override
    public Command[] update() {
        if (plant != null) {
            if(isPlantGrowing()){
                plant.grow(depleteWater(), depleteNutrition());
                //plant.grow(depleteWater(), depleteNutrition(), getPH());
            } if(plant.isRipe() && !ripePlantSeen){
                MessageHelper.Info.plantBecameRipe(plant.getName());
                playAnimation(GrowthStage.RIPE);
                ripePlantSeen = true;
            }
        }

        return super.update();
    }

    @Override
    public Command[] interact(Item item) {

        if (item instanceof Fertilizer) {
            MessageHelper.Item.usedItem(item.getName());
            return useFertilizer((Fertilizer) item);
        } else if (item instanceof Seed) {
            return useSeed((Seed) item);
        } else if (item instanceof Harvester) {
            return useHarvester((Harvester) item);
        } else if (item instanceof Irrigator) {
            MessageHelper.Item.usedItem(item.getName());
            useIrrigator((Irrigator) item);
            return null;
        }

        return super.interact(item);
    }

    private Command[] useFertilizer(Fertilizer item) {
        Command[] commands = new Command[1];
        if(nutrition.get() + item.getConsumptionRate() < maxNutrition.get()) {
            nutrition.set(nutrition.get() + item.deplete());
        }


        if (item.getRemaining() == 0) {
            commands[0] = new Command(CommandWord.REMOVEITEM, null, item);
        }

        return commands;
    }

    private void useIrrigator(Irrigator item) {
        item.water(this);
    }

    private Command[] useSeed(Seed item) {
        if (plant != null) {
            MessageHelper.Item.alreadyPlanted();
            return null;
        }

        Command[] commands = new Command[1];

        if (item.getRemaining() > 0) {
            plantSeed(item);
        }

        if (item.getRemaining() == 0) {
            commands[0] = new Command(CommandWord.REMOVEITEM, null);
        }


        return commands;
    }

    private void plantSeed(Seed item) {
        MessageHelper.Item.usedItemOn(item.getName(), this.getClass().getSimpleName());
        plant = (item.useSeed());
        ripePlantSeen = false;

        plant.stateProperty().addListener((observable, oldValue, newValue) -> {
            plantStateChanged(oldValue, newValue);
        });

    }

    private void plantStateChanged(GrowthStage oldValue, GrowthStage newValue) {
        playAnimation(newValue);
    }

    private Command[] useHarvester(Harvester item) {
        Command[] commands = new Command[1];
        if (plant != null) {
            if (plant.isRipe()) {
                MessageHelper.Item.harvested(plant.getName());
                commands[0] = new Command(CommandWord.ADDITEM, null, item.harvest(plant));
                removePlant();

            } else {
                MessageHelper.Item.unripePlant();
            }
        } else {
            MessageHelper.Item.noPlantOnField();
        }

        return commands;
    }

    private void removePlant() {
        playAnimation(GrowthStage.SEED);
        plant.stateProperty().removeListener((observable, oldValue, newValue) -> {
            plantStateChanged(oldValue, newValue);
        });
        this.plant = null;
    }

    private boolean isPlantGrowing() { return plant != null && !plant.isRipe(); }

    private float depleteWater() {
        if (getWater() > getDepletionRate()) {
            setWater(getWater() - getDepletionRate());
            return getDepletionRate();
        } else {
            return 0;
        }
    }

    private float depleteNutrition() {
        if (getNutrition() > getDepletionRate()) {
            setNutrition(getNutrition() - getDepletionRate());
            return getDepletionRate();
        } else {
            return 0;
        }
    }


    public void shineLight() {
    }


    public float getWater() {
        return water.get();
    }

    public void setWater(float water) {
        this.water.set(water);
    }

    public FloatProperty waterProperty() {
        return water;
    }

    public float getNutrition() {
        return nutrition.get();
    }

    public void setNutrition(float nutrition) {
        this.nutrition.set(nutrition);
    }

    public FloatProperty nutritionProperty() {
        return nutrition;
    }

    public float getDepletionRate() {
        return depletionRate.get();
    }

    public void setDepletionRate(float depletionRate) {
        this.depletionRate.set(depletionRate);
    }

    public FloatProperty depletionRateProperty() {
        return depletionRate;
    }


    public double getPH() {
        return pH.get();
    }

    @JsonIgnore
    public DoubleProperty phProperty() {
        return pH;
    }

    public void setPH(double pH) {
        this.pH.set(pH);
    }

    public float getMaxWater() {
        return maxWater.get();
    }

    @JsonIgnore
    public FloatProperty maxWaterProperty() {
        return maxWater;
    }

    public void setMaxWater(float maxWater) {
        this.maxWater.set(maxWater);
    }

    public float getMaxNutrition() {
        return maxNutrition.get();
    }
    @JsonIgnore
    public FloatProperty maxNutritionProperty() {
        return maxNutrition;
    }

    public void setMaxNutrition(float maxNutrition) {
        this.maxNutrition.set(maxNutrition);
    }
}
