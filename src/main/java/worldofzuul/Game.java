package worldofzuul;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import javafx.beans.property.ListProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import worldofzuul.item.*;
import worldofzuul.parsing.Command;
import worldofzuul.parsing.CommandWord;
import worldofzuul.parsing.Parser;
import worldofzuul.util.MessageHelper;
import worldofzuul.util.Vector;
import worldofzuul.world.*;

import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static worldofzuul.util.Math.tryParse;

public class Game {
    private final static int updateDelay = 60;
    private Parser parser;
    private Room currentRoom;
    private final ListProperty<Room> rooms = new SimpleListProperty<>(
            FXCollections.observableArrayList());
    private Player player;
    private ScheduledExecutorService scheduledThreadPool;

    public Game() {
        //createRooms();
        parser = new Parser();

    }

    public Player getPlayer() {
        return player;
    }

    @JsonIgnore
    public Room getRoom(){
        return currentRoom;
    }
    public void setRoom(Room room){
        currentRoom = room;
    }
    public void move(Direction direction) {
        processCommandInternal(new Command(CommandWord.MOVE, direction.toString()));
    }
    public void interact() {
        processCommandInternal(new Command(CommandWord.INTERACT, null));
    }


    public void reconfigureRooms(){
        for (Room room : rooms) {
            if (room.getExitStrings().size() <= 0) {
                continue;
            }

            MapProperty<String, Room> exits = new SimpleMapProperty<>(
                    FXCollections.observableHashMap()
            );

            for (Room.Exit exit : room.getExitStrings()) {
                Room exitRoom = findRoom(exit.getExitValue());
                exits.put(exit.getExitKey(), exitRoom);
            }

            if (exits.size() > 0) {
                room.setExits(exits);
            }
        }
        setRoom(getRooms().get(0));
    }

    private Room findRoom(String shortDescription){
        for (Room room : rooms) {
            if(room.getDescription().equals(shortDescription)){
                return room;
            }
        }

        return null;
    }

    public void createRooms() {
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

        outside.fillRoomGridWithBlocks(50, 50);
        theatre.fillRoomGridWithBlocks(50, 50);

        outside.setGridGameObject(new Door("east", new Vector()), new Vector(2, 3));
        outside.setGridGameObject(new Field(), new Vector(1, 2));

        player.getInventory().addItem(new Fertilizer("Manure", 3));
        player.getInventory().addItem(new Harvester("Sickle"));
        player.getInventory().addItem(new Irrigator("Hose"));
        player.getInventory().setSelectedItem(new Seed("Corn", 3));


        //DBG End

        Room[] roomsA = new Room[5];
        roomsA[0] = outside;
        roomsA[1] = theatre;
        roomsA[2] = pub;
        roomsA[3] = lab;
        roomsA[4] = office;

        setRooms(roomsA);


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

    public void update() {
        System.out.println("spam2");
        currentRoom.update().forEach(this::processCommandInternal);

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
        } else if (commandWord == CommandWord.EXAMINE){
            examineObject(command);
        }
        else if (commandWord == CommandWord.HARVEST){
            harvestObject(command);
        }
        return wantToQuit;
    }

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

    private void addItem(Command command) {
        if (command.hasItem()) {
            player.getInventory().addItem(command.getItem());
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


        if (Direction.NORTH.toString().equals(secondWord)) {
            y--;
        } else if (Direction.SOUTH.toString().equals(secondWord)) {
            y++;
        } else if (Direction.EAST.toString().equals(secondWord)) {
            x++;
        } else if (Direction.WEST.toString().equals(secondWord)) {
            x--;
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

        GameObject currentTile = player.getCurrentGameObject();
        player.setPos(position);
        GameObject newTile = currentRoom.getGridGameObject(position);
        player.setCurrentGameObject(newTile);

        if(currentTile != null){
            processCommandInternal(currentTile.uponExit());
        }

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
        if (targetPosition.isColliding()) {
            MessageHelper.Command.objectIsCollidable();
        }

        return !targetPosition.isColliding();
    }

    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            MessageHelper.Command.unknownArgumentWhere(CommandWord.QUIT.toString());
            return false;
        } else {
            return true;
        }
    }

    @JsonGetter
    public ObservableList<Room> getRooms() {
        return rooms.get();
    }

    @JsonSetter
    public void setRooms(Room[] rooms) {
        ObservableList<Room> temp = new SimpleListProperty<>(
                FXCollections.observableArrayList());

        temp.addAll(Arrays.asList(rooms));

        this.rooms.set(temp);
    }

    @JsonIgnore
    public void setRooms(ObservableList<Room> observableRooms) {
        this.rooms.set(observableRooms);
    }

    @JsonIgnore
    public ListProperty<Room> roomsProperty() {
        return rooms;
    }
}
