package worldofzuul;
import java.util.HashMap;


/**
 * The type Command words.
 */
public class CommandWords
{
    private HashMap<String, CommandWord> validCommands;

    /**
     * Instantiates a new Command words.
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
     * Is string a command
     *
     * @param aString command string key
     * @return the boolean
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
