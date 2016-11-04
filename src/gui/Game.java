package gui;


import be.Player;
import be.Room;
import be.Item;
import bll.Parser;
import bll.CommandWord;
import bll.Command;
import java.util.ArrayList;
import java.util.List;


/**
 * This class is the main class of the "World of Zuul" application. "World of
 * Zuul" is a very simple, text based adventure game. Users can walk around some
 * scenery. That's all. It should really be extended to make it more
 * interesting!
 *
 * To play this game, create an instance of this class and call the "play"
 * method.
 *
 * This main class creates and initialises all the others: it creates all rooms,
 * creates the parser and starts the game. It also evaluates and executes the
 * commands that the parser returns.
 *
 * @author Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */
public class Game
{

    private Parser parser;
    private Player player;
    private List<Room> lockedRooms;

    /**
     * Create the game and initialise its internal map.
     */
    public Game()
    {
        player = new Player("Rasmus", 10);        
        parser = new Parser();
        lockedRooms = new ArrayList<>();
        createRooms();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room startArea, firstDoorHall, storage, chasim, alcove, mainHall, kitchen;

        // create the rooms
        startArea = new Room("startArea", "in a dimlight room. There's a skeleton next to you", false);
        firstDoorHall = new Room("firstDoorHall", "in a big dusty area. There's a large old door. \nIt looks heavy", false);
        storage = new Room("storage", "in a small dirty room. It appears to be a storage.", false);
        chasim = new Room("chasim", "in a narrow room. There's a chasim dividing it.", false);
        alcove = new Room("alcove", "in what appears to be an old study. "
                + "\nStuff is lying around. in no apparent order. \nSo untidy.", true);
        mainHall = new Room("mainHall", "in a large room. There's a long table in the middle."
                + "\nThe table is set as a mighty feast is about to take place."
                + "\nSeveral skulls are displayed in the room.", true);
//        

         //initialise room exits
         startArea.setExits("north", firstDoorHall);
         firstDoorHall.setExits("south", startArea);
         firstDoorHall.setExits("east", storage);
         firstDoorHall.setExits("west", chasim);
         firstDoorHall.setExits("north", mainHall);
         storage.setExits("west", firstDoorHall);
         chasim.setExits("east", firstDoorHall);
         chasim.setExits("west", alcove);
         alcove.setExits("east", chasim);
         
         //Creates all the items in the game.
         storage.createItem("planks", "Long sturdy planks. \nSeems able to carry a person", 6, false);
         storage.createItem("nails", "Old rusty nails. \nSome are bend, others broken", 0, false);
         alcove.createItem("oldKey", "Old keys. Nothing speciel.", 0, false);
         
         //Set the keys for all of the locked rooms.
         firstDoorHall.setLockAndKey(firstDoorHall, "oldKey");
         chasim.setLockAndKey(chasim, "planks");
         
         //Puts all the locked rooms in a List to keep track of them.
         lockedRooms.add(alcove);
         lockedRooms.add(mainHall);

        player.setCurrentRoom(startArea);  // start game in startArea
    }

