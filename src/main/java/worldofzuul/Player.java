package worldofzuul;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import worldofzuul.util.Vector;
import worldofzuul.world.GameObject;

public class Player extends SpriteAnimation {

    private final Vector pos;
    private final Inventory inventory = new Inventory();
    private final DoubleProperty balance = new SimpleDoubleProperty();
    private GameObject currentGameObject;

    Player() {
        pos = new Vector();
    }


    public Inventory getInventory() {
        return inventory;
    }

    public Vector getPos() {
        return pos;
    }

    public void setPos(Vector pos) {
        this.pos.setY(pos.getY());
        this.pos.setX(pos.getX());
    }

    public double getBalance() {
        return balance.get();
    }

    public void setBalance(double balance) {
        this.balance.set(balance);
    }

    @JsonIgnore
    public DoubleProperty balanceProperty() {
        return balance;
    }

    @JsonIgnore
    public GameObject getCurrentGameObject() {
        return currentGameObject;
    }

    public void setCurrentGameObject(GameObject currentGameObject) {
        this.currentGameObject = currentGameObject;
    }
}
