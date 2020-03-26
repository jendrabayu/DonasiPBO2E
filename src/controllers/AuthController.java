package controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.User;

public class AuthController implements Initializable {
    
    Parent fxml;
    
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
            alertWarning("Semua filed tidak boleh kosong!");
        }else{
            if (!password.getText().equals(repassword.getText())) {
                alertWarning("Password harus sama!");
            }else{
                User.addNewUser(name.getText(), email.getText(), getMd5(password.getText()));
                alertSuccess("Registrasi Berhasil! Silahkan Login :)");
                changeFxml("Login");
            } 
        }     
    }

    @FXML
    private void masukAction(ActionEvent event) throws IOException {
        if (email.getText().equals("") || password.getText().toString().equals("")) {
            alertWarning("Email dan Password tidak boleh kosong!");
        }else{                 
            if (User.getLoginStatus(email.getText(), getMd5(password.getText())) == 1) {
                if (User.getRole(email.getText(), getMd5(password.getText())).equals("ADMIN")) {
                    loadFxml("admin");
                }else{
                    loadFxml("user");
                }
            }else{
                alertError("Email atau Password tidak valid!");
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
    

    private String getMd5(String text){
        String md5 = "";
         try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(text.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            md5 = sb.toString();
        } 
        catch (NoSuchAlgorithmException e) 
        {
            e.printStackTrace();
        }
        
        return  md5;
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
   
    }    
    
    
    private void changeFxml(String file){
        try {
            fxml = FXMLLoader.load(getClass().getResource("/views/"+file+".fxml"));
            mainPane.getChildren().removeAll();
            mainPane.getChildren().setAll(fxml);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void loadFxml(String role) throws IOException{
        mainPane.getScene().getWindow().hide();
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/views/"+role+"/Main.fxml")); 
        Scene scene = new Scene(root);
        stage.setScene(scene);
//        stage.setResizable(false);
        stage.show();
}
    
            
    private void alertWarning(String pesan){
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Peringatan");
        alert.setHeaderText(null);
        alert.setContentText(pesan);
        alert.showAndWait();
    }
    
     private void alertError(String pesan){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(pesan);
        alert.showAndWait();
    }
     
     private void alertSuccess(String pesan){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Berhasil");
        alert.setHeaderText(null);
        alert.setContentText(pesan);
        alert.showAndWait();
     }

}
