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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
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
public class PaymentInformationController extends ScreenTemplate implements Initializable , SetControlScreen {

    private transient ScreenController theController;
    private final transient QueryInfoMediator queryInfo = QueryInfoMediator.getInstance();
    private final transient String username = currentUser.getUsername();
    @FXML
    private transient TextField nameOnCard;
    @FXML
    private transient TextField cardNo;
    @FXML
    private transient TextField cvv;
    @FXML
    private transient DatePicker expirationDate;
    @FXML
    private transient ChoiceBox selectCard;
    @FXML
    private transient Label errormsgSave;
    @FXML
    private transient Label errormsgDelete;

    public PaymentInformationController()
    {
        nameOnCard = new TextField();
        cardNo = new TextField();
        cvv = new TextField();
        selectCard = new ChoiceBox();
        errormsgSave = new Label();
        errormsgDelete = new Label();
    }
    /**
    * Initializes the controller class.
    */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.setTitleLabel(this.getPaymentInformationScreen());
        expirationDate.setValue(LocalDate.now());
    }     
    public void initPaymentInformation() throws SQLException
    {
        ObservableList<Long> cardOptions = FXCollections.observableArrayList(queryInfo.getResidentCards(username));
        selectCard.setItems(cardOptions);
        if (cardOptions.size() > 0)
        {
            selectCard.setValue(cardOptions.get(0));
        }
    }
    /**
     *save button handler. 
    */
    @FXML
    private void homeScreenHandler() throws SQLException
    {
        if (nameOnCard.getText().length() == 0)
        {
            errormsgSave.setText("Enter Your Name!");
        }
        else if (cardNo.getText().length() < 15 || cardNo.getText().length() > 16)
        {
            errormsgSave.setText("Enter A Valid Card No. !");
        }
        else if (expirationDate.getValue().compareTo(LocalDate.now()) < 0)
        {
            errormsgSave.setText("Card Has Expired! Enter a New Expiraton Date or New Card");
        }
        else if (cvv.getText().length() == 0)
        {
            errormsgSave.setText("Enter a CVV No. !");
        }
        else if (cardNo.getText().length() == 15 && cvv.getText().length() != 4)
        {
            errormsgSave.setText("American Express Has 4 Digits of CVV No. !");
        }
        else if (cardNo.getText().length() == 16 && cvv.getText().length() != 3)
        {
            errormsgSave.setText("CVV No. Should Contain 3 Digits !");
        }
        else
        {
            final String name = nameOnCard.getText();
            final String card_no = cardNo.getText();
            final LocalDate expiration_date = expirationDate.getValue();
            final String cvv_no = cvv.getText();
            final boolean added = queryInfo.newCardAdd(card_no, cvv_no, name, expiration_date, username);
            if (added)
            {
                theController.setScreen(this.getHomepageScreen());
            }
            else
            {
                errormsgSave.setText("Error! Card Info not Added Yet");
            }
        }
    }
    /**
     * delete button handler.
     */
    @FXML
    private void homeScreenHandler0() throws SQLException
    {
        if (selectCard.getValue() != null)
        {
            final String card_no = selectCard.getValue().toString();
            final boolean deleted = queryInfo.cardDelete(card_no);
            if (deleted)
            {
                theController.setScreen(this.getHomepageScreen());
            }
            else
            {
                errormsgDelete.setText("Error! Card not Deleted Yet");
            }
        }
        else
        {
            errormsgDelete.setText("Error! No Card is Selected");
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
    public void setScreenParent(ScreenController screen) {
        theController = screen;
    }
     
}
