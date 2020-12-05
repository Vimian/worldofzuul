package worldofzuul.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import worldofzuul.world.Field;

public class Irrigator extends Consumable {

    public Irrigator(){}
    public Irrigator(String name) {
        super(name);
    }

    public Irrigator(String name, Double value, Double sellbackRate) {
        super(name, value, sellbackRate);
    }

    public Irrigator(String name, float flowRate, float waterCapacity, Double value, Double sellbackRate) {
        super(name, value, sellbackRate, flowRate, waterCapacity, waterCapacity);
    }

    public Irrigator(String name, float flowRate, float waterCapacity) {
        super(name, flowRate, waterCapacity, waterCapacity);

    }
    @JsonIgnore
    public void water(Field field) {
        field.addWater(deplete());
    }
}
