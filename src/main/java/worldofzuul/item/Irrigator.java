package worldofzuul.item;

import worldofzuul.world.Field;

public class Irrigator extends Item implements IConsumable {

    public Irrigator(String name) {
        super(name);
    }

    public Irrigator(String name, float flowRate, float waterCapacity) {
        super(name);
        setConsumptionRate(flowRate);
        setCapacity(waterCapacity);
        refill();
    }

    public void water(Field field) {
        field.addWater(deplete());
    }

}
