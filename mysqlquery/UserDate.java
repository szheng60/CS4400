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
public class UserDate {
    public List<String> getMonths() throws SQLException
    {
        return facade.getMonths();
    }
    public List<Integer> getYears() throws SQLException
    {
        return facade.getYears();
    }
}
