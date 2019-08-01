package c195project;


import static c195project.DBConnection.conn;
import com.mysql.jdbc.Statement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author chris
 */
public class Employee {
    private int id;
    private String name;
    private String department;
    private Date hireDate;
    public Employee(int id, String name, String department, Date hireDate) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.hireDate = hireDate;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setName(String name) {
        this.name = name;
            
        try {
            
//            Statement statement = (Statement) conn.createStatement();
            Statement statement = (Statement) conn.createStatement();
            String query = "UPDATE employee_tbl SET EmployeeName = '" + name + "' WHERE EmployeeID = " + this.id;
            
            int result = statement.executeUpdate(query);
            
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }
    
}
