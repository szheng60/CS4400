/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysqlquery;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import static rental10.Rental.facade;

/**
 *
 * @author song
 */
public final class QueryInfoMediator {
    /**
     *lazy instantiation. created only when needed. 
    */
    private static QueryInfoMediator instance = null;
    /**
     *User login verification. 
    */
    private final transient UserLogIn userLogin;
    /**
     *User registration. 
    */
    private final transient UserRegister userRegister;
    private final transient UserApplicationForm userApplicationForm;
    private final transient UserPayRent userPayRent;
    private final transient UserDate userDate;
    private final transient UserIssue userIssue;
    private final transient UserPaymentInformation userPaymentInformation;
    private final transient UserApplication userApplication;
    private final transient UserApartmentAllotment userApartmentAllotment;
    private final transient UserMaintenanceRequest userMaintenanceRequest;
    private final transient UserReminder userReminder;
    private final transient UserAptReport userAptReport;
    /**
     *ensure singleton can't be initiated .
    */
    private QueryInfoMediator()
    {
        this.userLogin = new UserLogIn();        
        this.userRegister = new UserRegister();
        this.userApplicationForm = new UserApplicationForm();
        this.userPayRent = new UserPayRent();
        this.userDate = new UserDate();
        this.userIssue = new UserIssue();
        this.userPaymentInformation = new UserPaymentInformation();
        this.userApplication = new UserApplication();
        this.userApartmentAllotment = new UserApartmentAllotment();
        this.userMaintenanceRequest = new UserMaintenanceRequest();
        this.userReminder = new UserReminder();
        this.userAptReport = new UserAptReport();
    }
    /**
     *@return a instance of the query info 
    */
    public static QueryInfoMediator getInstance()
    {
        if (instance == null)
        {
            instance = new QueryInfoMediator();
        }
        return instance;
    }
    /**
     *@param uname user entered an username
     * @param upassword user entered a password
     * @return true for user verified, otherwise false
    */
    public boolean userCredential(final String uname, final String upassword)
    {
        return userLogin.logIn(uname, upassword);
    }
    /**
     *@param  uname user entered username to register
     * @param upassword user entered password to register
     * @return true if username is registered to DB, otherwise false
    */
    public boolean userRegistration(final String uname, final String upassword)
    {
        return userRegister.register(uname, upassword);
    }
    /**
     * 
     * @return list of distinct apt category in the apartment table.
     * @throws SQLException 
     */
    public List<String> getAptCategory() throws SQLException
    {
        return userApplicationForm.getAptCategory();
    }
    /**
     * 
     * @return list of distinct apt leaseTerm in the apartment table.
     * @throws SQLException 
     */
    public List<String> getAptLeaseTerm(final String aptCategoryType) throws SQLException
    {
        return userApplicationForm.getAptLeaseTerm(aptCategoryType);
    }
    /**
     * 
     * @return a list of rent based on category type
     * @throws SQLException 
     */
    public List<String> getAptCategoryRent(String aptCategoryType) throws SQLException
    {
        return userApplicationForm.getAptCategoryRent(aptCategoryType);
    }
    public List<String> getAptAvailableOn(final String aptCategoryType) throws SQLException
    {
        return userApplicationForm.getAptAvailableOn(aptCategoryType);
    }
    /**
     * 
     * @return true if application has been submitted, otherwise false
     * @throws SQLException 
     */    
    public boolean applicationInfo(final String name, final String username, final String gender, final LocalDate dob, final double income,
            final String category, final double minRent, final double maxRent, final LocalDate moveIn, final int leaseTerm, final String prevAddress) throws SQLException
    {
        return userApplicationForm.applicationSubmit(name, username, gender, dob, income, category, minRent, maxRent, moveIn, leaseTerm, prevAddress);
    }
    /**
     * @param username current user
     * @return apt no for current user
     * @throws SQLException 
     */
    public String getResidentAptNo(final String username) throws SQLException
    {
        return userPayRent.getResidentAptNo(username);
    }
    /**
     *@param username current user
     * @return apt amount due
     * @throws SQLException 
     */
    public String getResidentAptDue(final String username, final int monthNow, final int daysOfMonth, final int dayNow) throws SQLException
    {
        return userPayRent.getResidentAptDue(username, monthNow, daysOfMonth, dayNow);
    }
    public LocalDate getResidentMoveInDate(final String username) throws SQLException
    {
        return userApplicationForm.getResidentMoveInDate(username);
    }
    public String getResidentLeaseTerm(final String username) throws SQLException
    {
        return userApplicationForm.getResidentLeaseTerm(username);
    }
    public List<String> getMonths() throws SQLException
    {
        return userDate.getMonths();
    }
    public List<Integer> getYears() throws SQLException
    {
        return userDate.getYears();
    }
    /**
     *@param username current user
     * @return current user's card info
     * @throws SQLException 
     */
    public List<Long> getResidentCards(final String username) throws SQLException
    {
        return userPayRent.getResidentCards(username);
    }
    public boolean rentPay(final String apt_no, final int year, final int month, final Long card_no, final LocalDate date, final double amount) throws SQLException
    {
        return userPayRent.rentPay(apt_no, year, month, card_no, date, amount);
    }
    public boolean getRentPaidForMonth(final String month, final String year, final String apt_no) throws SQLException
    {
        return userPayRent.getRentPaidForMonth(month, year, apt_no);
    }
    public List<String> getIssueTypes() throws SQLException
    {
        return userIssue.getIssueTypes();
    }
    public boolean requestMaintenanceSubmit(final String apt_no, final String issue, final LocalDate date) throws SQLException
    {
        return userIssue.requestMaintenanceSubmit(apt_no, issue, date);
    }
    public boolean newCardAdd(final String card_no, final String cvv, final String name, final LocalDate expiration_date, final String username) throws SQLException
    {
        return userPaymentInformation.newCardAdd(card_no, cvv, name, expiration_date, username);
    }
    public boolean cardDelete(final String card_no) throws SQLException
    {
        return userPaymentInformation.cardDelete(card_no);
    }
    public List<String[]> getApplicationInformation() throws SQLException
    {
        return userApplication.getApplicationInformation();
    }
    public List<String[]> getApartmentCategoryInformation(final String username) throws SQLException
    {
        return userApartmentAllotment.getApartmentCategoryInformation(username);
    }
    public String getApplicantName(final String username) throws SQLException
    {
        return userApartmentAllotment.getApplicantName(username);
    }
    public boolean apartmentAssign(final String username, final String apt_no, final String leaseTerm) throws SQLException
    {
        return userApartmentAllotment.apartmentAssign(username, apt_no, leaseTerm);
    }
    public List<String[]> getIssueRequested() throws SQLException
    {
        return userMaintenanceRequest.getIssueRequested();
    }
    public LocalDate getIssueRequestDate(final String aptNo) throws SQLException
    {
        return userMaintenanceRequest.getIssueRequestDate(aptNo);
    }
    public boolean requestResolvedForApt(final String apt_no, final String issue, final String date, final LocalDate ldate) throws SQLException
    {
        return userMaintenanceRequest.requestResolvedForApt(apt_no, issue, date, ldate);
    }
    public List<String> getPastDueAptNo() throws SQLException
    {
        return userPayRent.getPastDueAptNo();
    }
    public boolean reminderSend(final String apt_no, final LocalDate date, final String msg, final String status) throws SQLException
    {
        return userReminder.reminderSend(apt_no, date, msg, status);
    }
    public int getResidentReminderCount(final String username) throws SQLException
    {
        return userReminder.getResidentReminderCount(username);
    }
    public List<String> getResidentReminder(final String username) throws SQLException
    {
        return userReminder.getResidentReminder(username);
    }
    public boolean updateReminder(final String username) throws SQLException
    {
        return userReminder.updateReminder(username);
    }
    public List<String[]> getLeasingReport() throws SQLException
    {
        return userAptReport.getLeasingReport();
    }
    public List<String[]> getServiceReport() throws SQLException
    {
        return userAptReport.getServiceReport();
    }
    public List<String[]> getDefaultReport(final int month) throws SQLException
    {
        return userAptReport.getDefaultReport(month);
    }
    public boolean isResidentAptAlloted(final String username) throws SQLException
    {
        return userApartmentAllotment.isResidentAptAlloted(username);
    }
}
