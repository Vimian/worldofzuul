package worldofzuul.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import worldofzuul.world.Field;

public class Irrigator extends Item implements IConsumable {

    public Irrigator(String name, Double value, Double sellbackRate) {
        super(name,value,sellbackRate);
    }

    public Irrigator(String name, float flowRate, float waterCapacity, Double value, Double sellbackRate) {
        super(name,value,sellbackRate);
        setConsumptionRate(flowRate);
        setCapacity(waterCapacity);
        refill();
    }
    @JsonIgnore
    public void water(Field field) {
        field.addWater(deplete());
    }

}
