/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysqlquery;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static rental10.Rental.currentUser;

/**
 *
 * @author song
 */
public class Facade {
    DateFormatSymbols symbols = new DateFormatSymbols();  
    /**
     *sql connection. 
    */
    private Connection conn;
    /**
     * mysql driver.
     */
    private final transient String JDBC_DRIVER;
    /**
     * mysql url.
     */
    private final transient String DB_URL;
    /**
     *mysql login name. 
    */
    private final transient String dbUserName;
    /**
     *mysql login password. 
    */
    private final transient String dbPassword;
    /**
     *mysql statement holder. 
    */
    private PreparedStatement pst;
    /**
     * mysql query result set.
     */
    private ResultSet rs;
    /**
     *query statement. 
    */
    private String query;
    /**
     * define current user, whether resident of management.
     */
    private String userGroup;
    
    /**
     * setup MySQL connection.
     */
    public Facade()
    {
        conn = null;
        JDBC_DRIVER = "com.mysql.jdbc.Driver";
        DB_URL = "jdbc:mysql://academic-mysql.cc.gatech.edu/cs4400_Group_10";
        dbUserName = "cs4400_Group_10";
        dbPassword = "fcTqFy8H";
//        DB_URL = "jdbc:mysql://localhost:3306/rental";
//        dbUserName = "root";
//        dbPassword = "0919";
        
        initialize();        
    }
    /**
     * initialize connection.
    */
    private void initialize() 
    {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, dbUserName, dbPassword);
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, "DB connection error");
            Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     *@param uname user entered username
     * @param upassword user entered password
     * @return ture if find record in DB, otherwise false
     * @throws java.sql.SQLException
    */
    public boolean userCredential(final String uname, final String upassword) throws SQLException
    {
        query = "SELECT Username FROM user WHERE Username=? AND Password=?";
        pst = conn.prepareStatement(query);
        pst.setString(1, uname);
        pst.setString(2, upassword);
        rs = pst.executeQuery();
        boolean credential = rs.isBeforeFirst();
        if (credential)
        {
            String name = "";
            while (rs.next())
            {
                name = rs.getString("Username");
            }
            query = "SELECT Username FROM management WHERE Username=?";
            pst = conn.prepareCall(query);
            pst.setString(1, name);
            rs = pst.executeQuery();
            if (!rs.isBeforeFirst())
            {
                this.userGroup = "RESIDENT";
            }
            else
            {
                this.userGroup = "MANAGEMENT";
            }
        }    
        return credential;
    }
    /**
     * the username is not saved in the DB unless new user finishes the application.
     * @param uname user entered register username
     * @param upassword user entered register password
     * @return true if able to register in DB, otherwise false
     * @throws java.sql.SQLException
    */
    public boolean userRegister(final String uname, final String upassword) throws SQLException
    {
        query = "SELECT Username FROM user WHERE Username=?";
        pst = conn.prepareCall(query);
        pst.setString(1, uname);
        rs = pst.executeQuery();
        return !rs.isBeforeFirst();
    }
    /**
     * 
     * @return list of apartment categories.
     * @throws SQLException 
     */
    public List<String> getAptCategory() throws SQLException
    {
        query = "SELECT DISTINCT Category FROM apartment ORDER BY Category ASC";
        pst = conn.prepareCall(query);
        rs = pst.executeQuery();
        List<String> aptCategory = new ArrayList<>();
        while (rs.next())
        {
            String s = rs.getString("Category");
            aptCategory.add(s);
        }
        return aptCategory;
    }
    /**
     * 
     * @param aptCategoryType user selected apt category.
     * @return available lease term under certain apt category
     * @throws SQLException 
     */
    public List<String> getAptLeaseTerm(final String aptCategoryType) throws SQLException
    {
        query = "SELECT DISTINCT Lease_Term FROM apartment WHERE Category=? ORDER BY Lease_Term ASC";
        pst = conn.prepareCall(query);
        pst.setString(1, aptCategoryType);
        rs = pst.executeQuery();
        List<String> aptLeaseTerm = new ArrayList<>();
        while (rs.next())
        {
            String s = rs.getString("Lease_Term");
            aptLeaseTerm.add(s);
        }
        return aptLeaseTerm;
    }
    /**
     *@param aptCategoryType user selected category type
     * @return apartment rent price range for selected apartment category
     * @throws SQLException
    */
    public List<String> getAptCategoryRent(final String aptCategoryType) throws SQLException
    {
        query = "SELECT DISTINCT Rent FROM apartment WHERE Category=? ORDER BY Rent ASC";
        
        pst = conn.prepareCall(query);
        pst.setString(1, aptCategoryType);
        rs = pst.executeQuery();
        List<String> aptCategoryRent = new ArrayList<>();
        while (rs.next())
        {
            String s = rs.getString("Rent");
            aptCategoryRent.add(s);
        }
        return aptCategoryRent;
    }
    public List<String> getAptAvailableOn(final String aptCategoryType) throws SQLException
    {
        query = "SELECT Available_On FROM apartment WHERE Category=? ";
        pst = conn.prepareCall(query);
        pst.setString(1, aptCategoryType);
        rs = pst.executeQuery();
        List<String> aptCategoryAvailableOn = new ArrayList<>();
        while (rs.next())
        {
            String s = rs.getString("Available_On");
            aptCategoryAvailableOn.add(s);
        }
        return aptCategoryAvailableOn;
    }
    /**
     *@return true if application has been submitted, otherwise false 
    */
    public boolean applicationSubmitStatus(final String name, final String username, final String gender, final LocalDate dob, final double income,
            final String category, final double minRent, final double maxRent, final LocalDate moveIn, final int leaseTerm, final String prevAddress) throws SQLException
    {  
        boolean result = false;
        query = "SELECT Name, DOB FROM prospective_resident WHERE Name=? AND DOB=?";
        pst = conn.prepareCall(query);
        pst.setString(1, name);
        pst.setDate(2, Date.valueOf(dob.toString()));
        rs = pst.executeQuery();
        if (rs.isBeforeFirst())
        {
            JOptionPane.showMessageDialog(null, "Your have created an account!", "User has registered", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (!createUser(username, currentUser.getPassword()))
        {
            return false;
        }
        
        query = "INSERT INTO prospective_resident (Name, Username, DOB, Monthly_Income, Pref_Move_in_Date,"
                + "Required_Category, Pref_Lease_Term, Gender, Prev_Address, Min_Rent, Max_Rent) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        pst = conn.prepareStatement(query);
        pst.setString(1, name);
        pst.setString(2, username);
        pst.setDate(3, Date.valueOf(dob.toString()));
        pst.setDouble(4, income);
        pst.setDate(5, Date.valueOf(moveIn.toString()));
        pst.setString(6, category);
        pst.setInt(7, leaseTerm);
        pst.setString(8, gender);
        pst.setString(9, prevAddress);
        pst.setDouble(10, minRent);
        pst.setDouble(11, maxRent);
        try{
            pst.executeUpdate();
            result = true;
        } catch (SQLException e){
            result = false;
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 
     * @param uname username
     * @param upassword userpassword
     * @return true if user has been created, otherwise false
     * @throws SQLException 
     */
    private boolean createUser(final String uname, final String upassword) throws SQLException
    {
        boolean result = false;
        query = "INSERT INTO user(Username, Password) VALUES(?,?)";
        pst = conn.prepareCall(query);
        pst.setString(1, uname);
        pst.setString(2, upassword);
        try{
            pst.executeUpdate();
            result = true;
        } catch (SQLException e){
            result = false;
            e.printStackTrace();
        }
        return result;
    }
    /**
     * @param username current user name.
     *@return apt no for the current user. 
    */
    public String getResidentAptNo(final String username) throws SQLException
    {
        String apt_no = "null";
        query = "SELECT Apt_No FROM resident WHERE Username=?";
        pst = conn.prepareCall(query);
        pst.setString(1, username);
        rs = pst.executeQuery();
        if (rs.next())
        {
            apt_no = rs.getString("Apt_No");
        }
        return apt_no;
    }
    public boolean isResidentAptAlloted(final String username) throws SQLException
    {
        query = "SELECT Username FROM resident WHERE Username=?";
        pst = conn.prepareCall(query);
        pst.setString(1,username);
        rs = pst.executeQuery();
        return rs.isBeforeFirst();           
    }
    /**
     * @param username current username.
     *@return amount due for current user's apt 
    */
    public String getResidentAptDue(final String username, final int monthNow, final int daysOfMonth, final int dayNow) throws SQLException
    {
        String apt_due = "null";
        query = "SELECT" +
                    " IF (MONTH(`Pref_Move_in_Date`)=? AND YEAR(`Pref_Move_in_Date`)=YEAR(NOW())," +
                    " IF (DAY(`Pref_Move_in_Date`) <= 7," +
                    " a.`Rent`," +
                    " (a.`Rent` / ?) * (? - DAY(`Pref_Move_in_Date`))" +
                    " )," +
                    " IF (? < 4," +
                    " a.`Rent`," +
                    " a.`Rent` + (?-3)*50" +
                    " )" +
                    " ) AS 'Rent' " +
                    "FROM resident AS r" +
                    " JOIN apartment AS a ON a.`Apt_No` = r.`Apt_No`" +
                    " JOIN `prospective_resident` AS pr ON pr.`Username` = r.`Username`" +
                    "WHERE r.`Username` = ?";
        pst = conn.prepareCall(query);
        pst.setInt(1, monthNow);
        pst.setInt(2, daysOfMonth);
        pst.setInt(3, daysOfMonth);
        pst.setInt(4, dayNow);
        pst.setInt(5, dayNow);
        pst.setString(6, username);
        rs = pst.executeQuery();
        if (rs.next())
        {
            apt_due = rs.getString("Rent");
        }
        return apt_due;
    }

    public LocalDate getResidentMoveInDate(final String username) throws SQLException
    {
        query = "SELECT Pref_Move_in_Date FROM prospective_resident WHERE Username=?";
        pst = conn.prepareCall(query);
        pst.setString(1, username);
        rs = pst.executeQuery();
        String date = "";
        if (rs.next())
        {
            date = rs.getString("Pref_Move_in_Date");
        }
        if (date.length() > 0)
        {
            return LocalDate.parse(date);
        }
        return null;
    }
    public String getResidentLeaseTerm(final String username) throws SQLException
    {
        String leaseTerm = null;
        query = "SELECT Pref_Lease_Term FROM prospective_resident WHERE Username=?";
        pst = conn.prepareCall(query);
        pst.setString(1, username);
        rs = pst.executeQuery();
        if (rs.next())
        {
            leaseTerm = rs.getString("Pref_Lease_Term");
        }
        return leaseTerm;
    }
    public boolean getRentPaidForMonth(final String month, final String year, final String apt_no) throws SQLException
    {
        query = "SELECT Amount FROM pay_rent WHERE Apt_No=? AND Year=? AND Month=?";
        pst = conn.prepareCall(query);
        pst.setString(1, apt_no);
        pst.setString(2, year);
        pst.setString(3, month);
        rs = pst.executeQuery();
        return rs.isBeforeFirst();
    }
    /**
     *@return list of months. 
    */
    public List<String> getMonths() throws SQLException
    {
        query = "SELECT DISTINCT Month FROM date";
        pst = conn.prepareCall(query);
        rs = pst.executeQuery();
        List<String> months = new ArrayList<>();
        while (rs.next())
        {
            String s = rs.getString("Month");
            months.add(formatMonth(Integer.valueOf(s)));
        }
        return months;
    }
    /**
     * @param m month.
     *@return formatted integer month to string.
    */
    private String formatMonth(int m)
    {
        return symbols.getMonths()[m - 1];
    }
    /**
     *@return list of years. 
    */
    public List<Integer> getYears() throws SQLException
    {
        query = "SELECT DISTINCT Year FROM date";
        pst = conn.prepareCall(query);
        rs = pst.executeQuery();
        List<Integer> years = new ArrayList<>();
        while (rs.next())
        {
            String s = rs.getString("Year");
            years.add(Integer.parseInt(s));
        }
        return years;
    }
    /**
     * 
     * @param username username
     * @return list of current user's card informaton
     * @throws SQLException 
     */
    public List<Long> getResidentCards(final String username) throws SQLException
    {
        query = "SELECT  Card_No FROM payment_information WHERE Username=? ORDER BY Card_No ASC";
        pst = conn.prepareCall(query);
        pst.setString(1, username);
        rs = pst.executeQuery();
        List<Long> cards = new ArrayList<>();
        while (rs.next())
        {
            String s = rs.getString("Card_No");
//            s = s.substring(s.length()-4);
            cards.add(Long.parseLong(s));
        }
        return cards;
    }
    /**
     * 
     * @param apt_no apt no for payment
     * @param year year for payment
     * @param month month for payment
     * @param card_no chosen card no
     * @param date date for payment
     * @param amount amount for payment
     * @return true if paid, otherwise false
     * @throws SQLException 
     */
    public boolean rentPay(final String apt_no, final int year, final int month, final Long card_no, final LocalDate date, final double amount) throws SQLException
    {
        boolean result = false;
        query = "INSERT INTO pay_rent(Apt_No, Year, Month, Card_No, Date_of_Payment, Amount) "
                + "VALUES(?,?,?,?,?,?)";
        pst = conn.prepareCall(query);
        pst.setString(1, apt_no);
        pst.setInt(2, year);
        pst.setInt(3, month);
        pst.setLong(4, card_no);
        pst.setDate(5, Date.valueOf(date.toString()));
        pst.setDouble(6, amount);
        try{
            pst.executeUpdate();
            result = true;
        } catch (SQLException e){
            result = false;
            e.printStackTrace();
        }
        return result;
    }
    /**
     *@return list of issues
    */
    public List<String> getIssueTypes() throws SQLException
    {
        query = "SELECT Issue_type FROM issue ORDER BY Issue_type ASC";
        pst = conn.prepareCall(query);
        rs = pst.executeQuery();
        List<String> issueTypes = new ArrayList<>();
        while (rs.next())
        {
            String s = rs.getString("Issue_type");
            issueTypes.add(s);
        }
        return issueTypes;
    }
    /**
     * @param apt_no issued apt no
     * @param issue issue type
     * @param date date of submitting issue
     *@return true if request has been submitted, otherwise false 
    */
    public boolean requestMaintenanceSubmit(final String apt_no, final String issue, final LocalDate date) throws SQLException
    {
        boolean result = false;
        query = "INSERT INTO maintenance_request (Apt_no, Issue_type, Date_of_Request) "
                + "VALUES (?,?,?)";
        pst = conn.prepareCall(query);
        pst.setString(1, apt_no);
        pst.setString(2, issue);
        pst.setDate(3, Date.valueOf(date.toString()));
        try{
            pst.executeUpdate();
            result = true;
        } catch (SQLException e){
            result = false;
            e.printStackTrace();
        }
        return result;
    }
    public boolean newCardAdd(final String card_no, final String cvv, final String name, final LocalDate expiration_date, final String username) throws SQLException
    {
        boolean result = false;
        query = "INSERT INTO payment_information(Card_No, CVV, Name_on_Card, Exp_Date, Username) VALUES(?,?,?,?,?)";
        pst = conn.prepareCall(query);
        pst.setLong(1, Long.parseLong(card_no));
        pst.setInt(2, Integer.parseInt(cvv));
        pst.setString(3, name);
        pst.setDate(4, Date.valueOf(expiration_date.toString()));
        pst.setString(5, username);
        try{
            pst.executeUpdate();
            result = true;
        } catch (SQLException e){
            result = false;
            e.printStackTrace();
        }
        return result;
    }
    /**
     *@param card_no selected card to be deleted
     * @return true if deleted, otherwise false
    */
    public boolean cardDelete(final String card_no) throws SQLException
    {
        boolean result = false;
        query = "DELETE FROM payment_information WHERE Card_No=?";
        pst = conn.prepareCall(query);
        pst.setString(1, card_no);
        try{
            pst.executeUpdate();
            result = true;
        } catch (SQLException e){
            result = false;
            e.printStackTrace();
        }
        return result;
    }
    
    public List<String[]> getApplicationInformation() throws SQLException
    {        
        query = "SELECT DISTINCT Name, p.Username, DOB, Gender, Monthly_Income, Required_Category, Pref_Move_in_Date, Pref_Lease_Term, Min_Rent, Max_Rent "
                + "FROM prospective_resident p WHERE p.Username NOT IN (SELECT r.Username FROM resident r)";
        pst = conn.prepareCall(query);
        rs = pst.executeQuery();
        String[] single;
        List<String[]> applications = new ArrayList<>();
        while (rs.next())
        {
            single = new String[]{
                rs.getString("Name"),
                rs.getString("Username"),
                rs.getString("DOB"),
                rs.getString("Monthly_Income"),
                rs.getString("Pref_Move_in_Date"),
                rs.getString("Pref_Lease_Term"),
                rs.getString("Gender"),
                rs.getString("Min_Rent"),
                rs.getString("Max_Rent"),
                rs.getString("Required_Category")
            };
            applications.add(single);
        }
        return applications;
    }
    
    public List<String[]> getApartmentCategoryInformation(final String username) throws SQLException
    {
        query = "SELECT Apt_no, Category, Rent, Square_Feet, Available_On " 
                + "FROM apartment, prospective_resident " 
                + "WHERE apartment.Category = prospective_resident.Required_Category " 
                + "AND (3*apartment.Rent) <= prospective_resident.Monthly_Income "
                + "AND apartment.Available_On < prospective_resident.Pref_Move_in_Date "
                + "AND prospective_resident.Username=?"
                + " AND Apt_no NOT IN (SELECT Apt_no FROM resident)";
        pst = conn.prepareCall(query);
        pst.setString(1, username);
        rs = pst.executeQuery();
        String[] single;
        List<String[]> apartments = new ArrayList<>();
        while (rs.next())
        {
            single = new String[]{
                rs.getString("Apt_no"),
                rs.getString("Category"),
                rs.getString("Rent"),
                rs.getString("Square_Feet"),
                rs.getString("Available_On")
            };
            apartments.add(single);
        }
        return apartments;
    }
    public String getApplicantName(final String username) throws SQLException
    {
        String name = "";
        query = "SELECT Name FROM prospective_resident WHERE Username=?";
        pst = conn.prepareCall(query);
        pst.setString(1, username);
        rs = pst.executeQuery();
        if (rs.next())
        {
            name = rs.getString("Name");
        }
        return name;
    }   
    /**
     * 
     * @param username username for the assigned apartment
     * @param apt_no apartment number for the username
     * @return true if assigned, otherwise false
     * @throws SQLException 
     */
    public boolean apartmentAssign(final String username, final String apt_no, final String leaseTerm) throws SQLException
    {
        final LocalDate residentMoveInDate = getResidentMoveInDate(username);
        query = "UPDATE apartment SET Available_On=DATE_ADD(?, INTERVAL ? MONTH), Lease_Term=? WHERE Apt_No=?";
        pst = conn.prepareCall(query);
        pst.setDate(1, Date.valueOf(residentMoveInDate.toString()));
        pst.setString(2, leaseTerm);
        pst.setString(3, leaseTerm);
        pst.setString(4, apt_no);
        try{
            pst.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
        boolean result = false;
        query = "INSERT INTO resident(Username, Apt_No) VALUES(?,?)";
        pst = conn.prepareCall(query);
        pst.setString(1, username);
        pst.setString(2, apt_no);
        try{
            pst.executeUpdate();
            result = true;
        } catch (SQLException e){
            result = false;
            e.printStackTrace();
        }
        return result;
    }
    
    public LocalDate getIssueRequestDate(final String aptNo) throws SQLException
    {
        String date = "";
        query = "SELECT Date_of_Request FROM maintenance_request WHERE Date_Resolved IS NULL AND Apt_No=?;";
        pst = conn.prepareCall(query);
        pst.setString(1, aptNo);
        rs = pst.executeQuery();
        if (rs.next())
        {
            date = rs.getString("Date_of_Request");
            System.out.println(date);
        }
        if (date.length() > 0)
        {
            return LocalDate.parse(date);
        }
        System.out.println(aptNo);
        return null;
    }
    public List<String[]> getIssueRequested() throws SQLException
    {
        query = "SELECT Date_of_Request, Apt_No, Issue_Type, Date_Resolved FROM maintenance_request";
        pst = conn.prepareCall(query);
        rs = pst.executeQuery();
        String[] single;
        List<String[]> issueRequested = new ArrayList<>();
        while (rs.next())
        {
            single = new String[]{
                rs.getString("Date_of_Request"),
                rs.getString("Apt_No"),
                rs.getString("Issue_Type"),
                rs.getString("Date_Resolved")
            };
            issueRequested.add(single);
        }
        return issueRequested;
    }
    public boolean requestResolvedForApt(final String apt_no, final String issue, final String date, final LocalDate ldate) throws SQLException
    {
        boolean result = false;
        query = "UPDATE maintenance_request " 
                + "SET Date_Resolved=? " 
                + "WHERE Date_of_Request=? AND Apt_No=? AND Issue_Type=?";
        pst = conn.prepareCall(query);
        pst.setDate(1, Date.valueOf(ldate.toString()));
        pst.setString(2, date);
        pst.setString(3, apt_no);
        pst.setString(4, issue);
        try{
            pst.executeUpdate();
            result = true;
        } catch (SQLException e){
            result = false;
            e.printStackTrace();
        }
        return result;
    }
    
    public List<String> getPastDueAptNo() throws SQLException
    {
        query = "SELECT DISTINCT p.Apt_No FROM resident p "
            + "INNER JOIN(SELECT MAX(Month) Month, Apt_No FROM pay_rent GROUP BY Apt_No)ss "
            + "ON p.Apt_No=ss.Apt_No AND ss.Month<(SELECT MONTH (CURDATE())) AND 01<(SELECT DAY (CURDATE())) "
            + "ORDER BY Apt_No ASC;";
        pst = conn.prepareCall(query);
        rs = pst.executeQuery();
        List<String> aptList = new ArrayList<>();
        while (rs.next())
        {
            aptList.add(rs.getString("Apt_No"));
        }
        return aptList;
    }    
    public boolean reminderSend(final String apt_no, final LocalDate date, final String msg, final String status) throws SQLException
    {
        boolean result = false;
        query = "INSERT INTO reminder (Apt_No, Date, Message, Status) VALUES(?,?,?,?)";
        pst = conn.prepareCall(query);
        pst.setString(1, apt_no);
        pst.setDate(2, Date.valueOf(date.toString()));
        pst.setString(3, msg);
        pst.setString(4, status);
        try{
            pst.executeUpdate();
            result = true;
        } catch (SQLException e){
            result = false;
            e.printStackTrace();
        }
        return result;
    }
    public int getResidentReminderCount(final String username) throws SQLException
    {
        int count = 0;
        query = "SELECT COUNT(Message) FROM reminder "
                + "WHERE Apt_No IN (SELECT Apt_No FROM resident WHERE Username=?) AND status='unread' GROUP BY Apt_No";
        pst = conn.prepareCall(query);
        pst.setString(1, username);
        rs = pst.executeQuery();
        while (rs.next())
        {
            count += Integer.parseInt(rs.getString("COUNT(Message)"));
        }
        return count;
    }
    public List<String> getResidentReminder(final String username) throws SQLException
    {
        query = "SELECT DISTINCT Message FROM reminder, resident WHERE reminder.Apt_No=resident.Apt_No and resident.Username=?";
        pst = conn.prepareCall(query);
        pst.setString(1, username);
        rs = pst.executeQuery();
        List<String> msg = new ArrayList<>();
        while (rs.next())
        {
            msg.add(rs.getString("Message"));
        }
        return msg;
    }
    public boolean updateReminder(final String username) throws SQLException
    {
        boolean result = false;
        query = "UPDATE reminder SET status='read' WHERE Apt_No IN (SELECT Apt_No FROM resident WHERE Username=?)";
       // query = "DELETE FROM reminder WHERE Apt_No IN (SELECT Apt_No FROM resident WHERE Username=?)";
        pst = conn.prepareCall(query);
        pst.setString(1, username);
        try{
            pst.executeUpdate();
            result = true;
        } catch (SQLException e) {
            result = false;
            e.printStackTrace();
        }
        return result;    
    }
    public List<String[]> getLeasingReport() throws SQLException
    {       
        query = "SELECT MONTH(STR_TO_DATE(8, '%m')) AS 'month', Category, Count(Category) AS No_of_Apartments " +
            "FROM resident AS r " +
            " JOIN apartment AS a ON a.Apt_No = r.`Apt_No` " +
            " JOIN `prospective_resident` AS pr ON pr.`Username` = r.`Username` " +
            "WHERE 8 BETWEEN MONTH(pr.`Pref_Move_in_Date`) AND MONTH(pr.`Pref_Move_in_Date`) " +
            "GROUP BY Category " +
            "UNION " +
            "SELECT MONTH(STR_TO_DATE(9, '%m')) AS 'month', Category, Count(Category) AS No_of_Apartments " +
            "FROM resident AS r " +
            " JOIN apartment AS a ON a.Apt_No = r.`Apt_No` " +
            " JOIN `prospective_resident` AS pr ON pr.`Username` = r.`Username` " +
            "WHERE 9 BETWEEN MONTH(pr.`Pref_Move_in_Date`) AND MONTH(pr.`Pref_Move_in_Date`) " +
            "GROUP BY Category " +
            "UNION " +
            "SELECT MONTH(STR_TO_DATE(10, '%m')) AS 'month', Category, Count(Category) AS No_of_Apartments " +
            "FROM resident AS r " +
            " JOIN apartment AS a ON a.Apt_No = r.`Apt_No` " +
            " JOIN `prospective_resident` AS pr ON pr.`Username` = r.`Username` " +
            "WHERE 10 BETWEEN MONTH(pr.`Pref_Move_in_Date`) AND MONTH(pr.`Pref_Move_in_Date`) " +
            "GROUP BY Category";
        pst = conn.prepareCall(query);
        rs = pst.executeQuery();
        String[] single;
        List<String[]> reportList = new ArrayList<>();
        while (rs.next())
        {
            single = new String[]{
                rs.getString("Month"),
                rs.getString("Category"),
                rs.getString("No_of_Apartments")
            };
            reportList.add(single);
        }
        return reportList;
    }
    public List<String[]> getServiceReport() throws SQLException
    {
        query = "SELECT MONTH(Date_Resolved) AS MONTH, "
                + "issue.Issue_Type AS Type_of_Request, "
                + "IF(Date_Resolved IS NULL, '0', (CAST(IF(AVG(DATEDIFF(Date_Resolved, Date_Of_Request))>0, "
                + "AVG(DATEDIFF(Date_Resolved, Date_Of_Request)),1) AS UNSIGNED))) AS Average_No_Of_Days "
                + "FROM issue JOIN maintenance_request ON issue.Issue_type=maintenance_request.Issue_type "
                + "GROUP BY MONTH, issue.Issue_Type "
                + "ORDER BY MONTH ASC";
        pst = conn.prepareCall(query);
        rs = pst.executeQuery();
        String[] single;
        List<String[]> reportList = new ArrayList<>();
        while (rs.next())
        {
            single = new String[]{
                rs.getString("Month"),
                rs.getString("Type_of_Request"),
                rs.getString("Average_No_Of_Days")
            };
            reportList.add(single);
        }
        return reportList;
    }
    public List<String[]> getDefaultReport(final int month) throws SQLException
    {    
        query = "SELECT r.Apt_No AS apartment, ( 50 * ( DAY( Date_of_Payment ) -3 ) ) AS extra_amount_paid, "
            + "(DAY(Date_of_Payment ) -3) AS defaulted_by FROM pay_rent pay "
            + "JOIN resident r ON pay.`Apt_No` = r.`Apt_No` "
            + "JOIN `prospective_resident` AS pr ON pr.`Username` = r.`Username` "
            + "WHERE MONTH( `Pref_Move_in_Date` ) !=? "
            + "AND MONTH( pay.Date_of_Payment ) =? "
            + "AND DAY( pay.Date_of_Payment ) >=4";
        pst = conn.prepareCall(query);
        pst.setInt(1, month);
        pst.setInt(2, month);
        rs = pst.executeQuery();
        String[] single;
        List<String[]> reportList = new ArrayList<>();
        while (rs.next())
        {
            single = new String[]{
                rs.getString("apartment"),
                rs.getString("extra_amount_paid"),
                rs.getString("defaulted_by")
            };
            reportList.add(single);
        }
        return reportList;
    }
    /**
     * @return current user's group, as in resident or management.
    */
    public String getUserGroup()
    {
        return this.userGroup;
    }

}
