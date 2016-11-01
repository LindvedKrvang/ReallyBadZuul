
import java.util.Stack;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
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
        player = new Player("Rasmus");
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
        
        office.createItem("Computer", "A state of the art computer", 2);
        pub.createItem("Chair", "A solid, but used chair", 3);
        pub.createItem("Dart Game", "There is lots of holes in the wall next to it", 1);

        player.setCurrentRoom(outside);  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        printLocationInfo();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            
            goRoom(command);            
        }
        else if(commandWord.equals("back"))
        {
            goBack(command);
        }
        else if(commandWord.equals("look"))
        {
            look();
        }
        else if(commandWord.equals("take"))
        {
            Item itemTotake;
            itemTotake = player.getCurrentRoom().removeItem(command.getSecondWord());
            player.addItemToInventory(itemTotake);
            System.out.println(itemTotake.getName() + " was added to inventory");
        }
        else if(commandWord.equals("drop"))
        {
            Item itemToDrop;
            itemToDrop = player.removeItemFromInventroy(command.getSecondWord());
            player.getCurrentRoom().addItem(itemToDrop);
            System.out.println(itemToDrop.getName() + " was removed from inventory");
        }
        else if(commandWord.equals("eat"))
        {
            System.out.println("You hav eaten and are not hungry anymore.");
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
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
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     * Sets lastRoom to the currentRoom right before leaving the room.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = player.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            player.getPreviousRooms().push(player.getCurrentRoom());
            player.setCurrentRoom(nextRoom);
            printLocationInfo();
        }
    }
    
    /**
     * Goes back to the previues room.
     * If trying to go back from the very first room. Print a message.
     */
    private void goBack(Command command)
    {
        if(command.hasSecondWord()) 
        {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }
        
        if(!player.getPreviousRooms().isEmpty())
        {
            player.setCurrentRoom((Room) player.getPreviousRooms().pop());
            printLocationInfo();
        }
        else
        {
            System.out.println("You haven't left the first room yet.");
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    private void printLocationInfo()
    {
        System.out.println(player.getCurrentRoom().getLongDescription());
            System.out.println();
    }
    
    /**
     * Prints a long description of the room.
     */
    private void look()
    {
        System.out.println(player.getCurrentRoom().getLongDescription());
    }
}
