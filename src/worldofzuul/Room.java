package worldofzuul;

import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;


/**
 * The type Room.
 */
public class Room
{
    /**
     * The room description.
     */
    private String description;
    /**
     * The neighbor rooms and their corresponding exit directions.
     */
    private HashMap<String, Room> exits;

    /**
     * Instantiates a new Room.
     *
     * @param description the room's description
     */
    public Room(String description)
    {
        this.description = description;
        exits = new HashMap<String, Room>();
    }

    /**
     * Sets a room to be at a specified direction.
     *
     * @param direction the targeted direction to place room at
     * @param neighbor  the room to set at the specified direction
     */
    public void setExit(String direction, Room neighbor)
    {
        exits.put(direction, neighbor);
    }

    /**
     * Gets the specified room description
     *
     * @return the description
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Gets the room description and room exit directions
     *
     * @return the room description and possible exit directions
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString();
    }

    /**
     * Gets the directions of room exits.
     *
     * @return string containing list of all the directions corresponding with a neighbor room
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Gets the room specified by a direction from the exits list.
     *
     * @param direction the exit direction
     * @return the room specified by the direction
     */
    public Room getExit(String direction)
    {
        return exits.get(direction);
    }
}

