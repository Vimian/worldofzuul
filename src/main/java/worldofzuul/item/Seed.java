package worldofzuul.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import worldofzuul.Player;

import java.util.concurrent.ThreadLocalRandom;

public class Seed extends Item {
    private static final String nameDelimiter = " ";
    private int seedCount;
    private int maxCornSeedCount = 850;
    private int minCornSeedCount = 750;
    private int maxRiceSeedCount = 1100;
    private int minRiceSeedCount = 900;
    private int CashewSeedCount = 1;
    private int maxCowpeaSeedCount = 13;
    private int minCowpeaSeedCount = 6;
    private int MangoSeedCount = 1;
    private crops crops;

    public Seed(){

    }

    public Seed(String name, int seedCount) {
        super(name);
        this.seedCount = seedCount;
    }

    public Seed(String name, int seedCount, Double value, Double sellbackRate) {
        super(name,value,sellbackRate);
        this.seedCount = seedCount;
    }


    @JsonIgnore
    public Plant getPlant(){
        seedCount--;
        return new Plant(super.getName());
    }
    public int getSeedCount() {
        return seedCount;
    }

    public void getSeedsFromPlant(Plant plant, Player player){
switch (crops) {
    case CORN:
    if (player.getInventory().getItems().contains(plant)) {
        player.getInventory().removeItem(plant);
        ThreadLocalRandom randomSeedGenerator = ThreadLocalRandom.current();
        int randomSeed = randomSeedGenerator.nextInt(minCornSeedCount, maxCornSeedCount);
        seedCount = seedCount + randomSeed;
        Item Seed = new Seed("CornSeed", seedCount, 2.0, 0.20);
        player.getInventory().addItem(Seed);
    } else {
        System.out.println("This is not a plant and does not contain any seeds");

    }
    case RICE:
        if (player.getInventory().getItems().contains(plant)) {
            player.getInventory().removeItem(plant);
            ThreadLocalRandom randomSeedGenerator = ThreadLocalRandom.current();
            int randomSeed = randomSeedGenerator.nextInt(minRiceSeedCount, maxRiceSeedCount);
            seedCount = seedCount + randomSeed;
            Item Seed = new Seed("RiceSeed", seedCount, 2.0, 0.20);
            player.getInventory().addItem(Seed);
        } else {
            System.out.println("This is not a plant and does not contain any seeds");

        }
    case CASHEW:
        if(player.getInventory().getItems().contains(plant)){
            player.getInventory().removeItem(plant);
            seedCount = seedCount + CashewSeedCount;
            Item Seed = new Seed("CashewSeed", seedCount, 2.0, 0.50);
            player.getInventory().addItem(Seed);
        } else {
            System.out.println("This is not a plant and does not contain any seeds");
        }
    case COWPEA:
        if (player.getInventory().getItems().contains(plant)) {
            player.getInventory().removeItem(plant);
            ThreadLocalRandom randomSeedGenerator = ThreadLocalRandom.current();
            int randomSeed = randomSeedGenerator.nextInt(minCowpeaSeedCount, maxCowpeaSeedCount);
            seedCount = seedCount + randomSeed;
            Item Seed = new Seed("CowpeaSeed", seedCount, 2.0, 0.20);
            player.getInventory().addItem(Seed);
        } else {
            System.out.println("This is not a plant and does not contain any seeds");

        }
    case MANGO:
        if(player.getInventory().getItems().contains(plant)){
            player.getInventory().removeItem(plant);
            seedCount = seedCount + CashewSeedCount;
            Item Seed = new Seed("MangoSeed", seedCount, 2.0, 0.50);
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

    public void setSeedCount(int seedCount) {
        this.seedCount = seedCount;
    }

}
