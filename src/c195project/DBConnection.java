/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195project;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chris
 */
public class DBConnection {
    private static final String dbName = "U04zNO";
    private static final String dbUrl = "jdbc:mysql://52.206.157.109/" + dbName;
    private static final String dbUser = "U04zNO";
    private static final String dbPass = "53688391974";
    private static final String driver = "com.mysql.jdbc.Driver";
    
    static Connection conn;
    
    public static Connection makeConnection() throws ClassNotFoundException, SQLException {
        Class.forName(driver);        
        conn = (Connection) DriverManager.getConnection(dbUrl, dbUser, dbPass);
        
        System.out.println("Connection Successful");
        
        return conn;
    }
    
    public static void closeConnection() throws SQLException {
        conn.close();
        
        System.out.println("Connection Closed");
    }
    
    public static ArrayList<String> getDBAppointmentsFromDate(LocalDate date) {
            
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
    
    public static ArrayList<String> getDBCustomers() {
            
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
    
    public static void addDBAppointment(Appointment appointment) {
        try {
            
            Statement statement = (Statement) conn.createStatement();
            
            String start = appointment.getDate().toString() + " " + appointment.getStartTime();
            String end = appointment.getDate().toString() + " " + appointment.getEndTime();
            
            String query = "INSERT INTO appointment(customerId, userId, title, description, location, contact, type, url, start, end, createDate, createdBy, lastUpdateBy) VALUES ("+appointment.getCustomerId()+", "+appointment.getUserId()+", '"+appointment.getTitle()+"', '"+appointment.getDescription()+"', '"+appointment.getLocation()+"', '"+appointment.getContact()+"', '"+appointment.getType()+"', '"+appointment.getUrl()+"', CONVERT('"+start+"', DATETIME), CONVERT('"+end+"', DATETIME), NOW(), (SELECT "+appointment.getUserId()+"), "+appointment.getUserId()+");";
            
            int result = statement.executeUpdate(query);
        
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
