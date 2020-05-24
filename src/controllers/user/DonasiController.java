package controllers.user;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import helpers.DBHelper;
import helpers.Dialog;
import helpers.MyHelper;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
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
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.MakananModel;
import models.RekeningModel;

import models.UserModel;


public class DonasiController implements Initializable {

    @FXML
    private AnchorPane mainPane;
    
    
    @FXML
    private HBox hboxContainer;


    @FXML
    private JFXTextField nama_makanan;

    @FXML
    private JFXDatePicker expired_date;

    @FXML
    private JFXTimePicker expired_time;

    @FXML
    private JFXTextField jumlah_makanan;

    @FXML
    private JFXTextArea keterangan;

    @FXML
    private JFXTextField nama;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXTextField no_telp;

    @FXML
    private JFXTextArea alamat;

    
    
    
    //Makanan
    @FXML
    private TableView<MakananModel> tabel_makanan;

    @FXML
    private TableColumn<MakananModel, Integer> col_mkn_no;

    @FXML
    private TableColumn<MakananModel, String> col_mkn_date;

    @FXML
    private TableColumn<MakananModel, String> col_mkn_nama;

    @FXML
    private TableColumn<MakananModel, String> col_mkn_jumlah;

    @FXML
    private TableColumn<MakananModel, String> col_mkn_expdate;

    @FXML
    private TableColumn<MakananModel, String> col_mkn_status;

    @FXML
    private TableColumn<MakananModel, String> col_mkn_keterangan;
    
    private ObservableList<MakananModel> listMakanan;
    
    
    //uang
    @FXML
    private TableView<Uang> tabel_uang;

    @FXML
    private TableColumn<Uang, Number> col_uang_no;

    @FXML
    private TableColumn<Uang, String> col_uang_date;

    @FXML
    private TableColumn<Uang, String> col_uang_rekening_from;

    @FXML
    private TableColumn<Uang, String> col_uang_rekening_to;

    @FXML
    private TableColumn<Uang, String> col_uang_jumlah;

    @FXML
    private TableColumn<Uang, String> col_uang_status;
    
