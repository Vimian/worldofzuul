package worldofzuul.world;


import com.fasterxml.jackson.annotation.JsonIgnore;
import worldofzuul.parsing.Command;
import worldofzuul.util.Vector;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

public class Room {
    private String description;

    private HashMap<String, Room> exits = new HashMap<>();
    private GameObject[][] roomGrid;
    private Environment environment;
    private HashMap<String, String> roomStringExits = new HashMap<>();
    private String backgroundImage;

    // method for adding GameObjects to roomGrid, give positions as coordinate system.
    public void addToGrid(GameObject gameObject, int posX, int posY){
        roomGrid[posY][posX] = gameObject;
    }

    public Room(){}
    public Room(String description)
    {
        this.description = description;
        this.environment = new Environment();
    }

    public Room(String description, GameObject[][] roomGrid) {
        this(description);
        this.roomGrid = roomGrid;
        this.environment = new Environment();
    }

    public Room(String description, GameObject[][] roomGrid, Date date) {
        this(description);
        this.roomGrid = roomGrid;
        this.environment = new Environment(date);
    }

    public void setExit(String direction, Room neighbor) {
        roomStringExits.put(direction, neighbor.getShortDescription());

        exits.put(direction, neighbor);
    }

    @JsonIgnore
    public String getShortDescription() {
        return description;
    }

    @JsonIgnore
    public String getLongDescription() {
        return "You are " + description + ".\n" + getExitString();
    }


    private String getExitString() {
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

    public HashMap<String, String> getRoomStringExits() {
        return roomStringExits;
    }

    public void setRoomStringExits(HashMap<String, String> roomStringExits) {
        this.roomStringExits = roomStringExits;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }
}

