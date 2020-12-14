package worldofzuul.world;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.scene.image.ImageView;
import worldofzuul.item.*;
import worldofzuul.parsing.Command;
import worldofzuul.parsing.CommandWord;
import worldofzuul.util.MessageHelper;

/**
 * The type Field.
 *
 * Is responsible for the growth of {@link Plant} objects.
 *
 */
public class Field extends GameObject {
    /**
     * The constant minpH.
     *
     * Not used in game.
     *
     */
    private final static double minpH = 0.0;
    /**
     * The constant maxpH.
     *
     * Not used in game.
     *
     */
    private final static double maxpH = 14.0;
    /**
     * The P h.
     *
     * Not used in game.
     *
     */
    private final DoubleProperty pH = new SimpleDoubleProperty(7);
    /**
     * The Water.
     *
     * Amount of water contained in field.
     *
     */
    private final FloatProperty water = new SimpleFloatProperty(20000);
    /**
     * The Nutrition.
     *
     * Amount of nutrition contained in field.
     *
     */
    private final FloatProperty nutrition = new SimpleFloatProperty(10000);
    /**
     * The Depletion rate.
     *
     * Depletion coefficient used to multiply by {@link Plant}'s depletion rates
     *
     */
    private final FloatProperty depletionRate = new SimpleFloatProperty(1);
    /**
     * The Max water.
     *
     * Maximum amount water the field can contain.
     *
     */
    private final FloatProperty maxWater = new SimpleFloatProperty(20000);
    /**
     * The Max nutrition.
     *
     * Maximum amount of nutrition the field can contain.
     *
     */
    private final FloatProperty maxNutrition = new SimpleFloatProperty(20000);
    /**
     * The Plant.
     */
    private Plant plant;
    /**
     * The Ripe plant seen.
     */
    private boolean ripePlantSeen = false;

    /**
     * Instantiates a new Field.
     */
    public Field() {
    }


    /**
     * Instantiates a new Field.
     *
     * @param water the water
     */
    public Field(float water) {
        setWater(water);
    }

    /**
     * Instantiates a new Field.
     *
     * @param water the water
     * @param pH    the p h
     */
    public Field(float water, DoubleProperty pH) {
        this(water);
        this.setPH(pH.get());
    }


    /**
     * Add water.
     *
     * Adds water as long as it doesn't exceed {@link Field#maxWater}.
     *
     * @param water amount to add
     */
    public void addWater(float water) {
        if (getWater() + water <= getMaxWater()) {
            setWater(getWater() + water);
        }
    }

    /**
     * Update.
     *
     * Grows the {@link Field#plant} if one is present, taking water and nutrition from the field.
     *
     *
     * @return returns {@link GameObject#update()}
     */
    @Override
    public Command[] update() {
        if (plant != null) {
            if (isPlantGrowing()) {
                float waterToAdd = 0;
                float nutritionToAdd = 0;

                if (plant.getWaterNeeded() > 0) {
                    waterToAdd = depleteWater(plant.getWaterDepletionRate());
                }
                if (plant.getNutritionNeeded() > 0) {
                    nutritionToAdd = depleteNutrition(plant.getNutritionDepletionRate());
                }
                plant.grow(waterToAdd, nutritionToAdd);

            }
            if (plant.isRipe() && !ripePlantSeen) {
                MessageHelper.Info.plantBecameRipe(plant.getName());

                ripePlantSeen = true;
            }
        }

        return super.update();
    }

