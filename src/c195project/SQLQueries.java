/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195project;

import static c195project.DBConnection.conn;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.util.converter.LocalDateStringConverter;

/**
 *
 * @author chris
 */
public class SQLQueries {
    public static ArrayList<String> getAppointmentsTitlesOnDate(LocalDate date) {
        ArrayList<String> appointments = new ArrayList<>();
        
        try {
            
            Statement statement = (Statement) conn.createStatement();
//            String query = "SELECT CONVERT(start, DATE) FROM appointment WHERE CONVERT(start, DATE) = CONVERT('"+date.toString()+"', DATE);";
            String query = "SELECT * FROM appointment WHERE userId = "+C195Project.getLoggedInUser().getUserId()+" AND CONVERT(start, DATE) = CONVERT('"+date.toString()+"', DATE);";
            
            ResultSet result = statement.executeQuery(query);
            
            while (result.next()) {
                appointments.add(result.getString("title"));
            }
        
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        return appointments;
    }
    
    /// Queries logged in user's appointment on DatePicker's selected date
    public static ArrayList<Appointment> getUsersApponitmentsOnDate(LocalDate date) {
        ArrayList<Appointment> appointments = new ArrayList<>();
        
        try {
            
            Statement statement = (Statement) conn.createStatement();
            String query = "SELECT * FROM appointment WHERE userId = "+C195Project.getLoggedInUser().getUserId()+" AND CONVERT(start, DATE) = CONVERT('"+date.toString()+"', DATE);";
            
            ResultSet result = statement.executeQuery(query);
            
            while (result.next()) {
                LocalDate localDate = LocalDate.parse(result.getString("start").split(" ")[0]);
                String startTime = result.getString("start").split(" ")[1];
                String endTime = result.getString("end").split(" ")[1];
                
                Appointment appointment = new Appointment(result.getInt("appointmentId"), result.getInt("customerId"), result.getInt("userId"), result.getString("title"), result.getString("description"), result.getString("location"), result.getString("contact"), result.getString("type"), result.getString("url"), localDate, startTime, endTime);
                appointments.add(appointment);
            }
        
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        return appointments;
    }
    
    public static ArrayList<Customer> getAllCustomers() {
            
        ArrayList<Customer> customers = new ArrayList<>();
        
        try {
            
            Statement statement = (Statement) conn.createStatement();
            String query = "SELECT * FROM customer WHERE active = 1;";
            
            ResultSet result = statement.executeQuery(query);
            
            while (result.next()) {
//                customers.add(result.getString("customerName"));
                Customer customer = new Customer(result.getInt("customerId"), result.getString("customerName"), result.getInt("addressId"), result.getInt("active"));
                customers.add(customer);
            }
        
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        return customers;
    }
    
    public static ResultSet checkLoginCredentials(String username, String password) {
        ResultSet result = null;
            
        try {
            
            Statement statement = (Statement) conn.createStatement();
            String query = "SELECT * FROM user WHERE username = '" + username + "' AND BINARY password = '" + password + "' AND active = 1;";
            
            result = statement.executeQuery(query);
            
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
            
        return result;
    }
    
    public static void addAppointment(Appointment appointment) {
        try {
            
            Statement statement = (Statement) conn.createStatement();
            
            // Time format = ##:##:##
            
            String start = appointment.getDate().toString() + " " + appointment.getStartTime();
            String end = appointment.getDate().toString() + " " + appointment.getEndTime();
            
            String query = "INSERT INTO appointment(customerId, userId, title, description, location, contact, type, url, start, end, createDate, createdBy, lastUpdateBy) VALUES ("+appointment.getCustomerId()+", "+appointment.getUserId()+", '"+appointment.getTitle()+"', '"+appointment.getDescription()+"', '"+appointment.getLocation()+"', '"+appointment.getContact()+"', '"+appointment.getType()+"', '"+appointment.getUrl()+"', CONVERT('"+start+"', DATETIME), CONVERT('"+end+"', DATETIME), NOW(), '"+C195Project.getLoggedInUser().getUsername()+"', '"+C195Project.getLoggedInUser().getUsername()+"');";
            
            int result = statement.executeUpdate(query);
        
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    public static void addAddress(Address address) {
        try {
            Statement statement = (Statement) conn.createStatement();
            
            String query = "INSERT INTO address(address, address2, cityID, postalCode, phone, createDate, createdBy, lastUpdateBy) VALUES ('"+address.getAddress()+"', '"+address.getAddress2()+"', "+address.getCityId()+", '"+address.getPostalCode()+"', '"+address.getPhone()+"', NOW(), '"+C195Project.getLoggedInUser().getUsername()+"', '"+C195Project.getLoggedInUser().getUsername()+"');";
            
            int result = statement.executeUpdate(query);
            
            System.out.println("asdf: " + result);
        
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    public static int getLatestAddressId() {
        int addressId = -1;
        
        try {
            
            Statement statement = (Statement) conn.createStatement();
            String query = "SELECT addressId FROM address ORDER BY addressId DESC LIMIT 1;";
            
            ResultSet result = statement.executeQuery(query);
            
            if (result.next()) {
                addressId = result.getInt("addressId");
            }
        
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        return addressId;
    }
    
    public static Address getAddressById(int addressId) {
        Address address = null;
        
        try {
            
            Statement statement = (Statement) conn.createStatement();
            String query = "SELECT * FROM address WHERE addressId = "+addressId+";";
            
            ResultSet result = statement.executeQuery(query);
            
            if (result.next()) {
                new Address(addressId, result.getString("address"), result.getString("address2"), result.getInt("cityId"), result.getString("postalCode"), result.getString("phone"));
            }
        
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        return address;
    }
    
    public static void addCustomer(Customer customer) {
        try {
            Statement statement = (Statement) conn.createStatement();
            
            String query = "INSERT INTO customer(customerName, addressId, active, createDate, createdBy, lastUpdateBy) VALUES ('"+customer.getCustomerName()+"', "+customer.getAddressId()+", "+customer.getActive()+", NOW(),'"+C195Project.getLoggedInUser().getUsername()+"', '"+C195Project.getLoggedInUser().getUsername()+"');";
            
            int result = statement.executeUpdate(query);
        
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
