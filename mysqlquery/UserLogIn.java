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
public class UserLogIn implements Credential {
    /**
     *@param uname user entered username
     * @param upassword user entered password
     * @return true is username and password is verified, otherwise false
    */
    @Override
    public boolean logIn(final String uname, final String upassword)
    {
        boolean verified = false;
        try {
            verified = facade.userCredential(uname, upassword);
            if (verified)
            {
                this.setCurrentUser(uname);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserLogIn.class.getName()).log(Level.SEVERE, null, ex);
        }
        return verified;
    }
    /**
     * setup current user's username and group info.
     */
    public void setCurrentUser(final String uname)
    {
        final UserFactory userFactory = new UserFactory();
        final String userGroup = facade.getUserGroup();
        currentUser = userFactory.getUser(userGroup);
        currentUser.setUsername(uname);
        currentUser.setGroup(userGroup);
    }
}
