/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rental10;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import mysqlquery.QueryInfoMediator;
import mysqlquery.Visitor;
import rental10.model.RentalAppScreenNames;
import rental10.model.SetControlScreen;

/**
 *
 * @author song
 */
public class MainpageController extends RentalAppScreenNames implements Initializable, SetControlScreen {
    private transient Visitor visitor = new Visitor();
    private transient FXMLLoader loader;
    /**
     * main controller for screen swap.
     */
    private transient ScreenController theController;
    /**
     * query info holder that pass on to mediator for querying.
     */
    private final transient QueryInfoMediator queryInfo = QueryInfoMediator.getInstance();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }     
     
    /**
     * userName input from screen.
     */
    @FXML
    private transient TextField userName;
    
    /**
     *password input from screen. 
    */
    @FXML
    private transient PasswordField password;
    
    /**
     *display on the screen. 
    */
    @FXML
    private transient Label loginLabel;
     
    /**
     * handler for verifying user login information.
     * @throws SQLException 
     */
    @FXML
    private void loginHandler() throws SQLException, IOException {
        boolean credential = false;
        String msg;
        if (userName.getText().length() == 0)
        {
            loginLabel.setText("No UserName!");
        }
        else if (password.getText().length() == 0)
        {
            loginLabel.setText("No Password!");
        }
        else
        {
            credential = queryInfo.userCredential(userName.getText(), password.getText());            
            if (credential)
            {
                visitor.initHomepageInformation(theController, loader);
                theController.setScreen(this.getHomepageScreen());
            }
            else
            {
                msg = "Wrong UserName or Password";
                displayMsg(msg);
            }
        }
    }
    /**
     * @param msg message to be displayed.
     */
    private void displayMsg(final String msg)
    {
        loginLabel.setText(msg);
    }
    /**
     *prospective resident registration handler.
     */
    @FXML
    private void registrationFormHandler()
    {
        theController.setScreen(this.getRegistrationFormScreen());
    }
    /**
     * @param screen the main controller
    */
    @Override
    public void setScreenParent(final ScreenController screen) {
        theController = screen;
    }
         @FXML
    private void newApplicationFormHandler()
    {
        theController.setScreen(this.getNewApplicationFormScreen());
    }
}
