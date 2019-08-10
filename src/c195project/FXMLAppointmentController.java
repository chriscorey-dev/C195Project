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
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
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
    @FXML private ListView<User> users;
    @FXML private Label validation;
    
    @FXML private ChoiceBox startTimeChoiceBox;
    @FXML private ChoiceBox endTimeChoiceBox;
    
    @FXML private Button submitAppointmentButton;
    
    @FXML
    private void submitAppointmentHandle(ActionEvent event) throws IOException, ParseException {
        // TODO: Validate
        
        Customer customer = (Customer) customers.getSelectionModel().getSelectedItem();
        if (customer == null) {
            validation.setText("Pick a customer");
            return;
        }
        User user = (User) users.getSelectionModel().getSelectedItem();
        if (user == null) {
            validation.setText("Pick a consultant");
            return;
        }
        if (startTimeChoiceBox.getValue() == null || endTimeChoiceBox.getValue() == null) {
            validation.setText("Pick a start and end time");
            return;
        }
        if (date.getValue() == null) {
            validation.setText("Pick a date");
            return;
        }
        
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = format.parse(date.getValue()+" "+startTimeChoiceBox.getValue()+":00");
            Date end = format.parse(date.getValue()+" "+endTimeChoiceBox.getValue()+":00");
            Appointment appointment = new Appointment(customer.getCustomerId(), user.getUserId(), title.getText(), description.getText(), location.getText(), contact.getText(), type.getText(), url.getText(), start, end);
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
    
    private void editAppointmentHandle(Appointment appointment) {
        ArrayList<String> errors = Appointment.validate(appointment);
        
        if (errors.isEmpty()) {
        
            SQLQueries.updateAppointment(appointment);
            try {
                SceneManager.loadScene(SceneManager.MAIN_FXML);
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        } else {
            String errorString = "";
            for(String error : errors) {
                errorString += error + "\n";
            }
            validation.setText(errorString);
        }
    }
    
    @FXML
    private void cancelAppointmentHandle(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Cancel Appointment");
        alert.setContentText("Are you sure you want discard changes?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            SceneManager.loadScene(SceneManager.MAIN_FXML);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO: Check if updating or adding new. If updating insert correct fields
//            validation.setWrapText(true);

        String[] asdf = {"08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30"};
        ObservableList<String> times = FXCollections.observableArrayList(asdf);
        startTimeChoiceBox.setItems(times);
        endTimeChoiceBox.setItems(times);

        populateCustomers();
        populateUsers();
    }
    
    private void populateCustomers() {
        ArrayList<Customer> customerList = SQLQueries.getAllActiveCustomers();

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
    
    private void populateUsers() {
        ArrayList<User> userList = SQLQueries.getAllActiveUsers();

        ObservableList<User> list = FXCollections.observableArrayList(userList);
        users.setCellFactory(new Callback<ListView<User>, ListCell<User>>() {

            @Override
            public ListCell<User> call(ListView<User> param) {
                ListCell<User> cell = new ListCell<User>() {
                    @Override
                    protected void updateItem(User item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getUserName());
                        } else {
                            setText("");
                        }
                    }
                };
                return cell;
            }
        });
        users.setItems(list);
    }
    
    public void loadAppointment(Appointment appointment) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // Lambda expression: Sets on action for submit button to change when editing instead of adding new appointment.
        submitAppointmentButton.setOnAction((arg0) -> {
            try {
                Date start = format.parse(date.getValue()+" "+startTimeChoiceBox.getValue()+":00");
                Date end = format.parse(date.getValue()+" "+endTimeChoiceBox.getValue()+":00");
//                public Appointment(int appointmentId, int customerId, int userId, String title, String description, String location, String contact, String type, String url, Date start, Date end) {
                Appointment updatedAppointment = new Appointment(appointment.getAppointmentId(), customers.getSelectionModel().getSelectedItem().getCustomerId(), users.getSelectionModel().getSelectedItem().getUserId(), title.getText(), description.getText(), location.getText(), contact.getText(), type.getText(), url.getText(), start, end);
                editAppointmentHandle(updatedAppointment);
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        });
        
        title.setText(appointment.getTitle());
        description.setText(appointment.getDescription());
        location.setText(appointment.getLocation());
        contact.setText(appointment.getContact());
        type.setText(appointment.getType());
        url.setText(appointment.getUrl());
        
        Date mDate = appointment.getStart();
        LocalDate localDate = mDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        date.setValue(localDate);
        
        String[] startTimeString = format.format(appointment.getStart()).split(" ")[1].split(":");
        String startString = startTimeString[0]+":"+startTimeString[1];
        String[] endTimeString = format.format(appointment.getEnd()).split(" ")[1].split(":");
        String endString = endTimeString[0]+":"+endTimeString[1];
        
        startTimeChoiceBox.setValue(startString);
        endTimeChoiceBox.setValue(endString);
        
        ObservableList<Customer> customersList = customers.getItems();
        for (Customer customer : customersList) {
            if (customer.getCustomerId()== appointment.getCustomerId()) {
                customers.getSelectionModel().select(customer);
                break;
            }
        }
        
        ObservableList<User> usersList = users.getItems();
        for (User user : usersList) {
            if (user.getUserId()== appointment.getUserId()) {
                users.getSelectionModel().select(user);
                break;
            }
        }
    }
}
