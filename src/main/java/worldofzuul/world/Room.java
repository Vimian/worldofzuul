package worldofzuul.world;

import worldofzuul.Player;
import worldofzuul.parsing.Command;
import worldofzuul.util.Vector;

import java.util.*;


public class Room {
    private String description;
    private HashMap<String, Room> exits;
    private GameObject[][] roomGrid;
    private Environment environment;
    private String name;

    // method for adding GameObjects to roomGrid, give positions as coordinate system.
    public void addToGrid(GameObject gameObject, int posX, int posY){
        roomGrid[posY][posX] = gameObject;
    }

    public Room(String description, String name)
    {
        this.description = description;
        exits = new HashMap<String, Room>();
        this.environment = new Environment();
        this.name = name;
    }

    public Room(String description, GameObject[][] roomGrid) {
        this.description = description;
        this.roomGrid = roomGrid;
        this.environment = new Environment();
    }

    public Room(String description, GameObject[][] roomGrid, Date date) {
        this.description = description;
        this.roomGrid = roomGrid;
        this.environment = new Environment(date);
    }

    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    public String getShortDescription() {
        return description;
    }

    public String getLongDescription() {
        return "You are " + description + ".\n" + getExitString();
    }

    public String getExitString() {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for (String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    public Room getExit(String direction) {
        return exits.get(direction);
    }

    public String getExitName(Room room) {

        return "hej";
    }

    public GameObject[][] getRoomGrid() {
        return roomGrid;
    }

    public void setRoomGrid(GameObject[][] roomGrid) {
        this.roomGrid = roomGrid;
    }

    public GameObject getGridGameObject(Vector pos) {
        return getRoomGrid()[pos.getY()][pos.getX()];
    }

    public void setGridGameObject(GameObject gameObject, Vector pos) {
        roomGrid[pos.getY()][pos.getX()] = gameObject;
    }

    public String getName() {
        return this.name;
    }

    public LinkedList<Command[]> update() {
        LinkedList<Command[]> commands = new LinkedList<>();

        environment.update();

        for (GameObject[] gameObjects : roomGrid) {
            for (GameObject gameObject : gameObjects) {
                if (gameObject != null) {
                    commands.add(gameObject.update());
                    environment.update(gameObject);
                }
            }
        }
        return commands;
    }
}

