/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195project;

import com.mysql.jdbc.Connection;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author chris
 */
public class C195Project extends Application {
    
    public static Connection conn;
    private static User loggedInUser;
    
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
            
            launch(args); // Thread pauses while this is executed
            DBConnection.closeConnection(); // Happens after the program is closed
        
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
