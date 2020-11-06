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
    public Sprite sprite;

    public Double getBalance() {
        return balance;
    }
}
