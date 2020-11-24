package worldofzuul;

import worldofzuul.item.*;
import worldofzuul.parsing.Command;
import worldofzuul.parsing.CommandWord;
import worldofzuul.parsing.Parser;
import worldofzuul.util.MessageHelper;
import worldofzuul.util.Vector;
import worldofzuul.world.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static worldofzuul.util.Math.tryParse;

public class Game {
    private Parser parser;
    private Room currentRoom;
    private Player player;
    private ScheduledExecutorService scheduledThreadPool;
    private int updateDelay = 60;
    private Room[] roomArray;
    private String[][] globalMap;

    public Game() {
        createRooms();
        parser = new Parser();

    }

    public Player getPlayer(){
        return player;
    }
    public Room getRoom(){
        return currentRoom;
    }

    public void move(Direction direction) {
        processCommandInternal(new Command(CommandWord.MOVE, direction.toString()));
    }
    public void interact() {
        processCommandInternal(new Command(CommandWord.INTERACT, null));
    }

    private void createRooms() {
        Room outside, theatre, pub, lab, office;

        outside = new Room("outside the main entrance of the university", "outside");
        theatre = new Room("in a lecture theatre","theatre");
        pub = new Room("in the campus pub", "pub");
        lab = new Room("in a computing lab","lab");
        office = new Room("in the computing admin office","office");

        outside.setExit("east", theatre);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theatre.setExit("west", outside);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);

        roomArray = new Room[] {outside, theatre, pub, lab, office};

        //DBG Start
        player = new Player();
        outside.setRoomGrid(new GameObject[5][7]);
        for (GameObject[] gameObjects : outside.getRoomGrid()) {
            Arrays.fill(gameObjects, new Block());
        }

        theatre.setRoomGrid(new GameObject[9][3]);
        for (GameObject[] gameObjects : theatre.getRoomGrid()) {
            Arrays.fill(gameObjects, new Block());
        }
        //Dependency: If door is set outside room range, get index error
        outside.setGridGameObject(new Door("east", new Vector()), new Vector(2, 3));
        outside.setGridGameObject(new Field(), new Vector(1, 2));

        player.getInventory().addItem(new Fertilizer("Manure", 3));
        player.getInventory().addItem(new Harvester("Sickle"));
        player.getInventory().addItem(new Irrigator("Hose"));
        player.getInventory().setSelectedItem(new Seed("Corn", 3));


        //DBG End


