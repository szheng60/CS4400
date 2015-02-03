/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rental10;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import mysqlquery.QueryInfoMediator;
import mysqlquery.Visitor;
import static rental10.Rental.currentUser;
import rental10.model.ScreenTemplate;
import rental10.model.SetControlScreen;

/**
 * FXML Controller class.
 *
 * @author song
 */
public class NewApplicationFormController extends ScreenTemplate implements Initializable , SetControlScreen {

    ScreenController theController;
    private final transient QueryInfoMediator queryInfo = QueryInfoMediator.getInstance();
    private transient Visitor visitor = new Visitor();
    private transient FXMLLoader loader;
    @FXML
    private TextField residentName;    
    @FXML
    private ChoiceBox residentGender;
    @FXML
    private DatePicker residentDOB;
    @FXML
    private TextField residentIncome;
    @FXML
    private ComboBox residentMinRent;
    @FXML
    private ComboBox residentMaxRent;
    @FXML
    private DatePicker residentPrefMoveInDate;
    @FXML
    private ChoiceBox residentPrefLeaseTerm;
    @FXML
    private TextArea residentPrevAddress;
    @FXML
    private ComboBox residentPrefAptCategory;
    @FXML
    private Label errormsg;
     /**
      * Initializes the controller class.
     * @param url
     * @param rb
      */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.setTitleLabel(this.getNewApplicationFormScreen());            
            residentGender.setItems(FXCollections.observableArrayList("M", "F"));            
            final ObservableList<String> categoryList = FXCollections.observableList(this.apartmentCategory());
            final ObservableList<String> leaseTermList = FXCollections.observableArrayList("3", "6", "12");
            residentPrefLeaseTerm.setItems(leaseTermList);
            residentPrefLeaseTerm.setValue(leaseTermList.get(0));
            residentPrefAptCategory.setItems(categoryList);
            residentPrefAptCategory.valueProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    try {                    
                        final ObservableList<String> categoryRentList = FXCollections.observableList(this.apartmentCategoryRent(newValue));
//                        final ObservableList<String> leaseTermList = FXCollections.observableList(this.apartmentLeaseTerm(newValue));
                        residentMinRent.setItems(categoryRentList);
                        residentMinRent.setValue(categoryRentList.get(0));
                        residentMaxRent.setItems(categoryRentList);
                        residentMaxRent.setValue(categoryRentList.get(categoryRentList.size() - 1));
//                        residentPrefLeaseTerm.setItems(leaseTermList);
//                        residentPrefLeaseTerm.setValue(leaseTermList.get(0));
                    } catch (SQLException ex) {
                        Logger.getLogger(NewApplicationFormController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                public List<String> apartmentCategoryRent(final String aptCategoryType) throws SQLException
                {
                    return queryInfo.getAptCategoryRent(aptCategoryType);
                }
                public List<String> apartmentLeaseTerm(final String aptCategoryType) throws SQLException
                {
                    return queryInfo.getAptLeaseTerm(aptCategoryType);
                }
            });

            residentDOB.setValue(LocalDate.now());
            residentPrefMoveInDate.setValue(LocalDate.now());      
        } catch (SQLException ex) {
            Logger.getLogger(NewApplicationFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
    /**
     * 
     * @return list of available apartment categories
     * @throws SQLException 
     */
    public List<String> apartmentCategory() throws SQLException
    {
        return queryInfo.getAptCategory();        
    }
    /**
     * save application to the database.
     */
    @FXML
    private void homepageScreenHandler() throws SQLException, IOException
    {  
        if (residentName.getText().length() == 0)
        {
            errormsg.setText("Enter a Name");
        }
        else if (residentGender.getValue() == null)
        {
            errormsg.setText("Select a Gender");
        }
        else if (residentDOB.getValue().isAfter(LocalDate.now()))
        {
            errormsg.setText("Select a Valid DOB");
        }  
        else if (residentIncome.getText().length() == 0)
        {
            errormsg.setText("Enter an Income");
        }
        else if (residentPrefAptCategory.getValue() == null)
        {
            errormsg.setText("Select an Apartment Category");
        }
        else if (residentMinRent.getValue() == null)
        {
            errormsg.setText("Select a Min Rent");
        }
        else if (residentMaxRent.getValue() == null)
        {
            errormsg.setText("Select a Max Rent");
        }
        else if (residentPrefMoveInDate.getValue().compareTo(LocalDate.now().plusDays(60)) > 0)
        {
            errormsg.setText("Preferred Move In Date Is Greater Than 60 Days From Now");
        }
        else if (residentPrefMoveInDate.getValue().compareTo(LocalDate.now()) < 0)
        {
            errormsg.setText("Preferred Move In Date Is Before Today");
        }
        else if (residentPrefLeaseTerm.getValue() == null)
        {
            errormsg.setText("Select a Lease Term");
        }
        else if (residentPrevAddress.getText().length() == 0)
        {
            errormsg.setText("Enter the Previous Address");
        }
        else
        {
            final String name = residentName.getText();
            final String username = currentUser.getUsername();
            final String gender = residentGender.getValue().toString();
            final LocalDate dob = residentDOB.getValue();
            final double income = Double.parseDouble(residentIncome.getText());
            final String category = residentPrefAptCategory.getValue().toString();
            final double minRent = Double.parseDouble(residentMinRent.getValue().toString());
            final double maxRent = Double.parseDouble(residentMaxRent.getValue().toString());
            final LocalDate moveIn = residentPrefMoveInDate.getValue();
            final int leaseTerm = Integer.parseInt(residentPrefLeaseTerm.getValue().toString());
            final String prevAddress = residentPrevAddress.getText();
            
            final boolean submitted = queryInfo.applicationInfo(name, username, gender, dob, income, category, minRent, maxRent, moveIn, leaseTerm, prevAddress);
            if (submitted)
            {
                visitor.initHomepageInformation(theController, loader);
                theController.setScreen(this.getHomepageScreen());
            }
            else 
            {
                errormsg.setText("Error! Application Not Submitted!");
            }
        }    
    }
    /**
     * 
     * @param screen the main controller screen
     */
    @Override
    public void setScreenParent(ScreenController screen) {
        theController = screen;
    }
     
}
