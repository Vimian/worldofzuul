package worldofzuul;

import worldofzuul.util.Vector;

public class Player {

    Player(){
        pos = new Vector();
    }
    Player(int x, int y){
        pos = new Vector(x, y);
    }
    public Vector pos;
    public Double balance;
    public Inventory inventory = new Inventory();
    public float Velocity;
    public Sprite sprite;

    public Double getBalance() {
        return balance;
    }
}
