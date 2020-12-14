package worldofzuul;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import javafx.beans.property.*;
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

/**
 * The type Game.
 */
public class Game {
    /**
     * The constant updateDelay which determines the amount of times per second to update the game.
     *
     * Redundant.
     *
     */
    private final static int updateDelay = 60;
    /**
     * The Rooms.
     *
     * List of all rooms
     *
     */
    private final ListProperty<Room> rooms = new SimpleListProperty<>(
            FXCollections.observableArrayList());
    /**
     * The Parser.
     */
    private final Parser parser;
    /**
     * The Current room.
     *
     * The room that the player is currently in.
     *
     */
    private final Property<Room> currentRoom = new SimpleObjectProperty<>();
    /**
     * The Player.
     */
    private Player player;
    /**
     * The Scheduled thread pool which manages the game update thread.
     *
     * Redundant.
     *
     */
    private ScheduledExecutorService scheduledThreadPool;
    /**
     * The Market.
     */
    private Market market = new Market();

    /**
     * Instantiates a new Game.
     */
    public Game() {
        parser = new Parser();

    }

    /**
     * Gets player.
     *
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets room.
     *
     * @return the room
     */
    @JsonIgnore
    public Room getRoom() {
        return currentRoom.getValue();
    }

    /**
     * Sets room.
     *
     * @param room the room
     */
    @JsonIgnore
    public void setRoom(Room room) {
        currentRoom.setValue(room);
    }

    /**
     * Room property property.
     *
     * @return the property
     */
    @JsonIgnore
    public Property<Room> roomProperty() {
        return currentRoom;
    }


    /**
     * Gets market.
     *
     * @return the market
     */
    public Market getMarket() {
        return market;
    }

    /**
     * Sets market.
     *
     * @param market the market
     */
    public void setMarket(Market market) {
        this.market = market;
    }

    /**
     * Move.
     *
     * @param direction the direction
     */
    public void move(Direction direction) {
        processCommandInternal(new Command(CommandWord.MOVE, direction.toString()));
    }

    /**
     * Interact.
     *
     * Interacts with the GameObject at the given position of the current rooms {@link Room#getRoomGrid()}.
     * If param useItem is true then the player will interact upon the GameObject using the players {@link Inventory#getSelectedItem()}.
     *
     * @param gameObjectPos the game object pos
     * @param useItem       the use item
     */
    public void interact(Vector gameObjectPos, boolean useItem) {
        Command[] commands;
        if (useItem) {
            if (player.getInventory().getSelectedItem() == null) {
                interact(gameObjectPos, false);
            }

            commands = getRoom()
                    .getGridGameObject(gameObjectPos)
                    .interact(player.getInventory().getSelectedItem());
        } else {
            commands = getRoom()
                    .getGridGameObject(gameObjectPos)
                    .interact();
        }

        processCommandInternal(commands);
    }


    /**
     * Reconfigure rooms.
     *
     * Reconnects the rooms {@link Room#exitsProperty()} after serialization using {@link Room#exitStringsProperty()}.
     *
     */
    public void reconfigureRooms() {
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

    /**
     * Find room using {@link Room#getShortDescription()}.
     *
     * @param shortDescription the short description
     * @return the room
     */
    private Room findRoom(String shortDescription) {
        for (Room room : rooms) {
            if (room.getDescription().equals(shortDescription)) {
                return room;
            }
        }

        return null;
    }

    /**
     * Create rooms.
     *
     * Redundant.
     *
     */
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

        player = new Player();

        outside.fillRoomGridWithBlocks(50, 50);
        theatre.fillRoomGridWithBlocks(50, 50);

        outside.setGridGameObject(new Door("east", new Vector()), new Vector(2, 3));
        outside.setGridGameObject(new Field(), new Vector(1, 2));

        player.getInventory().addItem(new Fertilizer("Manure", 3));
        player.getInventory().addItem(new Harvester("Sickle"));
        player.getInventory().addItem(new Irrigator("Hose"));
        player.getInventory().setSelectedItem(new Seed("Corn", 3));

        Room[] roomsA = new Room[5];
        roomsA[0] = outside;
        roomsA[1] = theatre;
        roomsA[2] = pub;
        roomsA[3] = lab;
        roomsA[4] = office;

        setRooms(roomsA);


        setRoom(outside);
    }

