package worldofzuul;

import java.util.Objects;

public class Harvest extends Action {
    double harvestAmount;

    public Harvest(double harvestAmount){
        harvestAmount = this.harvestAmount;
    }
    public double getHarvest(){
        return this.harvestAmount;
    }
    public void setHarvest(){
        return this.harvestAmount = harvestAmount}
}
    public Field[] harvestPlantFromField(){
        System.out.println(Field.removePlant());;
        return plants;
    }