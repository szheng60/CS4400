/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysqlquery;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import static rental10.Rental.facade;

/**
 *
 * @author song
 */
public class UserApplicationForm {
    public List<String> getAptCategory() throws SQLException
    {
        return facade.getAptCategory();
    }
    public List<String> getAptLeaseTerm(final String aptCategoryType) throws SQLException
    {
        return facade.getAptLeaseTerm(aptCategoryType);
    }
    public List<String> getAptCategoryRent(final String aptCategoryType) throws SQLException
    {
        return facade.getAptCategoryRent(aptCategoryType);
    }
    public List<String> getAptAvailableOn(final String aptCategoryType) throws SQLException
    {
        return facade.getAptAvailableOn(aptCategoryType);
    }
    public boolean applicationSubmit(final String name, final String username, final String gender, final LocalDate dob, final double income,
            final String category, final double minRent, final double maxRent, final LocalDate moveIn, final int leaseTerm, final String prevAddress) throws SQLException
    {
        return facade.applicationSubmitStatus(name, username, gender, dob, income, category, minRent, maxRent, moveIn, leaseTerm, prevAddress);
    }
    public LocalDate getResidentMoveInDate(final String username) throws SQLException
    {
        return facade.getResidentMoveInDate(username);
    }
    public String getResidentLeaseTerm(final String username) throws SQLException
    {
        return facade.getResidentLeaseTerm(username);
    }
}
