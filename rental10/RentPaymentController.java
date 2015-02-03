
package rental10;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import mysqlquery.QueryInfoMediator;
import static rental10.Rental.currentUser;
import rental10.model.ScreenTemplate;
import rental10.model.SetControlScreen;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
/**
 * FXML Controller class
 *
 * @author song
 */
public class RentPaymentController extends ScreenTemplate implements Initializable , SetControlScreen {

    /**
     *main controller. 
    */
    private transient ScreenController theController;
    /**
     *query mediator. 
    */
    private final transient QueryInfoMediator queryInfo = QueryInfoMediator.getInstance();
    /**
     * current username.
     */
    private final transient String username = currentUser.getUsername();
    /**
     * month converter.
     */
    private final transient DateFormatSymbols symbols = new DateFormatSymbols();  
    /**
     * rent pay date.
     */
    @FXML
    private transient DatePicker rentPayDate;
    /**
     * rent pay for apt no.
     */
    @FXML
    private transient TextField rentPayAptNo;
    /**
     *rent amount for the apt.
    */
    @FXML
    private transient TextField rentAmount;
    /**
     *rent pay for month. 
    */
    @FXML
    private transient ChoiceBox rentForMonth;
    /**
     *rent pay for year.
    */
    @FXML
    private transient ChoiceBox rentForYear;
    /**
     *rent pay use card no. 
    */
    @FXML
    private transient ChoiceBox rentPayUseCard;
    /**
     *displaying error message.
    */
    @FXML
    private transient Label errormsg;
    DecimalFormat df = new DecimalFormat("#.##");   
    private LocalDate moveInDate;
    private double proratedRent = 0.0;
    private boolean prorated = false;
    private double aptRent = 0.0;
    private double fine = 0.0;
    private boolean overDued = false;
    private int currentYear = LocalDate.now().getYear();
    private int currentMonth = LocalDate.now().getMonthValue();
    private int currentDay = LocalDate.now().getDayOfMonth();
    private int daysOfMonth = LocalDate.now().lengthOfMonth();
    
    
    
    /**
    * Initializes the controller class.
    */  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.setTitleLabel(this.getRentPaymentScreen());
        this.rentPayDate.setValue(LocalDate.now());
    }     
    
    /**
     *initiate pop downs. 
    */
    @FXML
    public void initPayRentInformation() throws SQLException
    {      
        final String apt_no = queryInfo.getResidentAptNo(username);
        final String aptRent = queryInfo.getResidentAptDue(username, currentMonth, daysOfMonth, currentDay);
        moveInDate = queryInfo.getResidentMoveInDate(username);
        if (moveInDate != null)
        {
            this.moveInDate = moveInDate;
        }
        final int moveInMonth = moveInDate.getMonthValue();
        
        if (!aptRent.equalsIgnoreCase("null"))
        {
            this.rentPayAptNo.setText(apt_no);
        }
        final ObservableList<String> monthOptions = FXCollections.observableArrayList(queryInfo.getMonths());
        final ObservableList<Integer> yearOptions = FXCollections.observableArrayList(queryInfo.getYears());
        rentForMonth.setItems(monthOptions);
        rentForMonth.setValue(formatMonth());
        rentForYear.setItems(yearOptions);
        rentForYear.setValue(LocalDate.now().getYear());

        final ObservableList<Long> cardOptions = FXCollections.observableArrayList(queryInfo.getResidentCards(username));
        rentPayUseCard.setItems(cardOptions);
        if (cardOptions.size() > 0)
        {
            rentPayUseCard.setValue(cardOptions.get(0));
        }
        String year = rentForYear.getValue().toString();
        rentForMonth.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try 
                {                    
                    final String month = String.valueOf(deformatMonth(newValue));   //convert month to integer
                    if (queryInfo.getRentPaidForMonth(month, year, apt_no))
                    {
                        rentAmount.setText("Paid");
                        errormsg.setText("");
                    }
                    else
                    {
                        final String apt_due = aptRent;//aptDueAmount(moveInYear, moveInMonth, moveInDay, currentYear, deformatMonth(newValue), currentDay);
                        if (!apt_due.equalsIgnoreCase("null") && moveInMonth < deformatMonth(newValue))
                        {
                            rentAmount.setText(apt_due);
                        }
                        else
                        {
                            rentAmount.setText("");
                        }
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(NewApplicationFormController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    /**
     * 
     * @return string format for month.
     */
    private String formatMonth()
    {
        int m = LocalDate.now().getMonthValue();
        return symbols.getMonths()[m];
    }
    /**
     *@param month selected month.
     * @return integer format for month
    */
    public int deformatMonth(final String month)
    {
        int m = 0;
        for (int i = 1; i < 13; i++)
        {
            if (month.equalsIgnoreCase(symbols.getMonths()[i - 1]))
            {
                m = i;
                break;
            }
        }
        return m;
    }
        
    /**
     *pay button handler. 
    */
    @FXML
    private void homeScreenHandler() throws SQLException
    {
        if (rentPayAptNo.getText().length() == 0)
        {
            errormsg.setText("Invalid Apt No! Make Sure An Apartment Has Been Assigned To You!");
        }
        else if (rentAmount.getText().length() == 0)
        {
            errormsg.setText("Error! Apt Alloted After Selected Month");
        }
        else if (rentAmount.getText().equalsIgnoreCase("paid"))
        {
            errormsg.setText("Payment Has Been Made For Selected Month");
        }
        else if (rentPayDate.getValue().compareTo(LocalDate.now()) < 0)
        {
            errormsg.setText("Invalid Rent Pay Date!");
        }
        else
        {
            final LocalDate date = rentPayDate.getValue();
            final String apt_no = rentPayAptNo.getText();
            final int month = deformatMonth(rentForMonth.getValue().toString());
            final int year = Integer.parseInt(rentForYear.getValue().toString());
            final double amount = Double.parseDouble(rentAmount.getText());

            final Long card_no = Long.parseLong(rentPayUseCard.getValue().toString());
            final boolean paid = queryInfo.rentPay(apt_no, year, month, card_no, date, amount);
            if (paid)
            {
                theController.setScreen(this.getHomepageScreen());
            }
            else
            {
                errormsg.setText("Error! Payment Not Paid Yet!");
            }
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
    @FXML
    private void recalculateRentAmontHandler() throws SQLException
    {
        this.currentDay = this.rentPayDate.getValue().getDayOfMonth();
        final String aptRent = queryInfo.getResidentAptDue(username, currentMonth, daysOfMonth, currentDay);
        this.rentAmount.setText(aptRent);
    }
    @Override
    public void setScreenParent(final ScreenController screen) {
        theController = screen;
    }
    
}
