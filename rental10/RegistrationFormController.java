/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rental10;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import mysqlquery.QueryInfoMediator;
import rental10.model.ScreenTemplate;
import rental10.model.SetControlScreen;

/**
 * FXML Controller class
 *
 * @author song
 */
public class RegistrationFormController extends ScreenTemplate implements Initializable , SetControlScreen {

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirmPassword;
    @FXML
    private Label registerLabel;
    /**
     * main controller.
     */
    private transient ScreenController theController;
    private final transient QueryInfoMediator queryInfo = QueryInfoMediator.getInstance();
    /**
    * Initializes the controller class.
    */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.setTitleLabel(this.getRegistrationFormScreen());
    }     
    /**
     *return to main page. 
    */
    @FXML
    private void mainpageScreenHandler1()
    {
        theController.setScreen(this.getMainpageScreen());
    }
    /**
     *switch to application page if successfully registered. 
    */
    @FXML
    private void newApplicationFormHandler()
    {
        if (username.getText().length() == 0)
        {
            registerLabel.setText("Enter Name");
        }
        else if (password.getText().length() == 0)
        {
            registerLabel.setText("Enter Password");
        }
        else if (confirmPassword.getText().length() == 0)
        {
            registerLabel.setText("Confirm Password");
        }
        else if (!password.getText().equals(confirmPassword.getText()))
        {
            registerLabel.setText("Passwords do not match");
        }
        else
        {
            final boolean registered = queryInfo.userRegistration(username.getText(), password.getText());
            if (registered)
            {
                theController.setScreen(this.getNewApplicationFormScreen());                
            }
            else
            {
                registerLabel.setText("Error! Username Exists!");
            }
        }
    }
    @Override
    public void setScreenParent(final ScreenController screen) {
        theController = screen;
    }
     
}
