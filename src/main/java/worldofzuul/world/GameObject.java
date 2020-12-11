package worldofzuul.world;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import worldofzuul.SpriteAnimation;
import worldofzuul.parsing.Command;
import worldofzuul.util.MessageHelper;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Block.class, name = "Block"),
        @JsonSubTypes.Type(value = Field.class, name = "Field"),
        @JsonSubTypes.Type(value = Door.class, name = "Door"),
        @JsonSubTypes.Type(value = NPC.class, name = "NPC")
})
public abstract class GameObject extends SpriteAnimation {
    private final BooleanProperty colliding = new SimpleBooleanProperty();

    public GameObject() {
    }

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

    public boolean isColliding() {
        return colliding.get();
    }

    public void setColliding(boolean colliding) {
        this.colliding.set(colliding);
    }

    public BooleanProperty collidingProperty() {
        return colliding;
    }
}
