package worldofzuul;

import worldofzuul.util.Vector;

public class Player {

    private Vector pos;
    private Double balance = 0.0;
    private Inventory inventory = new Inventory();
    private float Velocity;
    private Sprite sprite;

    public Player(){
        pos = new Vector();
    }
    Player(int x, int y){
        pos = new Vector(x, y);
    }

    public Double getBalance() {
        return balance;
    }

    public Inventory getInventory(){
        return inventory;
    }

    public Vector getPos(){
        return pos;
    }

    public void setBalance(Double balance){
        this.balance = balance;
    }

    public void setPos(Vector pos){
        this.pos.setY(pos.getY());
        this.pos.setX(pos.getX());
    }
}
