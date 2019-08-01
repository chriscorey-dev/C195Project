/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195project;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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
    @FXML private Spinner customer;
    
    @FXML
    private void submitAppointmentHandle(ActionEvent event) {
        // TODO: Validate
        
        Appointment appointment = new Appointment(1, 1, title.getText(), description.getText(), location.getText(), contact.getText(), type.getText(), url.getText(), date.getValue(), startTime.getText(), endTime.getText());
        DBConnection.addDBAppointment(appointment);
    }
    
    @FXML
    private void cancelAppointmentHandle(ActionEvent event) {
        // TODO: Confirmation dialog
        SceneManager.loadScene(SceneManager.MAIN_FXML);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO: Check if updating or adding new. If updating insert correct fields
    }    
    
}
