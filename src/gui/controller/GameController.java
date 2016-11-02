/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controller;

import gui.Game;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Rasmus
 */
public class GameController implements Initializable
{
    Game game = new Game();
    
    @FXML
    private TextArea txtARoomInfo;
    @FXML
    private TextArea txtAItemInfo;
    @FXML
    private TextArea txtAGeneralInfo;
    @FXML
    private TextField txtCommands;

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
    }
}
