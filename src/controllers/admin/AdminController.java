/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.admin;

import app.App;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import helpers.Dialog;
import helpers.MyHelper;
import helpers.Session;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
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
    private JFXTextField nama;
    
    @FXML
    private JFXTextField email;
    
    @FXML
    private JFXTextField telepon;

    @FXML
    private JFXTextArea alamat;
    

    @FXML
    private Label titleProfil;

    @FXML
    private JFXButton editBtn;

    @FXML
    private JFXButton kembaliBtn;

    @FXML
    private JFXButton simpanBtn;
    
    
    
    @FXML
    private JFXPasswordField passwordLama;

    @FXML
    private Label labelPasswordLama;

    @FXML
    private Label labelPasswordBaru;

    @FXML
    private JFXPasswordField passwordBaru;

    @FXML
    private JFXPasswordField konfPasswordBaru;

    @FXML
    void gantiPassword(ActionEvent event) throws Exception {
        labelPasswordBaru.setVisible(false);
        labelPasswordLama.setVisible(false);
        
        String passwordLama = this.passwordLama.getText().toString();
        String passwordBaru = this.passwordBaru.getText().toString();
        String konfirmasiPassword = this.konfPasswordBaru.getText().toString();
        
        if (passwordLama.equals("") || passwordBaru.equals("") || konfirmasiPassword.equals("")) {
            Dialog.alertError("Semua Field Wajib Di isi!");
        }else if (Session.CekSessionData() == 1) {
        
            if (!MyHelper.getMd5(passwordLama).equals(User.getPassword())) {
                labelPasswordLama.setText("Password lama anda salah!");
                labelPasswordLama.setVisible(true);
            }else{
                if (!passwordBaru.equals(konfirmasiPassword) ){
                  
                    labelPasswordBaru.setText("Password baru anda tidak sama!");
                    labelPasswordBaru.setVisible(true);
                }else{
                    if (User.updatePassword(User.getId(), passwordBaru) == 1) {
                        Dialog.alertError("Password Berhasil DiUbah! SIlahkan Login Ulang!");
                        //hapus session
                        Session.hapusSession(); 
                        //tutup window & kembali ke login
                        Stage stage = (Stage) mainPane.getScene().getWindow();
                        stage.close();      
                        App app = new App();
                        app.start(stage);
                    }
                }
            }
        }
             
        
    }
    

    @FXML
    void showProfile(ActionEvent event) {     
        transition();
        disableEditField();
        email.setDisable(false);
        
        simpanBtn.setVisible(false);
        kembaliBtn.setVisible(false);
        editBtn.setVisible(true);

        
        titleProfil.setText("Info Profil");
    }

    @FXML
    void update(ActionEvent event) throws Exception {
        String nama = this.nama.getText();
        String telepon = this.telepon.getText();
        String alamat = this.alamat.getText();
        
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
        transition();
        enableEditField();
        
        kembaliBtn.setVisible(true);
        simpanBtn.setVisible(true);
        editBtn.setVisible(false);
        
        titleProfil.setText("Edit Profil");
        email.setDisable(true);
        
    }


    
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
        try {
            labelPasswordBaru.setVisible(false);
            labelPasswordLama.setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        try {
            kembaliBtn.setVisible(false);
            simpanBtn.setVisible(false);
            disableEditField();

            if (Session.CekSessionData() == 1) {
                nama.setText(User.getNama());
                email.setText(User.getEmail());
                telepon.setText(User.getTelepon());
                alamat.setText(User.getAlamat());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
       
    }   
    
    
    private void transition(){
        FadeTransition ft = new FadeTransition();
        ft.setDuration(Duration.millis(500));
        ft.setNode(mainPane);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }
    
    private void disableEditField(){
        nama.setEditable(false);
        email.setEditable(false);
        telepon.setEditable(false);
        alamat.setEditable(false);
    }
    
    private void enableEditField(){
        nama.setEditable(true);
        email.setEditable(true);
        telepon.setEditable(true);
        alamat.setEditable(true);
    }
    
   
}
