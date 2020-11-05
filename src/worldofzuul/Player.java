package worldofzuul;

<<<<<<< HEAD
=======
import worldofzuul.util.Vector;

>>>>>>> ea5fc609804d0b00c065def805d60cce402eece3
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
