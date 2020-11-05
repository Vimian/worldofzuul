package worldofzuul;

import worldofzuul.util.Vector;

public class Player {

    Player(){
        pos = new Vector();
    }
    public Vector pos;
    public Double balance;
    public Inventory inventory;
    public float Velocity;
    public Action[] actions;
    public Sprite sprite;

    public Double getBalance() {
        return balance;
    }
}
