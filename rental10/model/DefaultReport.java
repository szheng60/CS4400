/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rental10.model;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author song
 */
public class DefaultReport {
    SimpleStringProperty apt;
    SimpleStringProperty fine;
    SimpleStringProperty day;
    
    public DefaultReport(final String apta, final String finea, final String daya)
    {
        this.apt = new SimpleStringProperty(apta);
        this.fine = new SimpleStringProperty(finea);
        this.day = new SimpleStringProperty(daya);
    }
    public String getApt()
    {
        return apt.get();
    }
    public String getFine()
    {
        return fine.get();
    }
    public String getDay()
    {
        return day.get();
    }
            
}
