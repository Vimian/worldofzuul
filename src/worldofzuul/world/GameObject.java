package worldofzuul.world;

import worldofzuul.parsing.Command;
import worldofzuul.item.Item;

public abstract class GameObject {
    public boolean colliding;

    public Command[] interact(){
        return null;
    }
    public Command[] interact(Item item){
        System.out.println(item.getName() + " can't be used here.");
        return null;
    }
    public Command[] uponEntry(){
        return null;
    }
    public Command[] uponEntry(GameObject previousGameObject) {
        return null;
    }

    public Command[] uponExit(){
        return null;
    }
}
