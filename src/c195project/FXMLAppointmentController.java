/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195project;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author chris
 */
public class FXMLAppointmentController implements Initializable {
    
    @FXML private DatePicker date;
    @FXML private TextField title;
    @FXML private TextArea description;
    @FXML private TextField location;
    @FXML private TextField contact;
    @FXML private TextField type;
    @FXML private TextField url;
    @FXML private TextField startTime;
    @FXML private TextField endTime;
    @FXML private ListView<Customer> customers;
    @FXML private Label validation;
    
    @FXML
    private void submitAppointmentHandle(ActionEvent event) throws IOException, ParseException {
        // TODO: Validate
        
        Customer customer = (Customer) customers.getSelectionModel().getSelectedItem();
        if (customer == null) {
            validation.setText(":c");
            return;
        }
//        Appointment appointment = new Appointment(customer.getCustomerId(), C195Project.getLoggedInUser().getUserId(), title.getText(), description.getText(), location.getText(), contact.getText(), type.getText(), url.getText(), date.getValue(), startTime.getText(), endTime.getText());


        // TODO: Time pattern validation here
        if (startTime.getText().isEmpty() || endTime.getText().isEmpty()) {
            validation.setText(":c");
            return;
        }
        
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = format.parse(date.getValue()+" "+startTime.getText());
            Date end = format.parse(date.getValue()+" "+endTime.getText());
        
//            System.out.println("start: " + format.format(start));

            Appointment appointment = new Appointment(customer.getCustomerId(), C195Project.getLoggedInUser().getUserId(), title.getText(), description.getText(), location.getText(), contact.getText(), type.getText(), url.getText(), start, end);
            ArrayList<String> errors = Appointment.validate(appointment);

            if (errors.isEmpty()) {
                SQLQueries.addAppointment(appointment);
                SceneManager.loadScene(SceneManager.MAIN_FXML);
            } else {
                String errorString = "";
                for(String error : errors) {
                    errorString += error + "\n";
                }
                validation.setText(errorString);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    @FXML
    private void cancelAppointmentHandle(ActionEvent event) throws IOException {
        // TODO: Confirmation dialog
        SceneManager.loadScene(SceneManager.MAIN_FXML);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO: Check if updating or adding new. If updating insert correct fields
//            validation.setWrapText(true);

        populateCustomers();
    }
    
    private void populateCustomers() {
        ArrayList<Customer> customerList = SQLQueries.getAllCustomers();

        ObservableList<Customer> list = FXCollections.observableArrayList(customerList);
        customers.setCellFactory(new Callback<ListView<Customer>, ListCell<Customer>>() {

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
        customers.setItems(list);
    }
}
