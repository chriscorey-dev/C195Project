/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195project;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author chris
 */
public class C195Project extends Application {
    
    final static ArrayList<Employee> allEmployees = new ArrayList<>();
    public static Connection conn;
    private static User loggedInUser = null;
    
    public static void setLoggedInUser(User user) {
        loggedInUser = user;
    }
    
    public static User getLoggedInUser() {
        return loggedInUser;
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        SceneManager sceneManager = new SceneManager(stage);
        SceneManager.loadScene(SceneManager.LOGIN_FXML);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            
            DBConnection.makeConnection();
            conn = DBConnection.conn;
            
//            Statement statement = (Statement) conn.createStatement();
//            String query = "SELECT * FROM employee_tbl;";
//            
//            ResultSet result = statement.executeQuery(query);
////            ArrayList<Employee> allEmployees = new ArrayList<>();
//            
//            while (result.next()) {
//                Employee employee = new Employee(result.getInt("EmployeeID"),
//                        result.getString("EmployeeName"),
//                        result.getString("Department"),
//                        result.getDate("HireDate"));
//                allEmployees.add(employee);
//            }
//            
//            System.out.println("Number of employees: " + allEmployees.size());
            
            launch(args); // Thread pauses while this is executed
            DBConnection.closeConnection(); // Happens after the program is closed
        
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    public static ArrayList<Employee> getAllEmployees() {
        return allEmployees;
    }
    
    public static void crateNewEmployee(int id, String name, String department, Date hireDate) {
        Employee employee = new Employee(id, name, department, hireDate);
        allEmployees.add(employee);
    }
}
