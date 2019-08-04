/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195project;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author chris
 */
public class FXMLCustomerController implements Initializable {
    @FXML private TextField name;
    @FXML private TextField address1;
    @FXML private TextField address2;
    @FXML private TextField cityId;
    @FXML private TextField postalCode;
    @FXML private TextField phone;
    @FXML private CheckBox active;
    @FXML private Label validation;
    
    @FXML
    private void submitCustomerHandle(ActionEvent event) throws IOException {
        Address address = new Address(address1.getText(), address2.getText(), 1, postalCode.getText(), phone.getText());
        ArrayList<String> addressErrors = Address.validate(address);
        
        Customer customer = new Customer(name.getText(), -1, active.isSelected() ? 1 : 0);
        ArrayList<String> customerErrors = Customer.validate(customer);
        
        if (customerErrors.isEmpty() && addressErrors.isEmpty()) {
            SQLQueries.addAddress(address);
        
            int addressId = SQLQueries.getLatestAddressId();
            customer.setAddressId(addressId);
        
            SQLQueries.addCustomer(customer);
            SceneManager.loadScene(SceneManager.MAIN_FXML);
        } else {
            String errorString = "";
            for(String error : customerErrors) {
                errorString += error + "\n";
            }
            for(String error : addressErrors) {
                errorString += error + "\n";
            }
            validation.setText(errorString);
        }
    }
    
    @FXML
    private void cancelCustomerHandle(ActionEvent event) throws IOException {
        // TODO: Confirmation dialog
        SceneManager.loadScene(SceneManager.MAIN_FXML);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