    /**
     * Interact using an item.
     *
     * @param item the item
     * @return commands to be added dependent on the used item
     */
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
        } else if (item instanceof pHNeutralizers) {
            return usepHNeutralizers((pHNeutralizers) item);
        }

        return super.interact(item);
    }

    /**
     * Use fertilizer.
     * 
     * Adds nutrition to field.
     *
     * @param item the fertilizer item
     * @return command to remove item if {@link Item#getRemaining()} becomes 0.
     */
    private Command[] useFertilizer(Fertilizer item) {
        Command[] commands = new Command[1];
        if (nutrition.get() + item.getConsumptionRate() < maxNutrition.get()) {
            nutrition.set(nutrition.get() + item.deplete());
        }


        if (item.getRemaining() == 0) {
            commands[0] = new Command(CommandWord.REMOVE_ITEM, null, item);
        }

        return commands;
    }

    /**
     * Use irrigator.
     * 
     * Calls {@link Irrigator#water(Field)} using this.
     *
     * @param item the irrigator item
     */
    private void useIrrigator(Irrigator item) {
        item.water(this);
    }

    /**
     * Use Seed item.
     *
     * Calls {@link Field#plantSeed(Seed)} using param item.
     *
     * @param item the seed item
     * @return command to remove item if {@link Item#getRemaining()} becomes 0.
     */
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
            commands[0] = new Command(CommandWord.REMOVE_ITEM, null, item);
        }


        return commands;
    }

    /**
     * Plant seed.
     *
     * Sets {@link Field#plant} to be a copy of the {@link Seed#getPlant()} using {@link Seed#useSeed()} and readies for plant growth by listening to {@link Plant#stateProperty()} change.
     *
     * @param item the seed item
     */
    private void plantSeed(Seed item) {
        MessageHelper.Item.usedItemOn(item.getName(), this.getClass().getSimpleName());
        plant = (item.useSeed());
        ripePlantSeen = false;

        plant.setImageView(getImageView());
        plant.playAnimation(GrowthStage.SEED);
        plant.stateProperty().addListener((observable, oldValue, newValue) -> plantStateChanged(newValue));

    }

    /**
     * Plant state changed.
     *
     * Play animation that corresponds with the parameter.
     *
     * @param newValue the new value
     */
    private void plantStateChanged(GrowthStage newValue) {
        plant.playAnimation(newValue);
    }

    /**
     * Harvest plant.
     *
     * Harvests the plant if it is ripe and removes the plant without returning anything if it is dead.
     *
     * @param item the harvester item
     * @return command to add {@link Plant} or null
     */
    private Command[] useHarvester(Harvester item) {
        Command[] commands = new Command[1];
        if (plant != null) {
            if (plant.isRipe()) {
                MessageHelper.Item.harvested(plant.getName());
                commands[0] = new Command(CommandWord.ADD_ITEM, null, item.harvest(plant));
                removePlant();

            } else if (plant.getState() == GrowthStage.DEAD) {
                removePlant();
            } else {
                MessageHelper.Item.unripePlant();
            }
        } else {
            MessageHelper.Item.noPlantOnField();
        }

        return commands;
    }

    /**
     * Usep h neutralizers command [ ].
     *
     * Not used in game.
     *
     * @param item the item
     * @return the command [ ]
     */
    public Command[] usepHNeutralizers(pHNeutralizers item) {
        Command[] commands = new Command[1];

        if (item.getRemaining() <= 0) {
            System.out.println("You do not have enough Neutralizers! You only got " + item.getRemaining() + " left");
            return commands;
        }

        double currentpH = getPH();

        if ((currentpH == maxpH && item.getpHChange() > 0) || (currentpH == minpH && item.getpHChange() < 0)) {
            System.out.println("You can not go below 0 or above 14 pH");
            return commands;
        }

        item.deplete();

        if (item.getRemaining() <= 0) {
            commands[0] = new Command(CommandWord.REMOVE_ITEM, null, item);
        }

        setPH(getPH() + item.getpHChange());

        if (currentpH < getPH()) {
            MessageHelper.Item.increasedpH(Double.toString(getPH()));
        } else {
            MessageHelper.Item.decreasedpH(Double.toString(getPH()));
        }

        return commands;
    }

    /**
     * Remove plant.
     *
     * Removes listeners and readies for new plant growth.
     *
     */
    private void removePlant() {
        plant.stateProperty().removeListener((observable, oldValue, newValue) -> plantStateChanged(newValue));
        plant.playAnimation(null);
        plant.setImageView(null);
        this.plant = null;

    }

    /**
     * Is plant growing boolean.
     *
     * @return the boolean
     */
    private boolean isPlantGrowing() {
        return plant != null && !plant.isRipe();
    }

    /**
     * Deplete water float.
     *
     * @param waterDepletionRate the water depletion rate
     * @return the float
     */
    private float depleteWater(float waterDepletionRate) {
        if (getWater() > getDepletionRate() * waterDepletionRate) {
            setWater(getWater() - getDepletionRate() * waterDepletionRate);
            return getDepletionRate() * waterDepletionRate;
        } else {
            return 0;
        }
    }

    /**
     * Deplete nutrition float.
     *
     * @param nutritionDepletionRate the nutrition depletion rate
     * @return the float
     */
    private float depleteNutrition(float nutritionDepletionRate) {
        if (getNutrition() > getDepletionRate() * nutritionDepletionRate) {
            setNutrition(getNutrition() - getDepletionRate() * nutritionDepletionRate);
            return getDepletionRate() * nutritionDepletionRate;
        } else {
            return 0;
        }
    }

    /**
     * Gets water.
     *
     * @return the water
     */
    public float getWater() {
        return water.get();
    }

    /**
     * Sets water.
     *
     * @param water the water
     */
    public void setWater(float water) {
        this.water.set(water);
    }

    /**
     * Water property float property.
     *
     * @return the float property
     */
    public FloatProperty waterProperty() {
        return water;
    }

    /**
     * Gets nutrition.
     *
     * @return the nutrition
     */
    public float getNutrition() {
        return nutrition.get();
    }

    /**
     * Sets nutrition.
     *
     * @param nutrition the nutrition
     */
    public void setNutrition(float nutrition) {
        this.nutrition.set(nutrition);
    }

    /**
     * Nutrition property float property.
     *
     * @return the float property
     */
    public FloatProperty nutritionProperty() {
        return nutrition;
    }

    /**
     * Gets depletion rate.
     *
     * @return the depletion rate
     */
    public float getDepletionRate() {
        return depletionRate.get();
    }

    /**
     * Sets depletion rate.
     *
     * @param depletionRate the depletion rate
     */
    public void setDepletionRate(float depletionRate) {
        this.depletionRate.set(depletionRate);
    }

    /**
     * Depletion rate property float property.
     *
     * @return the float property
     */
    public FloatProperty depletionRateProperty() {
        return depletionRate;
    }


    /**
     * Gets ph.
     *
     * @return the ph
     */
    public double getPH() {
        return pH.get();
    }

    /**
     * Sets ph.
     *
     * Not used in game.
     *
     * @param pH the p h
     */
    public void setPH(double pH) {
        this.pH.set(pH);

        if (getPH() > maxpH) {
            setPH(maxpH);
        } else if (getPH() < minpH) {
            setPH(minpH);
        }
    }

    /**
     * Ph property double property.
     *
     * @return the double property
     */
    @JsonIgnore
    public DoubleProperty phProperty() {
        return pH;
    }

    /**
     * Gets max water.
     *
     * @return the max water
     */
    public float getMaxWater() {
        return maxWater.get();
    }

    /**
     * Sets max water.
     *
     * @param maxWater the max water
     */
    public void setMaxWater(float maxWater) {
        this.maxWater.set(maxWater);
    }

    /**
     * Max water property float property.
     *
     * @return the float property
     */
    @JsonIgnore
    public FloatProperty maxWaterProperty() {
        return maxWater;
    }

    /**
     * Gets max nutrition.
     *
     * @return the max nutrition
     */
    public float getMaxNutrition() {
        return maxNutrition.get();
    }

    /**
     * Sets max nutrition.
     *
     * @param maxNutrition the max nutrition
     */
    public void setMaxNutrition(float maxNutrition) {
        this.maxNutrition.set(maxNutrition);
    }

    /**
     * Max nutrition property float property.
     *
     * @return the float property
     */
    @JsonIgnore
    public FloatProperty maxNutritionProperty() {
        return maxNutrition;
    }

    @Override
    public void setImageView(ImageView imageView) {
        super.setImageView(imageView);

        if (plant != null) {
            plant.setImageView(imageView);
            plant.playAnimation(plant.getState());
        }

    }
}
