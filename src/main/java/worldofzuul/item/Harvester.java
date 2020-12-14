package worldofzuul.item;

/**
 * The type Harvester.
 *
 * Used to harvest a {@link Plant}
 *
 */
public class Harvester extends Item {

    /**
     * Instantiates a new Harvester.
     */
    public Harvester() {
    }

    /**
     * Instantiates a new Harvester.
     *
     * @param name the name
     */
    public Harvester(String name) {
        super(name);
    }

    /**
     * Instantiates a new Harvester.
     *
     * @param harvester the harvester
     */
    public Harvester(Harvester harvester) {
        super(harvester);
    }

    /**
     * Instantiates a new Harvester.
     *
     * @param name         the name
     * @param value        the value
     * @param sellbackRate the sellback rate
     */
    public Harvester(String name, Double value, Double sellbackRate) {
        super(name, value, sellbackRate);
    }

    @Override
    public Item copyItem() {
        return new Harvester(this);
    }

    /**
     * Harvest plant.
     *
     * @param plant the plant
     * @return the plant
     */
    public Plant harvest(Plant plant) {
        return plant;
    }
}
