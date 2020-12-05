package worldofzuul.item;

public class Watering extends Item {
    float Water;
    float maxWater;
    float minWater;

    public Watering(String name,float Water, float maxWater, float minWater, Double value, Double sellBackRate){
        super(name,value,sellBackRate);
        this.Water = Water;
        this. maxWater = maxWater;
        this. minWater = minWater;
    }
    public void addWater(float Water){
        Water++;
    }
    public void removeWater(float Water){
        Water--;
    }

}
