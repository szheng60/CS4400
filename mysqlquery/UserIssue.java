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
public class UserIssue {
    /**
     *@return list of issue types. 
    */
    public List<String> getIssueTypes() throws SQLException
    {
        return facade.getIssueTypes();
    }
    /**
     *@return true if request has been submitted, otherwise false 
    */
    public boolean requestMaintenanceSubmit(final String apt_no, final String issue, final LocalDate date) throws SQLException
    {
        return facade.requestMaintenanceSubmit(apt_no, issue, date);
    }
}
