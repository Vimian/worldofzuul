package worldofzuul;
import java.util.HashMap;


/**
 * The type Command words.
 */
public class CommandWords
{
    /**
     * The valid commands and their string keys.
     */
    private HashMap<String, CommandWord> validCommands;

    /**
     * Instantiates all commands specified in the CommandWord enum
     */
    public CommandWords()
    {
        validCommands = new HashMap<String, CommandWord>();
        for(CommandWord command : CommandWord.values()) {
            if(command != CommandWord.UNKNOWN) {
                validCommands.put(command.toString(), command);
            }
        }
    }

    /**
     * Gets command word.
     *
     * @param commandWord the string key of a command
     * @return respective command type
     */
    public CommandWord getCommandWord(String commandWord)
    {
        CommandWord command = validCommands.get(commandWord);
        if(command != null) {
            return command;
        }
        else {
            return CommandWord.UNKNOWN;
        }
    }

    /**
     * Is string key a command
     *
     * @param aString command string key
     * @return true if validCommands HashMap contains string key
     */
    public boolean isCommand(String aString)
    {
        return validCommands.containsKey(aString);
    }

    /**
     * Prints all commands.
     */
    public void showAll()
    {
        for(String command : validCommands.keySet()) {
            System.out.print(command + "  ");
        }
        System.out.println();
    }
}
