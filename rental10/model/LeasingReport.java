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
public class LeasingReport {
    SimpleStringProperty month;
    SimpleStringProperty category;
    SimpleStringProperty apts;

    public LeasingReport(String montha, String categorya, String apta) {
        this.month = new SimpleStringProperty(montha);
        this.category = new SimpleStringProperty(categorya);
        this.apts = new SimpleStringProperty(apta);
    }

    public String getMonth() {
        return month.get();
    }

    public String getCategory() {
        return category.get();
    }

    public String getApts() {
        return apts.get();
    }
    
}
