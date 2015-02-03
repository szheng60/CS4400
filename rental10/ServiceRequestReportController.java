/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rental10;

import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import mysqlquery.QueryInfoMediator;
import rental10.model.ScreenTemplate;
import rental10.model.ServiceReport;
import rental10.model.SetControlScreen;

/**
 * FXML Controller class
 *
 * @author song
 */
public class ServiceRequestReportController extends ScreenTemplate implements Initializable , SetControlScreen {

    /**
     *main controller. 
    */
    private transient ScreenController theController;
        /**
     *query mediator. 
    */
    private final transient QueryInfoMediator queryInfo = QueryInfoMediator.getInstance();
    /**
     * month converter.
     */
    private final transient DateFormatSymbols symbols = new DateFormatSymbols();  
    @FXML
    private TableColumn monthCol;
    @FXML
    private TableColumn requestCol;
    @FXML
    private TableColumn dayNoCol;
    @FXML
    private TableView tableReport;
    /**
    * Initializes the controller class.
    */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.setTitleLabel(this.getServiceRequestReportScreen());
        monthCol.setCellValueFactory(new PropertyValueFactory<ServiceReport, String>("month"));
        requestCol.setCellValueFactory(new PropertyValueFactory<ServiceReport, String>("request"));
        dayNoCol.setCellValueFactory(new PropertyValueFactory<ServiceReport, String>("dayNo"));
    }     
    public void initServiceRequestReportInformation() throws SQLException
    {
        tableReport.setItems(getServiceReport(queryInfo.getServiceReport()));
    }
    private ObservableList<ServiceReport> getServiceReport(final List<String[]> reportList) throws SQLException
    {
        List<ServiceReport> reports = new ArrayList<>();
        int m = 0;
        for (String[] s : reportList)
        {
            if (s[0] != null)
            {
                final String category = s[1];
                final String aveNoDays = s[2];
                m = Integer.parseInt(s[0]);
                final String month = symbols.getMonths()[m-1];

                reports.add(new ServiceReport(month, category, aveNoDays));
            }
        }
        final ObservableList<ServiceReport> data = FXCollections.observableArrayList(reports);
        return data;
    }
    @FXML
    private void homeScreenHandler()
    {
        theController.setScreen(this.getHomepageScreen());
    }
    @Override
    public void setScreenParent(ScreenController screen) {
        theController = screen;
    }
     
}
