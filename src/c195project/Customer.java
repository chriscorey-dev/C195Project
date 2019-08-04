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
        
//        if (appointment.getTitle().isEmpty()) {
//            errors.add("Title field is required");
//        }
//        if (appointment.getDescription().isEmpty()) {
//            errors.add("Description field is required");
//        }
//        if (appointment.getLocation().isEmpty()) {
//            errors.add("Location field is required");
//        }
//        if (appointment.getContact().isEmpty()) {
//            errors.add("Contact field is required");
//        }
//        if (appointment.getType().isEmpty()) {
//            errors.add("Type field is required");
//        }
//        if (appointment.getUrl().isEmpty()) {
//            errors.add("Url field is required");
//        }
//        if (appointment.getDate() == null) {
//            errors.add("Date field is required");
//        }
//        if (appointment.getStartTime().isEmpty()) {
//            errors.add("Start Time field is required");
//        }
//        if (appointment.getEndTime().isEmpty()) {
//            errors.add("End Time field is required");
//        }
//        
//        String timePattern = "\\d{2}:\\d{2}:\\d{2}";
////        String timePattern = "\\d{2}[0-23]:\\d{2}[0-59]:\\d{2}[0-59]";
////        String timePattern = "\\d[0-2]{2}:\\d{2}:\\d{2}";
////        String timePatter = "^()$";
////        Time startTime = new Time(appointment.getStartTime().split(":")[0]);
////        Time endTime = new Time(1,1,1);
//        if (!appointment.getStartTime().matches(timePattern)) {
//            errors.add("Start Time format must be '##:##:##'");
//        } else if (!appointment.getEndTime().matches(timePattern)) {
//            errors.add("End Time format must be '##:##:##'");}
////        } else if (startTime.after(endTime)) {
////            errors.add("Start Time must be before End Time");
////        }
        
        return errors;
    }
}
