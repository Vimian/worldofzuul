package worldofzuul.parsing;

import worldofzuul.util.MessageHelper;

import java.util.HashMap;
import java.util.Objects;


public class CommandWords {
    private final HashMap<String, CommandWord> validCommands;

    public CommandWords() {
        validCommands = new HashMap<>();
        for (CommandWord command : CommandWord.values()) {
            if (command != CommandWord.UNKNOWN) {
                validCommands.put(command.toString(), command);
            }
        }
    }

    public CommandWord getCommandWord(String commandWord) {
        CommandWord command = validCommands.get(commandWord);
        return Objects.requireNonNullElse(command, CommandWord.UNKNOWN);
    }

    public void showAll() {
        for (String command : validCommands.keySet()) {
            MessageHelper.Command.stringSpace(command);
        }
        MessageHelper.Command.line();
    }
}
