/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195project;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;
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
    
    @FXML private ChoiceBox reportChoiceBox;
    
    @FXML
    private void generateReport(ActionEvent event) throws IOException {
        String file = "report_output.txt";
        FileWriter fileWriter = new FileWriter(file, false); // False disables append
        PrintWriter outputFile = new PrintWriter(fileWriter);
        
        switch(reportChoiceBox.getSelectionModel().getSelectedItem().toString()) {
            case "Number of appointment types by month":
                ArrayList<String> appointmentTypes = SQLQueries.getAppointmentTypes();
                for (String type : appointmentTypes) {
                    outputFile.println(type);
                }
                break;
            case "The schedule for each consultant":
                ArrayList<String> schedules = SQLQueries.getConsultantSchedule();
                outputFile.println("UserName | Appointment Title | Start Time | End Time");
                
                for (String schedule : schedules) {
                    outputFile.println(schedule);
                }
                break;
            case "Number of customers in each city":
                ArrayList<String> counts = SQLQueries.getCustomersCountInCity();
                outputFile.println("City | occurrances");
                
                for (String count : counts) {
                    outputFile.println(count);
                }
                break;
            default:
                break;
        }
        outputFile.close();
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setHeaderText(null);
        File f = new File(file);
        alert.setContentText("Report generated at project root (" + f.getAbsoluteFile() + ")");
        ButtonType okButton = new ButtonType("OK");
        ButtonType openButton = new ButtonType("Open In Explorer");
        alert.getButtonTypes().setAll(okButton, openButton);
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == openButton) {
            Runtime.getRuntime().exec("explorer.exe /select," + f.getAbsolutePath());
        }
    }
    
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
                updateAppointments(SQLQueries.getAllAppointments7Days());
            } else if (newValue == "30 days") {
                updateAppointments(SQLQueries.getAllAppointments30Days());
            }
        });
        
        updateAppointments(null);
        updateCustomers();
        
        
        
        
        ObservableList<String> reportList = FXCollections.observableArrayList(new String[]{"Number of appointment types by month", "The schedule for each consultant", "Number of customers in each city"});
        reportChoiceBox.setItems(reportList);
        reportChoiceBox.getSelectionModel().selectFirst();
    }
    
    private void updateAppointments(ArrayList<Appointment> appointmentListDefault) {
        ArrayList<Appointment> appointmentList;
        
        if (appointmentListDefault == null) {
            appointmentList = SQLQueries.getAllAppointmentsOnDate(datePicker.getValue());
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
