package worldofzuul.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import worldofzuul.world.Field;

public class Irrigator extends Item {

    public Irrigator() {
    }

    public Irrigator(String name) {
        super(name);
    }

    public Irrigator(Irrigator irrigator) {
        super(irrigator);
    }

    public Irrigator(String name, Double value, Double sellbackRate) {
        super(name, value, sellbackRate);
    }

    public Irrigator(String bucket, int capacity, int remaining, double value, double sellbackRate) {
        this(bucket, value, sellbackRate);
        this.setCapacity(capacity);
        this.setRemaining(remaining);

    }

    @Override
    public Item copyItem() {
        return new Irrigator(this);
    }

    @JsonIgnore
    public void water(Field field) {
        field.addWater(deplete());
    }
}
