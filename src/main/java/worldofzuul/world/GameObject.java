package worldofzuul.world;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import worldofzuul.SpriteAnimation;
import worldofzuul.parsing.Command;
import worldofzuul.util.MessageHelper;

/**
 * The type Game object.
 *
 * Represents tiles in a {@link Room}
 *
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Block.class, name = "Block"),
        @JsonSubTypes.Type(value = Field.class, name = "Field"),
        @JsonSubTypes.Type(value = Door.class, name = "Door"),
        @JsonSubTypes.Type(value = NPC.class, name = "NPC")
})
public abstract class GameObject extends SpriteAnimation {
    /**
     * The Colliding.
     *
     * Determines whether the player can pass through the GameObject.
     *
     */
    private final BooleanProperty colliding = new SimpleBooleanProperty();

    /**
     * Instantiates a new Game object.
     */
    public GameObject() {
    }

    /**
     * Update GameObject.
     *
     * @return relevant commands
     */
    public Command[] update() {
        return null;
    }

    /**
     * Player interaction with GameObject.
     *
     * Is to overridden by subclasses if logic is necessary.
     *
     * @return relevant commands
     */
    public Command[] interact() {
        MessageHelper.Command.unknownAction();
        return null;
    }

    /**
     * Player interaction with GameObject using an item.
     *
     * Is to overridden by subclasses if logic is necessary.
     *
     * @param item the item
     * @return relevant commands
     */
    public Command[] interact(worldofzuul.item.Item item) {
        MessageHelper.Item.cantUseItem(item.getName());
        return null;
    }

    /**
     * Upon entering a GameObject.
     *
     * Not used.
     *
     * @return the command [ ]
     */
    public Command[] uponEntry() {
        return null;
    }

    /**
     * Upon player entry of GameObject.
     *
     * @param previousGameObject the previous game object the player was in
     * @return relevant commands
     */
    public Command[] uponEntry(GameObject previousGameObject) {
        return null;
    }

    /**
     * Upon player exit of GameObject.
     *
     * Not used.
     *
     * @return relevant commands
     */
    public Command[] uponExit() {
        return null;
    }

    /**
     * Is colliding boolean.
     *
     * @return the boolean
     */
    public boolean isColliding() {
        return colliding.get();
    }

    /**
     * Sets colliding.
     *
     * @param colliding the colliding
     */
    public void setColliding(boolean colliding) {
        this.colliding.set(colliding);
    }

    /**
     * Colliding property boolean property.
     *
     * @return the boolean property
     */
    public BooleanProperty collidingProperty() {
        return colliding;
    }
}
