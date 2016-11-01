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
    
    /**
     * Give the Item a description and a weight.
     * @param description The description.
     * @param weight The weight.
     */
    public Item(String name, String description, int weight)
    {
        this.name = name;
        this.description = description;
        this.weight = weight;
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
}
