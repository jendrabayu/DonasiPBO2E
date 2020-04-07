package app;

import controllers.AuthController;
import controllers.MainController;
import helpers.Session;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.User;

public class App extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        if (Session.CekSessionData() == 1) {
         
            if (User.getRole().equals("1")) {
                 Parent root = FXMLLoader.load(getClass().getResource("/views/admin/Main.fxml"));
                Scene scene = new Scene(root);
    //            stage.setResizable(false);
                stage.setScene(scene);
                stage.show();
            }else if(User.getRole().equals("0")){
                Parent root = FXMLLoader.load(getClass().getResource("/views/user/Main.fxml"));
                Scene scene = new Scene(root);
    //            stage.setResizable(false);
                stage.setScene(scene);
                stage.show();
            }
           
        }else{
            Parent root = FXMLLoader.load(getClass().getResource("/views/Main.fxml"));
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    

    
    
    
    
    
}
