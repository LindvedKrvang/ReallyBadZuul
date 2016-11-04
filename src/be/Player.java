package be;


import be.Room;
import be.Item;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Rasmus
 */
public class Player
{

    private String name;
    private int maxWeight;
    private int currentWeight;
    private Room currentRoom;
    private Stack previousRooms;
    private List<Item> inventory;

    public Player(String name, int maxWeight)
    {
        this.name = name;
        this.maxWeight = maxWeight;
        previousRooms = new Stack();
        inventory = new ArrayList<>();
        currentWeight = 0;
    }

    /**
     * Gets the current the Player is in.
     *
     * @return currentRoom.
     */
    public Room getCurrentRoom()
    {
        return currentRoom;
    }

    /**
     * Sets the currentRoom to the new Room.
     *
     * @param newRoom The new Room.
     */
    public void setCurrentRoom(Room newRoom)
    {
        currentRoom = newRoom;
    }

    /**
     * Gets the Stack with all Rooms previously entered.
     *
     * @return All preivious Rooms entered in a Stack.
     */
    public Stack getPreviousRooms()
    {
        return previousRooms;
    }

    /**
     * Add an Item to the inventory.
     *
     * @param itemToAdd The Item to be added.
     */
    public void addItemToInventory(Item itemToAdd)
    {
        inventory.add(itemToAdd);
    }

    /**
     * Removes the item from the inventroy and return the Item as an Item
     * object.
     *
     * @param itemToRemove The name of the Item to be removed as String.
     * @return The Item removed.
     */
    public Item removeItemFromInventroy(String itemToRemove)
    {
        Item itemRemoved = null;
        for (int i = 0; i < inventory.size(); i++)
        {
            if (inventory.get(i).getName().equals(itemToRemove))
            {
                itemRemoved = inventory.get(i);
                inventory.remove(i);
            }
        }
        return itemRemoved;
    }

    /**
     * Get the inventory of the player.
     *
     * @return The inventory as a List.
     */
    public List<Item> getInventory()
    {
        return inventory;
    }
    
    /**
     * Get an Item from the inventory.
     * @param itemToGet Name of Item as String.
     * @return Item if found. Null if no Item matching is found.
     */
    public Item getItemFromInventory(String itemToGet)
    {
        Item itemToReturn = null;
        for(int i = 0; i < inventory.size(); i++)
        {
            if(inventory.get(i).getName().equals(itemToGet))
            {
                itemToReturn = inventory.get(i);
            }
        }
        return itemToReturn;
    }

    /**
     * Checks if able to carry the Item.
     *
     * @param item The item supposed to be added.
     * @return True if able. False if unable.
     */
    public boolean canCarryItem(Item item)
    {
        boolean canCarry = true;
        if (getCurrentRoom().isItemInRoom(item))
        {
            if (currentWeight + item.getWeight() > maxWeight)
            {
                canCarry = false;
            }
        }
        return canCarry;
    }
    
    /**
     * Gets the current weight of the Player.
     * @return CurrentWeight as int.
     */
    public int getCurrentWeight()
    {
        return currentWeight;
    }
    
    /**
     * Set the new weight.
     * @param addedWeight Amount to be added.
     */
    public void setCurrentWeight(int addedWeight)
    {
        currentWeight += addedWeight;
    }
    
    /**
     * Set the new maxWeight.
     * @param addedWeight Amount to be added.
     */
    public void setMaxWeight(int addedWeight)
    {
        maxWeight += addedWeight;
    }
}
