package controllers.admin;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import helpers.MyHelper;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import javafx.util.StringConverter;
import models.PenerimaModel;


public class PenyaluranUangController implements Initializable {
    
    public static long totalUang;
    
    @FXML
    private Label dateTodayLabel;

    
    @FXML
    private AnchorPane mainPane;
    
    @FXML
    private JFXComboBox<PenerimaModel> penerimaComboBox;

    @FXML
    private JFXTextField jumlahUangField;

  
    @FXML
    private Label totalUangLabel;
    
    ObservableList<PenerimaModel> dataPenerima;

    @FXML
    void addPenyaluranUang(ActionEvent event) {
        changeFxml("TambahPenyaluranUang");
    }
    
    @FXML
    void showPenyaluranUang(ActionEvent event) {
        changeFxml("PenyaluranUang");
    }

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            dateTodayLabel.setText(String.format("Penyaluran hari ini (%s)", MyHelper.getDateNow()));
        } catch (Exception e) {
        }
        
        try {
            dataPenerima =  FXCollections.observableArrayList(PenerimaModel.getAll());
        } catch (SQLException ex) {
            Logger.getLogger(PenyaluranUangController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            totalUangLabel.setText(MyHelper.rupiahFormat(Long.toString(PenyaluranUangController.totalUang)));
            
            penerimaComboBox.setItems(dataPenerima);
            penerimaComboBox.setConverter(new StringConverter<PenerimaModel>() {
                @Override
                public String toString(PenerimaModel object) {
                   return String.format("%s - %s - %s - %s", 
                           object.getId(),
                           object.getNama(),
                           object.getEmail(),
                           object.getNo_telp());
                }

                @Override
                public PenerimaModel fromString(String string) {
                    return penerimaComboBox.getItems().stream().filter(ap -> 
                     ap.getNama().equals(string)).findFirst().orElse(null);
                }
            });
            
            jumlahUangField.textProperty().addListener(new ChangeListener<String>(){
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                   if (!newValue.matches("\\d{0,12}")) {
                        jumlahUangField.setText(oldValue);
                    }
                }
                
            });
  
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    
    
    
    private void changeFxml(String file){
        mainPane.setOpacity(0);
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("/views/admin/uang/"+file+".fxml"));
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


