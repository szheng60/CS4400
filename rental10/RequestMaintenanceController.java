/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rental10;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import mysqlquery.QueryInfoMediator;
import static rental10.Rental.currentUser;
import rental10.model.ScreenTemplate;
import rental10.model.SetControlScreen;

/**
 * FXML Controller class
 *
 * @author song
 */
public class RequestMaintenanceController extends ScreenTemplate implements Initializable , SetControlScreen {

    /**
     *main controller. 
    */
    private transient ScreenController theController;
    /**
     *query info mediator. 
    */
    private final transient QueryInfoMediator queryInfo = QueryInfoMediator.getInstance();
    /**
     * current username.
     */
    private final transient String username = currentUser.getUsername();
    /**
     *request maintenance apt no. 
    */
    @FXML
    private transient TextField rmAptNo;
    /**
     * request maintenance issue type for the apt.
     */
    @FXML
    private transient ComboBox rmIssue;
    /**
     *request maintenance date. 
    */
    @FXML
    private transient Label rmDate;
    /**
     *display error message. 
    */
    @FXML
    private transient Label errormsg;
    
    public RequestMaintenanceController()
    {
        rmAptNo = new TextField();
        rmIssue = new ComboBox();
        rmDate = new Label();
    }
    /**
    * Initializes the controller class.
    */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.setTitleLabel(this.getRequestMaintenanceScreen());
    }
    /**
     *initiate information for current user. 
    */
    public void initRequestMaintenance() throws SQLException
    {
        String apt_no = queryInfo.getResidentAptNo(username);
        if (!apt_no.equalsIgnoreCase("null"))
        {
            rmAptNo.setText(apt_no);
        }
        final ObservableList<String> issueOptions = FXCollections.observableArrayList(queryInfo.getIssueTypes());
        rmIssue.setItems(issueOptions);
        rmDate.setText(LocalDate.now().toString());
    }
    /**
     * submit request button.
     * @throws SQLException 
     */
    @FXML
    private void homeScreenHandler() throws SQLException
    {
        if (rmAptNo.getText().length() == 0)
        {
            errormsg.setText("Error! Make Sure An Apartment Has Been Assigned To You");
        }
        else if (rmIssue.getValue() == null)
        {
            errormsg.setText("Error! No Issue Selected");
        }
        else
        {
            final String apt_no = rmAptNo.getText();
            final String issue = rmIssue.getValue().toString();
            final LocalDate date = LocalDate.now(); 
            final boolean requested = queryInfo.requestMaintenanceSubmit(apt_no, issue, date);
            if (requested)
            {
                theController.setScreen(this.getHomepageScreen());
            }
            else
            {
                errormsg.setText("Error! Request Not Send Yet");
            }
        }
    }
    /**
     *cancel button handler. 
    */
    @FXML
    private void homeScreenHandler1() 
    {
        theController.setScreen(this.getHomepageScreen());
    }
    @Override
    public void setScreenParent(final ScreenController screen) {
        theController = screen;
    }
     
}
