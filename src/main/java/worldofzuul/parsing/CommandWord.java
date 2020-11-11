package worldofzuul;

public enum CommandWord
{

    SELECT("select"),ADDITEM(""),REMOVEITEM(""),INTERACT("interact"),GO("go"), QUIT("quit"), HELP("help"), EXAMINE("examine"), HARVEST("harvest"), MOVE("move"),TELEPORT(""), UNKNOWN("?");

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
