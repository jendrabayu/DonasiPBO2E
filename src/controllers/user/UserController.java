package controllers.user;

import app.App;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import helpers.Dialog;
import helpers.MyHelper;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import helpers.Session;
import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.UserModel;

public class UserController implements Initializable {
    
    @FXML
    private Pane mainPane;
    
    @FXML
    private JFXTextField nama;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXTextField telepon;

    @FXML
    private JFXTextArea alamat;
    
    /* Password */
    @FXML
    private JFXPasswordField oldPassword;

    @FXML
    private JFXPasswordField newPassword;

    @FXML
    private JFXPasswordField reNewPassword;
    
    
    @FXML
    private Label last_update;

    @FXML
    void updatePassword(ActionEvent event) throws Exception {    
        if(oldPassword.getText().equals("") || newPassword.getText().equals("") || reNewPassword.getText().equals("")){
            Dialog.alertError("Semua field harus diisi!");
        }else{
            if(!MyHelper.getMd5(oldPassword.getText()).equals(UserModel.getPassword())){
                Dialog.alertError("Password lama Anda tidak valid!");
                oldPassword.setText("");
                newPassword.setText("");
                reNewPassword.setText("");
            }else{
                if (!newPassword.getText().equals(reNewPassword.getText())) {
                    Dialog.alertWarning("Password baru anda tidak sama");
                }else{
                     if (UserModel.updatePassword(newPassword.getText())) {
                        Dialog.alertError("Password Berhasil Diubah! Silahkan Login Ulang!");
                        Session.sessionDestroy(); 
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
        changeFxml("Profile");
    }
       
    @FXML
    void showEdit(ActionEvent event) {
        changeFxml("EditProfile");
        nama.requestFocus();
    }

    @FXML
    void updateProfile(ActionEvent event) throws Exception {
        if (nama.getText().equals("") || telepon.getText().equals("") || alamat.getText().equals("")) {
             Dialog.alertError("Semua field harus diisi!");
        }else{
            if (UserModel.update(nama.getText(), telepon.getText(), alamat.getText())) {
                Session.sessionDestroy();
                UserModel.CreateSession();
                Dialog.alertSuccess("Data Anda berhasil di update");
                changeFxml("Profile");
                initData();                                         
            }else{
                 Dialog.alertError("Data Anda Gagal di update");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initData();
    }    

    private void changeFxml(String name){
        mainPane.setOpacity(0);
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("/views/user/"+name+".fxml"));
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
    
    
    private void initData(){
         try {
            nama.setText(UserModel.getNama());
            telepon.setText(UserModel.getNo_telp());
            alamat.setText(UserModel.getAlamat());
            last_update.setText(String.format("last update at %s", UserModel.getUpdated_at()));
            email.setText(UserModel.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    
}





