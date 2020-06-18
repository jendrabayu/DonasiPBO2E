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
import helpers.Dialog;
import java.io.IOException;
import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.util.StringConverter;
import models.RekeningModel;
import models.UangModel;


public class UangController implements Initializable{
    
    @FXML
    private AnchorPane mainPane;
    
    ObservableList<RekeningModel> rekening = FXCollections.observableArrayList();
    
    private static int selectedRekeningId;
    
    @FXML
    private JFXTextField fieldNamaBank;

    @FXML
    private JFXTextField fieldAtasNama;

    @FXML
    private JFXTextField fieldNoRekening;

    @FXML
    private JFXComboBox<RekeningModel> rekeningComboBox;

    @FXML
    private JFXTextField fieldJumlah;

    @FXML
    void index(ActionEvent event) {
        changeFxml("donasi/form_uang");
    }

    @FXML
    void store(ActionEvent event) {
        if (fieldNamaBank.getText().equals("") || fieldAtasNama.getText().equals("") 
                || fieldJumlah.getText().equals("") || selectedRekeningId == 0) {
            Dialog.alertWarning("Semua field tidak boleh kosong!");
        }else{
            if(UangModel.store(fieldNamaBank.getText(), fieldAtasNama.getText(), fieldNoRekening.getText(), Long.parseLong(fieldJumlah.getText()), selectedRekeningId)){
                Dialog.alertSuccess("Donasi anda akan diproses!");
                changeFxml("donasi/Donasi");
            }else{
                Dialog.alertError("Donasi anda gagal diproses, coba lagi!");
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectedRekeningId = 0;
        
        //set inputan hanya angka
        fieldNoRekening.textProperty().addListener(new ChangeListener<String>() {
               @Override
               public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                       if (!newValue.matches("\\d{0,25}")) {
                               fieldNoRekening.setText(oldValue);
                       }
               }
           });

        fieldJumlah.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (!newValue.matches("\\d{0,11}")) {
                            fieldJumlah.setText(oldValue);
                    }
            }
        });    
       
        //set data rekening ke combo box
        ArrayList<RekeningModel> rekenings = RekeningModel.getAll();
        for (RekeningModel rm : rekenings){
            rekening.addAll(new RekeningModel(
                    rm.getId(), 
                    rm.getNamaBank(), 
                    rm.getAtasNama(), 
                    rm.getNoRekening()));
        }

        rekeningComboBox.setItems(rekening);
        
        rekeningComboBox.setConverter(new StringConverter<RekeningModel>(){
            @Override
            public String toString(RekeningModel object) {
               return object.getNamaBank()+" - "+object.getNoRekening();
            }

            @Override
            public RekeningModel fromString(String string) {
                 return rekeningComboBox.getItems().stream().filter(ap -> 
                     ap.getNamaBank().equals(string)).findFirst().orElse(null);
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
            System.err.println(e);
        }
    }
    
}
