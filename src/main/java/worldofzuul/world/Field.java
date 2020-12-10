package worldofzuul.world;

import javafx.beans.property.FloatProperty;
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
    private float pH =6;


    private final FloatProperty water = new SimpleFloatProperty(10);
    private final FloatProperty nutrition = new SimpleFloatProperty(10000);
    private final FloatProperty depletionRate = new SimpleFloatProperty(5); //5 is default
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


    public void addWater(float water) {
        if (isPlantGrowing()) {
            setWater(getWater() + water);
        }
    }

    @Override
    public Command[] update() {
        if (plant != null) {
            if(isPlantGrowing()){
                plant.grow(depleteWater(), depleteNutrition());
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

        if (item.getSeedCount() > 0) {
            plantSeed(item);
        }

        if (item.getSeedCount() == 0) {
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

    public void growTimeModify(double tickAmount, boolean decreaseGrowTime) {
        if(plant.getGrowthTime() != 0 && !plant.isRipe() ){
            if(decreaseGrowTime){
                plant.setGrowthTime(plant.getGrowthTime()-tickAmount);
            } else {
                plant.setGrowthTime(plant.getGrowthTime()+tickAmount);
            }
        }
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

    public void setDepletionRate(double depletionRate) {
        this.depletionRate.set((float)depletionRate);
    }

    public FloatProperty depletionRateProperty() {
        return depletionRate;
    }

    public boolean hasPlant(){
        if(this.plant != null){
            return true;
        } else {
            return false;
        }
    }

    public Plant getPlant(){
        return this.plant;
    }

    public float getpH(){
        return pH;
    }
    public void setpH(float ph){this.pH=ph;}



}
