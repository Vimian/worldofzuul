package worldofzuul;

import java.util.Objects;

public class Harvest extends Action {

    public Harvest(GameObjects[] items){
        items = this.items;
    }
    @Override
    public Objects[] getItem(){
        return this.items;
    }
}