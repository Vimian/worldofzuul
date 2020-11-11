package worldofzuul;

import java.util.Objects;

public class Harvest extends Action {
    double harvestAmount;

    public Harvest(double harvestAmount) {
        harvestAmount = this.harvestAmount;
    }

    public double getHarvest() {
        return this.harvestAmount;
    }

    public void setHarvest() {
        this.harvestAmount = harvestAmount;
    }

    public Plant[] harvestPlantFromField(Field) {
        return Field.removePlant();
    }
}