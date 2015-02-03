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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import mysqlquery.QueryInfoMediator;
import rental10.model.DefaultReport;
import rental10.model.ScreenTemplate;
import rental10.model.SetControlScreen;

/**
 * FXML Controller class
 *
 * @author song
 */
public class RentDefaulterReportController extends ScreenTemplate implements Initializable , SetControlScreen {
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
    private transient ComboBox monthSelect;
    @FXML
    private transient TableView tableReport;
    @FXML
    private transient TableColumn apt;
    @FXML
    private transient TableColumn fine;
    @FXML
    private transient TableColumn day;
    
    /**
    * Initializes the controller class.
    */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.setTitleLabel(this.getRentDefaultReportScreen());
            apt.setCellValueFactory(new PropertyValueFactory<DefaultReport, String>("apt"));
            fine.setCellValueFactory(new PropertyValueFactory<DefaultReport, String>("fine"));
            day.setCellValueFactory(new PropertyValueFactory<DefaultReport, String>("day"));
            monthSelect.setItems(FXCollections.observableArrayList( queryInfo.getMonths()));
            monthSelect.valueProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    try {
                        tableReport.setItems(getDefaultReport(queryInfo.getDefaultReport(deformatMonth(newValue))));
                    } catch (SQLException ex) {
                        Logger.getLogger(RentDefaulterReportController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        } catch (SQLException ex) {
            Logger.getLogger(RentDefaulterReportController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }     
    public void initRentDefaulterReportInformation() throws SQLException
    {
 
    }
    private ObservableList<DefaultReport> getDefaultReport(final List<String[]> reportList)
    {
        List<DefaultReport> reports = new ArrayList<>();
        for (String[] s : reportList)
        {
            final String aptNo = s[0];
            final String fine = s[1];
            final String day = s[2];
            reports.add(new DefaultReport(aptNo, fine, day));
        }
        final ObservableList<DefaultReport> data = FXCollections.observableArrayList(reports);
        return data;
    }
    
    /**
     *@param month selected month.
     * @return integer format for month
    */
    public int deformatMonth(final String month)
    {
        int m = 0;
        for (int i = 1; i < 13; i++)
        {
            if (month.equalsIgnoreCase(symbols.getMonths()[i - 1]))
            {
                m = i;
                break;
            }
        }
        return m;
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
