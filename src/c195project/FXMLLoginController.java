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
                errorLabel.setText("The username and password did not match.");
            
//    //            DateTime dateTime = new DateTime();
//                Calendar myCalendar = Calendar.getInstance();
//                System.out.println("Time: " + myCalendar.getTime());
//                System.out.println("Time: " + myCalendar.get(Calendar.HOUR_OF_DAY));
//                myCalendar.set(Calendar.HOUR_OF_DAY, 10);
//                System.out.println("Time: " + myCalendar.get(Calendar.HOUR_OF_DAY));
                
                        
//                GregorianCalendar gregsCalendar = new GregorianCalendar();
//                System.out.println("Time: " + gregsCalendar.getTime());

//                TimeZone timeZone = TimeZone.getDefault();
//                Calendar calendar = Calendar.getInstance();
////                System.out.println("Timezone: " + timeZone);
////                System.out.println("GMT: " + calendar.);
//                System.out.println(Calendar.getInstance().getTimeZone().getDisplayName() + ": " + Calendar.getInstance().getTime());
//                timeZone.setDefault(TimeZone.getTimeZone("GMT"));
//                System.out.println(Calendar.getInstance().getTimeZone().getDisplayName() + ": " + Calendar.getInstance().getTime());


                // Adjust timezone
//                Scanner keyboard = new Scanner(System.in);
//                System.out.print("Enter desired time: ");
//                int hr = keyboard.nextInt();




                
//                TimeZone localTimeZone = TimeZone.getDefault();
//                TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
//                Calendar calendar = Calendar.getInstance();
//                
//                calendar.set(Calendar.HOUR_OF_DAY, 18);
//                calendar.set(Calendar.MINUTE, 0);
//                calendar.set(Calendar.SECOND, 0);
//                
//                System.out.println("Database time (GMT): " + calendar.getTime());
//                
//                TimeZone.setDefault(localTimeZone);
//                System.out.println("Local time: " + calendar.getTime());
                
                


                // DB Entry:
                // 2019-08-04 13:00:00
                
//                LocalDate localDate = LocalDate.parse("2019-08-04");
//                Calendar calendar = Calendar.getInstance();
//                DateTimeFormatter formatter = new DateTimeFormatter("YYYY-MM-DD hh:mm:ss");
//                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                LocalDateTime localDateTime = LocalDateTime.parse("2019-08-04 13:00:00", formatter);
//                localDateTime.format(formatter);
//                localDateTime = LocalDateTime.parse("2019-08-04 13:00:00", formatter);
////                calendar.set(localDateTime);

                
//                // This is how to parse dates from the database
//                TimeZone timeZone = TimeZone.getDefault();
//                TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
//                
//                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Date date = format.parse("2019-08-04 13:00:00");
//                
//                TimeZone.setDefault(timeZone);
//                System.out.println("Date: " + date.getTime());


//                // ..And back again
//                TimeZone localTimeZone = TimeZone.getDefault();
//                
//                Date date = C195Project.convertStringToDate("2019-08-04 13:00:00");
//                
//                System.out.println("Local Date: " + date);
//                TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
//                System.out.println("GMT Date: " + date);
//                TimeZone.setDefault(localTimeZone);
                
                
////                TimeZone.setDefault(TimeZone.getTimeZone("GMT")); // Activitating this line will return the stringDate in GMT timezone
//                String dateString = C195Project.convertDateToString(date);
////                TimeZone.setDefault(localTimeZone); // And this resets back to local
//                System.out.println("Time: " + dateString);
                
                
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
