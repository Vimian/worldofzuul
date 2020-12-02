package worldofzuul.item;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Seed extends Item {
    private static final String nameDelimiter = " ";
    private int seedCount;


    public Seed(){

    }

    public Seed(String name, int seedCount) {
        super(name);
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

    @Override
    public String getName() {
        return super.getName() + nameDelimiter + this.getClass().getSimpleName().toLowerCase();
    }

    public void setSeedCount(int seedCount) {
        this.seedCount = seedCount;
    }

}
