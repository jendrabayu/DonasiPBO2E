package controllers.user;

import app.App;
import helpers.Session;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class MainController implements Initializable {
    
    @FXML
    private AnchorPane mainPane;
    
    @FXML
    void logout(ActionEvent event) throws Exception {
        Session.sessionDestroy();       
        Stage stage = (Stage) mainPane.getScene().getWindow();
        stage.close();      
        App app = new App();
        app.start(stage);
    }
    
    @FXML
    void showBeranda(MouseEvent event) {   
            changeFxml("Beranda");
    }
    
    @FXML
    void showDonasi(MouseEvent event) {
        changeFxml("donasi/Donasi");
    }
      
    @FXML
    void showPenerima(MouseEvent event) {
        changeFxml("Penerima");
    }
    
    @FXML
    void showProfile(ActionEvent event) {
        changeFxml("Profile");
    }  
    
    @FXML
    void showUbahPassword(ActionEvent event) {
        changeFxml("UbahPassword");      
    }
    
      @FXML
    void showRekap(MouseEvent event) {   
            changeFxml("Rekap");
    }
    
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {    
        changeFxml("Beranda");
    }    
    
    
    

    private void changeFxml(String name){
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("/views/user/"+name+".fxml"));
            mainPane.getChildren().removeAll();
            mainPane.getChildren().setAll(fxml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
