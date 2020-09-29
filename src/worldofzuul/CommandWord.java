package worldofzuul;

/**
 * CommandWords and their respective string keys
 */
public enum CommandWord
{
    /**
     * Moves to selected room.
     */
    GO("go"),
    /**
     * Quits the game session.
     */
    QUIT("quit"),
    /**
     * Prints help message.
     */
    HELP("help"),
    /**
     * Unknown command word.
     */
    UNKNOWN("?");
    
    private String commandString;
    
    CommandWord(String commandString)
    {
        this.commandString = commandString;
    }
    
    public String toString()
    {
        return commandString;
    }
}
