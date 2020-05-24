package controllers.admin;

import com.jfoenix.controls.JFXComboBox;
import helpers.DBHelper;
import helpers.Dialog;
import helpers.MyHelper;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;


import models.UangModel;


public class UangController implements Initializable {

    private static int id;
    
    private static long jumlahUang;
    
   
    @FXML
    private Label totalUangLabel;
    
    @FXML
    private AnchorPane mainPane;
    
    @FXML
    private JFXComboBox<String> statusComboBox;
    
    @FXML
    private TableView<DataUang> tabelUangMasuk;

    @FXML
    private TableColumn<DataUang, Number> col_no;

    @FXML
    private TableColumn<DataUang, String> col_date;

    @FXML
    private TableColumn<DataUang, String> col_donatur_name;

    @FXML
    private TableColumn<DataUang, String> col_transfer_from;

    @FXML
    private TableColumn<DataUang, String> col_transfer_to;

    @FXML
    private TableColumn<DataUang, String> col_jumlah;

    @FXML
    private TableColumn<DataUang, String> col_status;
    
    ObservableList<DataUang> listUangMasuk = FXCollections.observableArrayList();
    
    ObservableList<String> listStatus = FXCollections.observableArrayList("DIPROSES","SELESAI","GAGAL");

    
    private void initTableUangMasuk(){
        ResultSet rs = DBHelper.query(
        
        "SELECT * "
                + "FROM users "
                + "JOIN uang ON users.id = uang.user_id "
                + "JOIN rekening ON uang.rekening_id = rekening.id "
                + "WHERE status = 'DIPROSES'"        
        );
        
        try {
            int no = 0;
            while (rs.next()) {                
                listUangMasuk.add(new DataUang(
                        rs.getInt("uang.id"),
                        1+no++, 
                        rs.getString("uang.created_at"), 
                        rs.getString("users.nama"), 
                        rs.getString("uang.no_rekening"), 
                        rs.getString("rekening.nama_bank")+" - "+rs.getString("rekening.no_rekening"), 
                        MyHelper.rupiahFormat(rs.getString("uang.jumlah")), 
                        rs.getString("uang.jumlah"),
                        rs.getString("uang.status")) );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        col_no.setCellValueFactory(cellData -> cellData.getValue().getNo());
        col_date.setCellValueFactory(cellData -> cellData.getValue().getCreatedAt());
        col_donatur_name.setCellValueFactory(cellData -> cellData.getValue().getDonaturName());
        col_transfer_from.setCellValueFactory(cellData -> cellData.getValue().getRekeningFrom());
        col_transfer_to.setCellValueFactory(cellData -> cellData.getValue().getRekeningTo());
        col_jumlah.setCellValueFactory(cellData -> cellData.getValue().getJumlah());
        col_status.setCellValueFactory(cellData -> cellData.getValue().getStatus());
        tabelUangMasuk.setItems(listUangMasuk);
    }

   
    
    @FXML
    void showUbahStatus(ActionEvent event) {
        UangController.id = -1;
        int selectedIndex = tabelUangMasuk.getSelectionModel().getSelectedIndex();
        System.out.println(selectedIndex);
        if(selectedIndex >= 0){
            DataUang uang = tabelUangMasuk.getItems().get(selectedIndex);

            UangController.id = uang.getId().getValue();
          
            UangController.jumlahUang = Long.parseLong(uang.getJumlahNumber().getValue());

            changeFxml("EditStatus");
        }else{
            Dialog.alertWarning("Mohon klik row yang dipilih");
        } 
    }

    @FXML
    void handleUbahStatus(ActionEvent event) {
        String status = statusComboBox.getSelectionModel().getSelectedItem();
        
        if(status.equals("SELESAI")){
            if (UangModel.updateStatus(status, UangController.id, UangController.jumlahUang + UangModel.getTotal()) == 1) {
                Dialog.alertSuccess(String.format("Berhasil menambahkan uang sebannyak Rp.%s"
                        + "Total uang : Rp.%s", UangController.jumlahUang, UangModel.getTotal()));
                changeFxml("Uang");
                UangController.jumlahUang = 0;
            }else{
                Dialog.alertError("Terjadi Keslahan...");
                changeFxml("Uang");
                UangController.jumlahUang = 0;
            }
        
        }else if (status.equals("GAGAL")) {
            if (UangModel.updateStatus(status, UangController.id, UangController.jumlahUang + UangModel.getTotal()) == 1) {
                Dialog.alertSuccess("Status berhasil di update");
                changeFxml("Uang");
                UangController.jumlahUang = 0;
            }else{
                Dialog.alertError("Terjadi Keslahan...");
                changeFxml("Uang");
                UangController.jumlahUang = 0;
            }
        }
    }

    @FXML
    void showUang(ActionEvent event) {
        changeFxml("Uang");
        UangController.id = -1;
        UangController.jumlahUang = 0;
    }
    
    
   
    private void changeFxml(String file){
        mainPane.setOpacity(0);
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("/views/admin/uang/"+file+".fxml"));
            mainPane.getChildren().removeAll();
            mainPane.getChildren().setAll(fxml);

            FadeTransition ft = new FadeTransition();
            ft.setDuration(Duration.millis(500));
            ft.setNode(mainPane);
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.play();

        } catch (IOException e) {
          e.printStackTrace();
        }
    }
      
      
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            statusComboBox.setItems(listStatus);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        try {
            initTableUangMasuk();
            totalUangLabel.setText(MyHelper.rupiahFormat(Long.toString(UangModel.getTotal())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    
}


class DataUang {
    
    IntegerProperty no, id;
    StringProperty
            createdAt,
            donaturName,
            rekeningFrom,
            rekeningTo,
            jumlah,
            jumlahNumber,
            status;

    public DataUang(int id, int no,  String createdAt, String donaturName, String rekeningFrom, String rekeningTo, String jumlah, String jumlahNumber,  String status) {
        this.id =  new SimpleIntegerProperty(id);
        this.no =  new SimpleIntegerProperty(no);
        this.createdAt =  new SimpleStringProperty(createdAt);
        this.donaturName =  new SimpleStringProperty(donaturName);
        this.rekeningFrom =  new SimpleStringProperty(rekeningFrom);
        this.rekeningTo =  new SimpleStringProperty(rekeningTo);
        this.jumlah =  new SimpleStringProperty(jumlah);
        this.jumlahNumber =  new SimpleStringProperty(jumlahNumber);
        this.status =  new SimpleStringProperty(status);
    }
    
    public IntegerProperty getId() {
        return id;
    }

    public void setId(IntegerProperty id) {
        this.id = id;
    }


    public IntegerProperty getNo() {
        return no;
    }

    public void setNo(IntegerProperty no) {
        this.no = no;
    }

    public StringProperty getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(StringProperty createdAt) {
        this.createdAt = createdAt;
    }

    public StringProperty getDonaturName() {
        return donaturName;
    }

    public void setDonaturName(StringProperty donaturName) {
        this.donaturName = donaturName;
    }

    public StringProperty getRekeningFrom() {
        return rekeningFrom;
    }

    public void setRekeningFrom(StringProperty rekeningFrom) {
        this.rekeningFrom = rekeningFrom;
    }

    public StringProperty getRekeningTo() {
        return rekeningTo;
    }

    public void setRekeningTo(StringProperty rekeningTo) {
        this.rekeningTo = rekeningTo;
    }

    public StringProperty getJumlah() {
        return jumlah;
    }

    public void setJumlah(StringProperty jumlah) {
        this.jumlahNumber = jumlah;
    }
    
        public StringProperty getJumlahNumber() {
        return jumlahNumber;
    }

    public void setJumlahNumber(StringProperty jumlah) {
        this.jumlahNumber = jumlahNumber;
    }


    public StringProperty getStatus() {
        return status;
    }

    public void setStatus(StringProperty status) {
        this.status = status;
    }
    
}



