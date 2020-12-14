package worldofzuul.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.scene.image.Image;


import java.util.HashMap;
import java.util.Objects;


import static java.lang.Float.valueOf;

/**
 * The type Seed.
 *
 * Is used to plant a {@link Plant} in a {@link worldofzuul.world.Field}.
 *
 */
public class Seed extends Item {
    /**
     * The constant nameDelimiter.
     */
    private static final String nameDelimiter = " ";

    /**
     * The Max seeds.
     *
     * Not used in game.
     *
     */
    private int maxSeeds;
    /**
     * The Min seeds.
     *
     * Not used in game.
     *
     */
    private int minSeeds;

    /**
     * The Plant.
     *
     * The plant which is to be planted in the {@link worldofzuul.world.Field}.
     *
     */
    private Plant plant = new Plant();

    /**
     * Instantiates a new Seed.
     */
    public Seed() {
    }

    /**
     * Instantiates a new Seed.
     *
     * @param name      the name
     * @param seedCount the seed count
     */
    public Seed(String name, Float seedCount) {
        super(name);
        setRemaining(seedCount);
    }

    /**
     * Instantiates a new Seed.
     *
     * @param name         the name
     * @param seedCount    the seed count
     * @param value        the value
     * @param sellbackRate the sellback rate
     */
    public Seed(String name, Float seedCount, Double value, Double sellbackRate) {
        this(name, seedCount);
        setValue(value);
        setSellBackRate(sellbackRate);
    }

    /**
     * Instantiates a new Seed.
     *
     * @param name      the name
     * @param seedCount the seed count
     */
    public Seed(String name, int seedCount) {
        this(name, valueOf(seedCount));
    }

    /**
     * Instantiates a new Seed.
     *
     * @param name         the name
     * @param seedCount    the seed count
     * @param value        the value
     * @param sellbackRate the sellback rate
     */
    public Seed(String name, int seedCount, Double value, Double sellbackRate) {
        this(name, valueOf(seedCount), value, sellbackRate);
    }


    /**
     * Instantiates a new Seed.
     *
     * @param other the other
     */
    public Seed(Seed other) {
        super(other);
        if (other.plant != null) {
            this.plant = (Plant) other.plant.copyItem();
        }
    }

    /**
     * Use seed plant.
     *
     * Depletes {@link Seed#remaining}.
     *
     * @return Copy of {@link Seed#plant}
     */
    @JsonIgnore
    public Plant useSeed() {
        deplete();
        return new Plant(plant);
    }

    @Override
    public Item copyItem() {
        return new Seed(this);
    }

    @Override
    public String getName() {
        return super.getName() + nameDelimiter + this.getClass().getSimpleName().toLowerCase();
    }


    /**
     * Gets max seeds.
     *
     * @return the max seeds
     */
    public int getMaxSeeds() {
        return maxSeeds;
    }

    /**
     * Sets max seeds.
     *
     * @param maxSeeds the max seeds
     */
    public void setMaxSeeds(int maxSeeds) {
        this.maxSeeds = maxSeeds;
    }

    /**
     * Gets min seeds.
     *
     * @return the min seeds
     */
    public int getMinSeeds() {
        return minSeeds;
    }

    /**
     * Sets min seeds.
     *
     * @param minSeeds the min seeds
     */
    public void setMinSeeds(int minSeeds) {
        this.minSeeds = minSeeds;
    }

    /**
     * Gets plant.
     *
     * @return the plant
     */
    public Plant getPlant() {
        return plant;
    }

    /**
     * Sets plant.
     *
     * @param plant the plant
     */
    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    @Override
    public void configureImages(HashMap<String, Image> images) {
        super.configureImages(images);

        if (plant != null) {
            plant.configureImages(images);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Seed)) return false;
        if (!super.equals(o)) return false;
        Seed seed = (Seed) o;
        return maxSeeds == seed.maxSeeds &&
                minSeeds == seed.minSeeds &&
                Objects.equals(plant, seed.plant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), maxSeeds, minSeeds, plant);
    }
}
