/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rental10;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import mysqlquery.QueryInfoMediator;
import rental10.model.Application;
import rental10.model.Maintenance;
import rental10.model.ScreenTemplate;
import rental10.model.SetControlScreen;

/**
 * FXML Controller class
 *
 * @author song
 */
public class ViewMaintenanceRequestController extends ScreenTemplate implements Initializable, SetControlScreen {
    /**
     *main controller. 
    */
    private transient ScreenController theController;
    /**
     *query mediator. 
    */
    private final transient QueryInfoMediator queryInfo = QueryInfoMediator.getInstance();
    
    @FXML
    private transient TableView tableRequested;
    @FXML
    private transient TableView tableResolved;
    @FXML
    private transient TableColumn dorRequested;
    @FXML
    private transient TableColumn aptRequested;     
    @FXML
    private transient TableColumn doiRequested;
    @FXML
    private transient TableColumn dorResolved;
    @FXML
    private transient TableColumn aptResolved;     
    @FXML
    private transient TableColumn doiResolved;
    @FXML
    private transient TableColumn dateResolved;
    @FXML
    private transient Label errormsg;
    private transient String resolvedForAptNo;
    private transient String resolvedForIssue;
    private transient String resolvedForDate;
    /**
    * Initializes the controller class.
    */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.setTitleLabel(this.getViewMaintenanceRequestScreen());
        dorRequested.setCellValueFactory(new PropertyValueFactory<Maintenance, String>("dateRequested"));
        aptRequested.setCellValueFactory(new PropertyValueFactory<Maintenance, String>("apt"));
        doiRequested.setCellValueFactory(new PropertyValueFactory<Maintenance, String>("issue"));
        
        dorResolved.setCellValueFactory(new PropertyValueFactory<Maintenance, String>("dateRequested"));
        aptResolved.setCellValueFactory(new PropertyValueFactory<Maintenance, String>("apt"));
        doiResolved.setCellValueFactory(new PropertyValueFactory<Maintenance, String>("issue"));
        dateResolved.setCellValueFactory(new PropertyValueFactory<Maintenance, String>("dateResolved"));
        tableRequested.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<Maintenance>() {
                @Override
                public void changed(ObservableValue<? extends Maintenance> observable, Maintenance oldValue, Maintenance newValue) {
                    if (newValue == null)
                    {
                        resolvedForAptNo = "";
                        resolvedForIssue = "";
                        resolvedForDate = "";
                    }
                    else
                    {
                        resolvedForAptNo = newValue.getApt();
                        resolvedForIssue = newValue.getIssue();
                        resolvedForDate = newValue.getDateRequested();
                    }
                }
            }      
        );   
    }     
    public void initViewMaintenanceRequestInformation() throws SQLException
    {
        List<String[]> issueRequested = queryInfo.getIssueRequested();
        tableRequested.setItems(getUnresolvedRequests(issueRequested));
        tableResolved.setItems(getResolvedRequests(issueRequested));
    }
    private ObservableList<Maintenance> getUnresolvedRequests(final List<String[]> requestList)
    {
        List<Maintenance> maintenances = new ArrayList<>();
        for (String[] s : requestList)
        {
            final String dateRequested = s[0];
            final String apt = s[1];
            final String issue = s[2];
            final String dateResolved = s[3];
            if (dateResolved == null)
            {
                maintenances.add(new Maintenance(dateRequested, apt, issue, " "));
            }
        }
        final ObservableList<Maintenance> data = FXCollections.observableArrayList(maintenances);
        return data;
    }
    private ObservableList<Maintenance> getResolvedRequests(final List<String[]> requestList)
    {
        List<Maintenance> maintenances = new ArrayList<>();
        for (String[] s : requestList)
        {
            final String dateRequested = s[0];
            final String apt = s[1];
            final String issue = s[2];
            final String dateResolved = s[3];
            if (dateResolved != null)
            {
                maintenances.add(new Maintenance(dateRequested, apt, issue, dateResolved));
            }
        }
        final ObservableList<Maintenance> data = FXCollections.observableArrayList(maintenances);
        return data;
    }
    @FXML
    private void issueResolvedHandler() throws SQLException
    {
        final boolean resolved = queryInfo.requestResolvedForApt(resolvedForAptNo, resolvedForIssue, resolvedForDate, LocalDate.now());
        if (resolved)
        {
            this.initViewMaintenanceRequestInformation();
        }
        else
        {
            errormsg.setText("Error! Issue not Resolved");
        }
    }
    @FXML
    private void homeScreenHandler()
    { 
        theController.setScreen(this.getHomepageScreen());
    }
    @Override
    public void setScreenParent(ScreenController screen) {
        theController = screen;     }
     
}
