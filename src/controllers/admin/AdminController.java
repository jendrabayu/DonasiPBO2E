package controllers.admin;

import app.App;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import helpers.Dialog;
import helpers.MyHelper;
import helpers.Session;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.UserModel;


public class AdminController implements Initializable {
    

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
    private JFXPasswordField newPassword;

    @FXML
    private JFXPasswordField reNewPassword;

    @FXML
    private JFXPasswordField oldPassword;
    

    
    @FXML
    void edit(ActionEvent event) {
        changeFxml("EditProfil");
    }
    
    @FXML
    void profile(ActionEvent event) {  
        changeFxml("Profil");
        
    }
    
    @FXML
    void update(ActionEvent event) throws Exception {
      
            if (UserModel.update(nama.getText(), telepon.getText(), alamat.getText())) {
                Session.sessionDestroy();
                UserModel.CreateSession();
                Dialog.alertSuccess("Profil Berhasil di Update! Refresh");

                mainPane.getScene().getWindow().hide();
                Stage stage = new Stage();
                App app = new App();
                app.start(stage);            
            }
        
    }

    @FXML
    void updatePassword(ActionEvent event) throws Exception {
        String passwordLama = this.oldPassword.getText().toString();
        String passwordBaru = this.newPassword.getText().toString();
        String konfirmasiPassword = this.reNewPassword.getText().toString();
        
        if (passwordLama.equals("") || passwordBaru.equals("") || konfirmasiPassword.equals("")) {
            Dialog.alertError("Semua Field Tidak Boleh Kosong!");
        }else if (Session.cekSession()== 1) {
        
            if (!MyHelper.getMd5(passwordLama).equals(UserModel.getPassword())) {
                Dialog.alertError("Password Lama Anda Tidak Valid!");
            }else{
                if (!passwordBaru.equals(konfirmasiPassword) ){
                    Dialog.alertError("Password Baru Anda Tidak Sama!");
                }else{
                    if (UserModel.updatePassword(passwordBaru)) {
                        Dialog.alertSuccess("Password Berhasil DiUbah! SIlahkan Login Ulang!");
                        //hapus session
                        Session.sessionDestroy(); 
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {          
            nama.setText(UserModel.getNama());
            telepon.setText(UserModel.getNo_telp());
            alamat.setText(UserModel.getAlamat());
            email.setText(UserModel.getEmail()); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }   

    private void changeFxml(String file){
        mainPane.setOpacity(0);
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("/views/admin/profil/"+file+".fxml"));
            mainPane.getChildren().removeAll();
            mainPane.getChildren().setAll(fxml);

            FadeTransition ft = new FadeTransition();
            ft.setDuration(Duration.millis(500));
            ft.setNode(mainPane);
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.play();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
   
}
