package worldofzuul;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import worldofzuul.util.Vector;
import worldofzuul.world.GameObject;

public class Player extends SpriteAnimation {

    private final Vector pos;
    private GameObject currentGameObject;
    private final Inventory inventory = new Inventory();
    private final DoubleProperty balance = new SimpleDoubleProperty();

    Player(){
        pos = new Vector();
    }


    public Inventory getInventory(){
        return inventory;
    }

    public Vector getPos(){
        return pos;
    }


    public double getBalance() {
        return balance.get();
    }
    @JsonIgnore
    public DoubleProperty balanceProperty() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance.set(balance);
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
