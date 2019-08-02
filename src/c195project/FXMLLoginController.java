/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195project;

import static c195project.C195Project.conn;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author chris
 */
public class FXMLLoginController implements Initializable {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;
    
    @FXML
    private void loginAction(ActionEvent event) {
        
        ResultSet result = SQLQueries.checkLoginCredentials(usernameField.getText(), passwordField.getText());
            
        try {
            // Valid login
            if (result.next()) {
                errorLabel.setTextFill(Color.web("#00ff00"));
                errorLabel.setText("Logged in");
                
                User loggedInUser = new User(result.getInt("userId"), result.getString("username"));
                C195Project.setLoggedInUser(loggedInUser);
                
                SceneManager.loadScene(SceneManager.MAIN_FXML);
            } else {
                // TODO: Multiple languages
                errorLabel.setTextFill(Color.web("#ff0000"));
                errorLabel.setText("The username and password did not match.");
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