        currentRoom = outside;
    }

    public void play() {
        MessageHelper.Info.welcomeMessage(currentRoom.getLongDescription());

        enableGameUpdater();

        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }

        scheduledThreadPool.shutdown();
        MessageHelper.Info.exitMessage();
    }

    private void enableGameUpdater() {
        scheduledThreadPool = Executors.newScheduledThreadPool(1);
        long delay = 1000000 / updateDelay;
        scheduledThreadPool.scheduleAtFixedRate(() -> update(), 0, delay, TimeUnit.MICROSECONDS);
    }

    private void update() {
        currentRoom.update().forEach(this::processCommandInternal);
        keyboardWalk();
    }

    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        if (commandWord == CommandWord.UNKNOWN) {
            MessageHelper.Command.unknownCommand();
            return false;
        }

        if (commandWord == CommandWord.HELP) {
            printHelp();
        } else if (commandWord == CommandWord.GO) {
            goRoom(command);
        } else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        } else if (commandWord == CommandWord.MOVE) {
            movePlayer(command);
        } else if (commandWord == CommandWord.SELECT) {
            selectItem(command);
        } else if (commandWord == CommandWord.INTERACT) {
            interactPlayer();
        } else if (commandWord == commandWord.EXAMINE){
            examineObject(command);
        } else if (commandWord == CommandWord.HARVEST){
            harvestObject(command);
        } else if (commandWord == CommandWord.SHOW){
            showPlayerRoom(command);
        }
        return wantToQuit;
    }

    //need to implement helper message on wrong 2'nd command
    //need to fix collision when moving into objects
    //DEPENDENCY: ROOMS MUST HAVE BEEN FILLED WITH STUFF, ELSE nullPointerExecption.
    //THEN make a dynamic world printer, meaning if you make dynamic world builder you can also show stuff.

    private void showPlayerRoom(Command command){
        if (!command.hasSecondWord()) {
            MessageHelper.Command.unknownArgumentWhat(CommandWord.SHOW.toString());
            return;
        }
        if("local".equals( command.getSecondWord() ) ){
            for(int y=0; y < currentRoom.getRoomGrid().length; y++){
                if(y>0){
                    System.out.println();
                }
                for(int x=0; x < currentRoom.getRoomGrid()[0].length; x++){
                    if((player.getPos().getX() == x ) && (player.getPos().getY() == y ) ){ //
                        System.out.print("P  ");
                    } else if (currentRoom.getRoomGrid()[y][x] instanceof Block) {
                        System.out.print("[] ");
                    } else if (currentRoom.getRoomGrid()[y][x] instanceof Field) {
                        System.out.print("F  ");
                    } else if (currentRoom.getRoomGrid()[y][x] instanceof Door) {
                        System.out.print("D  "); //showGameWorld();
                    }
                }
            }
        } else if("global".equals(command.getSecondWord())) {
//            showGlobal();
              showGameWorldStatic();

        }
        System.out.println(); //dont remove, makes room for userarrow
    }
    //navigate 2d arraylist
    //dependencies: crateRooms have been called. .GetShort description should be room symbol/name. Order of rooms is fixed.
    private void showGlobal(){
        int index = 0;
        ArrayList<String> gameWorld = new ArrayList<>(0);
        //for(Room rooms: roomArray){
        gameWorld.add(this.roomArray[index].getShortDescription());
        if(this.roomArray[index].getExit("west") != null){
            gameWorld.add(0,this.roomArray[index].getExit("west").getShortDescription()+" == "); //getExit("west) tager fat i nabo på west
        }
        if(this.roomArray[index].getExit("east") != null){
            gameWorld.add(gameWorld.size(), " == "+ this.roomArray[index].getExit("east").getShortDescription());
        }
        index++;
        //}
        System.out.println(gameWorld);
    }

    //mest dogshit implementation af globalt map. navne/strings skal redigeres manuelt ved ændring af nye rooms :)
    public void showGameWorldStatic(){
        String[] staticWorld ={"pub"," == ","outside", " == ", "theatre", "\r\n",
                "         ",   "||", "\r\n",
                "         ",  "lab", " == ", "office"};
        for(String string: staticWorld){
            System.out.print(string);
            if (currentRoom.getName() == string){
                System.out.print(" P");
            }
        }

    }

    public void keyboardWalk(){}



    public void unknownArgument(Command command){

    } //a lot of repeated code to handle unknown arguments, move it to this one

    private void selectItem(Command command) {
        if (!command.hasSecondWord()) {
            MessageHelper.Command.unknownArgumentWhat(CommandWord.SELECT.toString());
            return;
        }

        int itemIndex = tryParse(command.getSecondWord(), 0);

        if (itemIndex != 0) {

            Item item = player.getInventory().getItem(itemIndex);
            if (item != null) {
                MessageHelper.Command.selectedItem(item.getName());
                player.getInventory().setSelectedItem(item);
            } else {
                MessageHelper.Command.invalidItemIndex();
            }

        } else {
            MessageHelper.Command.unknownAction();
        }


    }

    private void processCommandInternal(Command command) {

        CommandWord commandWord = command.getCommandWord();

        if (commandWord == CommandWord.UNKNOWN) {
            MessageHelper.Command.unknownCommand();
            return;
        }

        if (commandWord == CommandWord.TELEPORT) {
            teleportPlayer(command);
        } else if (commandWord == CommandWord.REMOVEITEM) {
            removeItem(command);
        } else if (commandWord == CommandWord.ADDITEM) {
            addItem(command);
        } else {
            processCommand(command);
        }

    }

    private void processCommandInternal(Command[] commands) {
        if (commands != null && commands.length > 0) {
            for (Command command : commands) {
                if (command != null) {
                    processCommandInternal(command);
                }
            }
        }
    }

    private void addItem(Command command) {
        if (command.hasItem()) {
            player.getInventory().addItem(command.getItem());
        }
    }

    private void interactPlayer() {

        Item item = player.getInventory().getSelectedItem();

        Command[] commands;

        if (item == null) {
            commands = currentRoom
                    .getGridGameObject(player.getPos())
                    .interact();
        } else {
            commands = currentRoom
                    .getGridGameObject(player.getPos())
                    .interact(item);
        }

        processCommandInternal(commands);
    }

    private void removeItem(Command command) {
        int itemIndex = 0;
        if (command.hasItem()) {
            player.getInventory().removeItem(command.getItem());
            return;
        } else if (command.hasSecondWord()) {
            itemIndex = tryParse(command.getSecondWord(), 0);
        }

        player.getInventory().removeItem(itemIndex);
    }

    private void printHelp() {
        MessageHelper.Info.helpCommands();
        parser.showCommands();
    }

    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            MessageHelper.Command.unknownArgumentWhere(CommandWord.GO.toString());
            return;
        }

        String direction = command.getSecondWord();

        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    //Method for examining objects in room
    private void examineObject(Command command){ //TODO: Missing implementation
        if(!command.hasSecondWord()) {
            System.out.println("Examine what?");
            return;
        }
    }

    private void harvestObject(Command command) { //TODO: Missing implementation
        if (!command.hasSecondWord()) {
            System.out.println("Harvest what?");
        }
        //Merge conflict possibly redundant
        /*Field pfield = new Field(3,3);
        if (currentRoom.getGridGameObject(player.pos) ==  pfield) {
            Harvest pharvest = new Harvest(2);
                pharvest.harvestPlantFromField();
            } else {
                System.out.println("Can not harvest that!");
        }*/
    }

    private void movePlayer(Command command) {
        if (!command.hasSecondWord()) {
            MessageHelper.Command.unknownArgumentWhere(CommandWord.MOVE.toString());
            return;
        }

        String secondWord = command.getSecondWord();
        int x = player.getPos().getX();
        int y = player.getPos().getY();


        if (Direction.WEST.toString().equals(secondWord)) {
            x--;
        } else if (Direction.EAST.toString().equals(secondWord)) {
            x++;
        } else if (Direction.SOUTH.toString().equals(secondWord)) {
            y++;
        } else if (Direction.NORTH.toString().equals(secondWord)) {
            y--;
        } else {
            MessageHelper.Command.unknownCommand();
            return;
        }

        if (canPlayerMoveToPoint(x, y)) {
            MessageHelper.Command.moveCommand(secondWord);
            setPlayerPosition(new Vector(x, y));
        }
    }

    private void teleportPlayer(Command command) {
        if (!command.hasSecondWord()) {
            MessageHelper.Command.unknownArgumentWhere(CommandWord.TELEPORT.name());
            return;
        }

        String secondWord = command.getSecondWord();
        Vector pos = new Vector(secondWord);

        if (canPlayerMoveToPoint(pos.getX(), pos.getY())) {
            MessageHelper.Command.teleported(pos);
            setPlayerPosition(pos);
        }
    }

    private void setPlayerPosition(Vector position) {

        GameObject currentTile = currentRoom.getGridGameObject(player.getPos());

        player.setPos(position);
        GameObject newTile = currentRoom.getGridGameObject(position);

        processCommandInternal(currentTile.uponExit());
        processCommandInternal(newTile.uponEntry(currentTile));

    }

    private boolean canPlayerMoveToPoint(int x, int y) {
        int roomDimensionsY = currentRoom.getRoomGrid().length;
        int roomDimensionsX = currentRoom.getRoomGrid()[0].length;

        //Player exceeds bounds of array
        if (x < 0 || y < 0 || x >= roomDimensionsX || y >= roomDimensionsY) {
            MessageHelper.Command.positionExceedsMap();
            return false;
        }

        GameObject targetPosition = currentRoom.getGridGameObject(new Vector(x, y));
        if (targetPosition.colliding) {
            MessageHelper.Command.objectIsCollidable();
        }

        return !targetPosition.colliding;
    }


    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            MessageHelper.Command.unknownArgumentWhere(CommandWord.QUIT.toString());
            return false;
        } else {
            return true;
        }
    }


}
