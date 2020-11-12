package worldofzuul.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import worldofzuul.world.Field;

public class Irrigator extends Item implements IConsumable {

    public Irrigator(){}
    public Irrigator(String name) {
        super(name);
    }

    public Irrigator(String name, float flowRate, float waterCapacity) {
        super(name);
        setConsumptionRate(flowRate);
        setCapacity(waterCapacity);
        refill();
    }
    @JsonIgnore
    public void water(Field field) {
        field.addWater(deplete());
    }

}
