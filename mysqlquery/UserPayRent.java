/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysqlquery;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import static rental10.Rental.facade;

/**
 *
 * @author song
 */
public class UserPayRent {
    public String getResidentAptNo(final String username) throws SQLException
    {
        return facade.getResidentAptNo(username);
    }
    public String getResidentAptDue(final String username, final int monthNow, final int daysOfMonth, final int dayNow) throws SQLException
    {
        return facade.getResidentAptDue(username, monthNow, daysOfMonth, dayNow);
    }
    public List<Long> getResidentCards(final String username) throws SQLException
    {
        return facade.getResidentCards(username);
    }
    public boolean rentPay(final String apt_no, final int year, final int month, final Long card_no, final LocalDate date, final double amount) throws SQLException
    {
        return facade.rentPay(apt_no, year, month, card_no, date, amount);
    }
    public boolean getRentPaidForMonth(final String month, final String year, final String apt_no) throws SQLException
    {
        return facade.getRentPaidForMonth(month, year, apt_no);
    }
    public List<String> getPastDueAptNo() throws SQLException
    {
        return facade.getPastDueAptNo();
    }
}
