<<<<<<< HEAD
import java.util.Objects;

public abstract class Action {
    public float efficiency;
    public Sprite sprite;

    public Action(float efficiency, Sprite sprite){
        efficiency = this.efficiency;
        sprite = this.sprite;
    }
    public Objects[] getItems(){
        return items;
    }
}
