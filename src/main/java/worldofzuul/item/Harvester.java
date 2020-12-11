package worldofzuul.item;

public class Harvester extends Item {

    public Harvester() {
    }

    public Harvester(String name) {
        super(name);
    }

    public Harvester(Harvester harvester) {
        super(harvester);
    }

    public Harvester(String name, Double value, Double sellbackRate) {
        super(name, value, sellbackRate);
    }

    @Override
    public Item copyItem() {
        return new Harvester(this);
    }

    public Plant harvest(Plant plant) {
        return plant;
    }
}
