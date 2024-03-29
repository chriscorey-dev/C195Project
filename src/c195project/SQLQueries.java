/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195project;

import static c195project.DBConnection.conn;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.TimeZone;

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
    public static ArrayList<Appointment> getAllAppointmentsOnDate(LocalDate date) {
        ArrayList<Appointment> appointments = new ArrayList<>();
        
        try {
            
            Statement statement = (Statement) conn.createStatement();
//            String query = "SELECT * FROM appointment WHERE userId = "+C195Project.getLoggedInUser().getUserId()+" AND CONVERT(start, DATE) = CONVERT('"+date.toString()+"', DATE);";
            String query = "SELECT * FROM appointment WHERE CONVERT(start, DATE) = CONVERT('"+date.toString()+"', DATE);";
            
            ResultSet result = statement.executeQuery(query);
            
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
            
            while (result.next()) {
                Date start = format.parse(result.getString("start"));
                Date end = format.parse(result.getString("end"));
//                LocalDate localDate = LocalDate.parse(result.getString("start").split(" ")[0]);
//                String startTime = result.getString("start").split(" ")[1];
//                String endTime = result.getString("end").split(" ")[1];
                
//                Appointment appointment = new Appointment(result.getInt("appointmentId"), result.getInt("customerId"), result.getInt("userId"), result.getString("title"), result.getString("description"), result.getString("location"), result.getString("contact"), result.getString("type"), result.getString("url"), localDate, startTime, endTime);
                Appointment appointment = new Appointment(result.getInt("appointmentId"), result.getInt("customerId"), result.getInt("userId"), result.getString("title"), result.getString("description"), result.getString("location"), result.getString("contact"), result.getString("type"), result.getString("url"), start, end);
                appointments.add(appointment);
            }
        
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        return appointments;
    }
    
    /// Queries logged in user's appointment on DatePicker's selected date
    public static ArrayList<Appointment> getUsersAppointmentsOnDate(LocalDate date, int userId) {
        ArrayList<Appointment> appointments = new ArrayList<>();
        
        try {
            
            Statement statement = (Statement) conn.createStatement();
            String query = "SELECT * FROM appointment WHERE userId = "+userId+" AND CONVERT(start, DATE) = CONVERT('"+date.toString()+"', DATE);";
            
            ResultSet result = statement.executeQuery(query);
            
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
            
            while (result.next()) {
                Date start = format.parse(result.getString("start"));
                Date end = format.parse(result.getString("end"));
                Appointment appointment = new Appointment(result.getInt("appointmentId"), result.getInt("customerId"), result.getInt("userId"), result.getString("title"), result.getString("description"), result.getString("location"), result.getString("contact"), result.getString("type"), result.getString("url"), start, end);
                appointments.add(appointment);
            }
        
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        return appointments;
    }
    
    public static ArrayList<String> getAppointmentTypes() {
        ArrayList<String> types = new ArrayList<>();
        
        try {
            Statement statement = (Statement) conn.createStatement();
            
//            String query = "SELECT type, COUNT(type) occurrances FROM appointment GROUP BY type;";
            String inpersonQuery = "SELECT MONTHNAME(start) AS 'Month', count(type) AS 'In Person' FROM appointment WHERE type = 'In Person' GROUP BY MONTHNAME(start) ORDER BY MONTH(start);";
            String onlineQuery = "SELECT MONTHNAME(start) AS 'Month', count(type) AS 'Online' FROM appointment WHERE type = 'Online' GROUP BY MONTHNAME(start) ORDER BY MONTH(start);";
            String phoneQuery = "SELECT MONTHNAME(start) AS 'Month', count(type) AS 'Phone' FROM appointment WHERE type = 'Phone' GROUP BY MONTHNAME(start) ORDER BY MONTH(start);";
            
            ResultSet inpersonResult = statement.executeQuery(inpersonQuery);
            types.add("Month | In Person");
            while (inpersonResult.next()) {
                types.add(inpersonResult.getString("Month") + " | " + inpersonResult.getString("In Person"));
            }
            ResultSet onlineResult = statement.executeQuery(onlineQuery);
            types.add("");
            types.add("Month | Online");
            while (onlineResult.next()) {
                types.add(onlineResult.getString("Month") + " | " + onlineResult.getString("Online"));
            }
            ResultSet phoneResult = statement.executeQuery(phoneQuery);
            types.add("");
            types.add("Month | Phone");
            while (phoneResult.next()) {
                types.add(phoneResult.getString("Month") + " | " + phoneResult.getString("Phone"));
            }
        
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        return types;
    }
    
    public static ArrayList<String> getConsultantSchedule() {
        ArrayList<String> schedules = new ArrayList<>();
        
        try {
            Statement statement = (Statement) conn.createStatement();
            
            String query = "SELECT userName, title, start, end FROM user, appointment WHERE appointment.userId = user.userId AND NOW() < start ORDER BY user.userId, start;";
            
            ResultSet result = statement.executeQuery(query);
            
            while (result.next()) {
                schedules.add(result.getString("userName") + " | " + result.getString("title") + " | " + result.getString("start") + " | " + result.getString("end"));
            }
        
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        return schedules;
    }
    
    public static ArrayList<String> getCustomersCountInCity() {
        ArrayList<String> cities = new ArrayList<>();
        
        try {
            Statement statement = (Statement) conn.createStatement();
            
//            String query = "SELECT city FROM address, city WHERE city.cityId = address.cityId;";
            String query = "SELECT city, COUNT(city) occurrances FROM address, city WHERE city.cityId = address.cityId GROUP BY city;";
            
            ResultSet result = statement.executeQuery(query);
            
            while (result.next()) {
                cities.add(result.getString("city") + " | " + result.getString("occurrances"));
            }
        
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        return cities;
    }
    
    public static ArrayList<Appointment> getAllAppointments7Days() {
        ArrayList<Appointment> appointments = new ArrayList<>();
        
        try {
            
            Statement statement = (Statement) conn.createStatement();
            String query = "SELECT * FROM appointment WHERE start BETWEEN NOW() AND NOW() + INTERVAL 1 WEEK + INTERVAL 1 DAY;";
            
            ResultSet result = statement.executeQuery(query);
            
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
            
            while (result.next()) {
                Date start = format.parse(result.getString("start"));
                Date end = format.parse(result.getString("end"));
                Appointment appointment = new Appointment(result.getInt("appointmentId"), result.getInt("customerId"), result.getInt("userId"), result.getString("title"), result.getString("description"), result.getString("location"), result.getString("contact"), result.getString("type"), result.getString("url"), start, end);
                appointments.add(appointment);
            }
        
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        return appointments;
    }
    
    public static ArrayList<Appointment> getAllAppointments30Days() {
        ArrayList<Appointment> appointments = new ArrayList<>();
        
        try {
            
            Statement statement = (Statement) conn.createStatement();
            String query = "SELECT * FROM appointment WHERE start BETWEEN NOW() AND NOW() + INTERVAL 1 MONTH + INTERVAL 1 DAY;";
            
            ResultSet result = statement.executeQuery(query);
            
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
            
            while (result.next()) {
                Date start = format.parse(result.getString("start"));
                Date end = format.parse(result.getString("end"));
                Appointment appointment = new Appointment(result.getInt("appointmentId"), result.getInt("customerId"), result.getInt("userId"), result.getString("title"), result.getString("description"), result.getString("location"), result.getString("contact"), result.getString("type"), result.getString("url"), start, end);
                appointments.add(appointment);
            }
        
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        return appointments;
    }
    
    public static boolean getUsersAppointments15Minutes(int userId) {
        try {
            Statement statement = (Statement) conn.createStatement();
            
            // use clock
            
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date now = Date.from(Instant.now());
            String nowString = format.format(now);
            System.out.println("nowString: " + nowString);
            
//            String query = "SELECT * FROM appointment WHERE userId = "+userId+" AND start BETWEEN NOW() AND NOW() + INTERVAL 15 MINUTE;";
            String query = "SELECT * FROM appointment WHERE userId = "+userId+" AND start BETWEEN CONVERT('"+nowString+"', DATETIME) AND CONVERT('"+nowString+"', DATETIME) + INTERVAL 15 MINUTE;";
            
            ResultSet result = statement.executeQuery(query);
            
            if (result.next()) {
                format.setTimeZone(TimeZone.getTimeZone("GMT"));
                Date start = format.parse(result.getString("start"));
                System.out.println("next app: " + format.parse(result.getString("start")));
                return true;
            }
            
            return false;
//            return result.next();
        
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        return false;
    }
    
    // TODO: handle active field elsewhere
    public static ArrayList<Customer> getAllActiveCustomers() {
            
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
    
    public static ArrayList<User> getAllActiveUsers() {
            
        ArrayList<User> users = new ArrayList<>();
        
        try {
            
            Statement statement = (Statement) conn.createStatement();
            String query = "SELECT * FROM user WHERE active = 1;";
            
            ResultSet result = statement.executeQuery(query);
            
            while (result.next()) {
//                customers.add(result.getString("customerName"));
                User user = new User(result.getInt("userId"), result.getString("userName"));
                users.add(user);
            }
        
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        return users;
    }
    
    public static ArrayList<Customer> getAllCustomers() {
            
        ArrayList<Customer> customers = new ArrayList<>();
        
        try {
            
            Statement statement = (Statement) conn.createStatement();
            String query = "SELECT * FROM customer;";
            
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
    
    public static ArrayList<City> getAllCities() {
            
        ArrayList<City> cities = new ArrayList<>();
        
        try {
            
            Statement statement = (Statement) conn.createStatement();
            String query = "SELECT * FROM city;";
            
            ResultSet result = statement.executeQuery(query);
            
            while (result.next()) {
                City city = new City(result.getInt("cityId"), result.getString("city"), result.getInt("countryId"));
                cities.add(city);
            }
        
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        return cities;
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
                address = new Address(addressId, result.getString("address"), result.getString("address2"), result.getInt("cityId"), result.getString("postalCode"), result.getString("phone"));
            }
        
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        return address;
    }
    
    public static void addAppointment(Appointment appointment) {
        try {
            
            Statement statement = (Statement) conn.createStatement();
            
            // Time format = ##:##:##
            
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            
            Date start = appointment.getStart();
            Date end = appointment.getEnd();
            
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
            
            String startString = format.format(start);
            String endString = format.format(end);
            
            String query = "INSERT INTO appointment(customerId, userId, title, description, location, contact, type, url, start, end, createDate, createdBy, lastUpdateBy) VALUES ("+appointment.getCustomerId()+", "+appointment.getUserId()+", '"+appointment.getTitle()+"', '"+appointment.getDescription()+"', '"+appointment.getLocation()+"', '"+appointment.getContact()+"', '"+appointment.getType()+"', '"+appointment.getUrl()+"', CONVERT('"+startString+"', DATETIME), CONVERT('"+endString+"', DATETIME), NOW(), '"+C195Project.getLoggedInUser().getUserName()+"', '"+C195Project.getLoggedInUser().getUserName()+"');";
            
            int result = statement.executeUpdate(query);
        
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    public static void addAddress(Address address) {
        try {
            Statement statement = (Statement) conn.createStatement();
            
            String query = "INSERT INTO address(address, address2, cityID, postalCode, phone, createDate, createdBy, lastUpdateBy) VALUES ('"+address.getAddress()+"', '"+address.getAddress2()+"', "+address.getCityId()+", '"+address.getPostalCode()+"', '"+address.getPhone()+"', NOW(), '"+C195Project.getLoggedInUser().getUserName()+"', '"+C195Project.getLoggedInUser().getUserName()+"');";
            
            int result = statement.executeUpdate(query);
        
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    public static void addCustomer(Customer customer) {
        try {
            Statement statement = (Statement) conn.createStatement();
            
            String query = "INSERT INTO customer(customerName, addressId, active, createDate, createdBy, lastUpdateBy) VALUES ('"+customer.getCustomerName()+"', "+customer.getAddressId()+", "+customer.getActive()+", NOW(),'"+C195Project.getLoggedInUser().getUserName()+"', '"+C195Project.getLoggedInUser().getUserName()+"');";
            
            int result = statement.executeUpdate(query);
        
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    public static void deleteCustomerById(int customerId) {
        try {
            Statement statement = (Statement) conn.createStatement();
            
            String query = "DELETE FROM customer WHERE customerId = "+customerId+";";
            // TODO: Also delete related address & appointments
            
            int result = statement.executeUpdate(query);
        
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    public static void deleteAddressById(int addressId) {
        try {
            Statement statement = (Statement) conn.createStatement();
            
            String query = "DELETE FROM address WHERE addressId = "+addressId+";";
            // TODO: Also delete related address & appointments
            
            int result = statement.executeUpdate(query);
        
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    public static void deleteAppointmentsByCustomerId(int customerId) {
        try {
            Statement statement = (Statement) conn.createStatement();
            
            String query = "DELETE FROM appointment WHERE customerId = "+customerId+";";
            
            int result = statement.executeUpdate(query);
        
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    public static void deleteAppointmentById(int appointmentId) {
        try {
            Statement statement = (Statement) conn.createStatement();
            
            String query = "DELETE FROM appointment WHERE appointmentId = "+appointmentId+";";
            
            int result = statement.executeUpdate(query);
        
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    public static void updateAppointment(Appointment appointment) {
        try {
            Statement statement = (Statement) conn.createStatement();
            
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            
            Date start = appointment.getStart();
            Date end = appointment.getEnd();
            
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
            
            String startString = format.format(start);
            String endString = format.format(end);
            
            String query = "UPDATE appointment SET customerId = "+appointment.getCustomerId()+", userId = "+appointment.getUserId()+", title = '"+appointment.getTitle()+"', description = '"+appointment.getDescription()+"', location = '"+appointment.getLocation()+"', contact = '"+appointment.getContact()+"', type = '"+appointment.getType()+"', url = '"+appointment.getUrl()+"', start = CONVERT('"+startString+"', DATETIME), end = CONVERT('"+endString+"', DATETIME), lastUpdate = NOW(), lastUpdateBy = '"+C195Project.getLoggedInUser().getUserName()+"' WHERE appointmentId = "+appointment.getAppointmentId()+";";
            
            int result = statement.executeUpdate(query);
        
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    public static void updateCustomer(Customer customer) {
        try {
            Statement statement = (Statement) conn.createStatement();
            
            
            // UPDATE customer SET customerName = 'Corn Co.' WHERE customerId = 3;
            String query = "UPDATE customer SET customerName = '"+customer.getCustomerName()+"', active = "+customer.getActive()+", lastUpdate = NOW(), lastUpdateBy = '"+C195Project.getLoggedInUser().getUserName()+"' WHERE customerId = "+customer.getCustomerId()+";";
            
            int result = statement.executeUpdate(query);
        
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    public static void updateAddress(Address address) {
        try {
            Statement statement = (Statement) conn.createStatement();
            
            String query = "UPDATE address SET address = '"+address.getAddress()+"', address2 = '"+address.getAddress2()+"', cityID = "+address.getCityId()+", postalCode = '"+address.getPostalCode()+"', phone = '"+address.getPhone()+"', lastUpdate = NOW(), lastUpdateBy = '"+C195Project.getLoggedInUser().getUserName()+"' WHERE addressId = "+address.getAddressId()+";";
            
            int result = statement.executeUpdate(query);
        
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
