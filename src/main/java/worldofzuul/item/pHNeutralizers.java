package worldofzuul.item;

import worldofzuul.world.Field;

/**
 * The type P h neutralizers.
 *
 * Not used in game.
 * Used to adjust the pH of a {@link Field}.
 *
 */
public class pHNeutralizers extends Item {
    /**
     * The pH change.
     */
    private Double pHChange;

    /**
     * Instantiates a new P h neutralizers.
     */
    public pHNeutralizers() {
    }

    /**
     * Instantiates a new P h neutralizers.
     *
     * @param other the other
     */
    public pHNeutralizers(pHNeutralizers other) {
        super(other);
        this.pHChange = other.pHChange;
    }

    /**
     * Instantiates a new P h neutralizers.
     *
     * @param name         the name
     * @param value        the value
     * @param sellbackRate the sellback rate
     */
    public pHNeutralizers(String name, Double value, Double sellbackRate) {
        super(name, value, sellbackRate);
    }

    /**
     * Instantiates a new P h neutralizers.
     *
     * @param name         the name
     * @param value        the value
     * @param sellbackRate the sellback rate
     * @param pHChange     the p h change
     */
    public pHNeutralizers(String name, Double value, Double sellbackRate, Double pHChange) {
        super(name, value, sellbackRate);
        this.pHChange = pHChange;
    }

    @Override
    public Item copyItem() {
        return new pHNeutralizers(this);
    }

    /**
     * Gets h change.
     *
     * @return the h change
     */
    public Double getpHChange() {
        return pHChange;
    }
}
