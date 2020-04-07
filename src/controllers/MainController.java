/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


/**
 *
 * @author ACER
 */
public class MainController implements Initializable {
    
    @FXML
    private ImageView loading;


    private Parent fxml;
    
    
    @FXML
    private Pane mainPane;



    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            fxml = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
            mainPane.getChildren().removeAll();
            mainPane.getChildren().setAll(fxml);
            
            
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
 
    }
    
    
   

      
}
