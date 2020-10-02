package worldofzuul;

/**
 * The type Command.
 */
public class Command
{
    /**
     * The command word.
     */
    private CommandWord commandWord;
    /**
     * The command argument.
     */
    private String secondWord;

    /**
     * Instantiates a new Command.
     *
     * @param commandWord the command word
     * @param secondWord  the command argument
     */
    public Command(CommandWord commandWord, String secondWord)
    {
        this.commandWord = commandWord;
        this.secondWord = secondWord;
    }

    /**
     * Gets command word.
     *
     * @return command word
     */
    public CommandWord getCommandWord()
    {
        return commandWord;
    }

    /**
     * Gets command argument.
     *
     * @return the argument
     */
    public String getSecondWord()
    {
        return secondWord;
    }

    /**
     * Is command unknown.
     *
     * @return true if command is equal to the CommandWord.UNKNOWN enum
     */
    public boolean isUnknown()
    {
        return (commandWord == CommandWord.UNKNOWN);
    }

    /**
     * Has argument.
     *
     * @return true if argument is not null
     */
    public boolean hasSecondWord()
    {
        return (secondWord != null);
    }
}

