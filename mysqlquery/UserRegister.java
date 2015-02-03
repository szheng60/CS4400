/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysqlquery;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static rental10.Rental.currentUser;
import static rental10.Rental.facade;
import rental10.model.UserFactory;

/**
 *
 * @author song
 */
public class UserRegister {
    /**
     * 
     * @param uname user entered username to register
     * @param upassword user entered password to register
     * @return true if successfully register to DB, otherwise false
     */
    public boolean register(final String uname, final String upassword)
    {
        boolean registered = false;
        try {
            registered = facade.userRegister(uname, upassword);
            if (registered)
            {
                final UserFactory userFactory = new UserFactory();
                currentUser = userFactory.getUser("RESIDENT");
                currentUser.setUsername(uname);
                currentUser.setPassword(upassword);
                currentUser.setGroup("RESIDENT");             
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserRegister.class.getName()).log(Level.SEVERE, null, ex);
        }
        return registered;
    }
}
