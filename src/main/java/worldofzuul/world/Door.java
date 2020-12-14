package worldofzuul.world;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import worldofzuul.parsing.Command;
import worldofzuul.parsing.CommandWord;
import worldofzuul.util.Vector;

/**
 * The type Door.
 *
 * Used teleport the player to a new room or position.
 *
 */
public class Door extends GameObject {
    /**
     * The Exit.
     *
     * The exit key that is to be used with the {@link CommandWord#GO} and which determines what room the player will move to.
     *
     */
    private final StringProperty exit = new SimpleStringProperty();
    /**
     * The Linked location.
     *
     * The location to teleport the player to after moving them to the correct room.
     *
     */
    private Vector linkedLocation;

    /**
     * Instantiates a new Door.
     */
    public Door() {
        linkedLocation = new Vector();
    }


    /**
     * Instantiates a new Door.
     *
     * @param exit           the exit
     * @param linkedLocation the linked location
     */
    public Door(String exit, Vector linkedLocation) {
        this();
        setExit(exit);
        this.linkedLocation.setX(linkedLocation.getX());
        this.linkedLocation.setY(linkedLocation.getY());
    }

    @Override
    public Command[] uponEntry(GameObject previousGameObject) {

        if (!(previousGameObject instanceof Door)) {
            return getCommands();
        }

        return null;
    }

    /**
     * Gets linked location.
     *
     * @return the linked location
     */
    public Vector getLinkedLocation() {
        return linkedLocation;
    }

    /**
     * Sets linked location.
     *
     * @param linkedLocation the linked location
     */
    public void setLinkedLocation(Vector linkedLocation) {
        this.linkedLocation = linkedLocation;
    }


    /**
     * Gets exit.
     *
     * @return the exit
     */
    public String getExit() {
        return exit.get();
    }

    /**
     * Sets exit.
     *
     * @param exit the exit
     */
    public void setExit(String exit) {
        this.exit.set(exit);
    }

    /**
     * Exit property string property.
     *
     * @return the string property
     */
    public StringProperty exitProperty() {
        return exit;
    }

    /**
     * Get commands to move player and teleport player.
     *
     * @return the command array
     */
    private Command[] getCommands() {

        Command[] commands = new Command[2];
        commands[0] = new Command(CommandWord.GO, getExit());
        commands[1] = new Command(CommandWord.TELEPORT, linkedLocation.toString());

        return commands;
    }
}
