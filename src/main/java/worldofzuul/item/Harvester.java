package worldofzuul.item;

public class Harvester extends Item implements ISellable {
    public Harvester(){}
    public Harvester(String name) {
        super(name);
    }
    public Harvester(String name, Double value, Double sellbackRate ) {
        this(name);
        setValue(value);
        setSellBackRate(sellbackRate);
    }
    public Plant harvest(Plant plant){
        return plant;
    }
}
