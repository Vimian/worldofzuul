package worldofzuul.world;

import worldofzuul.parsing.Command;
import worldofzuul.util.Vector;

import java.util.Date;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashMap;


public class Room 
{
    private String description;
    private HashMap<String, Room> exits;
    private GameObject[][] roomGrid;
    private Environment environment;

    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<String, Room>();
        this.environment = new Environment();
    }

    public Room(String description, GameObject[][] roomGrid)
    {
        this(description);
        this.roomGrid = roomGrid;
        this.environment = new Environment();
    }
    public Room(String description, GameObject[][] roomGrid, Date date)
    {
        this(description);
        this.roomGrid = roomGrid;
        this.environment = new Environment(date);
    }
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    public String getShortDescription()
    {
        return description;
    }

    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString();
    }

    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }

    public GameObject[][] getRoomGrid() {
        return roomGrid;
    }
    public GameObject getGridGameObject(Vector pos) {
        return getRoomGrid()[pos.getY()][pos.getX()];
    }
    public void setGridGameObject(GameObject gameObject, Vector pos) {
        roomGrid[pos.getY()][pos.getX()] = gameObject;
    }

    public void setRoomGrid(GameObject[][] roomGrid) {
        this.roomGrid = roomGrid;
    }

    public LinkedList<Command[]> update(){
        LinkedList<Command[]> commands = new LinkedList<>();

        environment.update();

        for (GameObject[] gameObjects : roomGrid) {
            for (GameObject gameObject : gameObjects) {
                if(gameObject != null){
                    commands.add(gameObject.update());
                    environment.update(gameObject);
                }
            }
        }

        return commands;
    }

}

