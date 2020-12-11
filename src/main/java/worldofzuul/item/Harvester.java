package worldofzuul.item;

public class Harvester extends Item {

    public Harvester(){}
    public Harvester(String name) {
        super(name);
    }

    public Harvester(Harvester harvester) {
        super(harvester);
    }

    @Override
    public Item copyItem() {
        return new Harvester(this);
    }

    public Harvester(String name, Double value, Double sellbackRate ) {
        super(name, value, sellbackRate);
    }
    public Plant harvest(Plant plant) {
        return plant;
    }
}
