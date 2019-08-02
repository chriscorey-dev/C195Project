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

/**
 *
 * @author chris
 */
public class SQLQueries {
    public static ArrayList<String> getAppointmentsOnDate(LocalDate date) {
            
        ArrayList<String> appointments = new ArrayList<>();
        
        try {
            
            Statement statement = (Statement) conn.createStatement();
//            String query = "SELECT CONVERT(start, DATE) FROM appointment WHERE CONVERT(start, DATE) = CONVERT('"+date.toString()+"', DATE);";
            String query = "SELECT * FROM appointment WHERE CONVERT(start, DATE) = CONVERT('"+date.toString()+"', DATE);";
            
            ResultSet result = statement.executeQuery(query);
            
            while (result.next()) {
                appointments.add(result.getString("title"));
            }
        
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        return appointments;
    }
    
    public static ArrayList<String> getAllCustomers() {
            
        ArrayList<String> customers = new ArrayList<>();
        
        try {
            
            Statement statement = (Statement) conn.createStatement();
            String query = "SELECT * FROM customer WHERE active = 1;";
            
            ResultSet result = statement.executeQuery(query);
            
            while (result.next()) {
                customers.add(result.getString("customerName"));
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
}
