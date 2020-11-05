package worldofzuul;

public enum CommandWord
{

    GO("go"), QUIT("quit"), HELP("help"), EXAMINE("examine"), HARVEST("harvest"), UNKNOWN("?");

    MOVE("move"),GO("go"), QUIT("quit"), HELP("help"), UNKNOWN("?");

    MOVE("move"),TELEPORT("teleport"),GO("go"), QUIT("quit"), HELP("help"), UNKNOWN("?");

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
