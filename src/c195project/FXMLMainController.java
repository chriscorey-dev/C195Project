/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195project;

import java.io.IOException;
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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

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
    private ListView<Appointment> appointmentsListView;
    
//    @FXML
//    private TableView appointmentsTableView;
//    
//    @FXML
//    private TableColumn appointmentsTableTitleColumn;
    
    @FXML
    private ListView<Customer> customersListView;
    
    @FXML
    private DatePicker datePicker;
    
    @FXML
    private void addAppointmentHandler(ActionEvent event) throws IOException {
        SceneManager.loadScene(SceneManager.APPOINTMENT_FXML);
    }
    
    @FXML
    private void addCustomerHandler(ActionEvent event) throws IOException {
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
        ArrayList<Appointment> customerList = SQLQueries.getUsersApponitmentsOnDate(datePicker.getValue());

        ObservableList<Appointment> list = FXCollections.observableArrayList(customerList);
        appointmentsListView.setCellFactory(new Callback<ListView<Appointment>, ListCell<Appointment>>() {

            @Override
            public ListCell<Appointment> call(ListView<Appointment> param) {
                ListCell<Appointment> cell = new ListCell<Appointment>() {
                    @Override
                    protected void updateItem(Appointment item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getTitle());
                        } else {
                            setText("");
                        }
                    }
                };
                return cell;
            }
        });
        appointmentsListView.setItems(list);
    }
    
    private void updateCustomers() {
        ArrayList<Customer> customerList = SQLQueries.getAllCustomers();

        ObservableList<Customer> list = FXCollections.observableArrayList(customerList);
        customersListView.setCellFactory(new Callback<ListView<Customer>, ListCell<Customer>>() {

            @Override
            public ListCell<Customer> call(ListView<Customer> param) {
                ListCell<Customer> cell = new ListCell<Customer>() {
                    @Override
                    protected void updateItem(Customer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getCustomerName());
                        } else {
                            setText("");
                        }
                    }
                };
                return cell;
            }
        });
        customersListView.setItems(list);
    }
}
