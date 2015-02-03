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
public class ServiceReport {
    SimpleStringProperty month;
    SimpleStringProperty request;
    SimpleStringProperty dayNo;
    
    public ServiceReport(final String montha, final String requesta, final String day)
    {
        this.month = new SimpleStringProperty(montha);
        this.request = new SimpleStringProperty(requesta);
        this.dayNo = new SimpleStringProperty(day);
    }
    public String getMonth()
    {
        return month.get();
    }
    public String getRequest()
    {
        return request.get();
    }
    public String getDayNo()
    {
        return dayNo.get();
    }
}
