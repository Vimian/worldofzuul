package worldofzuul.item;

public class Harvester extends Item {

    public Harvester(){}
    public Harvester(String name) {
        super(name);
    }
    public Harvester(String name, Double value, Double sellbackRate ) {
        super(name, value, sellbackRate);
    }
    public Plant harvest(Plant plant) {
        return plant;
    }
}
