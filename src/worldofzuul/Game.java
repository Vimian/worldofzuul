package worldofzuul;

import worldofzuul.item.*;
import worldofzuul.parsing.Command;
import worldofzuul.parsing.CommandWord;
import worldofzuul.parsing.Parser;
import worldofzuul.util.MessageHelper;
import worldofzuul.util.Vector;
import worldofzuul.world.*;

import java.util.Arrays;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static worldofzuul.util.Math.tryParse;

public class Game
{
    private Parser parser;
    private Room currentRoom;
    private Player player;
    private ScheduledExecutorService scheduledThreadPool;
    private int updateDelay = 60;



    public Game()
    {
        createRooms();
        parser = new Parser();

    }


    private void createRooms()
    {
        Room outside, theatre, pub, lab, office;

        outside = new Room("outside the main entrance of the university");
        theatre = new Room("in a lecture theatre");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");

        outside.setExit("east", theatre);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theatre.setExit("west", outside);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);

        //DBG Start
        player = new Player();
        outside.setRoomGrid(new GameObject[10][10]);
        for (GameObject[] gameObjects : outside.getRoomGrid()) {
            Arrays.fill(gameObjects, new Block());
        }

        theatre.setRoomGrid(new GameObject[10][10]);
        for (GameObject[] gameObjects : theatre.getRoomGrid()) {
            Arrays.fill(gameObjects, new Block());
        }
        outside.setGridGameObject(new Door("east", new Vector()), new Vector(2, 3));
        outside.setGridGameObject(new Field(), new Vector(1, 2));
        player.inventory.addItem(new Fertilizer("Manure", 3));
        player.inventory.addItem(new Harvester("Sickle"));
        player.inventory.setSelectedItem(new Seed("Corn", 3));
        player.inventory.addItem(new Irrigator("Hose"));


        //DBG End




