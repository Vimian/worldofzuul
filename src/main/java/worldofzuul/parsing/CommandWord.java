package worldofzuul.parsing;

public enum CommandWord {

    ADD_ITEM(""), GO("go"), HELP("help"), INTERACT("interact"), MOVE("move"), QUIT("quit"), REMOVE_ITEM(""), SELECT("select"), TELEPORT(""), UNKNOWN("?");

    private final String commandString;

    CommandWord(String commandString) {
        this.commandString = commandString;
    }

    public String toString() {
        return commandString;
    }
}
