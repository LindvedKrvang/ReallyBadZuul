
import java.util.HashMap;

/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

public class CommandWords
{    
    private HashMap<String, CommandWord> validCommands;
    

    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
        validCommands = new HashMap<>();
        for (CommandWord command : CommandWord.values())
        {
            if(command != CommandWord.UNKOWN)
            {
                validCommands.put(command.toString(), command);
            }
        }
    }
    
    /**
     * Find the CommandWord associated with a command word.
     * @param commandWord the word to look up.
     * @return The CommandWord corresponding to commandWord, or UNKOWN if it is
     * not a valid command.
     */
    public CommandWord getCommandWord(String commandWord)
    {
        CommandWord command = validCommands.get(commandWord);
        if(command != null)
        {
            return command;
        }
        else
        {
            return CommandWord.UNKOWN;
        }
    }
    
    /**
     * Check whether a given String is a valid command word. 
     * @param aString
     * @return true if a given string is a valid command,
     * false if it isn't.
     */
    public boolean isCommand(String aString)
    {
        return validCommands.containsKey(aString);
    }
    
    /**
     * Return a String with all commands.
     * @return All commands in a String.
     */
    public String showALl()
    {
        String allCommands = "";
        for(String command : validCommands.keySet())
        {
            allCommands += command + " ";
        }
        return allCommands;
    }
}
