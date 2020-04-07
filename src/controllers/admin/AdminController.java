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
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
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
    private ImageView foto;

    @FXML
    private Label role;

    @FXML
    private TextField nama;

    @FXML
    private TextField email;

    @FXML
    private TextField telepon;

    @FXML
    private TextArea alamat;
   
    @FXML
    private TextField editNama;

    @FXML
    private TextField editTelepon;

    @FXML
    private TextArea editAlamat;

    @FXML
    private Button editFoto;
    
    
    @FXML
    void ubahProfile(ActionEvent event) throws Exception {
        String nama = editNama.getText();
        String telepon = editTelepon.getText();
        String alamat = editAlamat.getText();
        
        if (Session.CekSessionData() == 1) {
            if (User.updateUser(User.getId(), nama, telepon, alamat) == 1) {
                Session.hapusSession();
                User.getRole(User.getEmail(), User.getPassword());
                Dialog.alertSuccess("Profil Berhasil di Update!");
                //tutup window & kembali ke login
              Stage stage = (Stage) foto.getScene().getWindow();
              stage.close();      
              App app = new App();
              app.start(stage);

                
                
                
            }
            
           
             
        }
    }
    
    
    
    @Override
    
    public void initialize(URL url, ResourceBundle rb) {
        
          
        nama.setEditable(false);
        email.setEditable(false);
        telepon.setEditable(false);
        alamat.setEditable(false);
        
        
        if (Session.CekSessionData() == 1) {
            //tampilkan profile
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
            
            //edit
            editNama.setText(User.getNama());
            editTelepon.setText(User.getTelepon());
            editAlamat.setText(User.getAlamat());
            

            
        }
      
     
        
    }    
    
}
