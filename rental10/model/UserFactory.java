/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rental10.model;

/**
 *
 * @author song
 */
public class UserFactory {
    /**
     *instantiate current user group based on user login info. 
     * @param userType user login with table resident or management
    */
    public static User getUser(final String userType)
    {
        if (userType == null)
        {
            return null;
        }
        if (userType.equalsIgnoreCase("RESIDENT"))
        {
            return new Resident();
        }
        else if (userType.equalsIgnoreCase("MANAGEMENT"))
        {
            return new Management();
        }
        return null;
    }
}
