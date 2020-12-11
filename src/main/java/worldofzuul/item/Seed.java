package worldofzuul.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.scene.image.Image;
import worldofzuul.Player;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Float.valueOf;

public class Seed extends Item {
    private static final String nameDelimiter = " ";

    private int maxSeeds;
    private int minSeeds;

    private int maxCornSeedCount = 850;
    private int minCornSeedCount = 750;
    private int maxRiceSeedCount = 1100;
    private int minRiceSeedCount = 900;
    private int CashewSeedCount = 1;
    private int maxCowpeaSeedCount = 13;
    private int minCowpeaSeedCount = 6;
    private int MangoSeedCount = 1;
    private crops crops;
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


    @JsonIgnore
    public Plant useSeed() {
        deplete();
        return new Plant(plant);
    }


    public void getSeedsFromPlant(Plant plant, Player player) {
        switch (crops) {
            case CORN:
                if (player.getInventory().getItems().contains(plant)) {
                    player.getInventory().removeItem(plant);
                    ThreadLocalRandom randomSeedGenerator = ThreadLocalRandom.current();
                    int randomSeed = randomSeedGenerator.nextInt(minCornSeedCount, maxCornSeedCount);
                    setRemaining(getRemaining() + randomSeed);
                    Item Seed = new Seed("CornSeed", getRemaining(), 2.0, 0.20);
                    player.getInventory().addItem(Seed);
                } else {
                    System.out.println("This is not a plant and does not contain any seeds");

    }
    case RICE:
        if (player.getInventory().getItems().contains(plant)) {
            player.getInventory().removeItem(plant);
            ThreadLocalRandom randomSeedGenerator = ThreadLocalRandom.current();
            int randomSeed = randomSeedGenerator.nextInt(minRiceSeedCount, maxRiceSeedCount);
            setRemaining(getRemaining() + randomSeed);
            Item Seed = new Seed("RiceSeed", getRemaining(), 2.0, 0.20);
            player.getInventory().addItem(Seed);
        } else {
            System.out.println("This is not a plant and does not contain any seeds");

        }
    case CASHEW:
        if (player.getInventory().getItems().contains(plant)) {
            player.getInventory().removeItem(plant);
            setRemaining(getRemaining() + CashewSeedCount);
            Item Seed = new Seed("CashewSeed", getRemaining(), 2.0, 0.50);
            player.getInventory().addItem(Seed);
        } else {
            System.out.println("This is not a plant and does not contain any seeds");
        }
    case COWPEA:
        if (player.getInventory().getItems().contains(plant)) {
            player.getInventory().removeItem(plant);
            ThreadLocalRandom randomSeedGenerator = ThreadLocalRandom.current();
            int randomSeed = randomSeedGenerator.nextInt(minCowpeaSeedCount, maxCowpeaSeedCount);
            setRemaining(getRemaining() + randomSeed);
            Item Seed = new Seed("CowpeaSeed", getRemaining(), 2.0, 0.20);
            player.getInventory().addItem(Seed);
        } else {
            System.out.println("This is not a plant and does not contain any seeds");

        }
    case MANGO:
        if (player.getInventory().getItems().contains(plant)) {
            player.getInventory().removeItem(plant);
            setRemaining(getRemaining() + MangoSeedCount);
            Item Seed = new Seed("MangoSeed", getRemaining(), 2.0, 0.50);
            player.getInventory().addItem(Seed);
        } else {
            System.out.println("This is not a plant and does not contain any seeds");
        }
        }
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

        if(plant != null){
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
