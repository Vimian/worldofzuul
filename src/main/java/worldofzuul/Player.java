package worldofzuul;

import worldofzuul.util.Vector;

public class Player extends SpriteAnimation {

    private Vector pos;

    private Inventory inventory = new Inventory();
    private float Velocity;

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
}
