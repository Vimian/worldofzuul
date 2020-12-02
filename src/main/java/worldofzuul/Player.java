package worldofzuul;

import com.fasterxml.jackson.annotation.JsonIgnore;
import worldofzuul.util.Vector;
import worldofzuul.world.GameObject;

public class Player extends SpriteAnimation {

    private final Vector pos;
    private GameObject currentGameObject;
    private final Inventory inventory = new Inventory();

    Player(){
        pos = new Vector();
    }
    Player(int x, int y){
        pos = new Vector(x, y);
    }

    public Inventory getInventory(){
        return inventory;
    }

    public Vector getPos(){
        return pos;
    }

    public void setPos(Vector pos){
        this.pos.setY(pos.getY());
        this.pos.setX(pos.getX());
    }

    @JsonIgnore
    public GameObject getCurrentGameObject() {
        return currentGameObject;
    }

    public void setCurrentGameObject(GameObject currentGameObject) {
        this.currentGameObject = currentGameObject;
    }
}
