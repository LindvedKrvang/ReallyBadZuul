/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controller;

import bll.Command;
import bll.CommandWords;
import gui.Game;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author Rasmus
 */
public class GameController implements Initializable
{
    Game game = new Game();
    private CommandWords commands = new CommandWords();
    
    @FXML
    private TextArea txtARoomInfo;
    @FXML
    private TextArea txtAItemInfo;
    @FXML
    private TextArea txtAGeneralInfo;
    @FXML
    private TextField txtCommands;
    @FXML
    private Button btnGO;
    @FXML
    private TextArea txtAInventory;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    
    
    /**
     * Starts the game.
     */
    public void startGame()
    {
        txtAGeneralInfo.setText(game.printWelcome());
        txtARoomInfo.setText(game.printLocationInfo());
        txtAInventory.setText(game.showInventory());
    }
    
    @FXML
    public void goButton(KeyEvent event)
    {
        if(event.getCode() == KeyCode.ENTER)
        {
            String[] userInput;
        userInput = txtCommands.getText().split(" ");
        String firstWord = userInput[0];
        String secondWord = null;
        if(userInput.length > 1)
        {
            secondWord = userInput[1];
        }        
        
        Command command = new Command(commands.getCommandWord(firstWord), secondWord);
        txtAGeneralInfo.appendText(game.processCommand(command));
        txtARoomInfo.setText(game.printLocationInfo());
        txtAItemInfo.setText(game.getItemDescription());
        txtAInventory.setText(game.showInventory());
        txtCommands.setText("");
        }        
    }
}