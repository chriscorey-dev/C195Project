/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195project;

import java.util.ArrayList;

/**
 *
 * @author chris
 */
public class Customer {

    private final int customerId;
    private final String customerName;
    private int addressId;
    private final int active;

    public Customer(String customerName, int addressId, int active) {
        this.customerId = -1;
        this.customerName = customerName;
        this.addressId = addressId;
        this.active = active;
    }

    public Customer(int customerId, String customerName, int addressId, int active) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.addressId = addressId;
        this.active = active;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }
    
    public int getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getAddressId() {
        return addressId;
    }

    public int getActive() {
        return active;
    }
    
    public static ArrayList<String> validate(Customer customer) {
        ArrayList<String> errors = new ArrayList<>();
        
        if (customer.getCustomerName().isEmpty()) {
            errors.add("Customer Name field is required");
        }
        
        return errors;
    }
}
