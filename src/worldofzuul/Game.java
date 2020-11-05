package worldofzuul;


import java.awt.geom.Point2D;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

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
        }
        return wantToQuit;
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
            player.pos = new Vector(x, y);
        }
    }

    private boolean canPlayerMoveToPoint(int x, int y){
        int roomDimensionsY = currentRoom.getRoomGrid().length;
        int roomDimensionsX = currentRoom.getRoomGrid()[0].length;

        //Player exceeds bounds of array
        if (x < 0 || y < 0 || x >= roomDimensionsX || y >= roomDimensionsY){
            System.out.println("You can't walk there.");
            return false;
        }

        GameObject targetPosition = currentRoom.getRoomGrid()[y][x];
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
