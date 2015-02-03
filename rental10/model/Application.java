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
public class Application {
        
    private final SimpleStringProperty name;
    private final SimpleStringProperty username;
    private final SimpleStringProperty gender;
    private final SimpleStringProperty dob;
    private final SimpleStringProperty income;
    private final SimpleStringProperty apt;
    private final SimpleStringProperty moveDate;
    private final SimpleStringProperty term;
    private final SimpleStringProperty approval;
//    private transient String minRent;
//    private transient String maxRent; 
    
    public Application(String name, String dob, String gender, String income, String apt, String moveDate, String term, String approval, String username)
    {
        this.name = new SimpleStringProperty(name);
        this.dob = new SimpleStringProperty(dob);
        this.gender = new SimpleStringProperty(gender);
        this.income = new SimpleStringProperty(income);
        this.apt = new SimpleStringProperty(apt);
        this.moveDate = new SimpleStringProperty(moveDate);
        this.term = new SimpleStringProperty(term);
        this.approval = new SimpleStringProperty(approval);
        this.username = new SimpleStringProperty(username);
    }
    public void setName(String namea) {
        name.set(namea);
    }

    public void setDob(String doba) {
        dob.set(doba);
    }

    public void setGender(String gendera) {
        gender.set(gendera);
    }

    public void setIncome(String incomea) {
        income.set(incomea);
    }

    public void setApt(String apta) {
        apt.set(apta);
    }

    public void setMoveDate(String moveDatea) {
        moveDate.set(moveDatea);
    }

    public void setTerm(String terma) {
        term.set(terma);
    }

    public void setApproval(String approvala) {
        approval.set(approvala);
    }

    public String getName() {
        return name.get();
    }

    public String getDob() {
        return dob.get();
    }

    public String getGender() {
        return gender.get();
    }

    public String getIncome() {
        return income.get();
    }

    public String getApt() {
        return apt.get();
    }

    public String getMoveDate() {
        return moveDate.get();
    }

    public String getTerm() {
        return term.get();
    }

    public String getApproval() {
        return approval.get();
    } 
    public void setUsername(String usernamea)
    {
        username.set(usernamea);
    }
    public String getUsername() {
        return username.get();
    }
            
    @Override
    public String toString(){
        return getName() + "," + getDob() + "," + getGender() + "," + getIncome() + "," + getApproval();
    }
}
