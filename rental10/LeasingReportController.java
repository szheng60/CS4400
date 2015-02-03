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
import rental10.model.LeasingReport;
import rental10.model.ScreenTemplate;
import rental10.model.SetControlScreen;

/**
 * FXML Controller class
 *
 * @author song
 */
public class LeasingReportController extends ScreenTemplate implements Initializable , SetControlScreen {
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
    /**
    * Initializes the controller class.
    */
    @FXML
    private TableColumn monthCol;
    @FXML
    private TableColumn categoryCol;
    @FXML
    private TableColumn aptsCol;
    @FXML
    private TableView tableReport;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.setTitleLabel(this.getLeasingReportScreen());
        monthCol.setCellValueFactory(new PropertyValueFactory<LeasingReport, String>("month"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<LeasingReport, String>("category"));
        aptsCol.setCellValueFactory(new PropertyValueFactory<LeasingReport, String>("apts"));
    }     
    public void initLeasingReportInformation() throws SQLException
    {
        tableReport.setItems(getLeasingReport(queryInfo.getLeasingReport()));                
    }
    private ObservableList<LeasingReport> getLeasingReport(final List<String[]> reportList)
    {
        List<LeasingReport> reports = new ArrayList<>();
        for (String[] s : reportList)
        {
            final int m = Integer.parseInt(s[0]) - 1;
            final String month = symbols.getMonths()[m];
            final String category = s[1];
            final String aptNo = s[2];
            reports.add(new LeasingReport(month, category, aptNo));
        }
        final ObservableList<LeasingReport> data = FXCollections.observableArrayList(reports);
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
