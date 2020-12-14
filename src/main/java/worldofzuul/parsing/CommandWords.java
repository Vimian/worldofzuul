package worldofzuul.parsing;

import worldofzuul.util.MessageHelper;

import java.util.HashMap;
import java.util.Objects;


/**
 * The type Command words.
 */
public class CommandWords {
    /**
     * The Valid commands.
     */
    private final HashMap<String, CommandWord> validCommands;

    /**
     * Instantiates a new Command words.
     */
    public CommandWords() {
        validCommands = new HashMap<>();
        for (CommandWord command : CommandWord.values()) {
            if (command != CommandWord.UNKNOWN) {
                validCommands.put(command.toString(), command);
            }
        }
    }

    /**
     * Gets command word.
     *
     * @param commandWord the command word
     * @return the command word
     */
    public CommandWord getCommandWord(String commandWord) {
        CommandWord command = validCommands.get(commandWord);
        return Objects.requireNonNullElse(command, CommandWord.UNKNOWN);
    }

    /**
     * Show all.
     */
    public void showAll() {
        for (String command : validCommands.keySet()) {
            MessageHelper.Command.stringSpace(command);
        }
        MessageHelper.Command.line();
    }
}
