package worldofzuul.world;

import worldofzuul.parsing.Command;
import worldofzuul.parsing.CommandWord;
import worldofzuul.util.Vector;

public class Door extends GameObject {
    private String exit;
    private Vector linkedLocation;

    public Door() {
        linkedLocation = new Vector();
    }


    public Door(String exit, Vector linkedLocation) {
        this.exit = exit;
        this.linkedLocation = linkedLocation;
    }

    @Override
    public Command[] uponEntry(GameObject previousGameObject) {

        if(!(previousGameObject instanceof Door)){
            return getCommands();
        }

        return null;
    }

    public String getExit() {
        return exit;
    }

    public void setExit(String exit) {
        this.exit = exit;
    }

    public Vector getLinkedLocation() {
        return linkedLocation;
    }

    public void setLinkedLocation(Vector linkedLocation) {
        this.linkedLocation = linkedLocation;
    }

    private Command[] getCommands(){

        Command[] commands = new Command[2];
        commands[0] = new Command(CommandWord.GO, exit);
        commands[1] = new Command(CommandWord.TELEPORT, linkedLocation.toString());

        return commands;
    }
}
