package worldofzuul.world;

import worldofzuul.parsing.Command;
import worldofzuul.parsing.CommandWord;
import worldofzuul.util.Vector;

public class Door extends GameObject {
    public String exit;
    public Vector linkedLocation;

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



    private Command[] getCommands(){

        Command[] commands = new Command[2];
        commands[0] = new Command(CommandWord.GO, exit);
        commands[1] = new Command(CommandWord.TELEPORT, linkedLocation.toString());

        return commands;
    }
}
