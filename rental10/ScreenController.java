/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rental10;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import rental10.model.MusicPlayerSingleton;
import rental10.model.SetControlScreen;

/**
 *
 * @author song
 */
public class ScreenController extends StackPane implements Initializable{
    /**
     * screens as tree with root, children as sub screens. 
     */
    private final HashMap<String, Node> theScreens = new HashMap<>();
    private MusicPlayerSingleton player = MusicPlayerSingleton.getInstance();
    /**
     * initialize screen.
     * @param screenName for which screen to be loaded
     * @param path screen path
     * @return successful loaded or not
     */
    public boolean loadScreen(String screenName, String path)
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view/" + path));         //load fxml
            Parent loadScreen = (Parent) loader.load();                //load scene
            SetControlScreen myScreenController = (SetControlScreen) loader.getController();       //return screen controller
            myScreenController.setScreenParent(this);
            theScreens.put(screenName, loadScreen);
            return true;
        } catch(Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean loadScreen(SetControlScreen s, String n, Parent p)
    {
        try {
            s.setScreenParent(this);
            theScreens.put(n, p);
            return true;
        } catch(Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    /**
     * setup the screen for displaying.
     * @param screenName current screen
     * @return load up or fail to load
     */
    public boolean setScreen (String screenName)
    {
        initMediaPlayer();
        if (theScreens.get(screenName) != null)
        {             
            if (!getChildren().isEmpty())
            {
                getChildren().remove(0); //remove the current displayed screen
                getChildren().add(0, theScreens.get(screenName)); //replace with the new screen
            } else {
                getChildren().add(theScreens.get(screenName)); //no screen has been displayed
            }
            return true;
        }
        else
        {
            System.out.println("Screen hasn't been loaded!\n");
            return false;
        }
    }    

    private void initMediaPlayer()
    {
        player.play();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
