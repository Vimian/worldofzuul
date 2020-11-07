package worldofzuul.parsing;

public enum CommandWord
{
    HARVEST(""),REMOVEITEM(""),INTERACT("interact"),MOVE("move"),TELEPORT(""),GO("go"), QUIT("quit"), HELP("help"), UNKNOWN("?");

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
