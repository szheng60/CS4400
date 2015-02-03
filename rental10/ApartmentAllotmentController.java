/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rental10;

import java.net.URL;
import java.sql.SQLException;
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
import rental10.model.Apartment;
import rental10.model.Application;
import rental10.model.ScreenTemplate;
import rental10.model.SetControlScreen;

/**
 * FXML Controller class
 *
 * @author song
 */
public class ApartmentAllotmentController extends ScreenTemplate implements Initializable, SetControlScreen {

    /**
     *main controller. 
    */
    private transient ScreenController theController;
    /**
     *query mediator. 
    */
    private final transient QueryInfoMediator queryInfo = QueryInfoMediator.getInstance();
    
    @FXML
    private TableView tableApartment;
    @FXML
    private TableColumn aptnocol;
    @FXML
    private TableColumn catcol;
    @FXML
    private TableColumn rentcol;
    @FXML
    private TableColumn sqftcol;
    @FXML
    private TableColumn availcol;
    @FXML
    private Label applicantName;
    @FXML
    private Label errormsg;
    private transient String username;
    private transient String aptAssign;
    /**
    * Initializes the controller class.
    */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.setTitleLabel(this.getApartmentAllotmentScreen());
        aptnocol.setCellValueFactory(new PropertyValueFactory<Apartment, String>("aptno"));
        catcol.setCellValueFactory(new PropertyValueFactory<Apartment, String>("category"));
        rentcol.setCellValueFactory(new PropertyValueFactory<Apartment, String>("monthlyRent"));
        sqftcol.setCellValueFactory(new PropertyValueFactory<Apartment, String>("sqft"));
        availcol.setCellValueFactory(new PropertyValueFactory<Apartment, String>("availableDate"));
        tableApartment.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Apartment>() {
                    @Override
                    public void changed(ObservableValue<? extends Apartment> observable, Apartment oldValue, Apartment newValue) {
                        if (newValue == null)
                        {
                            errormsg.setText("");
                            aptAssign = "";
                            return;
                        }
                        aptAssign = newValue.getAptno();
                        errormsg.setText("  Assign Apt#: " + aptAssign);
                    }
                }      
        ); 
    }     
    public void initApartmentAllotmentInformation(final String username) throws SQLException
    {
        this.username = username;
        tableApartment.setItems(getApplications(queryInfo.getApartmentCategoryInformation(username)));  
        String name = queryInfo.getApplicantName(username);
        if (name.length() > 0)
        {
            applicantName.setText(name);
        }
    }
    private ObservableList<Apartment> getApplications(final List<String[]> apartmentList)
    {
        List<Apartment> apartments = new ArrayList<>();
        for (String[] s : apartmentList)
        {
            final String apt_no = s[0];
            final String category = s[1];
            final String rent = s[2];
            final String sqft = s[3];
            final String date = s[4];
            apartments.add(new Apartment(apt_no, category, rent, sqft, date));
        }
        final ObservableList<Apartment> data = FXCollections.observableArrayList(apartments);        
        return data;       
    }
    /**
     *assign button handler. 
    */
    @FXML
    private void homepageScreenHandler() throws SQLException
    {
        if (aptAssign.length() > 0)
        {
            final String leaseTerm = queryInfo.getResidentLeaseTerm(username);
            if (leaseTerm != null)
            {
                final boolean assigned = queryInfo.apartmentAssign(username, aptAssign, leaseTerm);
                if (assigned)
                {
                    theController.setScreen(this.getHomepageScreen());
                }
                else
                {
                    errormsg.setText("Error! Apartment Not Assigned Yet");
                }
            }
        }
    }
    /**
     * cancel button handler.
     */
    @FXML
    private void homepageScreenHandler1()
    {
        theController.setScreen(this.getHomepageScreen());
    }
    @Override
    public void setScreenParent(ScreenController screen) {
        theController = screen;
    }
     
}
