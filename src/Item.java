/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rasmus
 */
public class Item
{
    private String name;
    private String description;
    private int weight;
    private boolean edible;
    
    /**
     * Give the Item a description and a weight.
     * @param name The name of the Item as String.
     * @param description The description of the Item as String.
     * @param weight The weight of the Item as int.
     * @param edible Wheter the Player can eat the Item or not as boolean.
     */
    public Item(String name, String description, int weight, boolean edible)
    {
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.edible = edible;
    }
    
    /**
     * Get the name of the Item.
     * @return name as a String.
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Get the description of the Item.
     * @return The description as a String.
     */
    public String getDescription()
    {
        return description;
    }
    
    /**
     * Get the weight of the Item.
     * @return The wieght as an int.
     */
    public int getWeight()
    {
        return weight;
    }
    
    /**
     * Checks if the Item is edible or not.
     * @return True if edible. False if not.
     */
    public boolean isEdible()
    {
        return edible;
    }
}
