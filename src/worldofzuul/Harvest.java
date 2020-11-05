package worldofzuul;

import java.util.Objects;

public class Harvest extends Action {
    double harvestAmount;

    public Harvest(double harvestAmount){
        harvestAmount = this.harvestAmount;
    }
    @Override
    public Objects[] getItem(){
        return this.Item;
    }
    public double getHarvest(){
        return this.harvestAmount;
}