package app;

import helpers.Session;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.UserModel;

public class App extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {

        if (Session.cekSession() == 1) {         
            if (UserModel.getRole() == 1) {
                loadFxml("admin", 1200, 700);              
            }else if(UserModel.getRole() == 2){
                loadFxml("user", 1000, 670);
            }
            
        }else{
            Parent root = FXMLLoader.load(getClass().getResource("/views/auth/Login.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setWidth(960);
            stage.setHeight(670);
            stage.setTitle("Login");
            stage.setResizable(false);
            stage.show();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    private void loadFxml(String role, int width, int height) throws IOException{
        
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/views/"+role+"/Main.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setWidth(width);
        stage.setHeight(height);
        stage.setTitle("DONASI");
        stage.setResizable(false);
        stage.show();
        
    }
}
