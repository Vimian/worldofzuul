package worldofzuul;

public class Door extends GameObject {
    public String exit;
    public Vector linkedLocation;

    public Door(String exit, Vector linkedLocation) {
        this.exit = exit;
        this.linkedLocation = linkedLocation;
    }

    @Override
    public Command[] uponEntry(GameObject previousGameObject) {

        if(!Door.class.isAssignableFrom(previousGameObject.getClass())){
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
