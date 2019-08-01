/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195project;

import static c195project.C195Project.conn;
import static c195project.C195Project.getAllEmployees;
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
import javafx.scene.control.TextField;
import org.w3c.dom.Text;

/**
 * FXML Controller class
 *
 * @author chris
 */
public class FXMLLoginController implements Initializable {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Label errorLabel;
    
    @FXML
    private void loginAction(ActionEvent event) {
//        System.out.println("You clicked me!");
//        getAllEmployees().get(0).setName("Bill Williamson");

            
        try {
            
            Statement statement = (Statement) conn.createStatement();
            String query = "SELECT * FROM user WHERE username = '" + usernameField.getText() + "' AND password = '" + passwordField.getText() + "' AND active = 1;";
            
            ResultSet result = statement.executeQuery(query);
            
            if (result.next()) {
                errorLabel.setText("");
            } else {
                // TODO: Multiple languages
                errorLabel.setText("Invalid Credentials");
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
