package worldofzuul;

public enum CommandWord
{

    GO("go"), QUIT("quit"), HELP("help"), EXAMINE("examine"), HARVEST("harvest"), MOVE("move"),TELEPORT("teleport"), UNKNOWN("?");

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
