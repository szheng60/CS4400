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
public class UserReminder {
    public boolean reminderSend(final String apt_no, final LocalDate date, final String msg, final String status) throws SQLException
    {
        return facade.reminderSend(apt_no, date, msg, status);
    }   
    public int getResidentReminderCount(final String username) throws SQLException
    {
        return facade.getResidentReminderCount(username);
    }
    public List<String> getResidentReminder(final String username) throws SQLException
    {
        return facade.getResidentReminder(username);
    }
    public boolean updateReminder(final String username) throws SQLException
    {
        return facade.updateReminder(username);
    }
}
