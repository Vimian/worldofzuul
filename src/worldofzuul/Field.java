package worldofzuul;

public class Field extends GameObject {
    private float fertilization;

    public Field(float fertilization) {
        this.fertilization = fertilization;
    }

    public float getFertilization() {
        return this.fertilization;
    }

    public float setFertilization() {
        return this.fertilization;
    }
}
