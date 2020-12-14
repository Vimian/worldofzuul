package worldofzuul;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import worldofzuul.util.Vector;
import worldofzuul.world.GameObject;

/**
 * The type Player.
 */
public class Player extends SpriteAnimation {

    /**
     * The Pos.
     *
     * The Position of the player.
     *
     */
    private final Vector pos;
    /**
     * The Inventory.
     */
    private final Inventory inventory = new Inventory();
    /**
     * The Balance.
     *
     * The amount of money the player has available.
     *
     */
    private final DoubleProperty balance = new SimpleDoubleProperty();
    /**
     * The Current game object.
     */
    private GameObject currentGameObject;

    /**
     * Instantiates a new Player.
     */
    Player() {
        pos = new Vector();
    }


    /**
     * Gets inventory.
     *
     * @return the inventory
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Gets pos.
     *
     * @return the pos
     */
    public Vector getPos() {
        return pos;
    }

    /**
     * Sets pos.
     *
     * @param pos the pos
     */
    public void setPos(Vector pos) {
        this.pos.setY(pos.getY());
        this.pos.setX(pos.getX());
    }

    /**
     * Gets balance.
     *
     * @return the balance
     */
    public double getBalance() {
        return balance.get();
    }

    /**
     * Sets balance.
     *
     * @param balance the balance
     */
    public void setBalance(double balance) {
        this.balance.set(balance);
    }

    /**
     * Balance property double property.
     *
     * @return the double property
     */
    @JsonIgnore
    public DoubleProperty balanceProperty() {
        return balance;
    }

    /**
     * Gets current game object.
     *
     * @return the current game object
     */
    @JsonIgnore
    public GameObject getCurrentGameObject() {
        return currentGameObject;
    }

    /**
     * Sets current game object.
     *
     * @param currentGameObject the current game object
     */
    public void setCurrentGameObject(GameObject currentGameObject) {
        this.currentGameObject = currentGameObject;
    }
}