    /**
     * Parse command line interface input as {@link Command}.
     *
     * Redundant.
     *
     */
    public void play() {
        MessageHelper.Info.welcomeMessage(getRoom().getLongDescription());

        enableGameUpdater();

        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }

        scheduledThreadPool.shutdown();
        MessageHelper.Info.exitMessage();
    }

    /**
     * Enable game updater.
     *
     * Redundant.
     *
     */
    private void enableGameUpdater() {
        scheduledThreadPool = Executors.newScheduledThreadPool(1);
        long delay = 1000000 / updateDelay;
        scheduledThreadPool.scheduleAtFixedRate(this::update, 0, delay, TimeUnit.MICROSECONDS);
    }

    /**
     * Update all rooms.
     *
     * Calls the {@link Room#update()} in all rooms that exist in {@link Game#rooms} processes the commands using {@link Game#processCommandInternal(Command[])}
     *
     */
    public void update() {
        rooms.forEach(room -> room.update().forEach(this::processCommandInternal));
    }


    /**
     * Process command boolean.
     *
     * @param command the command
     * @return the boolean
     */
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
        }
        return wantToQuit;
    }

    /**
     * Select item.
     *
     * Processes the {@link CommandWord#SELECT} command.
     * If {@link Command#getSecondWord()} is not null and is a valid integer, the players selected item will be the one at the given index in their inventory.
     * Sets the selected player item to {@link Command#getItem()} if one is present.
     *
     * @param command the command
     */
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

    /**
     * Process command internal.
     *
     * Meant for processing commands that the player ought not have access to.
     *
     * @param command the command
     */
    private void processCommandInternal(Command command) {

        CommandWord commandWord = command.getCommandWord();

        if (commandWord == CommandWord.UNKNOWN) {
            MessageHelper.Command.unknownCommand();
            return;
        }

        if (commandWord == CommandWord.TELEPORT) {
            teleportPlayer(command);
        } else if (commandWord == CommandWord.REMOVE_ITEM) {
            removeItem(command);
        } else if (commandWord == CommandWord.ADD_ITEM) {
            addItem(command);
        } else {
            processCommand(command);
        }

    }

    /**
     * Add item.
     *
     * @param command the command
     */
    private void addItem(Command command) {
        if (command.hasItem()) {
            player.getInventory().addItem(command.getItem());
        }
    }

    /**
     * Process command internal.
     * 
     * Calls {@link Game#processCommand(Command)}
     *
     * @param commands the commands
     */
    private void processCommandInternal(Command[] commands) {
        if (commands != null && commands.length > 0) {
            for (Command command : commands) {
                if (command != null) {
                    processCommandInternal(command);
                }
            }
        }
    }

    /**
     * Interact player.
     *
     * Interact with the GameObject the player resides in.
     *
     */
    private void interactPlayer() {

        Item item = player.getInventory().getSelectedItem();

        Command[] commands;

        if (item == null) {
            commands = getRoom()
                    .getGridGameObject(player.getPos())
                    .interact();
        } else {
            commands = getRoom()
                    .getGridGameObject(player.getPos())
                    .interact(item);
        }

        processCommandInternal(commands);
    }

    /**
     * Remove item.
     *
     * @param command the command
     */
    private void removeItem(Command command) {
        if (command.hasItem()) {
            player.getInventory().removeItem(command.getItem());
        } else if (command.hasSecondWord()) {
            player.getInventory().removeItem(tryParse(command.getSecondWord(), 0));

        }

        if (!player.getInventory().getItems().contains(player.getInventory().getSelectedItem())) {
            player.getInventory().unselectItem();
        }

    }

    /**
     * Print help.
     */
    private void printHelp() {
        MessageHelper.Info.helpCommands();
        parser.showCommands();
    }

    /**
     * Go room.
     *
     * Changes the current room the the one that corresponds the the value defined in their {@link Room#exitsProperty()} using the parameters {@link Command#getSecondWord()} as the key.
     *
     * @param command the command
     */
    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            MessageHelper.Command.unknownArgumentWhere(CommandWord.GO.toString());
            return;
        }

        String direction = command.getSecondWord();

        Room nextRoom = getRoom().getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        } else {
            setRoom(nextRoom);
            System.out.println(getRoom().getLongDescription());
        }
    }


    /**
     * Move player.
     *
     * Constructs a {@link Direction} using the {@link Command#getSecondWord()} and moves the player in that direction.
     * If one cannot be constructed or the the {@link Game#canPlayerMoveToPoint(int, int)} is false, nothing will happen.
     *
     * @param command the command
     */
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
            setPlayerPosition(new Vector(x, y));
        }
    }

    /**
     * Teleport player.
     *
     * Constructs a {@link Vector} using the parameters {@link Command#getSecondWord()} and attempts to teleport the player to the {@link Vector} position.
     * If one cannot be constructed or the the {@link Game#canPlayerMoveToPoint(int, int)} is false, nothing will happen.
     *
     * @param command the command
     */
    private void teleportPlayer(Command command) {
        if (!command.hasSecondWord()) {
            MessageHelper.Command.unknownArgumentWhere(CommandWord.TELEPORT.name());
            return;
        }

        String secondWord = command.getSecondWord();
        Vector pos = new Vector(secondWord);

        if (canPlayerMoveToPoint(pos.getX(), pos.getY())) {
            setPlayerPosition(pos);
        }
    }

    /**
     * Sets player position.
     *
     * Handles entry and exit of GameObjects.
     *
     * @param position the position
     */
    private void setPlayerPosition(Vector position) {

        GameObject currentTile = player.getCurrentGameObject();
        player.setPos(position);
        GameObject newTile = getRoom().getGridGameObject(position);
        player.setCurrentGameObject(newTile);

        if (currentTile != null) {
            processCommandInternal(currentTile.uponExit());
        }

        processCommandInternal(newTile.uponEntry(currentTile));
    }

    /**
     * Can player move to point boolean.
     *
     * @param x the x
     * @param y the y
     * @return the boolean
     */
    private boolean canPlayerMoveToPoint(int x, int y) {
        int roomDimensionsY = getRoom().getRoomGrid().length;
        int roomDimensionsX = getRoom().getRoomGrid()[0].length;

        if (x < 0 || y < 0 || x >= roomDimensionsX || y >= roomDimensionsY) {
            MessageHelper.Command.positionExceedsMap();
            return false;
        }

        GameObject targetPosition = getRoom().getGridGameObject(new Vector(x, y));
        if (targetPosition.isColliding()) {
            MessageHelper.Command.objectIsCollidable();
        }

        return !targetPosition.isColliding();
    }

    /**
     * Quit boolean.
     *
     * @param command the command
     * @return the boolean
     */
    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            MessageHelper.Command.unknownArgumentWhere(CommandWord.QUIT.toString());
            return false;
        } else {
            return true;
        }
    }

    /**
     * Gets rooms.
     *
     * @return the rooms
     */
    @JsonGetter
    public ObservableList<Room> getRooms() {
        return rooms.get();
    }

    /**
     * Sets rooms.
     *
     * @param rooms the rooms
     */
    @JsonSetter
    public void setRooms(Room[] rooms) {
        ObservableList<Room> temp = new SimpleListProperty<>(
                FXCollections.observableArrayList());

        temp.addAll(Arrays.asList(rooms));

        this.rooms.set(temp);
    }

    /**
     * Sets rooms.
     *
     * @param observableRooms the observable rooms
     */
    @JsonIgnore
    public void setRooms(ObservableList<Room> observableRooms) {
        this.rooms.set(observableRooms);
    }

    /**
     * Rooms property list property.
     *
     * @return the list property
     */
    @JsonIgnore
    public ListProperty<Room> roomsProperty() {
        return rooms;
    }
}
