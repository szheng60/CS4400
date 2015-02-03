/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rental10;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javax.swing.JOptionPane;
import mysqlquery.QueryInfoMediator;
import mysqlquery.Visitor;
import static rental10.Rental.currentUser;
import rental10.model.ScreenTemplate;
import rental10.model.SetControlScreen;

/**
 * FXML Controller class
 *
 * @author song
 */
public class HomepageController extends ScreenTemplate implements Initializable, SetControlScreen{
    /**
     *main controller. 
    */
    private transient ScreenController theController;
        /**
     *query mediator. 
    */
    private final transient QueryInfoMediator queryInfo = QueryInfoMediator.getInstance();
    private transient Visitor visitor = new Visitor();
    private transient FXMLLoader loader;
    @FXML
    private transient Label residentRent;
    @FXML  
    private transient Label residentRequest;
    @FXML
    private transient Label residentPayment;
    @FXML  
    private transient Label msg1;
    @FXML
    private transient Label msg2;
    @FXML
    private transient Label msgCount;
    @FXML
    private transient Label managementReview;
    @FXML  
    private transient Label managementRequest;
    @FXML
    private transient Label managementReminder;
    @FXML  
    private transient Label managementReport;
    @FXML
    private transient ComboBox managementReportBox;
    @FXML
    private transient Label residentAptNo;
    @FXML
    private transient Label residentAptNoLabel;
    private transient String username;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.setTitleLabel(this.getHomepageScreen());
        managementReportBox.setItems(FXCollections.observableArrayList(
            "Leasing Report", "Service Report", "Default Report"));
        managementReportBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.equalsIgnoreCase("Leasing Report"))
                {
                    try {
                        leasingReportHandler();
                    } catch (IOException ex) {
                        Logger.getLogger(HomepageController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(HomepageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else if (newValue.equalsIgnoreCase("Service Report"))
                {
                    try {
                        serviceReportHandler();
                    } catch (IOException ex) {
                        Logger.getLogger(HomepageController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(HomepageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else if (newValue.equalsIgnoreCase("Default Report"))
                {
                    try {
                        defaultReportHandler();
                    } catch (IOException ex) {
                        Logger.getLogger(HomepageController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(HomepageController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }    
    public void initHomepageInformation() throws SQLException
    {
        username = currentUser.getUsername();
        final boolean isAptAlloted = queryInfo.isResidentAptAlloted(username);
        if (!isAptAlloted)
        {
            this.residentAptNo.setText("Your Application is Still Under Review");
            residentRent.setVisible(false);
            residentRequest.setVisible(false);
            residentPayment.setVisible(false);
            msg1.setVisible(false);
            msg2.setVisible(false);
            msgCount.setVisible(false);
        }
        else{
            this.residentAptNo.setText(queryInfo.getResidentAptNo(username));
        }
        if (!isAptAlloted && currentUser.getGroup().equalsIgnoreCase("resident"))
        {
            JOptionPane.showMessageDialog(null, "Your Application is Under Review");
        }
        setFunctionVisible();
        int msgNo = queryInfo.getResidentReminderCount(username);
        if (msgNo > 0)
        {
            msgCount.setText(String.valueOf(msgNo));
            msgCount.setTextFill(Color.web("#ff0000"));
        }
        else
        {
            msgCount.setTextFill(Color.web("000000"));
        }
    }
    @FXML
    private void messagePopHandler() throws SQLException
    {
        if (!msgCount.getText().equalsIgnoreCase("0"))
        {
            final List<String> msg = queryInfo.getResidentReminder(username);
            String displayMsg = "";
            for (String s : msg)
            {
                displayMsg += (s + "\n");
            }
            JOptionPane.showMessageDialog(null, displayMsg, "Reminders", JOptionPane.INFORMATION_MESSAGE);
            final boolean read = queryInfo.updateReminder(username);
            if (read)
            {
                msgCount.setText("0");
                this.initHomepageInformation();
            }
        }
    }
    @FXML
    private void rentPaymentScreenHandler() throws SQLException, IOException
    {
        if (currentUser.getGroup().equalsIgnoreCase("RESIDENT"))
        {
            visitor.initPayRentInformation(theController, loader);
            theController.setScreen(this.getRentPaymentScreen());
        }
    }
    
    @FXML
    private void requestMaintenanceScreenHandler() throws IOException, SQLException
    {
        if (currentUser.getGroup().equalsIgnoreCase("RESIDENT"))
        {
            visitor.initRequestMaintenance(theController, loader);
            theController.setScreen(this.getRequestMaintenanceScreen());
        }
    }
    @FXML
    private void paymentInformationScreenHandler() throws IOException, SQLException
    {
        if (currentUser.getGroup().equalsIgnoreCase("RESIDENT"))
        {
            visitor.initPaymentInformation(theController, loader);
            theController.setScreen(this.getPaymentInformationScreen());
        }
    }
    /**
     * management.
     * @throws IOException
     * @throws SQLException 
     */
    @FXML
    private void applicationReviewScreenHandler() throws IOException, SQLException
    {
        if (currentUser.getGroup().equalsIgnoreCase("MANAGEMENT"))
        {
            visitor.initApplicationReview(theController, loader);
            theController.setScreen(this.getApplicationReviewScreen());
        }
    }
    @FXML
    private void viewMaintenanceRequestScreenHandler() throws IOException, SQLException
    {
        if (currentUser.getGroup().equalsIgnoreCase("MANAGEMENT"))
        {        
            visitor.initViewMaintenanceRequest(theController, loader);
            theController.setScreen(this.getViewMaintenanceRequestScreen());
        }
    }
    @FXML
    private void reminderScreenHandler() throws IOException, SQLException
    {
        if (currentUser.getGroup().equalsIgnoreCase("MANAGEMENT"))
        {        
            visitor.initReminder(theController, loader);
            theController.setScreen(this.getReminderScreen());
        }
    }
    private void leasingReportHandler() throws IOException, SQLException
    {
        if (currentUser.getGroup().equalsIgnoreCase("MANAGEMENT"))
        {        
            visitor.initLeasingReport(theController, loader);
            theController.setScreen(this.getLeasingReportScreen());
        }
    }
    private void serviceReportHandler() throws IOException, SQLException
    {
        if (currentUser.getGroup().equalsIgnoreCase("MANAGEMENT"))
        {        
            visitor.initServiceRequestReport(theController, loader);
            theController.setScreen(this.getServiceRequestReportScreen());
        }
    }        
    private void defaultReportHandler() throws IOException, SQLException
    {
        if (currentUser.getGroup().equalsIgnoreCase("MANAGEMENT"))
        {        
            visitor.initRentDefaulterReport(theController, loader);
            theController.setScreen(this.getRentDefaultReportScreen());
        }
    }    
    @Override
    public void setScreenParent(ScreenController screen) {
        theController = screen;
    }
    /**
     * set available functions for different group user.
     */
    private void setFunctionVisible()
    {
        if (currentUser.getGroup().equalsIgnoreCase("RESIDENT"))
        {
            managementReview.setVisible(false);
            managementRequest.setVisible(false);
            managementReminder.setVisible(false);
            managementReportBox.setVisible(false);
        }        
        else if (currentUser.getGroup().equalsIgnoreCase("MANAGEMENT"))
        {
            residentAptNo.setVisible(false);
            residentAptNoLabel.setVisible(false);
            residentRent.setVisible(false);
            residentRequest.setVisible(false);
            residentPayment.setVisible(false);
            msg1.setVisible(false);
            msg2.setVisible(false);
            msgCount.setVisible(false);
        }        
    }
}
