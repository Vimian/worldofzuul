package worldofzuul.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.scene.image.Image;


import java.util.HashMap;
import java.util.Objects;


import static java.lang.Float.valueOf;

public class Seed extends Item {
    private static final String nameDelimiter = " ";

    private int maxSeeds;
    private int minSeeds;

    private Plant plant = new Plant();

    public Seed() {
    }

    public Seed(String name, Float seedCount) {
        super(name);
        setRemaining(seedCount);
    }

    public Seed(String name, Float seedCount, Double value, Double sellbackRate) {
        this(name, seedCount);
        setValue(value);
        setSellBackRate(sellbackRate);
    }

    public Seed(String name, int seedCount) {
        this(name, valueOf(seedCount));
    }

    public Seed(String name, int seedCount, Double value, Double sellbackRate) {
        this(name, valueOf(seedCount), value, sellbackRate);
    }


    public Seed(Seed other) {
        super(other);
        if (other.plant != null) {
            this.plant = (Plant) other.plant.copyItem();
        }
    }

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


    public int getMaxSeeds() {
        return maxSeeds;
    }

    public void setMaxSeeds(int maxSeeds) {
        this.maxSeeds = maxSeeds;
    }

    public int getMinSeeds() {
        return minSeeds;
    }

    public void setMinSeeds(int minSeeds) {
        this.minSeeds = minSeeds;
    }

    public Plant getPlant() {
        return plant;
    }

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
