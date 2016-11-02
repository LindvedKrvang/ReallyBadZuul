
import gui.controller.GameController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Stegger
 */
public class Zuul extends Application
{
    
    public static void main(String[] args)
    {
        launch(args);
       
    }
    
    /**
     * Loads the GUI.
     * @param stage
     * @throws Exception 
     */
    @Override
    public void start(Stage stage) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gui/view/gameView.fxml"));
        AnchorPane root = (AnchorPane)loader.load();
        
        Scene scene = new Scene(root);
        
        GameController gController = (GameController)loader.getController();
        gController.startGame();
        
        stage.setScene(scene);
        stage.show();
        
        
    }
}
