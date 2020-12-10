package worldofzuul.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import worldofzuul.world.Field;

public class Irrigator extends Item {

    public Irrigator(){}
    public Irrigator(String name) {
        super(name);
    }

    public Irrigator(String name, Double value, Double sellbackRate) {
        super(name, value, sellbackRate);
    }

    @JsonIgnore
    public void water(Field field) {
        field.addWater(deplete());
    }
}
