package c195project;

import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author chris
 */
public class SceneManager {
    private static ArrayList<Scene> allScenes;
    private static Stage stage;
    
    public static final String LOGIN_FXML = "FXMLLogin.fxml";
    public static final String MAIN_FXML = "FXMLMain.fxml";
    public static final String APPOINTMENT_FXML = "FXMLAppointment.fxml";
    public static final String CUSTOMER_FXML = "FXMLCustomer.fxml";
    
    public static Stage getStage() {
        return stage;
    }
    
    public SceneManager(Stage stage) {
        try {
            
            this.stage = stage;
//            this.allScenes = new ArrayList<>();
//            
//            this.allScenes.add(new Scene(FXMLLoader.load(getClass().getResource(this.LOGIN_FXML))));
//            this.allScenes.add(new Scene(FXMLLoader.load(getClass().getResource(this.MAIN_FXML))));
//            this.allScenes.add(new Scene(FXMLLoader.load(getClass().getResource(this.APPOINTMENT_FXML))));
//            this.allScenes.add(new Scene(FXMLLoader.load(getClass().getResource(this.CUSTOMER_FXML))));
            
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    public static void loadScene(String name) throws IOException {
//        // TODO: stage.close() might be bad. idk, investigate. Forces initialize() method each time a window opens. Another way to force code when the scene is opened?
//        stage.close();

        Scene scene = new Scene(FXMLLoader.load(SceneManager.class.getResource(name)));
        stage.setScene(scene);
        stage.show();
        
        switch(name) {
            case LOGIN_FXML:
//                scene = allScenes.get(0);
                stage.setTitle("Login");
              break;
            case MAIN_FXML:
//                scene = allScenes.get(1);
                stage.setTitle("Main Menu");
              break;
            case APPOINTMENT_FXML:
//                scene = allScenes.get(2);
                stage.setTitle("Appointment");
              break;
            case CUSTOMER_FXML:
//                scene = allScenes.get(3);
                stage.setTitle("Customer");
              break;
            default:
                System.out.println("Invalid string");
          }
    }
    
    public static ArrayList<Scene> getAllScenes() {
        return allScenes;
    }
}
