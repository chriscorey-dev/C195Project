package c195project;

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
    
    public SceneManager(Stage stage) {
        try {
            
        this.stage = stage;
        this.allScenes = new ArrayList<>();
        
            Parent login = FXMLLoader.load(getClass().getResource("FXMLLogin.fxml"));
            Parent main = FXMLLoader.load(getClass().getResource("FXMLMain.fxml"));
            Scene loginScene = new Scene(login);
            Scene mainScene = new Scene(main);
            this.allScenes.add(loginScene);
            this.allScenes.add(mainScene);
            
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    public static void loadScene(Scene scene) {
        stage.setScene(scene);
        stage.show();
    }
    
    public static ArrayList<Scene> getAllScenes() {
        return allScenes;
    }
}
