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
public class RentalAppScreenNames {
    public final String theScreenNames[] = {
        "Homepage",                             //0
        "RegistrationForm",                    //1
        "NewApplicationForm",               //2
        "MainpageScreen",                       //3
        "LeasingReport",                        //4
        "ApplicationReview",                    //5
        "PaymentInformation",                   //6
        "ApartmentAllotment",                   //7
        "Reminder",                             //8
        "RentDefaulterReport",                  //9
        "RentPayment",                          //10
        "RequestMaintenance",                   //11
        "ServiceRequestReport",                 //12
        "ViewMaintenanceRequest"                //13
    };
    public String getHomepageScreen()
    {
        return theScreenNames[0];
    }
    public String getRegistrationFormScreen()
    {
        return theScreenNames[1];
    }
    public String getNewApplicationFormScreen()
    {
        return theScreenNames[2];
    }
    public String getMainpageScreen()
    {
        return theScreenNames[3];
    }
    public String getLeasingReportScreen()
    {
        return theScreenNames[4];
    }
    public String getApplicationReviewScreen()
    {
        return theScreenNames[5];
    }
    public String getPaymentInformationScreen()
    {
        return theScreenNames[6];
    }
    public String getApartmentAllotmentScreen()
    {
        return theScreenNames[7];
    }
    public String getReminderScreen()
    {
        return theScreenNames[8];
    }
    public String getRentDefaultReportScreen()
    {
        return theScreenNames[9];
    }
    public String getRentPaymentScreen()
    {
        return theScreenNames[10];
    }
    public String getRequestMaintenanceScreen()
    {
        return theScreenNames[11];
    }
    public String getServiceRequestReportScreen()
    {
        return theScreenNames[12];
    }
    public String getViewMaintenanceRequestScreen()
    {
        return theScreenNames[13];
    }
    public String[] getScreenNames()
    {
        return this.theScreenNames;
    }
}
