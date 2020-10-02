package worldofzuul;

/**
 * CommandWords and their respective string keys.
 */
public enum CommandWord
{
    /**
     * All commands and their string key representations.
     */
    GO("go"),
    QUIT("quit"),
    HELP("help"),
    UNKNOWN("?");

    /**
     * The Command string key.
     */
    private String commandString;

    /**
     * Instantiates a new Command.
     *
     * @param commandString the command key string
     */
    CommandWord(String commandString)
    {
        this.commandString = commandString;
    }
    
    public String toString()
    {
        return commandString;
    }
}
