package bll;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rasmus
 */
public enum CommandWord
{
    //A value for each command word, plus one for unrecognized.
    // commands.
    GO("go"), QUIT("quit"), HELP("help"), UNKOWN("?"), LOOK("look"), EAT("eat"), 
    BACK("back"), TAKE("take"), DROP("drop"), INVENTORY("inventory");
    
    //The Command String.
    private String commandString;
    
    /**
     * Initialize with the corresponding command String.
     * @param commandString The command String.
     */
    CommandWord(String commandString)
    {
        this.commandString = commandString;
    }
    
    /**
     * Gets the command word.
     * @return The command word as a String.
     */
    public String toString()
    {
        return commandString;
    }
}
