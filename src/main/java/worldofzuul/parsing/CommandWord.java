package worldofzuul.parsing;

public enum CommandWord
{

    SELECT("select"),
    ADDITEM(""),
    REMOVEITEM(""),
    INTERACT("interact"),
    GO("go"),
    QUIT("quit"),
    HELP("help"),
    EXAMINE("examine"),
    HARVEST("harvest"),
    MOVE("move"),
    TELEPORT(""),
    SHOW("show"),
//    LOCAL("local"),
//    GLOBAL("global"),
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
