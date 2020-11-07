package worldofzuul.item;

public class Seed extends Item {
    private static final String nameDelimiter = " ";
    private int seedCount;

    public Seed(String name, int seedCount) {
        super(name);
        this.seedCount = seedCount;
    }
    public Plant getPlant(){
        seedCount--;
        return new Plant(getName());
    }
    public int getSeedCount() {
        return seedCount;
    }

    @Override
    public String getName() {
        return super.getName() + nameDelimiter + this.getClass().getSimpleName().toLowerCase();
    }
}
