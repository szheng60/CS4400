/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysqlquery;

/**
 *
 * @author song
 */
public interface Credential {
    public boolean logIn(String uname, String upassword);
}
