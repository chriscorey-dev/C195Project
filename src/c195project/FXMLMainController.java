/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195project;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author chris
 */
public class FXMLMainController implements Initializable {

    @FXML
    private Button addAppointmentButton;

    @FXML
    private Button addCustomerButton;
    
    @FXML
    private ListView appointmentsListView;
    
//    @FXML
//    private TableView appointmentsTableView;
//    
//    @FXML
//    private TableColumn appointmentsTableTitleColumn;
    
    @FXML
    private ListView customersListView;
    
    @FXML
    private DatePicker datePicker;
    
    @FXML
    private void addAppointmentHandler(ActionEvent event) {
        SceneManager.loadScene(SceneManager.APPOINTMENT_FXML);
    }
    
    @FXML
    private void addCustomerHandler(ActionEvent event) {
        SceneManager.loadScene(SceneManager.CUSTOMER_FXML);
    }
    
    @FXML
    private void selectedDateHandler(ActionEvent event) {
        updateAppointments();
    }
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        datePicker.setValue(LocalDate.now());
        
        updateAppointments();
        updateCustomers();
    }
    
    private void updateAppointments() {
        // TODO: Fill array with appointment object, and pull title to push to tableView. When clicked it displayes more data & has access to the ID
        
        ArrayList<String> appointmentList = DBConnection.getDBAppointmentsFromDate(datePicker.getValue());
        
        ObservableList<String> list = FXCollections.observableArrayList(appointmentList);
        appointmentsListView.setItems(list);
//        appointmentsTableView.setItems(list);
//        appointmentsTableTitleColumn.setCellValueFactory(new PropertyValueFactory("title"));
//        appointmentsTableTitleColumn.;
//        appointmentsTableView.getColumns().setAll(list);
    }
    
    private void updateCustomers() {
        ArrayList<String> customerList = DBConnection.getDBCustomers();
        
        ObservableList<String> list = FXCollections.observableArrayList(customerList);
        customersListView.setItems(list);
    }
}
