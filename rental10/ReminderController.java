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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import mysqlquery.QueryInfoMediator;
import rental10.model.ScreenTemplate;
import rental10.model.SetControlScreen;

/**
 * FXML Controller class
 *
 * @author song
 */
public class ReminderController extends ScreenTemplate implements Initializable , SetControlScreen {

    /**
     *main controller. 
    */
    private transient ScreenController theController;
    /**
     *query mediator. 
    */
    private final transient QueryInfoMediator queryInfo = QueryInfoMediator.getInstance();
    
    @FXML
    private transient Label reminder;
    @FXML
    private transient Label date;
    @FXML
    private transient ChoiceBox aptNo;
    @FXML
    private transient Label errormsg;
    private transient String msg;
    /**
    * Initializes the controller class.
    */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.setTitleLabel(this.getReminderScreen());
        date.setText(LocalDate.now().toString());
        msg = reminder.getText();
    }     
    
    public void initReminderInformation() throws SQLException
    {
        final ObservableList<String> aptOptions = FXCollections.observableArrayList(queryInfo.getPastDueAptNo());
        aptNo.setItems(aptOptions);
        if (aptOptions.size() > 0)
        {
            aptNo.setValue(aptOptions.get(0));
        }
        aptNo.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                errormsg.setText("Want to Send Reminder to Apt#" + newValue);
            }
        });
    }
    /**
     * send button handler.
     */
    @FXML
    private void sendReminderHandler() throws SQLException
    {
        if (aptNo.getValue() != null)
        {
            final String apt_no = aptNo.getValue().toString();

            final String status = "unread";
            final boolean sent = queryInfo.reminderSend(apt_no, LocalDate.now(), msg, status);
            if (!sent)
            {
                errormsg.setText("Error! Reminder Not Sent Yet Becasue A Reminder Might Have Been Sent to Resident Already");
            }
            else
            {
                errormsg.setText("Reminder Has Sent to Apt#" + apt_no);
            }
        }
        else
        {
            errormsg.setText("Error! No Apt # is Selected");
        }
    }    
    /**
     * cancel button handler.
     */
    @FXML
    private void homeScreenHandler1()
    {
        theController.setScreen(this.getHomepageScreen());
    }
    @Override
    public void setScreenParent(ScreenController screen) {
        theController = screen;
    }
     
}