    /**
     * Main play routine. Loops until end of play.
     * Uses for console.
     */
    public void play()
    {
        System.out.println("printWelcome()");

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
        boolean finished = false;
        while (!finished)
        {
            Command command = parser.getCommand();
//            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }
    
    

    /**
     * Print out the opening message for the player.
     * @return Welcome message as String.
     */
    public String printWelcome()
    {
        String welcomeString = "";
        welcomeString += "Welcome to the World of Zuul!"
                + "\nWorld of Zuul is a new, incredibly \nboring adventure game."
                + "\nType '" + CommandWord.HELP.toString() + "' if you need help.\n";
        return welcomeString;
    }

    /**
     * Given a command, process (that is: execute) the command.
     *
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    public String processCommand(Command command)
    {
        boolean canReturnText = false;
        String textToReturn = "";

        CommandWord commandWord = command.getCommandWord();
//        if (null != commandWord)
        switch (commandWord)
        {
            case UNKOWN:
                textToReturn = "I don't know what you mean...";
                break;
            case HELP:
                textToReturn += printHelp();
                break;
            case GO:
                textToReturn = goRoom(command);
                break;
            case BACK:
                textToReturn = goBack(command);
                break;
            case TAKE:                
                textToReturn += takeItem(command);
                break;
            case DROP:
                textToReturn = dropItem(command);
                break;
            case USE:
                textToReturn = useItemInIventory(command);
            default:
                break;
        }
        
        return  textToReturn;
    }

    // implementations of user commands:
    /**
     * Print out some help information. Here we print some stupid, cryptic
     * message and a list of the command words.
     * @return 
     */
    public String printHelp()
    {
        String help = "You are lost. You are alone. You wander around at the university."
                + "\n\nYour command words are:\n" + parser.showCommands() + "\n";
        
        return help;
    }

    /**
     * Try to go in one direction. If there is an exit, enter the new room,
     * otherwise print an error message. Sets lastRoom to the currentRoom right
     * before leaving the room.
     */
    private String goRoom(Command command)
    {
        String text;
        if (!command.hasSecondWord())
        {
            // if there is no second word, we don't know where to go...
            text = "Go where?\n";
            return text;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = player.getCurrentRoom().getExit(direction);

        if (nextRoom == null)
        {
            text = "There is no door!\n";
        }
        else if(nextRoom.getRoomIsLocked())
        {
            text = "You can't go that way yet.\nYou need to do something first.\n";
        } else
        {
            player.getPreviousRooms().push(player.getCurrentRoom());
            player.setCurrentRoom(nextRoom);
            text = "You move to the next room.\n";
        }
        return text;
    }

    /**
     * Goes back to the previues room. If trying to go back from the very first
     * room. Print a message.
     */
    private String goBack(Command command)
    {
        String text;
        if (command.hasSecondWord())
        {
            // if there is no second word, we don't know where to go...
            text = "Go where?";
            return text;
        }

        if (!player.getPreviousRooms().isEmpty())
        {
            player.setCurrentRoom((Room) player.getPreviousRooms().pop());
            text = "You move back the way you came.\n";
        } else
        {
            text = "You haven't left the first room yet.";
        }
        return text;
    }

    /**
     * "Quit" was entered. Check the rest of the command to see whether we
     * really quit the game.
     * 
     * TODO: Remove. The method is never used.
     *
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command)
    {
        if (command.hasSecondWord())
        {
            System.out.println("Quit what?");
            return false;
        } else
        {
            return true;  // signal that we want to quit
        }
    }

    /**
     * Prints the info of the room.
     * @return the info as String.
     */
    public String printLocationInfo()
    {
        String locationInfo = "";
        locationInfo += player.getCurrentRoom().getLongDescription() + "\n";
        return locationInfo;
    }

    /**
     * Prints a long description of the room.
     */
    private void look()
    {
        System.out.println(player.getCurrentRoom().getLongDescription());
    }

    /**
     * Checks if there is an Item to take. If yes, takes Item.
     *
     * @param command
     * @return A String depending on the status.
     */
    private String takeItem(Command command)
    {
        if (!command.hasSecondWord())
        {
            // if there is no second word, we don't know what to take...            
            return "Take what?";
        }
        
        String itemTaken = "";
        Item itemTotake = player.getCurrentRoom().getItem(command.getSecondWord());
        if (player.canCarryItem(itemTotake))
        {
            try
            {
                if (player.getCurrentRoom().isItemInRoom(itemTotake))
                {
                    player.getCurrentRoom().removeItem(itemTotake);
                    player.addItemToInventory(itemTotake);
                    player.setCurrentWeight(itemTotake.getWeight());
                    itemTaken = itemTotake.getName() + " was added to inventory\n";
                } else
                {
                    itemTaken = "There is no items to take\n";
                }
            } catch (NullPointerException e)
            {
                //Do Nothing
            }
        } else
        {
            itemTaken = "Not enough room in inventory for " + itemTotake.getName() + "\n";
        }
        return itemTaken;
    }

    /**
     * Checks if there is an Item to drop. If yes, drops it.
     *
     * @param command
     */
    private String dropItem(Command command)
    {
        String itemDropped = "";
        if (!command.hasSecondWord())
        {
            // if there is no second word, we don't know what to drop...
            itemDropped = "Drop what?";
            return itemDropped;
        }
        
        try
        {
            for (int i = 0; i < player.getInventory().size(); i++)
            {
                if (player.getInventory().get(i).getName().equals(command.getSecondWord()))
                {
                    Item itemToDrop;
                    itemToDrop = player.removeItemFromInventroy(command.getSecondWord());
                    player.getCurrentRoom().addItem(itemToDrop);
                    player.setCurrentWeight(-itemToDrop.getWeight());
                    itemDropped += itemToDrop.getName() + " was removed from inventory\n";
                }
            }
        } catch (NullPointerException e)
        {
            //Do nothing.
        }
        return itemDropped;
    }

    /**
     * Shows which Items the Player are holding and the total weight.
     * @return 
     */
    public String showInventory()
    {
        String inventoryList = "";
        int totalWeight = 0;
        for (Item inventory : player.getInventory())
        {
            inventoryList += inventory.getName() + "\n";
            totalWeight += inventory.getWeight();
        }
        return ("You are carrying:\n" + inventoryList
                + "It all weights " + totalWeight + " kg\n");
    }
    
    public String useItemInIventory(Command command)
    {
        String textToReturn;
        //Checks if there is a second word.
        if (!command.hasSecondWord())
        {
            // if there is no second word, we don't know what to use...
            textToReturn = "Use what?\n";
            return textToReturn;
        }
        
        Item item = player.getItemFromInventory(command.getSecondWord());
        if(item != null)
        {
            //Check if in the right room.
            if(player.getCurrentRoom().checkLockAndKey(player.getCurrentRoom(), item.getName()))
            {
                //Use Item.
                textToReturn = item.useItem(command.getSecondWord());
                //Remove Item from inventory.
                textToReturn += player.removeItemFromInventroy(command.getSecondWord()).getName()
                        + " was removed from your inventory.\n";
                //Unlock the room.
                unlockRoom(command.getSecondWord());
                
            }
            else
            {
                textToReturn = "That item has no effect here.\n";
            }
        }
        else
        {
            textToReturn = "You do not have that item in your inventory.\n";
        }
        return textToReturn;
    }
    
    /**
     * Finds the room that's unlocked by the specific key and unlocks it.
     * @param nameOfKey as String.
     */
    private void unlockRoom(String nameOfKey)
    {
        Room roomToUnlock;
        if(nameOfKey.equals("oldKey"))
        {
            for(int i = 0; i < lockedRooms.size(); i++)
            {
                if(lockedRooms.get(i).getName().equals("mainHall"))
                {
                    roomToUnlock = lockedRooms.get(i);
                    roomToUnlock.setRoomIsLocked(false);
                }
            }
        } 
        else if(nameOfKey.equals("planks"))
        {
            for(int i = 0; i < lockedRooms.size(); i++)
            {
                if(lockedRooms.get(i).getName().equals("alcove"))
                {
                    roomToUnlock = lockedRooms.get(i);
                    roomToUnlock.setRoomIsLocked(false);
                }
            }
        }
    }

    /**
     * Attempts to eat the given Item.
     * 
     * 
     * TODO: remove entire method. It is not used.
     * 
     *
     * @param command
     */
    private String eat(Command command)
    {
        String eatText = "";
        if (!command.hasSecondWord())
        {
            // if there is no second word, we don't know what to eat..
            eatText = "Eat what?";
            return eatText;
        }
        
        Item itemToBeEaten = player.getItemFromInventory(command.getSecondWord());
        if (itemToBeEaten != null && itemToBeEaten.isEdible())
        {
            player.getInventory().remove(itemToBeEaten);
            player.setCurrentWeight(-itemToBeEaten.getWeight());
            if (itemToBeEaten.getName().equals("cookie"))
            {
                player.setMaxWeight(10);
            }
            eatText += "You have eaten " + itemToBeEaten.getName()
                    + " and are not hungry anymore.";
        }
        return eatText;
    }
    
    /**
     * Gets the info on all Items in the Room.
     * @return All Items as String.
     */
    public String getItemDescription()
    {
        return player.getCurrentRoom().getItemDescription();
    }
}