    private ObservableList<Uang> listUang;
    
    
    private  void  initTableViewUang(){
        listUang = FXCollections.observableArrayList();
         

        ResultSet rs = DBHelper.selectAll("uang", 
         String.format("user_id = '%s'", UserModel.getId()), 
         "rekening", 
         "rekening_id");
        
        try {
            int no = 0;
            while (rs.next()) {                    
                listUang.add(new Uang(
                        1+no++, 
                        rs.getString("uang.created_at"), 
                        rs.getString("uang.no_rekening"), 
                        rs.getString("rekening.nama_bank")+" - "+rs.getString("rekening.no_rekening"), 
                        MyHelper.rupiahFormat(rs.getString("uang.jumlah")), 
                        rs.getString("uang.status")
                ));   
            }
            
            col_uang_no.setCellValueFactory(cellData -> cellData.getValue().getNo());
            col_uang_date.setCellValueFactory(cellData -> cellData.getValue().getCreatedAt());
            col_uang_rekening_from.setCellValueFactory(cellData -> cellData.getValue().getRekeningFrom());
            col_uang_rekening_to.setCellValueFactory(cellData -> cellData.getValue().getRekeningTo());
            col_uang_jumlah.setCellValueFactory(cellData -> cellData.getValue().getJumlah());
            col_uang_status.setCellValueFactory(cellData -> cellData.getValue().getStatus());
            tabel_uang.setItems(listUang);
       
        
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
       
       
        
                
     
     
    }

    
    private void initTableViewMakanan(){

        
        listMakanan = FXCollections.observableArrayList();
        
        ArrayList<MakananModel> makanans = new ArrayList<MakananModel>();
        makanans = MakananModel.get(UserModel.getId());
        for(MakananModel makanan : makanans){
            listMakanan.add(makanan);
        }
        
        try {
            col_mkn_no.setCellValueFactory(new PropertyValueFactory<>("id"));
            col_mkn_date.setCellValueFactory(new PropertyValueFactory<>("created_at"));
            col_mkn_nama.setCellValueFactory(new PropertyValueFactory<>("nama"));
            col_mkn_jumlah.setCellValueFactory(new PropertyValueFactory<>("jumlah_awal"));
            col_mkn_expdate.setCellValueFactory(new PropertyValueFactory<>("expired_date"));
            col_mkn_status.setCellValueFactory(new PropertyValueFactory<>("status"));
            col_mkn_keterangan.setCellValueFactory(new PropertyValueFactory<>("keterangan"));
            tabel_makanan.setItems(listMakanan);
        } catch (Exception e) {
            
        }
    }
    
    
    @FXML
    void addMakanan(ActionEvent event) {
        if (nama_makanan.getText().equals("") || 
                jumlah_makanan.getText().equals("") ) {
            Dialog.alertWarning("Semua field tidak boleh kosong kecuali keterangan");
        }else{
            
             //ubah waktu menjadi HH:mm:ss
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime localTime = LocalTime.parse(expired_time.getValue().toString());
            String dateTime = expired_date.getValue().toString()+" "+localTime.format(formatter);
            if ( MakananModel.create(
                    nama_makanan.getText(), 
                    Integer.parseInt(jumlah_makanan.getText()), 
                    dateTime, 
                    keterangan.getText()) == 1
                    ) {
                    changeFxml("Sukses");
            }else{
                Dialog.alertError("Terjadi Kesalahan...");
            }   
        }   
    }
    
    @FXML
    void showDonasi(ActionEvent event) {
        changeFxml("donasi/Donasi");
    }

    
    @FXML
    void showDonasiMakanan(ActionEvent event) {
        changeFxml("donasi/form_makanan");
                

    }
    
     @FXML
    void showDonasiUang(ActionEvent event) {
        changeFxml("donasi/form_uang");
    }
    
    @FXML
    void showKonfirmasiTransfer(ActionEvent event) {
        changeFxml("donasi/form_konfirmasi_transfer");
    }
    
    @FXML
    void showBeranda(ActionEvent event) {
        changeFxml("Beranda");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
       
        
        try {
            
            ObservableList<RekeningModel> bankList = FXCollections.observableArrayList();
            ArrayList<RekeningModel> rekenings = RekeningModel.getAll();
            ListView rekeningListView = new ListView();
            rekeningListView.setPrefSize(500, 220);
            rekeningListView.setOrientation(Orientation.VERTICAL);
            


            for(RekeningModel rekening : rekenings){
                rekeningListView.getItems().add(String.format("%s - %s", rekening.getNamaBank(), rekening.getNoRekening()));
            }
            
        

            
            
            hboxContainer.getChildren().add(rekeningListView);
        } catch (Exception e) {
        }
        
  
        initTableViewMakanan();
        
        initTableViewUang();    
       
        try {                   
            nama.setText(UserModel.getNama());
            email.setText(UserModel.getEmail());
            no_telp.setText(UserModel.getNo_telp());
            alamat.setText(UserModel.getAlamat());
        } catch (Exception e) {
            
        }
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

class Uang {
    
    IntegerProperty no;
    StringProperty
            createdAt,
            rekeningFrom,
            rekeningTo,
            jumlah,
            status;

    public Uang(int no, String createdAt, String rekeningFrom, String rekeningTo, String jumlah, String status) {
        this.no =  new SimpleIntegerProperty(no);;
        this.createdAt =  new SimpleStringProperty(createdAt);
        this.rekeningFrom =  new SimpleStringProperty(rekeningFrom);
        this.rekeningTo =  new SimpleStringProperty(rekeningTo);
        this.jumlah =  new SimpleStringProperty(jumlah);
        this.status =  new SimpleStringProperty(status);
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
        this.jumlah = jumlah;
    }

    public StringProperty getStatus() {
        return status;
    }

    public void setStatus(StringProperty status) {
        this.status = status;
    }
    
    
    
}




