/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.user;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import helpers.DBHelper;
import helpers.Dialog;
import helpers.MyHelper;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;
import models.UserModel;

/**
 *
 * @author ACER
 */
public class UangController implements Initializable{
    
    @FXML
    private AnchorPane mainPane;

    @FXML
    private JFXTextField no_rekening;

    @FXML
    private JFXComboBox<Rekening> rekeningComboBox;

    @FXML
    private JFXTextField jumlah;
    
    ObservableList<Rekening> rekening = FXCollections.observableArrayList();
    
    private static int selectedRekeningId = -1;

    @FXML
    void handleTransferConfirmation(ActionEvent event) {
        if(no_rekening.getText().equals("")
                || jumlah.getText().equals("")
                || selectedRekeningId == -1)
        {
            Dialog.alertWarning("Semua field tidak boleh kosong!");
        }else{
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("transfer Confirmation");
            alert.setHeaderText("Cek data anda");
            alert.setContentText("apakah data yang anda masukkan sudah benar?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                Map<String, String> params = new LinkedHashMap<>();
                params.put("`user_id`", String.format("'%s'",UserModel.getId()));
                params.put("`rekening_id`", String.format("'%s'", selectedRekeningId));
                params.put("`no_rekening`", String.format("'%s'", no_rekening.getText()));
                params.put("`jumlah`", String.format("'%s'", jumlah.getText()));        
                if (DBHelper.insert("uang", params)) {
                    Dialog.alertSuccess("Sukses");
                    changeFxml("donasi/Donasi");
                    
                }else{
                    Dialog.alertError("Gagal");
                }                         
            } else {
              
            }
        }
    }
    

    
    
    

    @FXML
    void showRekening(ActionEvent event) {
        changeFxml("donasi/form_uang");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
         selectedRekeningId = -1;
        
        //inputan hanya angka
        no_rekening.textProperty().addListener(new ChangeListener<String>() {
               @Override
               public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                       if (!newValue.matches("\\d{0,25}")) {
                               no_rekening.setText(oldValue);
                       }
               }
           });

        jumlah.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (!newValue.matches("\\d{0,11}")) {
                            jumlah.setText(oldValue);
                    }
            }
        });    
       
        //atur itrms di combo box
        ResultSet rs = DBHelper.selectAll("rekening");
        try {
            while (rs.next()) {                
                rekening.addAll(new Rekening(
                        rs.getInt("id"), 
                        rs.getString("nama_bank"), 
                        rs.getString("no_rekening")));
            }
        } catch (Exception e) {
            //
        }
        
        rekeningComboBox.setItems(rekening);
        
        rekeningComboBox.setConverter(new StringConverter<Rekening>(){
            @Override
            public String toString(Rekening object) {
               return object.getNama_bank()+" - "+object.getNo_rekening();
            }

            @Override
            public Rekening fromString(String string) {
                 return rekeningComboBox.getItems().stream().filter(ap -> 
                     ap.getNama_bank().equals(string)).findFirst().orElse(null);
            }
        
        });
        

        rekeningComboBox.valueProperty().addListener((obs, oldval, newval) -> {
            if (newval != null) {
               selectedRekeningId = newval.getId();
            }
        });
    }
    
    
    
       private void changeFxml(String name){
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("/views/user/"+name+".fxml"));
            mainPane.getChildren().removeAll();
            mainPane.getChildren().setAll(fxml);

        } catch (IOException e) {
            //
        }
    }
    

}


class Rekening{
    
    private int id;
    private String nama_bank;
    private String no_rekening;

    public Rekening(int id, String nama_bank, String no_rekening) {
        this.id = id;
        this.nama_bank = nama_bank;
        this.no_rekening = no_rekening;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama_bank() {
        return nama_bank;
    }

    public void setNama_bank(String nama_bank) {
        this.nama_bank = nama_bank;
    }

    public String getNo_rekening() {
        return no_rekening;
    }

    public void setNo_rekening(String no_rekening) {
        this.no_rekening = no_rekening;
    }
    
}
