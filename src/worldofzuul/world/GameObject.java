package worldofzuul.world;

import worldofzuul.parsing.Command;
import worldofzuul.util.MessageHelper;

public abstract class GameObject {
    public boolean colliding;

    public Command[] interact(){
        return null;
    }
    public Command[] interact(worldofzuul.item.Item item){
        MessageHelper.Item.cantUseItem(item.getName());
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
