/**
 * This class is part of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game.
 * <p>
 * This class holds information about a command that was issued by the user.
 * A command currently consists of two parts: a CommandWord and a string
 * (for example, if the command was "take map", then the two parts
 * are TAKE and "map").
 * <p>
 * The way this is used is: Commands are already checked for being valid
 * command words. If the user entered an invalid command (a word that is not
 * known) then the CommandWord is UNKNOWN.
 * <p>
 * If the command had only one word, then the second word is <null>.
 *
 * @author Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */

package worldofzuul.parsing;

import worldofzuul.item.Item;

public class Command {
    private final CommandWord commandWord;
    private String secondWord;
    private Item item;

    public Command(CommandWord commandWord) {
        this.commandWord = commandWord;
    }

    public Command(CommandWord commandWord, String secondWord) {
        this(commandWord);
        this.secondWord = secondWord;
    }

    public Command(CommandWord commandWord, String secondWord, Item item) {
        this(commandWord, secondWord);
        this.item = item;
    }

    public Command(CommandWord commandWord, Item item) {
        this(commandWord);
        this.item = item;
    }


    public CommandWord getCommandWord() {
        return commandWord;
    }

    public String getSecondWord() {
        return secondWord;
    }

    public Item getItem() {
        return item;
    }

    public boolean hasSecondWord() {
        return (secondWord != null);
    }

    public boolean hasItem() {
        return (item != null);
    }


}

