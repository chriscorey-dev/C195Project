/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195project;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
    private void loginAction(ActionEvent event) throws IOException {
        
        ResultSet result = SQLQueries.checkLoginCredentials(usernameField.getText(), passwordField.getText());
            
        try {
            // Valid login
            if (result.next()) {
                errorLabel.setTextFill(Color.web("#00ff00"));
                errorLabel.setText("Logged in");
                
                User loggedInUser = new User(result.getInt("userId"), result.getString("username"));
                C195Project.setLoggedInUser(loggedInUser);
                
                // Log file
                String file = "user_log.txt";
                FileWriter fileWriter = new FileWriter(file, true);
                PrintWriter outputFile = new PrintWriter(fileWriter);
                outputFile.println(loggedInUser.getUserName() + " logged in at " + Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTimeInMillis());
                outputFile.close();
                
                // Checks for impending appointments
                if (SQLQueries.getUsersAppointments15Minutes()) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Alert");
                    alert.setHeaderText(null);
                    alert.setContentText("You have a meeting within 15 minutes!");

                    alert.show();
                }
                
                SceneManager.loadScene(SceneManager.MAIN_FXML);
            } else {
                // TODO: Multiple languages
                errorLabel.setTextFill(Color.web("#ff0000"));
                errorLabel.setText(getLocaleErrorMessage());
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    private String getLocaleErrorMessage() {
        Locale locale = Locale.getDefault();
        String output = "The username and password did not match.";
        
        switch(locale.getLanguage()) {
            case "en":
                output = "The username and password did not match.";
                break;
            case "es":
                output = "El nombre de usuario y la contraseña no coinciden.";
                break;
            default:
                break;
        }
        
        return output;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
