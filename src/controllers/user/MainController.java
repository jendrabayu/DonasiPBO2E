/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.user;

import app.App;
import helpers.Session;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ACER
 */
public class MainController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    
    @FXML
    private AnchorPane mainPane;
    
    
    @FXML
    void logout(ActionEvent event) throws Exception {
        //hapus session
        Session.hapusSession();
        
        //tutup window & kembali ke login
        Stage stage = (Stage) mainPane.getScene().getWindow();
        stage.close();      
        App app = new App();
        app.start(stage);
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
