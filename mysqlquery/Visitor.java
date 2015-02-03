/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysqlquery;

import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import rental10.ApartmentAllotmentController;
import rental10.ApplicationReviewController;
import rental10.HomepageController;
import rental10.LeasingReportController;
import rental10.PaymentInformationController;
import rental10.ReminderController;
import rental10.RentDefaulterReportController;
import rental10.RentPaymentController;
import static rental10.Rental.currentUser;
import rental10.RequestMaintenanceController;
import rental10.ScreenController;
import rental10.ServiceRequestReportController;
import rental10.ViewMaintenanceRequestController;

/**
 *
 * @author song
 */
public class Visitor {

    /**
     *change to home page stage and initiate information based on current user. 
     * @param theController main controller
     * @param loader loader
     * @throws IOException
     * @throws SQLException 
    */
    public void initHomepageInformation(ScreenController theController, FXMLLoader loader) throws IOException, SQLException 
    {
        loader = new FXMLLoader(getClass().getClassLoader().getResource("rental10/view/Homepage.fxml"));
        final Parent loadScreen = (Parent) loader.load();
        final HomepageController controller = (HomepageController) loader.getController();                        
        controller.initHomepageInformation();
        theController.loadScreen(controller, "Homepage", loadScreen);
    }
    /**
     *change to pay rent stage and initiate information based on current user. 
     * @param theController main controller
     * @param loader loader
     * @throws IOException
     * @throws SQLException 
    */
    public void initPayRentInformation(ScreenController theController, FXMLLoader loader) throws IOException, SQLException 
    {
        loader = new FXMLLoader(getClass().getClassLoader().getResource("rental10/view/RentPayment.fxml"));
        final Parent loadScreen = (Parent) loader.load();
        final RentPaymentController controller = (RentPaymentController) loader.getController();                        
        controller.initPayRentInformation();
        theController.loadScreen(controller, "RentPayment", loadScreen);
    }
    /**
     * change to request maintenance stage and initiate information based on current user. 
     * @param theController main controller
     * @param loader loader
     * @throws IOException
     * @throws SQLException 
     */
    public void initRequestMaintenance(ScreenController theController, FXMLLoader loader) throws IOException, SQLException 
    {
        loader = new FXMLLoader(getClass().getClassLoader().getResource("rental10/view/RequestMaintenance.fxml"));
        final Parent loadScreen = (Parent) loader.load();
        final RequestMaintenanceController controller = (RequestMaintenanceController) loader.getController();
        controller.initRequestMaintenance();
        theController.loadScreen(controller, "RequestMaintenance", loadScreen);
    }
    /**
     * change to payment information stage and initiate information based on current user. 
     * @param theController main controller
     * @param loader loader
     * @throws IOException
     * @throws SQLException 
     */    
    public void initPaymentInformation(ScreenController theController, FXMLLoader loader) throws IOException, SQLException 
    {
        loader = new FXMLLoader(getClass().getClassLoader().getResource("rental10/view/PaymentInformation.fxml"));
        final Parent loadScreen = (Parent) loader.load();
        final PaymentInformationController controller = (PaymentInformationController) loader.getController();
        controller.initPaymentInformation();
        theController.loadScreen(controller,"PaymentInformation", loadScreen);
    }
    /**
     * change to application review stage and initiate information for management. 
     * @param theController main controller
     * @param loader loader
     * @throws IOException
     * @throws SQLException 
     */    
    public void initApplicationReview(ScreenController theController, FXMLLoader loader) throws IOException, SQLException 
    {
        loader = new FXMLLoader(getClass().getClassLoader().getResource("rental10/view/ApplicationReview.fxml"));
        final Parent loadScreen = (Parent) loader.load();
        final ApplicationReviewController controller = (ApplicationReviewController) loader.getController();
        controller.initApplicationReviewInformation();
        theController.loadScreen(controller,"ApplicationReview", loadScreen);
    }
    /**
     * change to application review stage and initiate information for management. 
     * @param theController main controller
     * @param loader loader
     * @throws IOException
     * @throws SQLException 
     */    
    public void initApartmentAllotment(ScreenController theController, FXMLLoader loader, final String username) throws IOException, SQLException 
    {
        loader = new FXMLLoader(getClass().getClassLoader().getResource("rental10/view/ApartmentAllotment.fxml"));
        final Parent loadScreen = (Parent) loader.load();
        final ApartmentAllotmentController controller = (ApartmentAllotmentController) loader.getController();
        controller.initApartmentAllotmentInformation(username);
        theController.loadScreen(controller,"ApartmentAllotment", loadScreen);
    }
    /**
     * change to view maintainance request stage and initiate information for management. 
     * @param theController main controller
     * @param loader loader
     * @throws IOException
     * @throws SQLException 
     */    
    public void initViewMaintenanceRequest(ScreenController theController, FXMLLoader loader) throws IOException, SQLException 
    {
        loader = new FXMLLoader(getClass().getClassLoader().getResource("rental10/view/ViewMaintenanceRequest.fxml"));
        final Parent loadScreen = (Parent) loader.load();
        final ViewMaintenanceRequestController controller = (ViewMaintenanceRequestController) loader.getController();
        controller.initViewMaintenanceRequestInformation();
        theController.loadScreen(controller,"ViewMaintenanceRequest", loadScreen);
    }
    /**
     * change to view reminder stage and initiate information for management. 
     * @param theController main controller
     * @param loader loader
     * @throws IOException
     * @throws SQLException 
     */    
    public void initReminder(ScreenController theController, FXMLLoader loader) throws IOException, SQLException 
    {
        loader = new FXMLLoader(getClass().getClassLoader().getResource("rental10/view/Reminder.fxml"));
        final Parent loadScreen = (Parent) loader.load();
        final ReminderController controller = (ReminderController) loader.getController();
        controller.initReminderInformation();
        theController.loadScreen(controller,"Reminder", loadScreen);
    }
    /**
     * change to leasing report stage and initiate information for management. 
     * @param theController main controller
     * @param loader loader
     * @throws IOException
     * @throws SQLException 
     */    
    public void initLeasingReport(ScreenController theController, FXMLLoader loader) throws IOException, SQLException 
    {
        loader = new FXMLLoader(getClass().getClassLoader().getResource("rental10/view/LeasingReport.fxml"));
        final Parent loadScreen = (Parent) loader.load();
        final LeasingReportController controller = (LeasingReportController) loader.getController();
        controller.initLeasingReportInformation();
        theController.loadScreen(controller,"LeasingReport", loadScreen);
    }
    /**
     * change to service request report stage and initiate information for management. 
     * @param theController main controller
     * @param loader loader
     * @throws IOException
     * @throws SQLException 
     */    
    public void initServiceRequestReport(ScreenController theController, FXMLLoader loader) throws IOException, SQLException 
    {
        loader = new FXMLLoader(getClass().getClassLoader().getResource("rental10/view/ServiceRequestReport.fxml"));
        final Parent loadScreen = (Parent) loader.load();
        final ServiceRequestReportController controller = (ServiceRequestReportController) loader.getController();
        controller.initServiceRequestReportInformation();
        theController.loadScreen(controller,"ServiceRequestReport", loadScreen);
    }
    /**
     * change to rent default report stage and initiate information for management. 
     * @param theController main controller
     * @param loader loader
     * @throws IOException
     * @throws SQLException 
     */    
    public void initRentDefaulterReport(ScreenController theController, FXMLLoader loader) throws IOException, SQLException 
    {
        loader = new FXMLLoader(getClass().getClassLoader().getResource("rental10/view/RentDefaulterReport.fxml"));
        final Parent loadScreen = (Parent) loader.load();
        final RentDefaulterReportController controller = (RentDefaulterReportController) loader.getController();
        controller.initRentDefaulterReportInformation();
        theController.loadScreen(controller,"RentDefaulterReport", loadScreen);
    }
}
