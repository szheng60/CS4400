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
public class Maintenance {
    SimpleStringProperty dateRequested;
    SimpleStringProperty apt;
    SimpleStringProperty issue;
    SimpleStringProperty dateResolved;

    public Maintenance(String dr, String ap, String is, String d) {
        this.dateRequested = new SimpleStringProperty(dr);
        this.apt = new SimpleStringProperty(ap);
        this.issue = new SimpleStringProperty(is);
        this.dateResolved = new SimpleStringProperty(d);
    }
    public String getDateRequested() {
        return dateRequested.get();
    }

    public String getApt() {
        return apt.get();
    }

    public String getIssue() {
        return issue.get();
    }
    public String getDateResolved(){
        return dateResolved.get();
    }    
}
