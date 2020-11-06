package worldofzuul;

public enum CommandWord
{
    INTERACT("interact"),MOVE("move"),TELEPORT("teleport"),GO("go"), QUIT("quit"), HELP("help"), UNKNOWN("?");

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
