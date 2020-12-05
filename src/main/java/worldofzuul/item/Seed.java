package worldofzuul.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import worldofzuul.Game;
import worldofzuul.Player;
import worldofzuul.Inventory;
import java.util.concurrent.ThreadLocalRandom;
import worldofzuul.item.SeedType.*;
import worldofzuul.item.crops.*;

public class Seed extends Item {
    private static final String nameDelimiter = " ";
    private int seedCount;
    private int maxSeedCount = 20;
    private int minSeedCount = 0;

    public Seed(String name, int seedCount, Double value, Double sellbackRate) {
        super(name,value,sellbackRate);
        this.seedCount = seedCount;
    }

    @JsonIgnore
    public Plant getPlant(){
        seedCount--;
        return new Plant(super.getName(),super.getValue(),super.getSellbackRate());
    }
    public int getSeedCount() {
        return seedCount;
    }

    public void getSeedsFromPlant(Plant plant, Player player){

        if (player.getInventory().doesContain(plant)) {
            player.getInventory().removeItem(plant);
            ThreadLocalRandom randomSeedGenerator = ThreadLocalRandom.current();
            int randomSeed = randomSeedGenerator.nextInt(minSeedCount, maxSeedCount);
            seedCount = seedCount + randomSeed;
            Item Seed = new Seed("Seed",seedCount,2.0,0.20);
            player.getInventory().addItem(Seed);
        }
        else {
            System.out.println("This is not a plant and does not contain any seeds");

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
