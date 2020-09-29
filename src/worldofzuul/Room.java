package worldofzuul;

import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;


/**
 * The type Room.
 */
public class Room
{
    private String description;
    private HashMap<String, Room> exits;

    /**
     * Instantiates a new Room.
     *
     * @param description the room description
     */
    public Room(String description)
    {
        this.description = description;
        exits = new HashMap<String, Room>();
    }

    /**
     * Sets exit rooms.
     *
     * @param direction the direction
     * @param neighbor  the room neighbor
     */
    public void setExit(String direction, Room neighbor)
    {
        exits.put(direction, neighbor);
    }

    /**
     * Gets short description.
     *
     * @return the short description
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Gets long description.
     *
     * @return the long description
     */
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

    /**
     * Gets room exit.
     *
     * @param direction the exit direction
     * @return the exit room
     */
    public Room getExit(String direction)
    {
        return exits.get(direction);
    }
}

