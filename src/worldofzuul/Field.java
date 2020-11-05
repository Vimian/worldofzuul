package worldofzuul;

public class Field extends GameObject {
    private Fertilizer fertilizer;
    private float water;
    private Plant plant;
    private Float nutrition;
    private Plant[] plants;

    public Field(Fertilizer fertilizer) {
        this.fertilizer = fertilizer;
    }

    public Field(Fertilizer fertilizer, float water) {
        this(fertilizer);
        this.water = water;
    }

    public Fertilizer getFertilizer() { return this.fertilizer; }

    public void setFertilizer(Fertilizer fertilizer) { this.fertilizer = fertilizer; }

    public float getWater() { return this.water; }

    public void setWater(float water) { this.water = water; }

    public Plant getPlant() { return this.plant; }

    public void setPlant(Plant plant) { this.plant = plant; }

    public void removePlant(Plant()){ plants.remove(Plant); }

    public float getNutrition() { return this.nutrition; }

    public void setNutrition(float nutrition) { this.nutrition = nutrition; }
}