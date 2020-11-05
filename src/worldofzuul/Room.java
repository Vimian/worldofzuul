package worldofzuul;

import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;


public class Room 
{
    private String description;
    private HashMap<String, Room> exits;
    private GameObject[][] roomGrid;

    GameObject[][] roomGrid = new GameObject[10][10];

    // method for adding GameObjects to roomGrid, give positions as coordinate system.
    public void addToGrid(GameObject gameObject, int posX, int posY){
        roomGrid[posY][posX] = gameObject;
    }

    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<String, Room>();
    }

    public Room(String description, GameObject[][] roomGrid)
    {
        this(description);
        this.roomGrid = roomGrid;
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

    public void setRoomGrid(GameObject[][] roomGrid) {
        this.roomGrid = roomGrid;
    }
}

