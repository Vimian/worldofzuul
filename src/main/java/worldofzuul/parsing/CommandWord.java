package worldofzuul.parsing;

import worldofzuul.Player;
import worldofzuul.item.Item;

/**
 * The enum Command word.
 */
public enum CommandWord {

    /**
     * Add item command word.
     *
     * Command used to add {@link Command#getItem()} to {@link Player#getInventory()}.
     *
     */
    ADD_ITEM(""),
    /**
     * Go command word.
     *
     * Command used to change room using the exit key defined by {@link Command#getSecondWord()}
     *
     */
    GO("go"),
    /**
     * Help command word.
     *
     * Displays help message.
     *
     */
    HELP("help"),
    /**
     * Interact command word.
     *
     * Used in order to interact with a {@link worldofzuul.world.GameObject} using {@link worldofzuul.world.GameObject#interact()} and {@link worldofzuul.world.GameObject#interact(Item)}
     *
     */
    INTERACT("interact"),
    /**
     * Move command word.
     *
     * Moves player in {@link worldofzuul.world.Direction} determined by {@link Command#getSecondWord()}.
     *
     */
    MOVE("move"),
    /**
     * Quit command word.
     */
    QUIT("quit"),
    /**
     * Remove item command word.
     *
     * Command used to remove {@link Command#getItem()} from {@link Player#getInventory()} or the selected item if an item is not associated with the Command.
     *
     */
    REMOVE_ITEM(""),
    /**
     * Select command word.
     */
    SELECT("select"),
    /**
     * Teleport command word.
     *
     * Move player to {@link worldofzuul.util.Vector} position.
     *
     */
    TELEPORT(""),
    /**
     * Unknown command word.
     */
    UNKNOWN("?");

    /**
     * The Command string.
     */
    private final String commandString;

    /**
     * Instantiates a new Command word.
     *
     * @param commandString the command string
     */
    CommandWord(String commandString) {
        this.commandString = commandString;
    }

    public String toString() {
        return commandString;
    }
}
