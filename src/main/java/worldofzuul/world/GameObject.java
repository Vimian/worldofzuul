package worldofzuul.world;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import worldofzuul.parsing.Command;
import worldofzuul.util.MessageHelper;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
public abstract class GameObject {
    public boolean colliding;

    public Command[] update() {
        return null;
    }

    public Command[] interact() {
        MessageHelper.Command.unknownAction();
        return null;
    }

    public Command[] interact(worldofzuul.item.Item item) {
        MessageHelper.Item.cantUseItem(item.getName());
        return null;
    }

    public Command[] uponEntry() {
        return null;
    }

    public Command[] uponEntry(GameObject previousGameObject) {
        return null;
    }

    public Command[] uponExit() {
        return null;
    }
}
