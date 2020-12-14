package worldofzuul.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Objects;

import static worldofzuul.item.GrowthStage.*;

/**
 * The type Plant.
 *
 */
public class Plant extends Item {
    /**
     * The growth stage of the plant.
     */
    private final ObjectProperty<GrowthStage> state = new SimpleObjectProperty<>(SEED);
    /**
     * The Seed quality.
     *
     * Not used in game.
     *
     */
    private float seedQuality = 1;
    /**
     * The Water needed.
     *
     * The amount of water left until the plant is ripe.
     *
     */
    private float waterNeeded = 1000;
    /**
     * The Water depletion rate.
     *
     * The rate at which the plant consumes water.
     *
     */
    private float waterDepletionRate = 5;
    /**
     * The Max water.
     *
     * The starting point of how much water the plant needed.
     *
     */
    private float maxWater = waterNeeded;
    /**
     * The Nutrition needed.
     *
     * The amount of nutrition left until the plant is ripe.
     *
     */
    private float nutritionNeeded = 1000;
    /**
     * The Nutrition depletion rate.
     *
     * The rate at which the plant consumes nutrition.
     *
     */
    private float nutritionDepletionRate = 5;
    /**
     * The Max nutrition.
     *
     * The starting point of how much nutrition the plant needed.
     *
     */
    private float maxNutrition = nutritionNeeded;
    /**
     * The Growth time.
     *
     * The minimum time that the plant needs to grow, only relevant if the plant grows too quickly due to a too high depletion rate.
     *
     */
    private int growthTime = 1000;
    /**
     * The Max time without water.
     *
     * The amount of update ticks a plant can go without withering and becoming {@link GrowthStage#DEAD}.
     *
     */
    private int maxTimeWithoutWater = 100;

    /**
     * The Ticks not watered.
     *
     * Amount of update ticks the plant has gone without the {@link Plant#waterNeeded} attribute being changed.
     *
     */
    private int ticksNotWatered;
    /**
     * The Grow ticks.
     *
     * Total amount of update ticks.
     *
     */
    private int growTicks = 0;

    /**
     * Instantiates a new Plant.
     */
    public Plant() {
    }

    /**
     * Instantiates a new Plant.
     *
     * @param name         the name
     * @param value        the value
     * @param sellbackRate the sellback rate
     */
    public Plant(String name, Double value, Double sellbackRate) {
        super(name, value, sellbackRate);
    }

    /**
     * Instantiates a new Plant.
     *
     * @param plant the plant
     */
    public Plant(Plant plant) {
        super(plant.getName());

        setSeedQuality(plant.getSeedQuality());
        setWaterNeeded(plant.getWaterNeeded());
        setNutritionNeeded(plant.getNutritionNeeded());
        setGrowthTime(plant.getGrowthTime());
        this.maxWater = waterNeeded;
        this.maxNutrition = nutritionNeeded;
        setMaxTimeWithoutWater(plant.getMaxTimeWithoutWater());
        setCapacity(plant.getCapacity());
        setValue(plant.getValue());
        setSellBackRate(plant.getSellBackRate());
        setDefaultImageFile(plant.getDefaultImageFile());
        setNutritionDepletionRate(plant.getNutritionDepletionRate());
        setWaterDepletionRate(plant.getWaterDepletionRate());
        setAnimationStringKeys(plant.getAnimationStringKeys());
        setAnimationStringValues(plant.getAnimationStringValues());
        setImageAnimations(plant.getImageAnimations());
        setAnimationCycleLengthMillis(plant.getAnimationCycleLengthMillis());
        setRemaining(plant.getRemaining());
    }

    @Override
    public Item copyItem() {
        return new Plant(this);
    }

