/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rental10;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import mysqlquery.QueryInfoMediator;
import mysqlquery.Visitor;
import rental10.model.Application;
import rental10.model.ScreenTemplate;
import rental10.model.SetControlScreen;

/**
 * FXML Controller class
 *
 * @author song
 */
public class ApplicationReviewController extends ScreenTemplate implements Initializable, SetControlScreen {

    /**
     *main controller. 
    */
    private transient ScreenController theController;
    /**
     *query mediator. 
    */
    private final transient QueryInfoMediator queryInfo = QueryInfoMediator.getInstance();
    private transient Visitor visitor = new Visitor();
    @FXML
    private TableView tableApplication;
    @FXML
    private TableColumn nameCol;
    @FXML
    private TableColumn dobCol;
    @FXML
    private TableColumn genderCol;
    @FXML
    private TableColumn incomeCol;
    @FXML
    private TableColumn aptRequestCol;
    @FXML
    private TableColumn moveInDateCol;
    @FXML
    private TableColumn termCol;
    @FXML
    private TableColumn raCol;    
    @FXML
    private TableColumn actionCol;
    @FXML
    private Label errormsg;
    private transient boolean allotable;
    private transient String username;
    /**
    * Initializes the controller class.
    */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.setTitleLabel(this.getApplicationReviewScreen());
        allotable = false;
        nameCol.setCellValueFactory(new PropertyValueFactory<Application, String>("name"));
        dobCol.setCellValueFactory(new PropertyValueFactory<Application, String>("dob"));
        genderCol.setCellValueFactory(new PropertyValueFactory<Application, String>("gender"));
        incomeCol.setCellValueFactory(new PropertyValueFactory<Application, String>("income"));
        aptRequestCol.setCellValueFactory(new PropertyValueFactory<Application, String>("apt"));
        moveInDateCol.setCellValueFactory(new PropertyValueFactory<Application, String>("moveDate"));
        termCol.setCellValueFactory(new PropertyValueFactory<Application, String>("term"));
        raCol.setCellValueFactory(new PropertyValueFactory<Application, String>("approval"));
        tableApplication.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Application>() {
                    @Override
                    public void changed(ObservableValue<? extends Application> observable, Application oldValue, Application newValue) {
                        if (newValue == null)
                        {
                            errormsg.setText("");
                            return;
                        }
                        else if (newValue.getApproval().equalsIgnoreCase("rejected"))
                        {
                            username = "";
                            allotable = false;
                            errormsg.setText("Warning! Only Approved Application Can Be Alloted An Apartment");
                        }
                        else if (newValue.getApproval().equalsIgnoreCase("approved"))
                        {
                            username = newValue.getUsername();
                            allotable = true;
                            errormsg.setText("");
                        }
                    }
                }      
        );            
    }     
    
    public void initApplicationReviewInformation() throws SQLException
    {
        tableApplication.setItems(getApplications(queryInfo.getApplicationInformation()));
    }
    
    private ObservableList<Application> getApplications(final List<String[]> applicationList) throws SQLException
    {        
        List<Application> applications = new ArrayList<Application>();
        for (String[] s : applicationList)
        {
            String applicationStatus;
            final String name = s[0];
            final String dob = s[2];
            final String gender = s[6];
            final String income = s[3];
            final String pref_apt = s[9];
            final String pref_moveDate = s[4];
            final String term = s[5];
            final String minRent = s[7];
            final String maxRent = s[8];
            final String username = s[1];
            final List<String> aptAvaiableOnList = queryInfo.getAptAvailableOn(pref_apt);
            boolean aptAvailable = false;
            for (String a:aptAvaiableOnList)
            {
                if (Date.valueOf(pref_moveDate).compareTo(Date.valueOf(a)) > 0)
                {
                    aptAvailable = true;
                    break;
                }                
            }
            if ((Double.parseDouble(income) < Double.parseDouble(minRent) * 3) || !aptAvailable)
            {
                applicationStatus = "rejected";
            }
            else
            {
                applicationStatus = "approved";
            }
            applications.add(new Application(name, dob, gender, income, pref_apt, pref_moveDate, term, applicationStatus, username));
        }
        final ObservableList<Application> data = FXCollections.observableArrayList(applications);
        
        return data;
    }
    /**
     *next button handler. 
    */
    @FXML
    private void apartmentAllotmentScreenHandler() throws IOException, SQLException
    {
        if (allotable)
        {
            FXMLLoader loader = new FXMLLoader();
            visitor.initApartmentAllotment(theController, loader, username);
            theController.setScreen(this.getApartmentAllotmentScreen());
        }
    }
    /**
     *cancel button handler. 
    */
    @FXML
    private void homeScreenHandler()
    {
        theController.setScreen(this.getHomepageScreen());
    }
    @Override
    public void setScreenParent(final ScreenController screen) {
        theController = screen;
    }
     
}