        currentRoom = outside;
    }

    public void play()
    {
        MessageHelper.Info.welcomeMessage(currentRoom.getLongDescription());

        enableGameUpdater();

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }

        scheduledThreadPool.shutdown();
        MessageHelper.Info.exitMessage();
    }

    private void enableGameUpdater(){
        scheduledThreadPool = Executors.newScheduledThreadPool(1);
        long delay = 1000000 / updateDelay;
        scheduledThreadPool.scheduleAtFixedRate(() -> update(), 0, delay, TimeUnit.MICROSECONDS);
    }

    private void update(){


        currentRoom.update().forEach(this::processCommandInternal);
    }


    private boolean processCommand(Command command)
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        if(commandWord == CommandWord.UNKNOWN) {
            MessageHelper.Command.unknownCommand();
            return false;
        }

        if (commandWord == CommandWord.HELP) {
            printHelp();
        }
        else if (commandWord == CommandWord.GO) {
            goRoom(command);
        }
        else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        }
        else if (commandWord == CommandWord.MOVE) {
            movePlayer(command);
        } else if (commandWord == CommandWord.SELECT) {
            selectItem(command);
        } else if(commandWord == CommandWord.INTERACT){
            interactPlayer();
        }
        return wantToQuit;
    }

    private void selectItem(Command command) {
        if(!command.hasSecondWord()) {
            MessageHelper.Command.unknownArgumentWhat(CommandWord.SELECT.toString());
            return;
        }

        int itemIndex = tryParse(command.getSecondWord(), 0);

        if(itemIndex != 0){

            Item item = player.inventory.getItem(itemIndex);
            if(item != null){
                MessageHelper.Command.selectedItem(item.getName());
                player.inventory.setSelectedItem(item);
            } else {
                MessageHelper.Command.invalidItemIndex();
            }

        } else {
            MessageHelper.Command.unknownAction();
        }


    }

    private void processCommandInternal(Command command)
    {

        CommandWord commandWord = command.getCommandWord();

        if(commandWord == CommandWord.UNKNOWN) {
            MessageHelper.Command.unknownCommand();
            return;
        }

        if (commandWord == CommandWord.TELEPORT) {
            teleportPlayer(command);
        } else if (commandWord == CommandWord.REMOVEITEM) {
            removeItem(command);
        } else if (commandWord == CommandWord.ADDITEM) {
            addItem(command);
        }
        else {
            processCommand(command);
        }

    }

    private void addItem(Command command) {
        if(command.hasItem()){
            player.inventory.addItem(command.getItem());
        }
    }

    private void processCommandInternal(Command[] commands)
    {
        if(commands != null && commands.length > 0){
            for (Command command : commands) {
                if(command != null){
                    processCommandInternal(command);
                }
            }
        }
    }

    private void interactPlayer() {

        Item item = player.inventory.getSelectedItem();

        Command[] commands;

        if(item == null){
            commands = currentRoom
                    .getGridGameObject(player.pos)
                    .interact();
        } else {
            commands = currentRoom
                    .getGridGameObject(player.pos)
                    .interact(item);
        }

        processCommandInternal(commands);
    }

    private void removeItem(Command command){
        int itemIndex = 0;
        if(command.hasItem()){
            player.inventory.removeItem(command.getItem());
            return;
        } else if (command.hasSecondWord()) {
            itemIndex = tryParse(command.getSecondWord(), 0);
        }

        player.inventory.removeItem(itemIndex);
    }

    private void printHelp()
    {
        MessageHelper.Info.helpCommands();
        parser.showCommands();
    }

    private void goRoom(Command command)
    {
        if(!command.hasSecondWord()) {
            MessageHelper.Command.unknownArgumentWhere(CommandWord.GO.toString());
            return;
        }

        String direction = command.getSecondWord();

        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            MessageHelper.Command.incorrectExit();
        }
        else {
            currentRoom = nextRoom;
            MessageHelper.Command.roomDescription(currentRoom.getLongDescription());
        }
    }

    private void movePlayer(Command command)
    {
        if(!command.hasSecondWord()) {
            MessageHelper.Command.unknownArgumentWhere(CommandWord.MOVE.toString());
            return;
        }

        String secondWord = command.getSecondWord();
        int x = player.pos.x;
        int y = player.pos.y;


        if(Direction.NORTH.toString().equals(secondWord)){
            y--;
        }
        else if (Direction.SOUTH.toString().equals(secondWord))
        {
            y++;
        }
        else if (Direction.EAST.toString().equals(secondWord))
        {
            x++;
        }
        else if (Direction.WEST.toString().equals(secondWord))
        {
            x--;
        } else {
            MessageHelper.Command.unknownCommand();
            return;
        }

        if(canPlayerMoveToPoint(x, y)){
            MessageHelper.Command.moveCommand(secondWord);
            setPlayerPosition(new Vector(x, y));
        }
    }
    private void teleportPlayer(Command command)
    {
        if(!command.hasSecondWord()) {
            MessageHelper.Command.unknownArgumentWhere(CommandWord.TELEPORT.name());
            return;
        }

        String secondWord = command.getSecondWord();
        Vector pos = new Vector(secondWord);

        if(canPlayerMoveToPoint(pos.x, pos.y)){
            MessageHelper.Command.teleported(pos);
            setPlayerPosition(pos);
        }
    }
    private void setPlayerPosition(Vector position){

        GameObject currentTile = currentRoom.getGridGameObject(player.pos);

        player.pos = position;
        GameObject newTile = currentRoom.getGridGameObject(position);

        processCommandInternal(currentTile.uponExit());
        processCommandInternal(newTile.uponEntry(currentTile));
        
    }

    private boolean canPlayerMoveToPoint(int x, int y){
        int roomDimensionsY = currentRoom.getRoomGrid().length;
        int roomDimensionsX = currentRoom.getRoomGrid()[0].length;

        //Player exceeds bounds of array
        if (x < 0 || y < 0 || x >= roomDimensionsX || y >= roomDimensionsY){
            MessageHelper.Command.positionExceedsMap();
            return false;
        }

        GameObject targetPosition = currentRoom.getGridGameObject(new Vector(x, y));
        if(targetPosition.colliding){
            MessageHelper.Command.objectIsCollidable();
        }

        return !targetPosition.colliding;
    }



    private boolean quit(Command command)
    {
        if(command.hasSecondWord()) {
            MessageHelper.Command.unknownArgumentWhere(CommandWord.QUIT.toString());
            return false;
        }
        else {
            return true;
        }
    }
}
