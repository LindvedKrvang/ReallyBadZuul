
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
    private Room currentRoom;
    private Stack previousRooms;
    
    public Player()
    {
        previousRooms = new Stack();
    }
    
    public Room getCurrentRoom()
    {
        return currentRoom;
    }
    
    public void setCurrentRoom(Room newRoom)
    {
        currentRoom = newRoom;
    }
    
    public Stack getPreviousRooms()
    {
        return previousRooms;
    }
}
