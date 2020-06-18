package controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.UserModel;
import helpers.Dialog;
import helpers.MyHelper;
import java.sql.SQLException;
import javafx.scene.layout.BorderPane;

public class AuthController implements Initializable {
    
    @FXML
    private BorderPane mainPane;
      
    @FXML
    private JFXTextField nama;
    
    @FXML
    private JFXTextField email;

    @FXML
    private JFXTextField telepon;

    @FXML
    private JFXTextArea alamat;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXPasswordField repassword;


    @FXML
    void showLogin(MouseEvent event) {
        changeFxml("Login");
    }
    
    @FXML
    void showRegister(MouseEvent event) {
        changeFxml("Register");
    }
    
    @FXML
    void login(ActionEvent event) throws IOException, SQLException {
           
        Stage stage = (Stage) mainPane.getScene().getWindow();
        if (email.getText().equals("") || password.getText().equals("")) {
            Dialog.alertWarning("Email atau Password tidak boleh kosong!");
        }else{
            if (MyHelper.checkConnection() == 1) {
                int role = UserModel.getRole(email.getText(), password.getText());
                if (role != 0) {
                    if (role == 1) {        
                        UserModel.CreateSession();
                        stage.close();
                        loadFxml("admin",1200,700);
                    }else if(role == 2){     
                        UserModel.CreateSession();
                        stage.close();
                        loadFxml("user",1000,670);
                        
                    }
                }else{
                    Dialog.alertError("Email atau kata sandi tidak valid!");
                }
            }else{
                Dialog.alertError("Whoops, Database belum aktif!");
            }
        }
    }
    
    
    @FXML
    void register(ActionEvent event) {
        if (nama.getText().equals("") || email.getText().equals("") || telepon.getText().equals("") || alamat.getText().equals("") || password.getText().equals("") || repassword.getText().equals("")) {
            Dialog.alertWarning("Semua field harus diisi!");
        }else{
            if (!password.getText().equals(repassword.getText())) {
                Dialog.alertWarning("Password harus sama!");
            }else{
                if (UserModel.store(nama.getText(), email.getText(), telepon.getText() ,alamat.getText(), password.getText())) {
                    Dialog.alertSuccess("Registrasi Berhasil! Silahkan Login :)");
                    changeFxml("Login");
                }else{
                    Dialog.alertError("Registrasi Gagal :)");
                }       
            } 
        }     
    }

    
    private void changeFxml(String name){
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("/views/auth/"+name+".fxml"));
            mainPane.getChildren().removeAll();
            mainPane.getChildren().setAll(fxml); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
           
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
