package worldofzuul.world;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import worldofzuul.item.*;
import worldofzuul.parsing.Command;
import worldofzuul.parsing.CommandWord;
import worldofzuul.util.MessageHelper;

import java.util.ArrayList;

public class Field extends GameObject {
    private float maxWater = 20000;
    private float maxNutrition = 20000;


    private Fertilizer fertilizer;
    private Plant plant;
    private ArrayList<Plant> plants;
    private Double pH;

    private final FloatProperty water = new SimpleFloatProperty(20000);
    private final FloatProperty nutrition = new SimpleFloatProperty(10000);
    private final FloatProperty depletionRate = new SimpleFloatProperty(5);
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
        this.pH = pH;
    }


    public void addWater(float water) {
        if (isPlantGrowing()) {
            setWater(getWater() + water);
        }
    }

    public Double getPH(){
        return pH;
    }

    public void setPH(Double value){
        pH = value;
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
        return null; //TODO: Implement method.
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
        plant = (item.getPlant());
        ripePlantSeen = false;
        playAnimation(GrowthStage.ADULT);
    }

    private Command[] useHarvester(Harvester item) {
        Command[] commands = new Command[1];
        if (plant != null) {
            if (plant.isRipe()) {
                MessageHelper.Item.harvested(plant.getName());
                commands[0] = new Command(CommandWord.ADDITEM, null, item.harvest(plant));
                removePlant();

                playAnimation(GrowthStage.SEED);

            } else {
                MessageHelper.Item.unripePlant();
            }
        } else {
            MessageHelper.Item.noPlantOnField();
        }

        return commands;
    }

    private void removePlant() {
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


    public float getMaxWater() {
        return maxWater;
    }

    public void setMaxWater(float maxWater) {
        this.maxWater = maxWater;
    }

    public float getMaxNutrition() {
        return maxNutrition;
    }

    public void setMaxNutrition(float maxNutrition) {
        this.maxNutrition = maxNutrition;
    }
}
