package gui;


import be.Player;
import be.Room;
import be.Item;
import bll.Parser;
import bll.CommandWord;
import bll.Command;


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

    /**
     * Create the game and initialise its internal map.
     */
    public Game()
    {
        player = new Player("Rasmus", 10);
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theater, pub, lab, office, cellar;

        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        cellar = new Room("in the cellar under the office");

        // initialise room exits
        outside.setExits("east", theater);
        outside.setExits("south", lab);
        outside.setExits("west", pub);
        theater.setExits("west", outside);
        pub.setExits("east", outside);
        lab.setExits("east", office);
        lab.setExits("north", outside);
        office.setExits("west", lab);
        office.setExits("down", cellar);
        cellar.setExits("up", office);

        office.createItem("computer", "A state of the art computer", 2, false);
        pub.createItem("chair", "A solid, but used chair", 3, false);
        pub.createItem("dartgame", "There is lots of holes in the wall next to it", 1, false);
        pub.createItem("table", "Just a table", 6, false);
        pub.createItem("book", "A big, heavy book", 2, false);
        cellar.createItem("cookie", "A magic cookie that increases maximum weight by 10", 1, true);

        player.setCurrentRoom(outside);  // start game outside
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
            finished = processCommand(command);
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
                + "\nWorld of Zuul is a new, incredibly boring adventure game."
                + "\nType '" + CommandWord.HELP.toString() + "' if you need help.\n";
        return welcomeString;
    }

    /**
     * Given a command, process (that is: execute) the command.
     *
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command)
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();
//        if (null != commandWord)
        switch (commandWord)
        {
            case UNKOWN:
                System.out.println("I don't know what you mean...");
                return false;
            case HELP:
                printHelp();
                break;
            case GO:
                goRoom(command);
                break;
            case BACK:
                goBack(command);
                break;
            case LOOK:
                look();
                break;
            case INVENTORY:
                showInventory();
                break;
            case TAKE:
                takeItem(command);
                break;
            case DROP:
                dropItem(command);
                break;
            case EAT:
                eat(command);
                break;
            case QUIT:
                wantToQuit = quit(command);
                break;
            default:
                break;
        }

        return wantToQuit;
    }

    // implementations of user commands:
    /**
     * Print out some help information. Here we print some stupid, cryptic
     * message and a list of the command words.
     */
    private void printHelp()
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.showCommands());
    }

    /**
     * Try to go in one direction. If there is an exit, enter the new room,
     * otherwise print an error message. Sets lastRoom to the currentRoom right
     * before leaving the room.
     */
    private void goRoom(Command command)
    {
        if (!command.hasSecondWord())
        {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = player.getCurrentRoom().getExit(direction);

        if (nextRoom == null)
        {
            System.out.println("There is no door!");
        } else
        {
            player.getPreviousRooms().push(player.getCurrentRoom());
            player.setCurrentRoom(nextRoom);
            printLocationInfo();
        }
    }

    /**
     * Goes back to the previues room. If trying to go back from the very first
     * room. Print a message.
     */
    private void goBack(Command command)
    {
        if (command.hasSecondWord())
        {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        if (!player.getPreviousRooms().isEmpty())
        {
            player.setCurrentRoom((Room) player.getPreviousRooms().pop());
            printLocationInfo();
        } else
        {
            System.out.println("You haven't left the first room yet.");
        }
    }

    /**
     * "Quit" was entered. Check the rest of the command to see whether we
     * really quit the game.
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
     */
    private void takeItem(Command command)
    {
        if (!command.hasSecondWord())
        {
            // if there is no second word, we don't know what to take...
            System.out.println("Take what?");
            return;
        }
        
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
                    System.out.println(itemTotake.getName() + " was added to inventory\n");
                } else
                {
                    System.out.println("There is no items to take\n");
                }
            } catch (NullPointerException e)
            {
                //Do Nothing
            }
        } else
        {
            System.out.println("Not enough room in inventory for " + itemTotake.getName() + "\n");
        }
    }

    /**
     * Checks if there is an Item to drop. If yes, drops it.
     *
     * @param command
     */
    private void dropItem(Command command)
    {
        if (!command.hasSecondWord())
        {
            // if there is no second word, we don't know what to drop...
            System.out.println("Drop what?");
            return;
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
                    System.out.println(itemToDrop.getName() + " was removed from inventory\n");
                }
            }
        } catch (NullPointerException e)
        {
            //Do nothing.
        }
    }

    /**
     * Shows which Items the Player are holding and the total weight.
     */
    private void showInventory()
    {
        String inventoryList = "";
        int totalWeight = 0;
        for (Item inventory : player.getInventory())
        {
            inventoryList += inventory.getName() + " ";
            totalWeight += inventory.getWeight();
        }
        System.out.println("You are carrying: " + inventoryList
                + "\nIt all weights " + totalWeight + " kg\n");
    }

    /**
     * Attempts to eat the given Item.
     *
     * @param command
     */
    private void eat(Command command)
    {
        if (!command.hasSecondWord())
        {
            // if there is no second word, we don't know what to eat..
            System.out.println("Eat what?");
            return;
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
            System.out.println("You have eaten " + itemToBeEaten.getName()
                    + " and are not hungry anymore.");
        }
    }
}