    /**
     * Grow.
     *
     * Represents the update function of {@link Plant}.
     * Consumes water and nutrition as given.
     * Changes {@link Plant#state} if plant is {@link Plant#readyForNextStage()}
     *
     * @param water     the water
     * @param nutrition the nutrition
     */
    public void grow(float water, float nutrition) {

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


    /**
     * Wither plant.
     *
     * Updates the {@link Plant#ticksNotWatered} and changes the {@link Plant#state} to {@link GrowthStage#DEAD} if it reaches {@link Plant#maxTimeWithoutWater}.
     *
     */
    private void witherPlant() {
        if (waterNeeded <= 0) {
            return;
        }

        ticksNotWatered++;

        if (ticksNotWatered >= maxTimeWithoutWater) {
            setState(DEAD);
        }

    }

    /**
     * Is ripe boolean.
     *
     * Determines whether the plant is ripe.
     *
     * @return the boolean
     */
    @JsonIgnore
    public boolean isRipe() {
        return waterNeeded <= 0 && nutritionNeeded <= 0 && growTicks >= growthTime && getState() == RIPE;
    }


    /**
     * Consume water.
     *
     * @param water the water
     */
    private void consumeWater(float water) {
        waterNeeded -= water * seedQuality;
    }

    /**
     * Consume nutrition.
     *
     * @param nutrition the nutrition
     */
    private void consumeNutrition(float nutrition) {
        nutritionNeeded -= nutrition * seedQuality;
    }


    /**
     * Ready for next stage boolean.
     *
     * @return the boolean
     */
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

    /**
     * Determines whether the {@link Plant} has reached the quartile of attributes required for advancement.
     *
     * @param i the
     * @return whether {@link Plant} is ready to advance to next {@link GrowthStage}
     */
    private boolean requiredPropertiesReady(int i) {
        return (maxWater / 4) * i <= maxWater - waterNeeded &&
                (maxNutrition / 4) * i <= maxNutrition - nutritionNeeded &&
                (growthTime / 4) * i <= growTicks;
    }

    /**
     * Advance stage.
     */
    private void advanceStage() {
        switch (getState()) {
            case SEED -> setState(SPROUT);
            case SPROUT -> setState(ADULT);
            case ADULT -> setState(RIPE);
        }
    }

    /**
     * Gets water depletion rate.
     *
     * @return the water depletion rate
     */
    public float getWaterDepletionRate() {
        return waterDepletionRate;
    }

    /**
     * Sets water depletion rate.
     *
     * @param waterDepletionRate the water depletion rate
     */
    public void setWaterDepletionRate(float waterDepletionRate) {
        this.waterDepletionRate = waterDepletionRate;
    }

    /**
     * Gets nutrition depletion rate.
     *
     * @return the nutrition depletion rate
     */
    public float getNutritionDepletionRate() {
        return nutritionDepletionRate;
    }

    /**
     * Sets nutrition depletion rate.
     *
     * @param nutritionDepletionRate the nutrition depletion rate
     */
    public void setNutritionDepletionRate(float nutritionDepletionRate) {
        this.nutritionDepletionRate = nutritionDepletionRate;
    }

    /**
     * Gets seed quality.
     *
     * @return the seed quality
     */
    public float getSeedQuality() {
        return seedQuality;
    }

    /**
     * Sets seed quality.
     *
     * @param seedQuality the seed quality
     */
    public void setSeedQuality(float seedQuality) {
        this.seedQuality = seedQuality;
    }

    /**
     * Gets water needed.
     *
     * @return the water needed
     */
    public float getWaterNeeded() {
        return waterNeeded;
    }

    /**
     * Sets water needed.
     *
     * @param waterNeeded the water needed
     */
    public void setWaterNeeded(float waterNeeded) {
        this.waterNeeded = waterNeeded;
    }

    /**
     * Gets nutrition needed.
     *
     * @return the nutrition needed
     */
    public float getNutritionNeeded() {
        return nutritionNeeded;
    }

    /**
     * Sets nutrition needed.
     *
     * @param nutritionNeeded the nutrition needed
     */
    public void setNutritionNeeded(float nutritionNeeded) {
        this.nutritionNeeded = nutritionNeeded;
    }

    /**
     * Gets growth time.
     *
     * @return the growth time
     */
    public int getGrowthTime() {
        return growthTime;
    }

    /**
     * Sets growth time.
     *
     * @param growthTime the growth time
     */
    public void setGrowthTime(int growthTime) {
        this.growthTime = growthTime;
    }

    /**
     * Gets grow ticks.
     *
     * @return the grow ticks
     */
    @JsonIgnore
    public int getGrowTicks() {
        return growTicks;
    }

    /**
     * Sets grow ticks.
     *
     * @param growTicks the grow ticks
     */
    public void setGrowTicks(int growTicks) {
        this.growTicks = growTicks;
    }

    /**
     * Gets max time without water.
     *
     * @return the max time without water
     */
    public int getMaxTimeWithoutWater() {
        return maxTimeWithoutWater;
    }

    /**
     * Sets max time without water.
     *
     * @param maxTimeWithoutWater the max time without water
     */
    public void setMaxTimeWithoutWater(int maxTimeWithoutWater) {
        this.maxTimeWithoutWater = maxTimeWithoutWater;
    }

    /**
     * Gets state.
     *
     * @return the state
     */
    @JsonIgnore
    public GrowthStage getState() {
        return state.get();
    }

    /**
     * Sets state.
     *
     * @param state the state
     */
    @JsonIgnore
    public void setState(GrowthStage state) {
        this.state.set(state);
    }

    /**
     * State property property.
     *
     * @return the property
     */
    @JsonIgnore
    public Property<GrowthStage> stateProperty() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Plant)) return false;
        if (!super.equals(o)) return false;
        Plant plant = (Plant) o;
        return Float.compare(plant.seedQuality, seedQuality) == 0 &&
                Float.compare(plant.waterNeeded, waterNeeded) == 0 &&
                Float.compare(plant.waterDepletionRate, waterDepletionRate) == 0 &&
                Float.compare(plant.maxWater, maxWater) == 0 &&
                Float.compare(plant.nutritionNeeded, nutritionNeeded) == 0 &&
                Float.compare(plant.nutritionDepletionRate, nutritionDepletionRate) == 0 &&
                Float.compare(plant.maxNutrition, maxNutrition) == 0 &&
                growthTime == plant.growthTime &&
                maxTimeWithoutWater == plant.maxTimeWithoutWater;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), seedQuality, waterNeeded, waterDepletionRate, maxWater, nutritionNeeded, nutritionDepletionRate, maxNutrition, growthTime, maxTimeWithoutWater);
    }

}
