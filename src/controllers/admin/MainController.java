/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.admin;

import helpers.Session;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import app.App;
import javafx.scene.control.MenuButton;
import javafx.scene.image.ImageView;
import models.User;

/**
 * FXML Controller class
 *
 * @author ACER
 */
public class MainController implements Initializable {
     @FXML
    private AnchorPane mainPane;
     
    @FXML
    private Label title;
    
    
    
    @FXML
    private ImageView infoFoto;

    @FXML
    private Label infoNama;

    @FXML
    private Label infoRole;
    
    @FXML
    private MenuButton labelNama;

     
    @FXML
    void showProfile(ActionEvent event) {
        title.setText("Profil");
        changeFxml("Profil");
    }
    
    @FXML
    void showDashboard(ActionEvent event) {
        title.setText("Dashboard");
         changeFxml("Dashboard");
    }
    
    @FXML
    void logout(ActionEvent event) throws Exception {
        //hapus session
        Session.hapusSession();
        
        //tutup window & kembali ke login
        Stage stage = (Stage) mainPane.getScene().getWindow();
        stage.close();      
        App app = new App();
        app.start(stage);
    
    
    
    }



    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (Session.CekSessionData() == 1){
            infoNama.setText(User.getNama());
            labelNama.setText(User.getNama());
            if (User.getRole().equals("1")) {
                infoRole.setText("Admin");
            }
        }
        
        
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("/views/admin/Dashboard.fxml"));
            mainPane.getChildren().removeAll();
            mainPane.getChildren().setAll(fxml);
            
            
        } catch (IOException ex) {
            Logger.getLogger(controllers.MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


        private void changeFxml(String file){
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("/views/admin/"+file+".fxml"));
            mainPane.getChildren().removeAll();
            mainPane.getChildren().setAll(fxml);
            
            //efek transisi
//            FadeTransition ft = new FadeTransition();
//            ft.setDuration(Duration.millis(500));
//            ft.setNode(mainPane);
//            ft.setFromValue(0);
//            ft.setToValue(1);
//            ft.play();
            
        } catch (IOException ex) {
            Logger.getLogger(controllers.MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
