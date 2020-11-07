package worldofzuul;

import worldofzuul.item.Fertilizer;
import worldofzuul.item.Harvester;
import worldofzuul.item.Item;
import worldofzuul.item.Seed;
import worldofzuul.parsing.Command;
import worldofzuul.parsing.CommandWord;
import worldofzuul.parsing.Parser;
import worldofzuul.util.Vector;
import worldofzuul.world.*;

import java.util.Arrays;

import static worldofzuul.util.Math.tryParse;

public class Game
{
    private Parser parser;
    private Room currentRoom;
    private Player player;


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
        player.inventory.addItem(new Harvester("Corn"));
        player.inventory.addItem(new Fertilizer("Corn", 3));
        Seed seed = new Seed("Corn", 3);
        player.inventory.setSelectedItem(seed);


        //DBG End




        currentRoom = outside;
    }

    public void play()
    {
        printWelcome();


        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    private boolean processCommand(Command command)
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        if(commandWord == CommandWord.UNKNOWN) {
            System.out.println("I don't know what you mean...");
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
        } else if(commandWord == CommandWord.INTERACT){
            interactPlayer();
        }
        return wantToQuit;
    }

    private void processCommandInternal(Command command)
    {

        CommandWord commandWord = command.getCommandWord();

        if(commandWord == CommandWord.UNKNOWN) {
            System.out.println("I don't know what you mean...");
            return;
        }

        if (commandWord == CommandWord.TELEPORT) {
            teleportPlayer(command);
        } else if (commandWord == CommandWord.REMOVEITEM) {
            removeItem(command);
        } else {
            processCommand(command);
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
        if(command.hasSecondWord()) {
            itemIndex = tryParse(command.getSecondWord(), 0);
        }
        player.inventory.removeItem(itemIndex);
    }

    private void printHelp()
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    private void goRoom(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("Go where?");
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

    private void movePlayer(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("Move where?");
            return;
        }

        String secondWord = command.getSecondWord();
        int x = player.pos.x;
        int y = player.pos.y;


        if(Direction.NORTH.name().toLowerCase().equals(secondWord)){
            y--;
        }
        else if (Direction.SOUTH.name().toLowerCase().equals(secondWord))
        {
            y++;
        }
        else if (Direction.EAST.name().toLowerCase().equals(secondWord))
        {
            x++;
        }
        else if (Direction.WEST.name().toLowerCase().equals(secondWord))
        {
            x--;
        } else {
            System.out.println("Where do you want to go?");
            return;
        }

        if(canPlayerMoveToPoint(x, y)){
            System.out.println("You walked " + secondWord + ".");
            setPlayerPosition(new Vector(x, y));
        }
    }
    private void teleportPlayer(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("Teleport where?");
            return;
        }

        String secondWord = command.getSecondWord();
        Vector pos = new Vector(secondWord);

        if(canPlayerMoveToPoint(pos.x, pos.y)){
            System.out.println("You were teleported to " + secondWord + ".");
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
            System.out.println("You can't walk there.");
            return false;
        }

        GameObject targetPosition = currentRoom.getGridGameObject(new Vector(x, y));
        if(targetPosition.colliding){
            System.out.println("You can't walk through that.");
        }

        return !targetPosition.colliding;
    }



    private boolean quit(Command command)
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;
        }
    }
}
