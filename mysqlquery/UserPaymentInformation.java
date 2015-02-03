/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysqlquery;

import java.sql.SQLException;
import java.time.LocalDate;
import static rental10.Rental.facade;

/**
 *
 * @author song
 */
public class UserPaymentInformation {
    /**
     *@return true if add the card, otherwise false 
    */
    public boolean newCardAdd(final String card_no, final String cvv, final String name, final LocalDate expiration_date, final String username) throws SQLException
    {
        return facade.newCardAdd(card_no, cvv, name, expiration_date, username);
    }
    /**
     * @param card_no user selected card no
     *@return true if card is deleted, otherwise false 
    */
    public boolean cardDelete(final String card_no) throws SQLException
    {
        return facade.cardDelete(card_no);
    }
}
