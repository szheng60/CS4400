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
public class UserMaintenanceRequest {
    public List<String[]> getIssueRequested() throws SQLException
    {
        return facade.getIssueRequested();
    }    
    public boolean requestResolvedForApt(final String apt_no, final String issue, final String date, final LocalDate ldate) throws SQLException
    {
        return facade.requestResolvedForApt(apt_no, issue, date, ldate);
    }
    public LocalDate getIssueRequestDate(final String aptNo) throws SQLException
    {
        return facade.getIssueRequestDate(aptNo);
    }
}
