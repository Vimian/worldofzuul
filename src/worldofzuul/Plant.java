package worldofzuul;

public class Plant extends Item {

    private GrowthStage state = GrowthStage.SEED;
    private float seedQuality;
    private float waterNeeded;
    private float nutritionNeeded;
    private Disease disease;
    private Pest pest;

    public Plant(String name) {
        super(name);
    }
    public Plant(String name, float seedQuality, float waterNeeded, float nutritionNeeded) {
        super(name);
        this.seedQuality = seedQuality;
        this.waterNeeded = waterNeeded;
        this.nutritionNeeded = nutritionNeeded;
    }

    public GrowthStage getState() {
        return state;
    }

    public Disease getDisease() {
        return disease;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }

    public Pest getPest() {
        return pest;
    }

    public void setPest(Pest pest) {
        this.pest = pest;
    }
}
