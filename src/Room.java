
import java.util.HashMap;
import java.util.Set;


/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game.
 *
 * A "Room" represents one location in the scenery of the game.  It is
 * connected to other rooms via exits.  The exits are labelled north,
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 *
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */
public class Room
{

    private String description;
    private HashMap<String, Room> exits;

    /**
     * Create a room described "description". Initially, it has no exits.
     * "description" is something like "a kitchen" or "an open court yard".
     *
     * @param description The room's description.
     */
    public Room(String description)
    {
        this.description = description;
        exits = new HashMap<>();
    }

    /**
     * Define the exits of this room.     *
     * @param direction The direction of the exit.
     * @param neighbor The room in the given direction.
     */
    public void setExits(String direction, Room neighbor)
    {
        exits.put(direction, neighbor);
    }
    
    /**
     * Return the exit attempting to exit.
     * @param direction that the player attempts to exit.
     * @return the exit for the direction.
     */
    public Room getExit(String direction)
    {
        return exits.get(direction);
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }
    
    /**
     * Return a description of the room's exits.
     * for example, "Exits: north west".
     * @return  A description of the available exits.
     */
    public String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys)
        {
            returnString += " " + exit;
        }
        return returnString;
    }
    
    /**
     * Retun a long description of this room, of the form:
     *  You are in the kitchen.
     *  Exits: north west.
     * @return 
     */
    public String getLongDescription()
    {
        return "You are " + description + "\n" + getExitString();
    }
}
