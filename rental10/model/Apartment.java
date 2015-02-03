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
public class Apartment {
    private final SimpleStringProperty aptno;
    private final SimpleStringProperty category;
    private final SimpleStringProperty monthlyRent;
    private final SimpleStringProperty sqft;
    private final SimpleStringProperty availableDate;

    /**
     *
     * @param num
     * @param cat
     * @param rent
     * @param size
     * @param date
     */
    public Apartment(final String num, final String cat, final String rent, final String size, final String date) {
        this.aptno = new SimpleStringProperty(num);
        this.category = new SimpleStringProperty(cat);
        this.monthlyRent = new SimpleStringProperty(rent);
        this.sqft = new SimpleStringProperty(size);
        this.availableDate = new SimpleStringProperty(date);
    }
    /**
     *
     * @return apt no
     */
    public String getAptno() {
        return aptno.get();
    }

    /**
     *
     * @return apt category type
     */
    public String getCategory() {
        return category.get();
    }

    /**
     *
     * @return apt monthly rent
     */
    public String getMonthlyRent() {
        return monthlyRent.get();
    }

    /**
     *
     * @return apt size
     */
    public String getSqft() {
        return sqft.get();
    }

    /**
     *
     * @return available date for the apt
     */
    public String getAvailableDate() {
        return availableDate.get();
    }    
}
