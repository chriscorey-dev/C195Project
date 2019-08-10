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
public class Address {

    private int addressId;
    private final String address;
    private final String address2;
    private final int cityId;
    private final String postalCode;
    private final String phone;
    
    public Address(int addressId, String address, String address2, int cityId, String postalCode, String phone) {
        this.addressId = addressId;
        this.address = address;
        this.address2 = address2;
        this.cityId = cityId;
        this.postalCode = postalCode;
        this.phone = phone;
    }
    
    public Address(String address, String address2, int cityId, String postalCode, String phone) {
        this.addressId = -1;
        this.address = address;
        this.address2 = address2;
        this.cityId = cityId;
        this.postalCode = postalCode;
        this.phone = phone;
    }
    
    public int getAddressId() {
        return addressId;
    }

    public String getAddress() {
        return address;
    }

    public String getAddress2() {
        return address2;
    }

    public int getCityId() {
        return cityId;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPhone() {
        return phone;
    }
    
    public static ArrayList<String> validate(Address address) {
        ArrayList<String> errors = new ArrayList<>();
        
        if (address.getAddress().isEmpty()) {
            errors.add("Address field is required");
        }
        if (address.getPostalCode().isEmpty()) {
            errors.add("Postal Code field is required");
        }
        if (address.getPhone().isEmpty()) {
            errors.add("Postal field is required");
        }
        
        return errors;
    }
}
