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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import app.App;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuButton;
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
    void showGantiPassword(ActionEvent event) {
        title.setText("Ganti Password");
        changeFxml("GantiPassword");
    }

    
    @FXML
    void logout(ActionEvent event) throws Exception {
        
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi");
        alert.setHeaderText(null);
        alert.setContentText("Anda yakin ingin keluar?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            //hapus session
            Session.hapusSession(); 
            //tutup window & kembali ke login
            Stage stage = (Stage) mainPane.getScene().getWindow();
            stage.close();      
            App app = new App();
            app.start(stage);
        } else {
        // ... user chose CANCEL or closed the dialog
        }
    
    }
    
    
 

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        if (Session.CekSessionData() == 1){  
            labelNama.setText(User.getNama());
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

        } catch (IOException ex) {
            Logger.getLogger(controllers.MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
