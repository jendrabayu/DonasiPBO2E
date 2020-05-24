package controllers.admin;

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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import app.App;
import java.util.Optional;
import javafx.animation.FadeTransition;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuButton;
import javafx.util.Duration;
import models.UserModel;
import models.UangModel;


public class MainController implements Initializable {

    @FXML
    private AnchorPane mainPane;
     
    @FXML
    public Label menuTitle;

    @FXML
    private MenuButton myName;

    @FXML
    void profile(ActionEvent event) {
        menuTitle.setText("Profil");
        changeFxml("profil/Profil");
    }
    
    @FXML
    void dashboard(ActionEvent event) {
        menuTitle.setText("Dashboard");
        changeFxml("Dashboard");
    }
    
    @FXML
    void changePassword(ActionEvent event) {
        menuTitle.setText("Ganti Password");
        changeFxml("profil/GantiPassword");
    }

    @FXML
    void donatur(ActionEvent event) {
        menuTitle.setText("Donatur");
        changeFxml("donatur/Donatur");
    }
    
    @FXML
    void penerima(ActionEvent event) {
        menuTitle.setText("Penerima Donasi");
        changeFxml("penerima/Penerima");
    }
    
    @FXML
    void makanan(ActionEvent event) {
        menuTitle.setText("Makanan");
        changeFxml("makanan/Makanan");
    }

    
    @FXML
    void uang(ActionEvent event) {
        menuTitle.setText("Uang");
        changeFxml("uang/Uang");
    }
    
    
    @FXML
    void penyaluranUang(ActionEvent event) {
        menuTitle.setText("Penyaluran Uang");
        changeFxml("uang/PenyaluranUang");
    }
    
    @FXML
    void penyaluranMakanan(ActionEvent event) {
        menuTitle.setText("Penyaluran Makanan");
        //
    }
    
    @FXML
    void rekening(ActionEvent event) {
        menuTitle.setText("Rekening");
        changeFxml("rekening/Rekening");
    }
    
    
    @FXML
    void logout(ActionEvent event) throws Exception {  
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi");
        alert.setHeaderText(null);
        alert.setContentText("Anda yakin ingin keluar?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Session.sessionDestroy(); 
            Stage stage = (Stage) mainPane.getScene().getWindow();
            stage.close();      
            App app = new App();
            app.start(stage);
        }
    }
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PenyaluranUangController.totalUang = UangModel.getTotal();
        
        if (Session.cekSession() == 1){  
            myName.setText(UserModel.getNama());
        }
        
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("/views/admin/Dashboard.fxml"));
            mainPane.getChildren().removeAll();
            mainPane.getChildren().setAll(fxml);
        } catch (IOException e) {
           e.printStackTrace();
        }
    }

    private void changeFxml(String file){
        mainPane.setOpacity(0);
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("/views/admin/"+file+".fxml"));
            mainPane.getChildren().removeAll();
            mainPane.getChildren().setAll(fxml);

            FadeTransition ft = new FadeTransition();
            ft.setDuration(Duration.millis(600));
            ft.setNode(mainPane);
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.play();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
