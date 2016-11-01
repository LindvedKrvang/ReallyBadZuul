
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private List<Item> items;
    private boolean itemInRoom = false;

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
        items = new ArrayList<>();
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
     * Define an Item that spawns in the room.
     * @param name as String.
     * @param description as String.
     * @param weight as int.
     */
    public void createItem(String name, String description, int weight)
    {
        Item item = new Item(name, description, weight);
        items.add(item);
        itemInRoom = true;
    }
    
    /**
     * Adds an Item to the Room dropped by the player.
     * @param itemToAdd The Item added to the Room.
     */
    public void addItem(Item itemToAdd)
    {
        items.add(itemToAdd);
    }
    
    /**
     * Removes an Item from the Room.
     * @param itemToRemove The name of the Item as String.
     * @return A reference to the removed Items.
     */
    public Item removeItem(String itemToRemove)
    {
        Item itemRemoved = null;
        for(int i = 0; i < items.size(); i++)
        {
            if(items.get(i).getName().equals(itemToRemove))
            {
                itemRemoved = items.get(i);
                items.remove(i);
            }
        }
        return itemRemoved;
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
        String longDescription;
        longDescription = "You are " + description + "\n" + getExitString();
        if(itemInRoom)
        {
            longDescription += "\n\nYou see some items:";
            for(Item item : items)
            {
                longDescription += "\nName: " + item.getName()
                    + "\nDescription: " + item.getDescription() + "\nWeight: " + item.getWeight();
            }            
        }
        return longDescription;
    }
}
