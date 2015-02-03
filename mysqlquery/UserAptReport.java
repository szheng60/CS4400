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
public class UserAptReport {
    public List<String[]> getLeasingReport() throws SQLException
    {
        return facade.getLeasingReport();
    }   
    public List<String[]> getServiceReport() throws SQLException
    {
        return facade.getServiceReport();
    }
    public List<String[]> getDefaultReport(final int month) throws SQLException
    {
        return facade.getDefaultReport(month);
    }
}
