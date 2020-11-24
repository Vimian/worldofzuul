package worldofzuul;

import worldofzuul.util.Vector;

public class Player {

    private Vector pos;
    private Double balance;
    private Inventory inventory = new Inventory();
    private float Velocity;
    private Sprite sprite;

    Player(){
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

    public void setPos(Vector pos){
        this.pos.setY(pos.getY());
        this.pos.setX(pos.getX());
    }

}
