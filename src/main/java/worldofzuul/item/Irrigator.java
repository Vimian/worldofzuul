package worldofzuul.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import worldofzuul.world.Field;

public class Irrigator extends Item implements IConsumable, ISellable {

    public Irrigator(){}
    public Irrigator(String name) {
        super(name);
    }

    public Irrigator(String name, Double value, Double sellbackRate) {
        this(name);
        setValue(value);
        setSellBackRate(sellbackRate);
    }

    public Irrigator(String name, float flowRate, float waterCapacity, Double value, Double sellbackRate) {
        this(name, value, sellbackRate);
        setConsumptionRate(flowRate);
        setCapacity(waterCapacity);
        refill();
    }

    public Irrigator(String name, float flowRate, float waterCapacity) {
        this(name);
        setConsumptionRate(flowRate);
        setCapacity(waterCapacity);
        refill();
    }
    @JsonIgnore
    public void water(Field field) {
        field.addWater(deplete());
    }

}
