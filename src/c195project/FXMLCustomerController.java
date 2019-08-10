/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195project;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author chris
 */
public class FXMLCustomerController implements Initializable { 
    @FXML private TextField name;
    @FXML private TextField address1;
    @FXML private TextField address2;
    @FXML private ListView<City> cityListView;
    @FXML private TextField postalCode;
    @FXML private TextField phone;
    @FXML private CheckBox active;
    @FXML private Label validation;
    
    @FXML private Button submitCustomerButton;
    
    @FXML
    private void submitCustomerHandle(ActionEvent event) throws IOException {
        City city = cityListView.getSelectionModel().getSelectedItem();
        if (city == null) {
            validation.setText("Pick a city");
            return;
        }
        
        Address address = new Address(address1.getText(), address2.getText(), cityListView.getSelectionModel().getSelectedItem().getCityId(), postalCode.getText(), phone.getText());
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
    
    private void editCustomerHandle(Customer customer, Address address) {
        
        ArrayList<String> addressErrors = Address.validate(address);
        ArrayList<String> customerErrors = Customer.validate(customer);
        
        if (customerErrors.isEmpty() && addressErrors.isEmpty()) {
        
            SQLQueries.updateCustomer(customer);
            SQLQueries.updateAddress(address);
            try {
                SceneManager.loadScene(SceneManager.MAIN_FXML);
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Cancel Customer");
        alert.setContentText("Are you sure you want discard changes?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            SceneManager.loadScene(SceneManager.MAIN_FXML);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ArrayList<City> cityList = SQLQueries.getAllCities();

        ObservableList<City> list = FXCollections.observableArrayList(cityList);
        cityListView.setCellFactory(new Callback<ListView<City>, ListCell<City>>() {

            @Override
            public ListCell<City> call(ListView<City> param) {
                ListCell<City> cell = new ListCell<City>() {
                    @Override
                    protected void updateItem(City item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getCity());
                        } else {
                            setText("");
                        }
                    }
                };
                return cell;
            }
        });
        cityListView.setItems(list);
    }
    
    public void loadCustomer(Customer customer) {
        Address address = SQLQueries.getAddressById(customer.getAddressId());
        
        // Lambda expression: Sets on action for submit button to change when editing instead of adding new customer.
        submitCustomerButton.setOnAction((arg0) -> {
            Customer updatedCustomer = new Customer(customer.getCustomerId(), name.getText(), customer.getAddressId(), active.isSelected() ? 1 : 0);
            Address updatedAddress = new Address(customer.getAddressId(), address1.getText(), address2.getText(), cityListView.getSelectionModel().getSelectedItem().getCityId(), postalCode.getText(), phone.getText());
            editCustomerHandle(updatedCustomer, updatedAddress);
        });
        
        name.setText(customer.getCustomerName());
        active.setSelected(customer.getActive() == 1);
        
        address1.setText(address.getAddress());
        address2.setText(address.getAddress2());
        postalCode.setText(address.getPostalCode());
        phone.setText(address.getPhone());
        
        ObservableList<City> cities = cityListView.getItems();
        for (City city : cities) {
            if (city.getCityId() == address.getCityId()) {
                cityListView.getSelectionModel().select(city);
                break;
            }
        }
    }
}
