package worldofzuul.item;


/**
 * The type Fertilizer.
 *
 * Item used to add nutrition to a {@link worldofzuul.world.Field}.
 *
 */
public class Fertilizer extends Item {


    /**
     * Instantiates a new Fertilizer.
     */
    public Fertilizer() {
    }

    /**
     * Instantiates a new Fertilizer.
     *
     * @param fertilizer the fertilizer
     */
    public Fertilizer(Fertilizer fertilizer) {
        super(fertilizer);
    }

    /**
     * Instantiates a new Fertilizer.
     *
     * @param name   the name
     * @param amount the amount
     */
    public Fertilizer(String name, float amount) {
        super(name);
        this.setRemaining(amount);
    }

    /**
     * Instantiates a new Fertilizer.
     *
     * @param name         the name
     * @param amount       the amount
     * @param value        the value
     * @param sellbackRate the sellback rate
     */
    public Fertilizer(String name, float amount, Double value, Double sellbackRate) {
        super(name, value, sellbackRate);
    }

    @Override
    public Item copyItem() {
        return new Fertilizer(this);
    }


}
