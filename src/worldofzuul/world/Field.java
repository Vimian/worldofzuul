package worldofzuul.world;

import worldofzuul.item.*;
import worldofzuul.parsing.Command;
import worldofzuul.parsing.CommandWord;
import worldofzuul.util.MessageHelper;

public class Field extends GameObject {
    private Fertilizer fertilizer;

    public Field() {
    }

    private float water;
    private Plant plant;
    private Float nutrition;
    private int plantTickCounter = 0;

    public Field(Fertilizer fertilizer) {
        this.fertilizer = fertilizer;
    }

    public Field(Fertilizer fertilizer, float water) {
        this(fertilizer);
        this.water = water;
    }

    public void removePlant() {
        this.plant = null;
        plantTickCounter = 0;
    }


    private boolean isPlantGrowing(){
        return plant != null && plantTickCounter < 1000; //TODO: Implement "ripeness" check
    }


    private void growPlant(){
        //TODO: Implement growPlant
    }



    @Override
    public Command[] update() {

        if(isPlantGrowing()){
            growPlant();
        }


        return super.update();
    }

    @Override
    public Command[] interact() {
        MessageHelper.Command.unknownAction();
        return super.interact();
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
        } else {
            MessageHelper.Item.cantUseItem(item.getName());
        }

        return super.interact(item);
    }

    private Command[] useFertilizer(Fertilizer item) {
        return null; //TODO: Implement method.
    }

    private Command[] useSeed(Seed item) {
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
            if (!isPlantGrowing()) {
                MessageHelper.Item.harvested(plant.getName());
                commands[0] = new Command(CommandWord.ADDITEM, null, item.harvest(plant));
                removePlant();
            } else {
                MessageHelper.Item.unripePlant();
            }
        }
        else {
            MessageHelper.Item.noPlantOnField();
        }

        return commands;
    }


}
