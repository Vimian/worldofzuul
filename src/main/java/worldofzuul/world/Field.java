package worldofzuul.world;

import java.util.ArrayList;
import worldofzuul.item.*;
import worldofzuul.parsing.Command;
import worldofzuul.parsing.CommandWord;
import worldofzuul.util.MessageHelper;

public class Field extends GameObject {
    private Fertilizer fertilizer;
    private float water = 0;
    private Plant plant;
    private ArrayList<Plant> plants;
    private float nutrition = 10000;
    private float depletionRate = 5;
    public Field() {
    }
    public Field(Fertilizer fertilizer) {
        this.fertilizer = fertilizer;
    }

    public Field(Fertilizer fertilizer, float water) {
        this(fertilizer);
        this.water = water;
    }


    public void addWater(float water) {
        if (isPlantGrowing()) {
            this.water += water;
        }
    }

    @Override
    public Command[] update() {

        if (isPlantGrowing()) {
            plant.grow(depleteWater(), depleteNutrition());
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
        if (isPlantGrowing()) {
            MessageHelper.Item.alreadyPlanted();
            return null;
        }

        Command[] commands = new Command[1];

        if (item.getSeedCount() > 0) {
            MessageHelper.Item.usedItemOn(item.getName(), this.getClass().getSimpleName());
            plant = (item.getPlant());
        }

        if (item.getSeedCount() == 0) {
            commands[0] = new Command(CommandWord.REMOVEITEM, null);
        }


        return commands;
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
        this.plant = null;
    }

    private boolean isPlantGrowing() { return plant != null && !plant.isRipe(); }

    private float depleteWater() {
        if (water > depletionRate) {
            return water = -depletionRate;
        } else {
            return 0;
        }
    }

    private float depleteNutrition() {
        if (nutrition > depletionRate) {
            return nutrition = -depletionRate;
        } else {
            return 0;
        }
    }


    public void shineLight() {
    }
}
