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
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author chris
 */
public class FXMLMainController implements Initializable {

    @FXML private Button addAppointmentButton;
    @FXML private Button addCustomerButton;
    @FXML private Button editAppointmentButton;
    @FXML private Button editCustomerButton;
    @FXML private Button deleteAppointmentButton;
    @FXML private Button deleteCustomerButton;
    @FXML private ListView<Appointment> appointmentsListView;
    @FXML private ListView<Customer> customersListView;
    @FXML private DatePicker datePicker;
    
    @FXML private ChoiceBox appViewType;
    
    @FXML
    private void addAppointmentHandler(ActionEvent event) throws IOException {
        SceneManager.loadScene(SceneManager.APPOINTMENT_FXML);
    }
    
    @FXML
    private void addCustomerHandler(ActionEvent event) throws IOException {
        SceneManager.loadScene(SceneManager.CUSTOMER_FXML);
    }
    
    @FXML
    private void editAppointmentHandler(ActionEvent event) throws IOException {
        Appointment selectedAppointment = appointmentsListView.getSelectionModel().getSelectedItem();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLAppointment.fxml"));
        Parent root = loader.load();
        
        FXMLAppointmentController appointmentController = loader.getController();
        appointmentController.loadAppointment(selectedAppointment);
        
//        Stage stage = new Stage();
        Stage stage = SceneManager.getStage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Edit Appointment");
    }
    
    @FXML
    private void editCustomerHandler(ActionEvent event) throws IOException {
        Customer selectedCustomer = customersListView.getSelectionModel().getSelectedItem();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLCustomer.fxml"));
        Parent root = loader.load();
        
        FXMLCustomerController customerController = loader.getController();
        customerController.loadCustomer(selectedCustomer);
        
//        Stage stage = new Stage();
        Stage stage = SceneManager.getStage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Edit Customer");
    }
    
    @FXML
    private void deleteAppointmentHandler(ActionEvent event) {
        Appointment appointment = appointmentsListView.getSelectionModel().getSelectedItem();
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Deleting Appointment");
        alert.setContentText("Are you sure you want to delete " + appointment.getTitle() + "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            SQLQueries.deleteAppointmentById(appointment.getAppointmentId());
            updateAppointments(null);
        }
    }
    
    @FXML
    private void deleteCustomerHandler(ActionEvent event) {
        Customer customer = customersListView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Deleting Cusomer");
        alert.setContentText("Are you sure you want to delete " + customer.getCustomerName()+ " and all of it's related appointments?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            SQLQueries.deleteAppointmentsByCustomerId(customer.getCustomerId());
            SQLQueries.deleteCustomerById(customer.getCustomerId());
            SQLQueries.deleteAddressById(customer.getAddressId());
            updateAppointments(null);
            updateCustomers();
        }
    }
    
    @FXML
    private void selectedDateHandler(ActionEvent event) {
        updateAppointments(null);
    }
    
    
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        datePicker.setValue(LocalDate.now());
        
        ObservableList<String> list = FXCollections.observableArrayList(new String[]{"Single Day", "7 days", "30 days"});
        appViewType.setItems(list);
        appViewType.getSelectionModel().selectFirst();
        // Lambda expression: Listens for ChoiceBox value change and pulls appointments appropriately
        appViewType.getSelectionModel().selectedItemProperty().addListener((item, oldValue, newValue) -> {
            datePicker.setDisable(newValue != "Single Day");
            if (newValue == "Single Day") {
                updateAppointments(null);
            } else if (newValue == "7 days") {
                updateAppointments(SQLQueries.getUsersAppointments7Days());
            } else if (newValue == "30 days") {
                updateAppointments(SQLQueries.getUsersAppointments30Days());
            }
        });


        
        
        updateAppointments(null);
        updateCustomers();
    }
    
    private void updateAppointments(ArrayList<Appointment> appointmentListDefault) {
        ArrayList<Appointment> appointmentList;
        
        if (appointmentListDefault == null) {
            appointmentList = SQLQueries.getUsersAppointmentsOnDate(datePicker.getValue());
        } else {
            appointmentList = appointmentListDefault;
        }

        ObservableList<Appointment> list = FXCollections.observableArrayList(appointmentList);
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
        appointmentsListView.getSelectionModel().select(0);
        
        deleteAppointmentButton.setDisable(list.isEmpty());
        editAppointmentButton.setDisable(list.isEmpty());
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
                            setDisabled(item.getActive() == 0);
                        } else {
                            setText("");
                        }
                    }
                };
                return cell;
            }
        });
        customersListView.setItems(list);
        customersListView.getSelectionModel().select(0);
        
        deleteCustomerButton.setDisable(list.isEmpty());
        editCustomerButton.setDisable(list.isEmpty());
    }
}
