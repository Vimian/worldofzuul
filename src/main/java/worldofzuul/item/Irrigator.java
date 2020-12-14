package worldofzuul.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import sdu.student.FXMLController;
import worldofzuul.world.Field;

/**
 * The type Irrigator.
 *
 * Item used to add water to a {@link Field}.
 *
 */
public class Irrigator extends Item {

    /**
     * Instantiates a new Irrigator.
     */
    public Irrigator() {
    }

    /**
     * Instantiates a new Irrigator.
     *
     * @param name the name
     */
    public Irrigator(String name) {
        super(name);
    }

    /**
     * Instantiates a new Irrigator.
     *
     * @param irrigator the irrigator
     */
    public Irrigator(Irrigator irrigator) {
        super(irrigator);
    }

    /**
     * Instantiates a new Irrigator.
     *
     * @param name         the name
     * @param value        the value
     * @param sellbackRate the sellback rate
     */
    public Irrigator(String name, Double value, Double sellbackRate) {
        super(name, value, sellbackRate);
    }

    /**
     * Instantiates a new Irrigator.
     *
     * @param bucket       the bucket
     * @param capacity     the capacity
     * @param remaining    the remaining
     * @param value        the value
     * @param sellbackRate the sellback rate
     */
    public Irrigator(String bucket, int capacity, int remaining, double value, double sellbackRate) {
        this(bucket, value, sellbackRate);
        this.setCapacity(capacity);
        this.setRemaining(remaining);

    }

    @Override
    public Item copyItem() {
        return new Irrigator(this);
    }

    /**
     * Adds water to parameter using {@link Field#addWater(float)} with amount specified in {@link Irrigator#deplete()}
     *
     * @param field the field
     */
    @JsonIgnore
    public void water(Field field) {
        field.addWater(deplete());
    }
}
