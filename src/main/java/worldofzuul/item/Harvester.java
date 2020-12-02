package worldofzuul.item;

public class Harvester extends Item {
    public Harvester(String name) {
        super(name);
    }
    public Plant harvest(Plant plant){
        return plant;
    }
}
