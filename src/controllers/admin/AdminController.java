/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.admin;

import app.App;
import helpers.Dialog;
import helpers.Session;
import java.io.File;
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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.User;

/**
 * FXML Controller class
 *
 * @author ACER
 */
public class AdminController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private AnchorPane mainPane;
    
 
    @FXML
    private Label nama;

    @FXML
    private Label email;

    @FXML
    private Label telepon;

    @FXML
    private Label alamat;

    @FXML
    private ImageView foto;

    @FXML
    private Label role;
        @FXML
    private Pane profilPane;

    
        
        
    @FXML
    private TextField namaField;

    @FXML
    private TextField teleponField;

    @FXML
    private TextArea alamatField;


    @FXML
    void update(ActionEvent event) throws Exception {
        String nama = namaField.getText();
        String telepon = teleponField.getText();
        String alamat = alamatField.getText();
        
        if (Session.CekSessionData() == 1) {
            if (User.updateUser(User.getId(), nama, telepon, alamat) == 1) {
                Session.hapusSession();
                User.getRole(User.getEmail(), User.getPassword());
                Dialog.alertSuccess("Profil Berhasil di Update! Refresh");
                
                mainPane.getScene().getWindow().hide();
                Stage stage = new Stage();
                App app = new App();
                
                app.start(stage);
                
            }
            
           
             
        }
    }    
    
    @FXML
    void showEdit(ActionEvent event) {
        
        changeFxml("profil/Edit");
 
     
    }
    
    
    
          @FXML
    void showProfil(ActionEvent event) {
        changeFxml("profil/Profil");
    }

    
    
    @Override
    
    public void initialize(URL url, ResourceBundle rb) {
        
          
        try {
            if (Session.CekSessionData() == 1) {
                   System.out.println(User.getNama());
                //edit
                namaField.setText(User.getNama());
                teleponField.setText(User.getTelepon());
                alamatField.setText(User.getAlamat());
                
            }
        } catch (Exception e) {
        }
        
        
        try {
       
        
            if (Session.CekSessionData() == 1) {
                     // profile
                nama.setText(User.getNama());
                email.setText(User.getEmail());
                telepon.setText(User.getTelepon());
                alamat.setText(User.getAlamat());
                
             
                
           

                if (User.getRole().equals("1")) {
                    role.setText("ADMIN");
                }

                Image image = new Image(getClass().getResourceAsStream("/views/assets/img/foto/kinan.png"));
                System.out.println(image);
                foto.setImage(image);


            }
            
        } catch (Exception e) {
        }
       
        
        
        
      
     
        
    }    
    
    
       
        private void changeFxml(String file){
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("/views/admin/"+file+".fxml"));
            mainPane.getChildren().removeAll();
            mainPane.getChildren().setAll(fxml);
            
           
            FadeTransition ft = new FadeTransition();
            ft.setDuration(Duration.millis(500));
            ft.setNode(mainPane);
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.play();
            
        } catch (IOException ex) {
            Logger.getLogger(controllers.MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
