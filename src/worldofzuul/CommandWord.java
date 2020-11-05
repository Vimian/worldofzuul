package worldofzuul;

public enum CommandWord
{
<<<<<<< HEAD
<<<<<<< HEAD
    GO("go"), QUIT("quit"), HELP("help"), EXAMINE("examine"), HARVEST("harvest"), UNKNOWN("?");
    
=======
    MOVE("move"),GO("go"), QUIT("quit"), HELP("help"), UNKNOWN("?");

>>>>>>> d59468a4cfdfd56d6fed58147a3280684b87f80d
=======
    MOVE("move"),TELEPORT("teleport"),GO("go"), QUIT("quit"), HELP("help"), UNKNOWN("?");

>>>>>>> ea5fc609804d0b00c065def805d60cce402eece3
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
