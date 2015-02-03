/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysqlquery;

import java.sql.SQLException;
import java.util.List;
import static rental10.Rental.facade;

/**
 *
 * @author song
 */
public class UserApartmentAllotment {
    public List<String[]> getApartmentCategoryInformation(final String username) throws SQLException
    {
        return facade.getApartmentCategoryInformation(username);
    }    
    public String getApplicantName(final String username) throws SQLException
    {
        return facade.getApplicantName(username);
    }
    public boolean apartmentAssign(final String username, final String apt_no, final String leaseTerm) throws SQLException
    {
        return facade.apartmentAssign(username, apt_no, leaseTerm);
    }
    public boolean isResidentAptAlloted(final String username) throws SQLException
    {
        return facade.isResidentAptAlloted(username);
    }
}
