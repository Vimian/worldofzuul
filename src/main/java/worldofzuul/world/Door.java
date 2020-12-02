package worldofzuul.world;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import worldofzuul.parsing.Command;
import worldofzuul.parsing.CommandWord;
import worldofzuul.util.Vector;

public class Door extends GameObject {
    private final StringProperty exit = new SimpleStringProperty();
    private Vector linkedLocation;

    public Door() {
        linkedLocation = new Vector();
    }


    public Door(String exit, Vector linkedLocation) {
        this();
        setExit(exit);
        this.linkedLocation.setX(linkedLocation.getX());
        this.linkedLocation.setY(linkedLocation.getY());
    }

    @Override
    public Command[] uponEntry(GameObject previousGameObject) {

        if(!(previousGameObject instanceof Door)){
            return getCommands();
        }

        return null;
    }

    public Vector getLinkedLocation() {
        return linkedLocation;
    }

    public void setLinkedLocation(Vector linkedLocation) {
        this.linkedLocation = linkedLocation;
    }


    public String getExit() {
        return exit.get();
    }

    public void setExit(String exit) {
        this.exit.set(exit);
    }

    public StringProperty exitProperty() {
        return exit;
    }

    private Command[] getCommands() {

        Command[] commands = new Command[2];
        commands[0] = new Command(CommandWord.GO, getExit());
        commands[1] = new Command(CommandWord.TELEPORT, linkedLocation.toString());

        return commands;
    }
}
