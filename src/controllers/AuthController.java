package controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

//my code
import models.User;
import helpers.Dialog;
import helpers.MyHelper;
import helpers.Session;

public class AuthController implements Initializable {
    
    @FXML
    private JFXTextField email;

    @FXML
    private JFXPasswordField password;
    
    @FXML
    private JFXPasswordField repassword;

    @FXML
    private JFXTextField name;
    
    @FXML
    private Pane mainPane;

    @FXML
    private void daftarAction(ActionEvent event) {
        if (name.getText().equals("") || email.getText().equals("") || password.getText().equals("") || repassword.getText().equals("")) {
            Dialog.alertWarning("Semua filed tidak boleh kosong!");
        }else{
            if (!password.getText().equals(repassword.getText())) {
                Dialog.alertWarning("Password harus sama!");
            }else{
                User.addNewUser(name.getText(), email.getText(), MyHelper.getMd5(password.getText()));
                Dialog.alertSuccess("Registrasi Berhasil! Silahkan Login :)");
                changeFxml("Login");
            } 
        }     
    }

    @FXML
    private void masukAction(ActionEvent event) throws IOException {
        //ROLE 1 = Admin
        //ROLE 0 = User

        if (email.getText().equals("") || password.getText().equals("")) {
            Dialog.alertWarning("Email dan Password tidak boleh kosong!");
        }else{
            if (MyHelper.checkConnection() == 1) {  
                if (User.getLoginStatus(email.getText(), MyHelper.getMd5(password.getText())) == 1) {
                    if (User.getRole(email.getText(), MyHelper.getMd5(password.getText())) == 1) {
                        loadFxml("admin");
                    }else{
                        loadFxml("user");
                    }
                }else{
                    Dialog.alertError("Email atau Password tidak valid!");
                }
            }
        }
        
        
        
      
    }

    @FXML
    private void openRegister(MouseEvent event) {
        changeFxml("Register");
    }
       
    @FXML
    private void openLogin(MouseEvent event) {
        changeFxml("Login");
    }
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
     
    }    
    
    private void changeFxml(String file){
        mainPane.setOpacity(0);
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("/views/"+file+".fxml"));
            mainPane.getChildren().removeAll();
            mainPane.getChildren().setAll(fxml);
            
            //efek transisi
            FadeTransition ft = new FadeTransition();
            ft.setDuration(Duration.millis(500));
            ft.setNode(mainPane);
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.play();
            
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void loadFxml(String role) throws IOException{
        mainPane.getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/views/"+role+"/Main.fxml")); 
        Scene scene = new Scene(root);
//        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

}
