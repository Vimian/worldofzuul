package worldofzuul.item;

public class Seed extends Item {
    public Seed(String name) {
        super(name);
    }
    public Plant getPlant(){
        return new Plant(getName());
    }
}
