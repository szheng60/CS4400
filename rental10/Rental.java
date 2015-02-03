/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rental10;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mysqlquery.Facade;
import mysqlquery.QueryInfoMediator;
import rental10.model.User;
import rental10.model.RentalAppScreenNames;
/**
 *
 * @author song
 */
public class Rental extends Application {
    /**
     *screens' name for swapping. 
    */
    private final transient RentalAppScreenNames theNames;

    /**
     * query information to be passed on.
     */
    public transient QueryInfoMediator queryInfo;
    /**
     * initialize screen names and user factory.
     */
    public static User currentUser;
    /**
     * facade for query with DB.
     */
    public static Facade facade;
    /**
     *initialize. 
    */
    public Rental() {
        super();
        this.queryInfo = QueryInfoMediator.getInstance();
        this.theNames = new RentalAppScreenNames();
        facade = new Facade();
    }
    /**
    * @param stage the application stage.
    */
    @Override
    public void start(final Stage stage) 
    {      
        final ScreenController main = new ScreenController();
        initialize(main);
        main.setScreen(theNames.getMainpageScreen());          
        final Group root = new Group();
        
        root.getChildren().add(main);
        final Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * 
     * @param main the main controller
     */
    private void initialize(final ScreenController main)
    {
        for (int i = 0; i < 5; i++)
        {
            String s = theNames.getScreenNames()[i];
            main.loadScreen(s, s + ".fxml");
        }
    }

    /**
     *
     * @param args start application
     */
    public static void main(final String[] args) {
        launch(args);
    }
    
}
