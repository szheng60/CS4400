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
public class User {

    protected transient String username;
    private transient String password;
    private transient String group;
    /**
     * 
     * @param name username.
     */
    public void setUsername(String name)
    {
        this.username = name;
    }
    public String getUsername()
    {
        return this.username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the group
     */
    public String getGroup() {
        return group;
    }

    /**
     * @param group the group to set
     */
    public void setGroup(String group) {
        this.group = group;
    }
}
