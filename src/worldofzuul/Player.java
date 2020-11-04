package worldofzuul;

import java.awt.geom.Point2D;

public class Player {

    Player(){
        pos = new Point2D.Double();
    }
    public Point2D pos;
    public Double balance;
    public Inventory inventory;
    public float Velocity;
    public Action[] actions;
    public Sprite sprite;

    public Double getBalance() {
        return balance;
    }
}
