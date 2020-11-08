package worldofzuul.item;

public class Irrigator extends Item {
    private float flowRate = 5;
    private float water = 1000;
    private float waterCapacity;

    public Irrigator(String name) {
        super(name);
    }
    public Irrigator(String name, float flowRate, float waterCapacity) {
        super(name);
        this.flowRate = flowRate;
        this.waterCapacity = this.water = waterCapacity;

    }

    public void water(Plant plant){
        plant.addWater(getWaterFlow());
    }

    public float getWater() {
        return water;
    }
    public void refill(){
        water = waterCapacity;
    }

    private float getWaterFlow(){
        if(water > flowRate){
             return water =- flowRate;
        }

        return 0;
    }


}
