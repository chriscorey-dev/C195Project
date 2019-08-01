/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195project;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
}
