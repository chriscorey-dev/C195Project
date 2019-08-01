package c195project;

import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
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
    
    public SceneManager(Stage stage) {
        try {
            
            this.stage = stage;
            this.allScenes = new ArrayList<>();
            
            this.allScenes.add(new Scene(FXMLLoader.load(getClass().getResource(this.LOGIN_FXML))));
            this.allScenes.add(new Scene(FXMLLoader.load(getClass().getResource(this.MAIN_FXML))));
            this.allScenes.add(new Scene(FXMLLoader.load(getClass().getResource(this.APPOINTMENT_FXML))));
            this.allScenes.add(new Scene(FXMLLoader.load(getClass().getResource(this.CUSTOMER_FXML))));
            
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    public static void loadScene(String name) {
        
        Scene scene = null;
        
        switch(name) {
            case LOGIN_FXML:
                scene = allScenes.get(0);
              break;
            case MAIN_FXML:
                scene = allScenes.get(1);
              break;
            case APPOINTMENT_FXML:
                scene = allScenes.get(2);
              break;
            case CUSTOMER_FXML:
                scene = allScenes.get(3);
              break;
            default:
                System.out.println("Invalid string");
                return;
          }
        
        stage.setScene(scene);
        stage.show();
    }
    
    public static ArrayList<Scene> getAllScenes() {
        return allScenes;
    }
}
